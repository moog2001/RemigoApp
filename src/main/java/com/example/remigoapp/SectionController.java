package com.example.remigoapp;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private TextArea memoText = new TextArea();

    private List<MemoDate> memoDateList = new ArrayList<>();
    private ObservableList<MemoDate> memoDateData;
    private MemoDate currentMemoDate = new MemoDate();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        memoDateList = Variables.memoDateList;
        memoDateData = Variables.memoDateData;

        listView.setCellFactory(new Callback<ListView<MemoDate>, ListCell<MemoDate>>() {
            @Override
            public ListCell<MemoDate> call(ListView<MemoDate> memoDateListView) {
                return new CustomListCell();
            }
        });

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentMemoDate = listView.getSelectionModel().getSelectedItem();
                if(currentMemoDate == null)
                    return;
                memoTitle.setText(currentMemoDate.getTitle());
                memoText.setText(currentMemoDate.getText());


            }
        });

        memoTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            if(memoDateList.size() <= 0)
                memoDateList = Variables.memoDateList;

            for(int i = 0; i < memoDateList.size(); i++){
                if(memoDateList.get(i).getMemoId() == currentMemoDate.getMemoId()){
                    memoDateList.get(i).setTitle(newValue);
                    currentMemoDate.setTitle(newValue);
                    Variables.memoDateList = memoDateList;
                }

            }

        });

        memoText.textProperty().addListener((observable, oldValue, newValue) -> {
            if(memoDateList.size() <= 0)
                memoDateList = Variables.memoDateList;


            for(int i = 0; i < memoDateList.size(); i++){
                if(memoDateList.get(i).getMemoId() == currentMemoDate.getMemoId()){
                    memoDateList.get(i).setText(newValue);
                    currentMemoDate.setText(newValue);
                    Variables.memoDateList = memoDateList;
                }
            }
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
        Variables.memoDateData = memoDateData;
        listView.setItems(memoDateData);
    }

    @FXML
    void onClickPaneAdd(MouseEvent event) {

    }

    @FXML
    void onClickPaneBack(MouseEvent event) {

    }

}
