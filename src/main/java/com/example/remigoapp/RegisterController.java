package com.example.remigoapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class RegisterController {

    @FXML
    private Button btnAsGuest;

    @FXML
    private Button btnLoginNavigate;

    @FXML
    private ChoiceBox<?> cbGender;

    @FXML
    private VBox conMain;

    @FXML
    private VBox conMenu;

    @FXML
    private SVGPath imgRegisterDone;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfPassword1;

    @FXML
    private TextField tfPassword2;

    @FXML
    private TextField tfPassword3;

    @FXML
    private TextField tfUsername;

    @FXML
    void onClickAsGuest(ContextMenuEvent event) {

    }

    @FXML
    void onClickBtnLoginNavigation(MouseEvent event) {

    }

    @FXML
    void onClickRegisterDone(MouseEvent event) {

    }

}
