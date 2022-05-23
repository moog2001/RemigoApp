package com.example.remigoapp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

import java.io.IOException;

public class HelloApplication extends Application {
    public Manager manager;
    User user;
    DatabaseHandler databaseHandler;
    Stage currentStage;
    Stage loginStage;
    Stage registerStage;
    Stage folderStage;
    Stage sectionStage;

    LoginController loginController;
    RegisterController registerController;
    SectionController sectionController;
    FolderController folderController;


    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        ObservableList<MemoDate> memoDateData =  FXCollections.observableArrayList();
        Variables.memoDateData = memoDateData;
        ObservableList<MemoDate> memoDateDaily =  FXCollections.observableArrayList();
        Variables.memoDateData = memoDateDaily ;

        databaseHandler = new DatabaseHandler();
        Variables.setHelloApplication(this);
        Variables.setDatabaseHandler(databaseHandler);

        manager = new Manager();
        Variables.setManager(manager);
        manager.startTimer();
        currentStage = stage;
        FXMLLoader fxmlLoaderLoginView = new FXMLLoader(HelloApplication.class.getResource("login_view.fxml"));
        Scene sceneLogin = new Scene(fxmlLoaderLoginView.load());
        loginController = fxmlLoaderLoginView.getController();
        loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(sceneLogin);

        FXMLLoader fxmlLoaderRegisterView = new FXMLLoader(HelloApplication.class.getResource("register_view.fxml"));
        Scene sceneRegister = new Scene(fxmlLoaderRegisterView.load());
        registerController = fxmlLoaderRegisterView.getController();
        registerStage = new Stage();
        registerStage.setTitle("Register");
        registerStage.setScene(sceneRegister);

        FXMLLoader fxmlLoaderFolderView = new FXMLLoader(HelloApplication.class.getResource("folder_view.fxml"));
        Scene sceneFolder = new Scene(fxmlLoaderFolderView.load());
        folderController = fxmlLoaderFolderView.getController();
        folderStage = new Stage();
        folderStage.setTitle("Folders");
        folderStage.setScene(sceneFolder);
        FXMLLoader fxmlLoaderSectionView = new FXMLLoader(HelloApplication.class.getResource("section_view.fxml"));
        Scene sceneSection = new Scene(fxmlLoaderSectionView.load());
        sectionController = fxmlLoaderSectionView.getController();
        sectionStage = new Stage();
        sectionStage.setTitle("Section");
        sectionStage.setScene(sceneSection);
        Variables.sectionController = sectionController;
        user = Variables.getCurrentUser();

        startLoginView();

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

    public boolean startRegisterView() {
        if (currentStage != null) {
            currentStage.close();
        }

        currentStage = registerStage;
        registerStage.show();
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

    public LoginController getLoginController() {
        return loginController;
    }

    public SectionController getSectionController() {
        return sectionController;
    }

    public FolderController getFolderController() {
        return folderController;
    }
}