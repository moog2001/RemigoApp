package com.example.remigoapp;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class SectionController implements Initializable {

    @FXML
    private VBox conMain;

    @FXML
    private VBox conMenu;

    @FXML
    private Pane paneAdd;

    @FXML
    private Pane paneBack;

    @FXML
    private ListView<MemoDate> listView = new ListView<>();

    @FXML
    private TextField memoTitle = new TextField();

    @FXML
    private TextField memoText = new TextField();

    private List<MemoDate> memoDateList = new ArrayList<>();
    ObservableList<MemoDate> memoDateData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listView.setCellFactory(new Callback<ListView<MemoDate>, ListCell<MemoDate>>() {
            @Override
            public ListCell<MemoDate> call(ListView<MemoDate> memoDateListView) {
                return new CustomListCell();
            }
        });

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MemoDate memoDate = listView.getSelectionModel().getSelectedItem();
                if(memoDate == null)
                    return;
                memoTitle.setText(memoDate.getTitle());
                memoText.setText(memoDate.getText());
                System.out.println("Clicked on " + memoDate.getText());
            }
        });

        memoTitle.textProperty().addListener((observable, oldValue, newValue) -> {

            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });

        memoText.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
    }
    private class CustomListCell extends ListCell<MemoDate> {
        private HBox content;
        private Text name;
        private Text text;

        public CustomListCell() {
            super();
            name = new Text();
            text = new Text();
            VBox vBox = new VBox(name, text);
            content = new HBox(vBox);
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(MemoDate item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter
                name.setText(item.getTitle());
                text.setText(item.getText());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }

    public void setListView(ObservableList<MemoDate> memoDateData){
        listView.setItems(memoDateData);
    }

    @FXML
    void onClickPaneAdd(MouseEvent event) {

    }

    @FXML
    void onClickPaneBack(MouseEvent event) {

    }

}
