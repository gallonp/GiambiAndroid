package com.example.giambi.presenter;

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
import com.example.giambi.util.GetReportException;
import com.example.giambi.util.Util;
import com.example.giambi.view.ReportView;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Presenter for account activity.
 *
 * @author cwl
 */
public class ReportPresenter {

    private static final String[] NORMAL_FIELDS = {"category",
            "amount", "startDate", "endDate"};
    private final ReportView v;
    private final String loginAccount;
    private final String accountNumber;
    private final String reportType;
    private List<Map<String, String>> listData = new LinkedList<Map<String,
            String>>();
    /**
     * Listener for listView item click
     */
    private OnItemClickListener onListItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            //TODO
        }
    };
    /**
     * Listener for Menu Item click
     */
    private OnMenuItemClickListener onMenuItemClickListener =
            new OnMenuItemClickListener() {

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
     */
    public ReportPresenter(ReportView view, String loginAccount,
                           String accountNumber, String reportType) {
        this.v = view;
        this.loginAccount = loginAccount;
        this.accountNumber = accountNumber;
        this.reportType = reportType;
        // TODO get report

        v.addOnListItemClick(this.onListItemClickListener);
        Log.v("AccountPresenter", "Listeners set up complete.");
    }

    public OnMenuItemClickListener getOnMenuItemClickListener() {
        return this.onMenuItemClickListener;
    }

    @SuppressWarnings("unchecked")
    private int requestReport() throws GetReportException {
        String encodedLoginAcc = Util.encodeString(loginAccount);
        String encodedAccNumber = Util.encodeString(accountNumber);

        HttpPost request = new HttpPost("http://10.0.2.2:8888/getreport");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("userAccount", encodedLoginAcc);
        jsonObj.put("accountNumber", encodedAccNumber);

        request.setEntity(Util.jsonToEntity(jsonObj));

        HttpResponse response = GiambiHttpClient.getResponse(request);
        String responseCookie = "";// response.getHeaders("Cookie")[0].getValue();
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
            return -2;
        }
//        JSONArray jsonArr;
        JSONParser jsonParser = new JSONParser();
        try {
            System.out.println(content);
//            jsonObj = (JSONObject) jsonParser.parse(content);
            jsonObj = (JSONObject) jsonParser.parse(content);
        } catch (ParseException e) {
            Log.i("onJSONArrayCreate", "Error on casting");
            return -1;
        }

        // Add accounts to bankAccounts list
        listData.clear();
        if (content.equals("No accounts."))
            return 0;
        if (jsonObj.size() != 0) {

            Map<String, String> reportInfo;
            String category;
            String amount;
            String startDate;
            String endDate;
            String date;
            Map<String, String> processedMap = new HashMap<String, String>(4);
            JSONArray jsonArr = (JSONArray) jsonObj.get("Data");

            for (Object aJsonArr : jsonArr) {
                reportInfo = (Map<String, String>) aJsonArr;
                category = "";
                amount = "";
                category = reportInfo.get(NORMAL_FIELDS[0]);
                try {
                    amount = new BigDecimal(reportInfo.get
                            (NORMAL_FIELDS[1]))
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
                } catch (NumberFormatException e) {
                    Log.e("onReportProcess", e.getMessage());
                    return -1;
                }
//                startDate = reportInfo.get(NORMAL_FIELDS[2]);
//                endDate = reportInfo.get(NORMAL_FIELDS[3]);
//                if (Util.stringToDate(startDate) == null
//                        || Util.stringToDate(endDate) == null) {
//                    return -1;
//                }
//                date = String.format("%s - %s", startDate, endDate);

                processedMap.clear();
                processedMap.put("Category", category);
                processedMap.put("Amount", amount);

//                processedMap.put("Date", date);
                listData.add(processedMap);
                for (int i = 0; i < listData.size(); ++i) {
                    System.out.println(listData.get(i).toString());
                }
            }
            return 0;
        }
        return -1;
    }

    public List<Map<String, String>> getReport() {
        try {
            int result = this.requestReport();
            for (int i = 0; i < listData.size(); ++i) {
                System.out.println(listData.get(i).toString());
            }
            if (result == -2) {
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
