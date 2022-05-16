package com.example.remigoapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    Manager manager;
    User user;
    @Override
    public void start(Stage stage) throws IOException {
        user = new User("TestUser", "test@test.com", "testPass", 1,
                false, "TestFName", "TestLName", 18, "Male");
        manager = new Manager(user);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}