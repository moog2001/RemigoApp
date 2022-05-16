package com.example.remigoapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import java.io.IOException;

public class HelloApplication extends Application {
<<<<<<< Updated upstream
    Manager manager;
    User user;
    @Override
    public void start(Stage stage) throws IOException {
        user = new User("TestUser", "test@test.com", "testPass", 1,
                false, "TestFName", "TestLName", 18, "Male");

        List<MemoDate> memoDateList = new ArrayList<>();;
        MemoDate memoDate = new MemoDate("TestMemo", "Testing", 1, LocalDate.now(), LocalDate.now(), LocalDate.now());
        memoDateList.add(memoDate);
        user.setMemoDateList(memoDateList);

        manager = new Manager(user);
//        manager.startTimer();
=======


    private static HelloApplication appInstance;
    DatabaseHandler databaseHandler = null;

    @Override
    public void start(Stage stage) throws IOException {
        appInstance = this;
>>>>>>> Stashed changes

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        databaseHandler = new DatabaseHandler();
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }

    public HelloApplication getInstance() {
        return appInstance;
    }

}