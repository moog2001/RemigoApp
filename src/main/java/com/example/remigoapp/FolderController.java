package com.example.remigoapp;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.GridView;

import java.io.IOException;

public class FolderController {

    @FXML
    private VBox conMain;

    @FXML
    private VBox conMenu;

    @FXML
    private GridView<?> gvFolderList;

    @FXML
    private ListView<?> listView;

    @FXML
    private Pane paneAdd;

    @FXML
    private Pane paneBack;

    HelloApplication helloApplication;

    @FXML
    public void initialize() {
        helloApplication = Variables.getHelloApplication();
//        gvFolderList.setCellFactory();
    }

    @FXML
    void onClickPaneAdd(MouseEvent event) {

    }

    @FXML
    void onClickPaneBack(MouseEvent event) throws IOException {
        helloApplication.startLoginView();
    }

}
