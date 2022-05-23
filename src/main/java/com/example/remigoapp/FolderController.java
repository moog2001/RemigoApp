package com.example.remigoapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import java.io.IOException;

public class FolderController {

    @FXML
    private VBox conMain;

    @FXML
    private VBox conMenu;

    @FXML
    private GridView<Section> gvFolderList;

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
        gvFolderList.setCellFactory(new Callback<GridView<Section>, GridCell<Section>>() {
            @Override
            public GridCell<Section> call(GridView<Section> integerGridView) {
                return new GridItemCell();
            }
        });
        ObservableList<Section> list = FXCollections.observableArrayList(Variables.getCurrentUser().getSections());
        gvFolderList.setItems(list);

    }

    @FXML
    void onClickPaneAdd(MouseEvent event) {

    }

    @FXML
    void onClickPaneBack(MouseEvent event) throws IOException {
        helloApplication.startLoginView();
    }

}
