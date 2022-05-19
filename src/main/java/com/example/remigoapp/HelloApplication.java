package com.example.remigoapp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import java.io.IOException;

public class HelloApplication extends Application {
    Manager manager;
    User user;
    HelloApplication appInstance;
    DatabaseHandler databaseHandler;


    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("section_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ObservableList<MemoDate> memoDateData =  FXCollections.observableArrayList();
        SectionController sectionController = fxmlLoader.<SectionController>getController();;
        appInstance = this;
        databaseHandler = new DatabaseHandler();

        user = new User("TestUser", "test@test.com", "testPass", 1,
                false, "TestFName", "TestLName", 18, "Male");
        List<MemoDate> memoDateList = new ArrayList<>();
        MemoDate memoDate = new Education("TestMemoEdu", "TestingEdu",
                1, LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, 1);
        memoDateList.add(memoDate);
        memoDateData.add(memoDate);

        user.setMemoDateList(memoDateList);
        manager = new Manager(user);
        manager.startTimer();
        sectionController.setListView(memoDateData);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void staticData(ObservableList<MemoDate> memoDateData){
        user = new User("TestUser", "test@test.com", "testPass", 1,
                false, "TestFName", "TestLName", 18, "Male");
        List<MemoDate> memoDateList = new ArrayList<>();
        MemoDate memoDate = new Education("TestMemoEdu", "TestingEdu",
                1, LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, 1);
        memoDateList.add(memoDate);
        memoDateData.add(memoDate);
    }



    public static void main(String[] args) {
        launch();
    }


    public Manager getManager() {
        return manager;
    }

    public User getUser() {
        return user;
    }

    public HelloApplication getAppInstance() {
        return appInstance;
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }
}