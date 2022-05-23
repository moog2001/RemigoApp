/**
 * @author Ganbayar Sumiyakhuu
 * Date:5/18/2022
 *
 * Description of code:
 * This class is used for users writen memoDaily. User will be reminded this on a set inteval days.
 * This is a parent of Education class and child of MemoDate class.
 * interval: interval days of nextRemindDate
 * type: type of this class
 */
package com.example.remigoapp;

import java.time.LocalDate;

public class MemoDaily extends MemoDate{
    protected int interval;
    private static final int type = Constants.TYPE_MEMO_DAILY;

    /**
     * This constructs a MemoDaily using following parameters
     * @param title title the tile of this MemoDaily
     * @param text text the text of this MemoDaily
     * @param memoId memoId the memoId of this MemoDaily
     * @param createDate createDate the createDate of this MemoDaily
     * @param lastRemindDate lastRemindDate the lastRemindDate of this MemoDaily
     * @param nextRemindDate nextRemindDate the nextRemindDate of this MemoDaily
     * @param interval interval the interval of this MemoDaily
     */
    public MemoDaily(String title, String text, int memoId, LocalDate createDate, LocalDate lastRemindDate,
                     LocalDate nextRemindDate, int interval) {
        super(title, text, memoId, createDate, lastRemindDate, nextRemindDate);
        this.interval = interval;
    }

    /**
     * This constructs a MemoDaily without parameters
     */
    public MemoDaily(){}

    /**
     * this constructs a MemDaily using following parameter
     * @param interval interval the interval of this MemoDaily
     */

    public MemoDaily(int interval) {
        this.interval = interval;
    }

    public void resetInterval(){

    }

    /**
     * Simple getter and setter methods for all the parameters.
     */
    public int getType(){ return type;}

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }


}
