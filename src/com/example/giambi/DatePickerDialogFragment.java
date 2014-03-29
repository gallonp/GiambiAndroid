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

public class DatePickerDialogFragment extends DialogFragment {

    public interface DateListener {
        void setDate(String date1, String date2);
        void startReport(String type);
    }

    private DatePicker datePicker1;
    private DatePicker datePicker2;
    private  DateListener dateListener;
    private Calendar startDate;
    private Calendar endDate;

    public DatePickerDialogFragment(Activity activity) {
        try {
            dateListener = (DateListener) activity;
        } catch (ClassCastException e) {
            Log.e("", e.getMessage());
        }
    }

    /**
     * Creates the dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

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
                     * This method will be invoked when a button in the dialog is clicked.
                     *
                     * @param dialog The dialog that received the click.
                     * @param which  The button that was clicked (e.g.
                     *               {@link android.content.DialogInterface#BUTTON1}) or the position
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startDate.set(datePicker1.getYear(), datePicker1.getMonth(),
                                        datePicker1.getDayOfMonth(), 0, 0, 0);
                        endDate.set(datePicker2.getYear(),
                                datePicker2.getMonth(),
                                datePicker2.getDayOfMonth(), 23, 59, 59);
                        dateListener.setDate(Util.dateToString(startDate),
                                Util.dateToString(endDate));
                        dateListener.startReport("Spending");
                    }
                });

        builder.setNegativeButton(R.string.dialog_cancel,
                new DialogInterface.OnClickListener() {

                    /**
                     * This method will be invoked when a button in the dialog is clicked.
                     *
                     * @param dialog The dialog that received the click.
                     * @param which  The button that was clicked (e.g.
                     *               {@link android.content.DialogInterface#BUTTON1}) or the position
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
