package com.example.giambi.activity;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giambi.R;
import com.example.giambi.model.ReportEntry;
import com.example.giambi.presenter.ReportPresenter;
import com.example.giambi.view.ReportView;

/**
 * report activity.
 */
public class ReportActivity extends NavigationDrawerActivity implements ReportView {

    /**
     * list view.
     */
    private ListView listView;
    /**
     * report presenter.
     */
    private ReportPresenter reportPresenter;
    /**
     * action bar.
     */
    private ActionBar actionBar;
    /**
     * list data.
     */
    private List<ReportEntry> listData = new LinkedList<ReportEntry>();
    /**
     * list adapter.
     */
    private MyAdapter adapter;
    /**
     * login account name.
     */
    private String loginAccName;
    /**
     * account number.
     */
    private String accountNumber;
    /**
     * report type.
     */
    private String reportType;
    /**
     * start date.
     */
    private String startDate;
    /**
     * end date.
     */
    private String endDate;
    /**
     * number format.
     */
    private final NumberFormat currencyFormat = NumberFormat
            .getCurrencyInstance(Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginAccName = getUsernameFromPreference();
        getBundleInfo();

        listView = (ListView) this.findViewById(R.id.account_list);
        actionBar = this.getActionBar();
        adapter = new MyAdapter(this);
        if (adapter == null){
            Log.d("", "adapter is null");
        }
        listView.setAdapter(adapter);
        currencyFormat.setMinimumFractionDigits(2);
        reportPresenter = new ReportPresenter(this, loginAccName,
                accountNumber, reportType, startDate, endDate);
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
    public final void flushList() {
        this.listData = reportPresenter.getReport();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reportactivity_menu_options, menu);
        final int num = 3;
        MenuItem[] menuItems = new MenuItem[num];
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

    @Override
    protected void setupView() {
        setContentView(R.layout.account_page);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.account_page_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_bar_account);
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

    /**
     * get bundle info.
     */
    private void getBundleInfo() {
        Bundle b = this.getIntent().getExtras();
        if (b.getString("AccountNumber") == null
                || b.getString("ReportType") == null
                || b.getString("StartDate") == null
                || b.getString("EndDate") == null) {
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
        /**
         * category.
         */
        private TextView category;
        /**
         * another textview.
         */
        private TextView textView2;
        /**
         * balance.
         */
        private TextView balance;
        /**
         * date.
         */
        private TextView date;
    }

    /**
     * Adapter for ListView.
     * 
     * @author cwl
     */
    public class MyAdapter extends BaseAdapter {

        /**
         * inflater.
         */
        private final LayoutInflater mInflater;

        /**
         * construcot.
         * 
         * @param context
         *            context
         */
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public final int getCount() {
            return listData.size() + 1;
        }

        @Override
        public final Object getItem(int arg0) {
            if (arg0 != 0) {
                return listData.get(arg0 - 1);
            }
            return null;
        }

        @Override
        public final long getItemId(int arg0) {
            if (arg0 != 0) {
                return listData.get(arg0 + 1).getId();
            }
            return 0;
        }

        @Override
        public final View getView(int position, View convertView,
                ViewGroup parent) {

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
                holder.category.setText(listData.get(position - 1)
                        .getCategory());
                holder.balance.setText(listData.get(position - 1).getAmount());
            }

            return convertView;
        }
    }

}
