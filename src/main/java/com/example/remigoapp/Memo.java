package com.example.remigoapp;

import java.time.LocalDate;

public class Memo {
    protected String title;
    protected String text;
    protected int memoId;
    protected LocalDate createDate;
    private static final int TYPE = 1;

    public Memo(String title, String text, int memoId, LocalDate createDate) {
        this.title = title;
        this.text = text;
        this.memoId = memoId;
        this.createDate = createDate;
    }

    public Memo(){
    }
    //need local storage deleter
    public boolean deleteMemo(Memo memo){
        memo = null;
        return true;
    }
    public int getType(){ return TYPE;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMemoId() {
        return memoId;
    }

    public void setMemoId(int memoId) {
        this.memoId = memoId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
