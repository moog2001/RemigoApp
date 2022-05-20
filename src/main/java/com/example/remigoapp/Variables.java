package com.example.remigoapp;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.List;

public class Variables {
    static public HelloApplication helloApplication;
    static public DatabaseHandler databaseHandler;
    static public List<Memo> memoList = new ArrayList<>();
    static public List<MemoDate> memoDateList = new ArrayList<>();
    static public List<MemoDate> remindTodayList = new ArrayList<>();
    static public User currentUser;
    static public Manager manager;
    static public ObservableList<MemoDate> memoDateData;

    public static HelloApplication getHelloApplication() {
        return helloApplication;
    }

    public static void setHelloApplication(HelloApplication helloApplication) {
        Variables.helloApplication = helloApplication;
    }

    public static DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public static void setDatabaseHandler(DatabaseHandler databaseHandler) {
        Variables.databaseHandler = databaseHandler;
    }

    public static List<Memo> getMemoList() {
        return memoList;
    }

    public static void setMemoList(List<Memo> memoList) {
        Variables.memoList = memoList;
    }

    public static List<MemoDate> getMemoDateList() {
        return memoDateList;
    }

    public static void setMemoDateList(List<MemoDate> memoDateList) {
        Variables.memoDateList = memoDateList;
    }

    public static List<MemoDate> getRemindTodayList() {
        return remindTodayList;
    }

    public static void setRemindTodayList(List<MemoDate> remindTodayList) {
        Variables.remindTodayList = remindTodayList;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * sets current user and lists in it.
     * @author Moog
     * @param currentUser
     */
    public static void setCurrentUserData(User currentUser) {
        Variables.currentUser = currentUser;
        Variables.memoDateList = currentUser.getMemoDateList();
        Variables.memoList = currentUser.getMemoList();
    }

    public static Manager getManager() {
        return manager;
    }

    public static void setManager(Manager manager) {
        Variables.manager = manager;
    }

    public static ObservableList<MemoDate> getMemoDateData() {
        return memoDateData;
    }

    public static void setMemoDateData(ObservableList<MemoDate> memoDateData) {
        Variables.memoDateData = memoDateData;
    }
}
