package com.example.remigoapp;

import java.time.LocalDate;

public class MemoDate extends Memo {
    protected LocalDate lastRemindDate;
    protected LocalDate nextRemindDate;

    public MemoDate(String title, String text, int memoId, LocalDate createDate, LocalDate lastRemindDate, LocalDate nextRemindDate) {
        super(title, text, memoId, createDate);
        this.lastRemindDate = lastRemindDate;
        this.nextRemindDate = nextRemindDate;
    }

    public MemoDate(LocalDate lastRemindDate, LocalDate nextRemindDate) {
        this.lastRemindDate = lastRemindDate;
        this.nextRemindDate = nextRemindDate;
    }

    public MemoDate(){}

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
