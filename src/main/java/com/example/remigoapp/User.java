/**
 * @author Ganbayar Sumiyakhuu
 * Date:5/18/2022
 *
 * Description of code:
 * This class is a child of abstract Person class. This class is used for current user using the system.
 * userName: Username of this user.
 * eMail: Email of this user.
 * password: password of this user.
 * userId: ID of this user.
 * isGuest: Boolean to check if this user is a guest or not.
 * memoList: List that contains memo that this user wrote.
 * memoDateList: List that contains MemoDate, MemoDaily, Education that this user wrote.
 */
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

    /**
     * This constructs a User without parameters.
     */
    public User(){
    }

    /**
     * This constructs a User using following parameters.
     * @param userName userName the userName of this User
     * @param eMail eMail the eMail of this User
     * @param password password the password of thisUser
     * @param userId userID the userID of this User
     * @param isGuest isGuest the isGuest of this User
     * @param firstName firstName the firstName of this User
     * @param lastName lastName the lastName of this User
     * @param age age the age of this User
     * @param gender gender the gender of this User
     * @param memoList memoList the memoList of this User
     * @param memoDateList memoDateList the memoList of this User
     */
    public User(String userName, String eMail, String password, int userId, boolean isGuest,
                String firstName, String lastName, int age, String gender,List<Memo> memoList, List<MemoDate> memoDateList) {
        this.userName = userName;
        this.eMail = eMail;
        this.password = password;
        this.userId = userId;
        this.isGuest = isGuest;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.memoList = memoList;
        this.memoDateList = memoDateList;
        Variables.memoList = memoList;
        Variables.memoDateList = memoDateList;
    }

    /**
     * This constructs a User using following parameters.
     * @param userName userName the userName of this User
     * @param eMail eMail the eMail of this User
     * @param password password the password of thisUser
     * @param isGuest isGuest the isGuest of this User
     */
    public User(String userName, String eMail, String password, boolean isGuest) {
        this.userName = userName;
        this.eMail = eMail;
        this.password = password;
        this.isGuest = isGuest;
    }

    /**
     * This constructs a User using following parameters.
     * @param userName userName the userName of this User
     * @param eMail eMail the eMail of this User
     * @param password password the password of thisUser
     * @param userId userID the userID of this User
     * @param isGuest isGuest the isGuest of this User
     * @param firstName firstName the firstName of this User
     * @param lastName lastName the lastName of this User
     * @param age age the age of this User
     * @param gender gender the gender of this User
     */
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
        this.memoList = new ArrayList<Memo>();
        this.memoDateList = new ArrayList<MemoDate>();
    }

    /**
     * Simple getter and setter methods for all the parameters.
     */
    public List<Memo> getMemoList() {
        return memoList;
    }

    public void setMemoList(List<Memo> memoList) {
        this.memoList = memoList;
    }

    public List<MemoDate> getMemoDateList() {
        return memoDateList;
    }

    /**
     * returns only memo date list from memo list;
     * @author Moog
     * @return
     */
    public List<MemoDate> getMemoDateListSingle() {
        List<MemoDate> list = new ArrayList<>();
        memoDateList.forEach(memoDate -> {
            if(!(memoDate instanceof MemoDaily)){
                list.add(memoDate);
            }
        });
        return list;
    }

    /**
     * returns only memo daily list from memo list;
     * @author Moog
     * @return
     */
    public List<MemoDaily> getMemoDailyList() {
        List<MemoDaily> list = new ArrayList<>();
        memoDateList.forEach(memoDaily -> {
            if(!(memoDaily instanceof Education) && (memoDaily instanceof MemoDaily)){
                list.add((MemoDaily) memoDaily);
            }
        });
        return list;
    }

    /**
     * returns only education list from memo list;
     * @author Moog
     * @return
     */
    public List<Education> getEducation() {
        List<Education> list = new ArrayList<>();
        memoDateList.forEach(education -> {
            if((education instanceof Education)){
                list.add((Education) education);
            }
        });
        return list;
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
