package com.example.giambi.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.giambi.R;
import com.example.giambi.model.ReportEntry;
import com.example.giambi.presenter.ReportPresenter;
import com.example.giambi.view.ReportView;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ReportActivity extends Activity implements ReportView {

    private ListView listView;
    private ReportPresenter reportPresenter;
    private ActionBar actionBar;
    private List<ReportEntry> listData = new LinkedList<ReportEntry>();
    private MyAdapter adapter;
    private String loginAccName;
    private String accountNumber;
    private String reportType;
    private String startDate;
    private String endDate;
    private final NumberFormat currencyFormat = NumberFormat
            .getCurrencyInstance(Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page);
        loginAccName = getUsernameFromPreference();
        getBundleInfo();

        System.out.println("AccNum: " + accountNumber + " Type: " + reportType);
        System.out.println("Start Date: " + startDate + " End Date: " + endDate);
        listView = (ListView) this.findViewById(R.id.account_list);
        actionBar = this.getActionBar();
        adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
        currencyFormat.setMinimumFractionDigits(2);
        reportPresenter = new ReportPresenter(this, loginAccName,
                accountNumber, reportType, startDate,endDate);
        setupActionBar();
        flushList();

        Log.i(ACTIVITY_SERVICE, "Report Activity initialization complete.");
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void flushList() {
        this.listData = reportPresenter.getReport();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reportactivity_menu_options, menu);
        MenuItem[] menuItems = new MenuItem[3];
        menuItems[0] = menu.findItem(R.id.report_refresh);
        menuItems[1] = menu.findItem(R.id.report_search);
        menuItems[2] = menu.findItem(R.id.report_logout);
        for (MenuItem item : menuItems) {
            item.setOnMenuItemClickListener(reportPresenter
                    .getOnMenuItemClickListener());
        }
        return true;
    }

    @Override
    public void addOnListItemClick(OnItemClickListener l) {
        listView.setOnItemClickListener(l);
    }

    /**
     * Get username from preference.
     *
     * @return current username
     */
    private String getUsernameFromPreference() {
        SharedPreferences prefs = this.getSharedPreferences("com.example.app",
                Context.MODE_PRIVATE);
        return prefs.getString("USERNAME_GIAMBI", null);
    }

    private void getBundleInfo() {
        Bundle b = this.getIntent().getExtras();
        if (b.getString("AccountNumber") == null || b.getString("ReportType")
         == null || b.getString("StartDate") == null || b.getString
                ("EndDate") == null) {
            this.finish();
        } else {
            this.accountNumber = b.getString("AccountNumber");
            this.reportType = b.getString("ReportType");
            this.startDate = b.getString("StartDate");
            this.endDate = b.getString("EndDate");
        }
    }

    /**
     * @author cwl Holder for view in each entry of list.
     */
    private final class ViewHolder {
        TextView category;
        TextView textView2;
        TextView balance;
        TextView date;
    }

    /**
     * Adapter for ListView.
     *
     * @author cwl
     */
    public class MyAdapter extends BaseAdapter {

        private final LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return listData.size() + 1;
        }

        @Override
        public Object getItem(int arg0) {
            if (arg0 != 0) {
                return listData.get(arg0 - 1);
            }
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            if (arg0 != 0) {
                return listData.get(arg0 + 1).getId();
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();

                if (position == 0) {
                    convertView = mInflater.inflate(R.layout.vlist2, null);
                } else {
                    convertView = mInflater.inflate(R.layout.vlist, null);
                }
                holder.category = (TextView) convertView
                        .findViewById(R.id.bAccount_alias);
                holder.textView2 = (TextView) convertView
                        .findViewById(R.id.bAccount_bName);
                holder.balance = (TextView) convertView
                        .findViewById(R.id.bAccount_balance);
                holder.date = (TextView) convertView
                        .findViewById(R.id.bAccount_date);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == 0) {
                holder.category.setText("Report from: ");
                holder.textView2.setText("To: ");
                holder.balance.setText(startDate);
                holder.date.setText(endDate);
            } else {
                holder.category.setText(listData.get(position - 1).getCategory());
                holder.balance.setText(listData.get(position - 1).getAmount());
            }

            return convertView;
        }
    }

}
