package com.example.remigoapp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.sql.SQLException;

public class RegisterController {

    @FXML
    private Button btnAsGuest;

    @FXML
    private Button btnLoginNavigate;

    @FXML
    private ChoiceBox<String> cbGender;

    @FXML
    private VBox conMain;

    @FXML
    private VBox conMenu;

    @FXML
    private SVGPath imgRegisterDone;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfAge;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfUsername;

    HelloApplication helloApplication;
    Manager manager;
    private Label lblWarning;

    public void initialize() {
        helloApplication = Variables.getHelloApplication();
        manager = Variables.getManager();
        cbGender.setItems(FXCollections.observableArrayList("FEMALE", "MALE", "PREFER NOT TO TELL"));
    }

    @FXML
    void onClickAsGuest(ContextMenuEvent event) {
        helloApplication.loginController.loginAsGuest();
    }

    @FXML
    void onClickBtnLoginNavigation(MouseEvent event) {
        manager.refresh();
        helloApplication.startLoginView();
    }

    @FXML
    void onClickRegisterDone(MouseEvent event) {
        if (cbGender.getValue() == null || tfAge.getText() == null || tfFirstName.getText() == null || tfLastName.getText() == null || tfUsername.getText() == null || tfEmail.getText() == null) {
            showWarning();
        }
        if (lblWarning == null) {
            lblWarning = new Label("Registered");
            conMenu.getChildren().add(lblWarning);
        }
        lblWarning.setText("Registered");

        try {
            String userName = tfUsername.getText();
            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            String password = tfPassword.getText();
            Integer age = Integer.valueOf(tfAge.getText());
            String eMail = tfEmail.getText();
            String gender = cbGender.getValue();


            User user = manager.createUser(userName, eMail, password, false, firstName, lastName, age, gender);
            Variables.setCurrentUserData(user);

        } catch (SQLException e) {
            if (lblWarning == null) {
                lblWarning = new Label("Failed");
                conMenu.getChildren().add(lblWarning);
            }
            lblWarning.setText("Failed");

            return;
        }
    }

    void showWarning() {
        if (lblWarning == null) {
            lblWarning = new Label("Fill Fields");
            conMenu.getChildren().add(lblWarning);
        }
        lblWarning.setText("Fill Fields");

    }


}
