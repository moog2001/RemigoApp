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
    Stage currentStage;
    Stage loginStage;
    Stage folderStage;
    Stage sectionStage;


    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        ObservableList<MemoDate> memoDateData =  FXCollections.observableArrayList();
        Variables.memoDateData = memoDateData;
        databaseHandler = new DatabaseHandler();
        Variables.setHelloApplication(this);
        Variables.setDatabaseHandler(databaseHandler);
        databaseHandler.resetDatabaseData("agreed");

        manager = new Manager();
        Variables.setManager(manager);
        manager.start();

        currentStage = stage;
        FXMLLoader fxmlLoaderLoginView = new FXMLLoader(HelloApplication.class.getResource("login_view.fxml"));
        Scene sceneLogin = new Scene(fxmlLoaderLoginView.load());
        LoginController loginController = fxmlLoaderLoginView.getController();
        loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(sceneLogin);
        FXMLLoader fxmlLoaderFolderView = new FXMLLoader(HelloApplication.class.getResource("folder_view.fxml"));
        Scene sceneFolder = new Scene(fxmlLoaderFolderView.load());
        FolderController folderController = fxmlLoaderFolderView.getController();
        folderStage = new Stage();
        folderStage.setTitle("Folders");
        folderStage.setScene(sceneFolder);
        FXMLLoader fxmlLoaderSectionView = new FXMLLoader(HelloApplication.class.getResource("section_view.fxml"));
        Scene sceneSection = new Scene(fxmlLoaderSectionView.load());
        SectionController sectionController = fxmlLoaderSectionView.getController();
        sectionStage = new Stage();
        sectionStage.setTitle("Section");
        sectionStage.setScene(sceneSection);

        user = Variables.getCurrentUser();
        sectionController.setListView(Variables.memoDateData);

        startSectionView();

    }

    /**
     * @return
     * @throws IOException
     * @author Moog
     */
    public boolean startLoginView() {
        if (currentStage != null) {
            currentStage.close();
        }
        currentStage = loginStage;
        loginStage.show();
        return true;
    }

    /**
     * @return
     * @throws IOException
     * @author Moog
     */
    public boolean startFolderView() {
        if (currentStage != null) {
            currentStage.close();
        }
        currentStage = folderStage;
        folderStage.show();
        return true;
    }

    /**
     * @return
     * @throws IOException
     * @author Moog
     */
    public boolean startSectionView() {
        if (currentStage != null) {
            currentStage.close();
        }

        currentStage = sectionStage;
        sectionStage.show();
        return true;
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