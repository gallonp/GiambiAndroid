package com.example.giambi.presenter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.giambi.GiambiHttpClient;
import com.example.giambi.activity.LoginActivity;
import com.example.giambi.model.ReportEntry;
import com.example.giambi.util.GetReportException;
import com.example.giambi.util.Util;
import com.example.giambi.view.ReportView;

/**
 * Presenter for account activity.
 * 
 * @author cwl
 */
public class ReportPresenter {

    /**
     * fields.
     */
    private static final String[] NORMAL_FIELDS = {"category", "amount", "startDate", "endDate"};
    /**
     * report view.
     */
    private final ReportView v;
    /**
     * login account.
     */
    private final String loginAccount;
    /**
     * account number.
     */
    private final String accountNumber;
    /**
     * report type.
     */
    private final String reportType;
    /**
     * start date.
     */
    private final String startDate;
    /**
     * end date.
     */
    private final String endDate;
    /**
     * list data.
     */
    private List<ReportEntry> listData = new LinkedList<ReportEntry>();
    /**
     * Listener for listView item click.
     */
    private OnItemClickListener onListItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            // TODO
        }
    };

    /**
     * Listener for Menu Item click.
     */
    private OnMenuItemClickListener onMenuItemClickListener = new OnMenuItemClickListener() {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String itemTitle = item.getTitle().toString();
            if (itemTitle.equals("Refresh")) {
                v.flushList();
                Log.i("MenuItem", "1");
            } else if (itemTitle.equals("Search")) {
                Log.i("MenuItem", "2");
            } else if (itemTitle.equals("Log Out")) {
                Log.i("MenuItem", "3");
            } else {
                Log.i("MenuItem", "Unknown");
            }
            return false;
        }

    };

    /**
     * Constructor.
     * 
     * @param view
     *            view
     * @param loginAccount1
     *            login account
     * @param accountNumber1
     *            account number
     * @param reportType1
     *            report type
     * @param startDate1
     *            start date
     * @param endDate1
     *            end date
     */
    public ReportPresenter(ReportView view, String loginAccount1,
            String accountNumber1, String reportType1, String startDate1,
            String endDate1) {
        this.v = view;
        this.loginAccount = loginAccount1;
        this.accountNumber = accountNumber1;
        this.reportType = reportType1;
        this.startDate = startDate1;
        this.endDate = endDate1;
        // TODO get report

        v.addOnListItemClick(this.onListItemClickListener);
        Log.v("AccountPresenter", "Listeners set up complete.");
    }

    /**
     * on menu item click listener.
     * 
     * @return listener
     */
    public OnMenuItemClickListener getOnMenuItemClickListener() {
        return this.onMenuItemClickListener;
    }

    /**
     * request report.
     * 
     * @return report
     * @throws GetReportException
     *             error getting report
     */
    @SuppressWarnings("unchecked")
    private int requestReport() throws GetReportException {
        final int error2 = -2;

        String encodedLoginAcc = Util.encodeString(loginAccount);
        String encodedAccNumber = Util.encodeString(accountNumber);
        String encodedStartDate = Util.encodeString(startDate);
        String encodedEndDate = Util.encodeString(endDate);

        HttpPost request = new HttpPost("http://" + Util.LOCALHOST
                + "/getreport");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("userAccount", encodedLoginAcc);
        jsonObj.put("accountNumber", encodedAccNumber);
        jsonObj.put("startDate", encodedStartDate);
        jsonObj.put("endDate", encodedEndDate);

        request.setEntity(Util.jsonToEntity(jsonObj));

        HttpResponse response = GiambiHttpClient.getResponse(request);
        String content;

        try {
            content = Util.HttpContentReader(response.getEntity().getContent());
            System.out.println(content);
        } catch (IllegalStateException e) {
            Log.e("IllegalStateException", e.getMessage());
            return -1;
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
            return -1;
        }
        if (content == null) {
            throw new GetReportException("Server return no content.");
        }
        if (content.equals("invalid cookie")) {
            return error2;
        }

        JSONParser jsonParser = new JSONParser();
        try {
            System.out.println(content);
            jsonObj = (JSONObject) jsonParser.parse(content);
        } catch (ParseException e) {
            Log.i("onJSONArrayCreate", "Error on casting");
            return -1;
        }

        // Add entries to list
        listData.clear();
        if (content.equals("No accounts.")) {
            return 0;
        }
        if (jsonObj.size() != 0) {

            Map<String, String> reportInfo;
            String category;
            String amount;
            JSONArray jsonArr = (JSONArray) jsonObj.get("Data");

            for (Object aJsonArr : jsonArr) {
                reportInfo = (Map<String, String>) aJsonArr;
                System.out.println(reportInfo);
                category = reportInfo.keySet().toArray()[0].toString();
                try {
                    amount = new BigDecimal(reportInfo.get(category)).setScale(
                            2, BigDecimal.ROUND_HALF_EVEN).toString();
                } catch (NumberFormatException e) {
                    Log.e("onReportProcess", e.getMessage());
                    return -1;
                }

                ReportEntry entry = new ReportEntry(jsonArr.indexOf(aJsonArr),
                        category, amount);

                listData.add(entry);
            }
            return 0;
        }
        return -1;
    }

    /**
     * get report.
     * 
     * @return list of report
     */
    public final List<ReportEntry> getReport() {
        final int error2 = -2;
        try {
            int result = this.requestReport();
            if (result == error2) {
                Intent intent = new Intent();
                intent.setClass((Context) v, LoginActivity.class);
                ((Context) v).startActivity(intent);
                ((Activity) v).finish();
            } else if (result == -1) {
                Log.e("onGetData", "Exception occurs.");
                ((Activity) v).finish();
            } else {
                return this.listData;
            }
        } catch (GetReportException e) {
            Log.e("onGetData", e.getMessage());
            return null;
        }
        return null;
    }

}
