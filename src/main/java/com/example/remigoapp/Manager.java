/**
 * @author Ganbayar Sumiyakhuu
 * Date:5/18/2022
 *
 * Description of code:
 * This class checks the reminder date of MemoDaily, MemoDate, Education and notify the user.
 * currnetUser: current session of the user's User
 * memoDateList: list of every MemoDate, MemoDaily, Education
 * remindTodayList: list of every MemoDate, MemoDaily, Education should be reminded today
 */
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


    public Manager(User currentUser) {
        this.currentUser = currentUser;
        this.memoDateList = currentUser.getMemoDateList();
    }

    public Manager(){
    };

    /**
     * checkNewRemind Timer's checkRemind TimerTask
     * calls getDueDateList after the timer is done.
     */
    TimerTask checkRemind = new TimerTask() {
        @Override
        public void run() {

            boolean tmp = getDueMemoDateList();
        }
    };

    /**
     * This method starts the Timer usually called start of the application.
     * Checks memoDateList is empty. if memoDateList is empty gets memoDateList from the user.
     * class updateRemindToday() method starts Fixed Rate Timer.
     * Schedule of the Timer is Constants.CHECK_INTERVAL starts without delay
     */
    public void startTimer(){
        if(memoDateList.size() <= 0)
            memoDateList = currentUser.getMemoDateList();

        updateRemindToday();
        checkNewRemind.scheduleAtFixedRate(checkRemind, 0, Constants.CHECK_INTERVAL);
    }

    /**
     * This method send notification to the user. This sends how many notification currently user has.
     * @param remindCount
     */
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

    /**
     * This method class updateRemindToday method.
     * if today doesn't have anything to remind the method ends returns false.
     * if today has something to remind it calls notifyUser method.
     */
    public boolean getDueMemoDateList(){
        updateRemindToday();

        int remindTodaySize = remindTodayList.size();

        if(remindTodaySize <= 0)
            return false;

        notifyUser(remindTodaySize);
        return true;
    }

    /**
     * This Method resets the remindTodayList list.
     * Then if local date matches the memoDate nextRemindDate it gets added to the remindTodayList List.
     */
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
    /**
     * Simple getter method for currentUser.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
