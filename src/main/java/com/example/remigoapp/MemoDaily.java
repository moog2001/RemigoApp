package com.example.remigoapp;

import java.time.LocalDate;

public class MemoDaily extends MemoDate{
    protected int interval;

    public MemoDaily(String title, String text, int memoId, LocalDate createDate, LocalDate lastRemindDate,
                     LocalDate nextRemindDate, int interval) {
        super(title, text, memoId, createDate, lastRemindDate, nextRemindDate);
        this.interval = interval;
    }

    public MemoDaily(int interval) {
        this.interval = interval;
    }

    public MemoDaily(){}

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
