/**
 * @author Ganbayar Sumiyakhuu
 * Date:5/18/2022
 *
 * Description of code:
 * This class is used for users writen memo and parent of MemoDate class
 * title: Title of this memo
 * text: Text of this memo
 * memoId: ID of this memo
 * createDate: Creation date of this memo
 * type: type of this class
 */
package com.example.remigoapp;

import java.time.LocalDate;

public class Memo {
    protected String title;
    protected String text;
    protected int memoId;
    protected LocalDate createDate;
    private static final int type = Constants.TYPE_MEMO;

    /**
     * This constructs a Memo using following parameters
     * @param title title the tile of this Memo
     * @param text text the text of this Memo
     * @param memoId memoId the memoId of this Memo
     * @param createDate createDate the createDate of this memo
     */
    public Memo(String title, String text, int memoId, LocalDate createDate) {
        this.title = title;
        this.text = text;
        this.memoId = memoId;
        this.createDate = createDate;
    }

    /**
     * This constructs Memo without parameters
     */
    public Memo(){
    }

    /**
     * Deletes this memo
     * @param memo Memo of this Memo
     * @return Boolean to check the if memo deleted successfully
     */
    public boolean deleteMemo(Memo memo){
        memo = null;
        return true;
    }

    /**
     * Simple getter and setter methods for all the parameters.
     */
    public int getType(){ return type;}

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
