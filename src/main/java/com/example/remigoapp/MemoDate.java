/**
 * @author Ganbayar Sumiyakhuu
 * Date:5/18/2022
 *
 * Description of code:
 * This class is used for users writen memoDate.User will be reminded this one time on a set date.
 * After one successfull reminder this will be deleted.
 * This is a parent of MemoDaily class and child of Memo class.
 * lastRemindDate: last reminded date of this MemoDate
 * nextRemindDate: next remind date of this MemoDate
 * type: type of this class
 */
package com.example.remigoapp;

import java.time.LocalDate;

public class MemoDate extends Memo {
    protected LocalDate lastRemindDate;
    protected LocalDate nextRemindDate;
    private static final int type = Constants.TYPE_MEMO_DATE;

    /**
     * This constructs a MemoDate using following parameters
     * @param title title the tile of this MemoDate
     * @param text text the text of this MemoDate
     * @param memoId memoId the memoId of this MemoDate
     * @param createDate createDate the createDate of this MemoDate
     * @param lastRemindDate lastRemindDate the lastRemindDate of this MemoDate
     * @param nextRemindDate nextRemindDate the nextRemindDate of this MemoDate
     */
    public MemoDate(String title, String text, int memoId, LocalDate createDate, LocalDate lastRemindDate, LocalDate nextRemindDate) {
        super(title, text, memoId, createDate);
        this.lastRemindDate = lastRemindDate;
        this.nextRemindDate = nextRemindDate;
    }

    /**
     * This constructs a MemoDate using following parameters
     * @param lastRemindDate lastRemindDate the lastRemindDate of this MemoDate
     * @param nextRemindDate nextRemindDate the nextRemindDate of this MemoDate
     */
    public MemoDate(LocalDate lastRemindDate, LocalDate nextRemindDate) {
        this.lastRemindDate = lastRemindDate;
        this.nextRemindDate = nextRemindDate;
    }

    /**
     * This constructs a MemoDate without parameters
     */
    public MemoDate(){}

    /**
     * Simple getter and setter methods for all the parameters.
     */
    public int getType(){ return type;}

    public LocalDate getLastRemindDate() {
        return lastRemindDate;
    }

    public void setLastRemindDate(LocalDate lastRemindDate) {
        this.lastRemindDate = lastRemindDate;
    }

    public LocalDate getNextRemindDate() {
        return nextRemindDate;
    }

    public void setNextRemindDate(LocalDate nextRemindDate) {
        this.nextRemindDate = nextRemindDate;
    }
}
