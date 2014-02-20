/**
 * Interface for account activity view.
 */
package com.example.giambi.view;

import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author cwl
 */
public interface AccountView {

    void addOnListItemClick(OnClickListener l);

    void addOnOptionsItemSelected(OnMenuItemClickListener listener);

}
