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
    public Manager manager;
    User user;
    DatabaseHandler databaseHandler;


    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {

        Variables.setHelloApplication(this);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("section_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        ObservableList<MemoDate> memoDateData =  FXCollections.observableArrayList();
        SectionController sectionController = fxmlLoader.<SectionController>getController();;

        databaseHandler = new DatabaseHandler();
        Variables.setDatabaseHandler(databaseHandler);
        databaseHandler.resetDatabaseData("agreed");

        user = new User("TestUser", "test@test.com", "testPass", 1,
                false, "TestFName", "TestLName", 18, "Male");

        List<MemoDate> memoDateList = new ArrayList<>();

        MemoDate memoDate1 = new Education("TestMemoEdu1", "TestingEdu1",
                1, LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, 1);
        MemoDate memoDate2 = new Education("TestMemoEdu2", "TestingEdu2",
                2, LocalDate.now(), LocalDate.now(), LocalDate.now(), 0, 0);
        memoDateList.add(memoDate1);
        memoDateData.add(memoDate1);
        memoDateList.add(memoDate2);
        memoDateData.add(memoDate2);

        user.setMemoDateList(memoDateList);
        manager = new Manager();
        sectionController.setListView(memoDateData);

        Variables.memoDateData = memoDateData;
        Variables.currentUser = user;
        Variables.memoDateList = memoDateList;
        Variables.memoDateData = memoDateData;
        Variables.manager = manager;

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
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

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }
}