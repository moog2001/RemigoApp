/**
 * @author Ganbayar Sumiyakhuu
 * Date:5/18/2022
 * <p>
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

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Manager {
    private User currentUser = new User();
    private Timer checkNewRemind = new Timer();
    private List<MemoDate> memoDateList = new ArrayList<>();
    private List<MemoDate> remindTodayList = new ArrayList<>();
    private DatabaseHandler databaseHandler;

    public Manager() {
        start();
    }


    /**
     * @author Moog
     * starts manager process
     */
    public void start() {
        databaseHandler = Variables.getDatabaseHandler();
        this.currentUser = Variables.currentUser;
        this.memoDateList = Variables.memoDateList;
        this.remindTodayList = Variables.remindTodayList;

        try {
            setUpAsGuest();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        startTimer();

    }

    /**
     * Starts application in As Guest
     */
    private void setUpAsGuest() throws SQLException {
        User asGuestUser = databaseHandler.getUserData("AS GUEST username");
        if(asGuestUser == null)
            asGuestUser = createUser("AS GUEST username", "ASGUEST@email.com", "AS GUEST password",false,"AS", "GUEST", Constants.NULL_INT,"MALE");
        Variables.setCurrentUserData(asGuestUser);
        memoDateList = asGuestUser.getMemoDateList();
        if(memoDateList == null)
            memoDateList = new ArrayList<MemoDate>();
        for(int i = 0; i < memoDateList.size(); i++){
            Variables.memoDateData.add(memoDateList.get(i));
        }
    }

//    private User createAsGuestUser(){
//
//    }

    /**
     * create a User in the application and return the object.
     *
     * @return
     * @throws SQLException
     */
    public User createUser(String userName, String eMail, String password, boolean isGuest,
                            String firstName, String lastName, int age, String gender) throws SQLException {
        User user = new User(userName, eMail, password, Constants.NULL_INT, isGuest,
                firstName, lastName, age, gender);
        int userId = databaseHandler.createUser(user);
        user.setUserId(userId);
        return user;
    }

    /**
     * create a Memo in the application
     * @author Moog
     * @param userInput
     * @return memo
     * @throws SQLException
     */
    public Memo createMemo(String title, String text, LocalDate createDate, User userInput) throws SQLException {
        Memo memo = new Memo(title, text, Constants.NULL_INT, createDate);
        int memoId = databaseHandler.createMemo(memo, userInput.getUserId());
        memo.setMemoId(memoId);
        userInput.getMemoList().add(memo);
        return memo;
    }

    /**
     * create a MemoDate in the application
     * @return
     */
    public MemoDate createMemoDate(String title, String text, LocalDate createDate, LocalDate lastRemindDate, LocalDate nextRemindDate, User userInput) throws SQLException {
        MemoDate memoDate = new MemoDate(title, text, Constants.NULL_INT, createDate, lastRemindDate, nextRemindDate);
        int memoDateId = databaseHandler.createMemoDate(memoDate, userInput.getUserId());
        memoDate.setMemoId(memoDateId);
        userInput.getMemoDateList().add(memoDate);
        return memoDate;
    }

    /**
     * create a MemoDAily in the application
     * @return
     */
    public MemoDaily createMemoDaily(String title, String text, LocalDate createDate, LocalDate lastRemindDate,
                                      LocalDate nextRemindDate, int interval, User userInput) throws SQLException {
        MemoDaily memoDaily = new MemoDaily(title, text, Constants.NULL_INT ,  createDate, lastRemindDate, nextRemindDate, interval);
        int memoDailyId = databaseHandler.createMemoDate(memoDaily, userInput.getUserId());
        memoDaily.setMemoId(memoDailyId);
        userInput.getMemoDateList().add(memoDaily);
        return memoDaily;
    }

    /**
     * create a Education in the application
     * @return
     */
    public Education createEducation(String title, String text, LocalDate createDate, LocalDate lastRemindDate,
                                      LocalDate nextRemindDate, int interval, int streak, User userInput) throws SQLException {
        Education education = new Education(title,  text, Constants.NULL_INT, createDate,  lastRemindDate,
                nextRemindDate, interval, streak);
        int educationId = databaseHandler.createMemoDate(education, userInput.getUserId());
        return education;
    }

    /**
     * reset User object with data from the database
     * @author Moog
     * @return
     */
    public User resetUser(User userInput) throws SQLException {
        userInput = databaseHandler.getUserData(userInput.getUserId());
        return userInput;
    }

    /**
     * checkNewRemind Timer's checkRemind TimerTask
     * calls getDueDateList after the timer is done.
     */
    TimerTask checkRemind = new TimerTask() {
        @Override
        public void run() {
            boolean tmp = getDueMemoDateList();
            System.out.println("Check " + tmp);
        }
    };

    /**
     * This method starts the Timer usually called start of the application.
     * Checks memoDateList is empty. if memoDateList is empty gets memoDateList from the user.
     * class updateRemindToday() method starts Fixed Rate Timer.
     * Schedule of the Timer is Constants.CHECK_INTERVAL starts without delay
     */
    public void startTimer() {
        if (memoDateList.size() <= 0)
            memoDateList = Variables.memoDateList;

        updateRemindToday();
        checkNewRemind.scheduleAtFixedRate(checkRemind, 0, Constants.CHECK_INTERVAL);
    }

    /**
     * This method send notification to the user. This sends how many notification currently user has.
     * @param remindCount
     */
    private void notifyUser(int remindCount) {

        Notifications notificationBuilder = Notifications.create()
                .title("You have notifications")
                .text("Click here to see them")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                    }
                });
        Platform.runLater(() -> notificationBuilder.showWarning());

    }

    /**
     * This method class updateRemindToday method.
     * if today doesn't have anything to remind the method ends returns false.
     * if today has something to remind it calls notifyUser method.
     */
    public boolean getDueMemoDateList() {
        updateRemindToday();

        int remindTodaySize = remindTodayList.size();

        if (remindTodaySize <= 0)
            return false;
        System.out.println(remindTodaySize);
        notifyUser(remindTodaySize);
        return true;
    }

    /**
     * This Method resets the remindTodayList list.
     * Then if local date matches the memoDate nextRemindDate it gets added to the remindTodayList List.
     */
    private void updateRemindToday() {
        if(memoDateList.size() >= 0)
            memoDateList = Variables.memoDateList;
        MemoDate memoDate;
        if (memoDateList.size() != 0)
            remindTodayList = new ArrayList<>();


        for (int i = 0; i < memoDateList.size(); i++) {
            memoDate = memoDateList.get(i);

            if (memoDate.getNextRemindDate().isBefore(LocalDate.now()) || memoDate.getNextRemindDate().isEqual(LocalDate.now())) {
                remindTodayList.add(memoDate);
            }

        }
         Variables.remindTodayList = remindTodayList;

    }

    /**
     * Simple setter method for currentUser.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }


}
