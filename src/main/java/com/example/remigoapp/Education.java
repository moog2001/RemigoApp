package com.example.remigoapp;

import java.time.LocalDate;

public class Education extends MemoDaily{
    private int streak;
    private static final int type = Constants.TYPE_EDUCATION;

    public Education(String title, String text, int memoId, LocalDate createDate, LocalDate lastRemindDate,
                     LocalDate nextRemindDate, int interval, int streak) {
        super(title, text, memoId, createDate, lastRemindDate, nextRemindDate, interval);
        this.streak = streak;
    }

    public int getType(){ return type;}

    public Education(int streak) {
        this.streak = streak;
    }

    public Education(){}

    public int calculateInterval(){
        return 0;
    }

    public int updateStreak(){
        return 0;
    }
}
