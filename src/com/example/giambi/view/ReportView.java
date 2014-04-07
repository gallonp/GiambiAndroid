/**
 * Interface for report activity view.
 */
package com.example.giambi.view;

import android.widget.AdapterView.OnItemClickListener;

/**
 * @author cwl
 */
public interface ReportView {

    /**
     * flush list.
     */
    void flushList();

    /**
     * add on list item click listener.
     * 
     * @param onListItemClickListener
     *            onListItemClickListener
     */
    void addOnListItemClick(OnItemClickListener onListItemClickListener);

}
