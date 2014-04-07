package com.example.giambi;

/**
 * DateListener interface.
 */
public interface DateListener {
    /**
     * set dates.
     * 
     * @param date1
     *            start date
     * @param date2
     *            end date
     */
    void setDate(String date1, String date2);

    /**
     * start report.
     * 
     * @param type
     *            report type
     */
    void startReport(String type);
}
