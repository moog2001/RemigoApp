package com.example.remigoapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.io.IOException;
import java.sql.SQLException;

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

    Manager manager;

    @FXML
    public void initialize() {
        helloApplication = Variables.getHelloApplication();
        manager = Variables.getManager();
    }
    Label lblWarning;


    /**
     * starts the program as guest
     * @param event
     * @throws IOException
     * @author Moog
     */
    @FXML
    void onClickBtnAsGuest(MouseEvent event) throws IOException {
        loginAsGuest();
    }

    /**
     * navigates to register user
     * @author Moog
     */
    @FXML
    void onClickBtnRegisterNavigation(MouseEvent event) {
        helloApplication.startRegisterView();
    }

    /**
     * searches the user in the database and starts the program with the available suer
     * @param event
     */
    @FXML
    void onClickImgLogin(MouseEvent event) {

        try {
            User user = Variables.getDatabaseHandler().getUserData(tfUsername.getText());
            if(user == null){
                if(lblWarning == null){
                    lblWarning = new Label("Invalid");
                }
                return;
            }
            if(!tfPassword.getText().equals(user.getPassword())){
                if(lblWarning == null){
                    lblWarning = new Label("Invalid");
                }
                return;
            }
            manager.startAs(user);
            manager.refresh();
            helloApplication.startFolderView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loginAsGuest(){
        manager.startAsUser();
        manager.refresh();
        helloApplication.startFolderView();
    }

}
