package com.example.remigoapp;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Variables {
    static public List<Memo> memoList = new ArrayList<>();
    static public List<MemoDate> memoDateList = new ArrayList<>();
    static public List<MemoDate> remindTodayList = new ArrayList<>();
    static public User currentUser = new User();
    static public Manager manager = new Manager();
    static public ObservableList<MemoDate> memoDateData;
}
