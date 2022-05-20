package com.example.remigoapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button btnAsGuest;

    @FXML
    private Button btnRegisterNavigate;

    @FXML
    private VBox conMain;

    @FXML
    private VBox conMenu;

    @FXML
    private SVGPath imgLoginDone;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfUsername;

    HelloApplication helloApplication;

    @FXML
    public void initialize() {
        helloApplication = Variables.getHelloApplication();
    }

    @FXML
    void onClickBtnAsGuest(MouseEvent event) throws IOException {
        helloApplication.startFolderView();
    }

    @FXML
    void onClickBtnRegisterNavigation(MouseEvent event) {

    }

    @FXML
    void onClickImgLogin(MouseEvent event) {

    }

}
