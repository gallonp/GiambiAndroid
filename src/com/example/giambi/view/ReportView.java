/**
 * Interface for report activity view.
 */
package com.example.giambi.view;

import android.widget.AdapterView.OnItemClickListener;

/**
 * @author cwl
 */
public interface ReportView {

    void flushList();

    void addOnListItemClick(OnItemClickListener onListItemClickListener);

}
