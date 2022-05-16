package com.example.remigoapp;

import java.util.ArrayList;
import java.util.List;

public class User extends Person {
    protected String userName;
    protected String eMail;
    protected String password;
    protected int userId;
    private boolean isGuest;
    private List<Memo> memoList = new ArrayList<>();
    private List<MemoDate> memoDateList = new ArrayList<>();
    private List<MemoDaily> memoDailyList = new ArrayList<>();
    private List<Education> educationList = new ArrayList<>();


    public User(){
    }
    public User(String userName, String eMail, String password, int userId, boolean isGuest,
                String firstName, String lastName, int age, String gender) {
        this.userName = userName;
        this.eMail = eMail;
        this.password = password;
        this.userId = userId;
        this.isGuest = isGuest;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public User(String userName, String eMail, String password, int userId, boolean isGuest) {
        this.userName = userName;
        this.eMail = eMail;
        this.password = password;
        this.userId = userId;
        this.isGuest = isGuest;
    }

    public User(String userName, String eMail, String password, int userId, boolean isGuest,
                List<Memo> memoList, List<MemoDate> memoDateList) {
        this.userName = userName;
        this.eMail = eMail;
        this.password = password;
        this.userId = userId;
        this.isGuest = isGuest;
        this.memoList = memoList;
        this.memoDateList = memoDateList;
    }

    public List<MemoDaily> getMemoDailyList() {
        return memoDailyList;
    }

    public void setMemoDailyList(List<MemoDaily> memoDailyList) {
        this.memoDailyList = memoDailyList;
    }

    public List<Education> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<Education> educationList) {
        this.educationList = educationList;
    }

    public List<Memo> getMemoList() {
        return memoList;
    }


    public void setMemoList(List<Memo> memoList) {
        this.memoList = memoList;
    }

    public List<MemoDate> getMemoDateList() {
        return memoDateList;
    }

    public void setMemoDateList(List<MemoDate> memoDateList) {
        this.memoDateList = memoDateList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }
}
