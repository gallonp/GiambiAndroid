package com.example.giambi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import com.example.giambi.util.Util;

import java.util.Calendar;

/**
 * DatePicker Dialog Fragment.
 */
public class DatePickerDialogFragment extends DialogFragment {

    /**
     * DateListener interface.
     */
    public interface DateListener {
        /**
         * set dates.
         * @param date1 start date
         * @param date2 end date
         */
        void setDate(String date1, String date2);

        /**
         * start report.
         * @param type report type
         */
        void startReport(String type);
    }

    /**
     * first picker.
     */
    private DatePicker datePicker1;
    /**
     * second picker.
     */
    private DatePicker datePicker2;
    /**
     * listener.
     */
    private  DateListener dateListener;
    /**
     * start date.
     */
    private Calendar startDate;
    /**
     * end date.
     */
    private Calendar endDate;

    /**
     * Constructor.
     * @param activity caller
     */
    public DatePickerDialogFragment(Activity activity) {
        try {
            dateListener = (DateListener) activity;
        } catch (ClassCastException e) {
            Log.e("", e.getMessage());
        }
    }

    /**
     * Create dialog.
     * @param savedInstanceState default arg
     * @return dialog
     */
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.date_pick, null);
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        builder.setTitle("Choose date...").setView(view);

        datePicker1 = (DatePicker) view.findViewById(R.id.datePicker1);
        datePicker2 = (DatePicker) view.findViewById(R.id.datePicker2);

        builder.setPositiveButton(R.string.dialog_OK,
                new DialogInterface.OnClickListener() {

                    /**
                     * This method will be invoked when a button in the
                     * dialog is clicked.
                     *
                     * @param dialog The dialog that received the click.
                     * @param which  The button that was clicked
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final int h = 23;
                        final int ms = 59;
                        startDate.set(datePicker1.getYear(), datePicker1.
                                getMonth(),
                                        datePicker1.getDayOfMonth(), 0, 0, 0);
                        endDate.set(datePicker2.getYear(),
                                datePicker2.getMonth(),
                                datePicker2.getDayOfMonth(), h, ms, ms);
                        dateListener.setDate(Util.dateToString(startDate),
                                Util.dateToString(endDate));
                        dateListener.startReport("Spending");
                    }
                });

        builder.setNegativeButton(R.string.dialog_cancel,
                new DialogInterface.OnClickListener() {

                    /**
                     * This method will be invoked when a button in the dialog
                     * is clicked.
                     *
                     * @param dialog The dialog that received the click.
                     * @param which  The button that was clicked
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        //do something when cancelled
                    }
                }
        );

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
