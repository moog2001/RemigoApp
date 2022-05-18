package com.example.remigoapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Manager {
    private User currentUser = new User();
    private Timer checkNewRemind = new Timer();
    private List<MemoDate> memoDateList;
    private List<MemoDate> remindTodayList = new ArrayList<>();

    TimerTask checkRemind = new TimerTask() {
        @Override
        public void run() {
            getDueMemoDateList();

            System.out.println("YES");
        }
    };

    public Manager(User currentUser) {
        this.currentUser = currentUser;
        this.memoDateList = currentUser.getMemoDateList();
    }

    public Manager(){
    };

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void startTimer(){
        if(memoDateList == null)
            memoDateList = currentUser.getMemoDateList();

        updateRemindToday();
        checkNewRemind.scheduleAtFixedRate(checkRemind, 0, Constants.CHECK_INTERVAL);
    }

    private void notifyUser(int remindCount){

        Notifications notificationBuilder = Notifications.create()
                .title("You have " + remindCount + " notifications")
                .text("Click here to see them")
                .graphic(null)
                .hideAfter(Duration.seconds(30))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                    }
                });
        Platform.runLater(()->notificationBuilder.showWarning() );

    }

    private Date convertLocalDate(LocalDate localDate){
        //default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

        return date;
    }

    public void getDueMemoDateList(){
        updateRemindToday();

        int remindTodaySize = remindTodayList.size();

        if(remindTodaySize <= 0) {
            System.out.println("Skipped");
            return;
        }

        notifyUser(remindTodaySize);
    }

    private void updateRemindToday(){
        MemoDate memoDate;
        if(memoDateList.size() != 0)
            remindTodayList =  new ArrayList<>();;

        for(int i = 0; i < memoDateList.size() ; i++){
            memoDate = memoDateList.get(i);
            if(memoDate.getNextRemindDate().isEqual(LocalDate.now())){
                remindTodayList.add(memoDate);
            }
        }
    }

}
