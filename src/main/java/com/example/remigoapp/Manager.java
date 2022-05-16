package com.example.remigoapp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Manager {
    User currentUser = new User();
    Timer timer  = new Timer();

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            getDueMemoDateList();

        }


    };

    public Manager(User currentUser) {
        this.currentUser = currentUser;
    }

    public Manager(){
    };

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void startTimer(){

            timer.scheduleAtFixedRate(task,0,5000);
    }


    private Date convertLocalDate(LocalDate localDate){
        //default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

        return date;
    }

    public void getDueMemoDateList(){
        MemoDate memoDate;
        List<MemoDate> memoDateList;
        memoDateList = currentUser.getMemoDateList();
        memoDate = memoDateList.get(0);
        for(int i = 0; i < memoDateList.size(); i++){
            if(memoDate.getNextRemindDate().isEqual(LocalDate.now())){

                System.out.println(memoDate.getTitle() + " " + memoDate.getText());
            }
        }

    }

}
