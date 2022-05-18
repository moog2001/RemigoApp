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
    private Timer timer  = new Timer();
    private  List<MemoDate> memoDateList;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            getDueMemoDateList();
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

            timer.scheduleAtFixedRate(task,0,5000);
    }

    private void notifyUser(int i){
        MemoDate memoDate = memoDateList.get(i);;
        //
        Notifications notificationBuilder = Notifications.create()
                .title(memoDate.title)
                .text(memoDate.text)
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
        MemoDate memoDate;

        if(memoDateList == null)
            memoDateList = currentUser.getMemoDateList();


        for(int i = 0; i < memoDateList.size(); i++){
            memoDate = memoDateList.get(i);
            if(memoDate.getNextRemindDate().isEqual(LocalDate.now())){
                notifyUser(i);
                System.out.println(memoDate.getTitle() + " " + memoDate.getText());
            }
        }

    }

}
