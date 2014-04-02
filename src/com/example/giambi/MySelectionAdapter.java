package com.example.giambi;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author zhangjialiang A simple text ListView adapter.
 * @param <T>
 */
public class MySelectionAdapter<T> extends ArrayAdapter<T> {

    public List<T> data = new ArrayList<T>();
    private Context context;

    /**
     * Constructer to link necessary resources.
     * 
     * @param context
     *            application context
     * @param resource
     *            resource
     * @param data
     *            list data
     */
    public MySelectionAdapter(Context context, int resource, List<T> data) {
        super(context, resource, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.v("getItemId", Integer.toString(position));
        T obj = data.get(position);
        return obj.hashCode();
    }

    /**
     * @author zhangjialiang inner class to improve field search speed
     */
    static class ViewHolder {
        public TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.select_item_row, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) rowView
                    .findViewById(R.id.itemTextView);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        T obj = null;
        try {
            obj = data.get(position);
        } catch (IndexOutOfBoundsException e) {
            return rowView;
        }
        holder.textView.setText(obj.toString());

        return rowView;
    }

}
