package com.example.remigoapp;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


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
    private DatePicker memoDatePicker;

    @FXML
    private ListView<MemoDate> listView = new ListView<>();

    @FXML
    private TextField memoTitle = new TextField();

    @FXML
    private TextArea memoText = new TextArea();

    @FXML
    private Button addMemoButton;

    @FXML
    private Button updateMemoButton;

    @FXML
    private Button deleteMemoButton;

    private List<MemoDate> memoDateList = new ArrayList<>();
    private ObservableList<MemoDate> memoDateData;
    private MemoDate currentMemoDate = new MemoDate();
    private DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseHandler = Variables.getDatabaseHandler();
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

        memoDatePicker.setOnAction(event ->{
            if(currentMemoDate != null)
                for(int i = 0; i < memoDateList.size(); i++){
                    if(memoDateList.get(i).getMemoId() == currentMemoDate.getMemoId()){
                        memoDateList.get(i).setNextRemindDate(memoDatePicker.getValue());
                        currentMemoDate.setNextRemindDate(memoDatePicker.getValue());
                    }
                }
        });

        addMemoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MemoDate newMemoDate = new MemoDate();
                Alert alert = new Alert(Alert.AlertType.WARNING);

                if(memoTitle.getText() == ""){
                    alert.setTitle("Error");
                    alert.showAndWait();
                    return;
                }

                newMemoDate.setTitle(memoTitle.getText());
                newMemoDate.setText(memoText.getText());
                newMemoDate.setCreateDate(LocalDate.now());
                newMemoDate.setNextRemindDate(memoDatePicker.getValue());
                newMemoDate.setMemoId(generateMemoId());

                Manager manager = Variables.getManager();
//                manager.crea
//                     databaseHandler.createMemoDate(memoTitle.getText(), memoText.getText(), LocalDate.now()))


                memoDateList.add(newMemoDate);

                if(memoDateData == null)
                    memoDateData = Variables.memoDateData;
                memoDateData.add(newMemoDate);
                setListView(memoDateData);
                clearText();
            }
        });

        updateMemoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String newTitleValue = memoTitle.getText();
                String newTextValue = memoText.getText();
                LocalDate newDateValue = memoDatePicker.getValue();

                if(memoDateList.size() <= 0)
                    memoDateList = Variables.memoDateList;

                for(int i = 0; i < memoDateList.size(); i++){
                    if(memoDateList.get(i).getMemoId() == currentMemoDate.getMemoId()){
                        memoDateList.get(i).setTitle(newTitleValue);
                        memoDateList.get(i).setText(newTextValue);
                        memoDateList.get(i).setNextRemindDate(newDateValue);
                        currentMemoDate = memoDateList.get(i);
                    }
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
        listView.setItems(memoDateData);
    }

    private void clearText(){
        memoText.setText("");
        memoTitle.setText("");
    }

    private int generateMemoId(){
        String uniqueID = UUID.randomUUID().toString();
        int memoId = 0;
        for(int i = 0; i < uniqueID.length(); i++){
            char tmp = uniqueID.charAt(i);
            if(tmp >= '0' && tmp <= '9'){
                memoId = memoId * 10 + Character.getNumericValue(tmp);
            }
        }

        return memoId;
    }

    @FXML
    void onClickPaneAdd(MouseEvent event) {
        clearText();
    }

    @FXML
    void onClickPaneBack(MouseEvent event) {

    }

}
