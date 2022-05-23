/**
 * @author Ganbayar Sumiyakhuu
 * Date:5/18/2022
 *
 * Description of code:
 * This class is used for users writen Education. User will be reminded this on a set inteval days.
 * Interval days will be calculated seperatly
 * This is a child of MemoDaily class.
 * streak: number of times User successfully remembered Education
 * type: type of this class
 */
package com.example.remigoapp;

import java.time.LocalDate;

public class Education extends MemoDaily {
    private int streak;
    private static final int type = Constants.TYPE_EDUCATION;

    /**
     * This constructs Education using following parameters
     * @param title title the tile of this Education
     * @param text text the text of this Education
     * @param memoId memoId the memoId of this Education
     * @param createDate createDate the createDate of this Education
     * @param lastRemindDate lastRemindDate the lastRemindDate of this Education
     * @param nextRemindDate nextRemindDate the nextRemindDate of this Education
     * @param interval interval the interval of this Education
     * @param streak streak the streak of this Education
     */
    public Education(String title, String text, int memoId, LocalDate createDate, LocalDate lastRemindDate,
                     LocalDate nextRemindDate, int interval, int streak) {
        super(title, text, memoId, createDate, lastRemindDate, nextRemindDate, interval);
        this.streak = streak;
    }

    /**
     * This constructs Education using following parameters
     * @param streak streak the streak of this Education
     */
    public Education(int streak) {
        this.streak = streak;
    }

    /**
     * This constructs Education without parameters
     */
    public Education(){}

    /**
     * This method calculates
     * @return
     */
    public int calculateInterval(){
        return 0;
    }

    /**
     * Simple getter and setter methods for all the parameters.
     */
    public int getType(){ return type;}

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }
}
