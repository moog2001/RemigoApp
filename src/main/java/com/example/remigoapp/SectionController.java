/**
 * @author Ganbayar Sumiyakhuu
 *
 * Description of code:
 * This class controls section_view.fxml.
 */
package com.example.remigoapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements Initializable.
 */
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
    HelloApplication helloApplication;

    /**
     * this method sets parameters initializes fxml.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloApplication = Variables.getHelloApplication();
        databaseHandler = Variables.getDatabaseHandler();
        memoDateList = Variables.memoDateList;
        memoDateData = Variables.memoDateData;
        manager = Variables.getManager();

        /**
         * This method sets a cell factory for listView. The cell factory uses CustomListCell.
         */
        listView.setCellFactory(new Callback<ListView<MemoDate>, ListCell<MemoDate>>() {
            @Override
            public ListCell<MemoDate> call(ListView<MemoDate> memoDateListView) {
                return new CustomListCell();
            }
        });

        /**
         * when item selected from list view this new event is created then executed.
         * The event gets selected items value show it on memo.
         */
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentMemoDate = listView.getSelectionModel().getSelectedItem();
                if(currentMemoDate == null)
                    return;
                memoTitle.setText(currentMemoDate.getTitle());
                memoText.setText(currentMemoDate.getText());
                memoDatePicker.setValue(currentMemoDate.getNextRemindDate());

            }
        });

        /**
         * When addMemoButton is clicked in scene this method creates a new event executes it.
         * The Event adds a new memoDate to the database and shows it on the listView.
         */
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

                if( manager == null)
                    manager = Variables.manager;

                try {
                    newMemoDate=  manager.createMemoDate(memoTitle.getText(), memoText.getText(), LocalDate.now(), LocalDate.now(),memoDatePicker.getValue(), Variables.currentUser);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                    memoDateList.add(newMemoDate);

                if(memoDateData == null)
                    memoDateData = Variables.memoDateData;
                memoDateData.add(newMemoDate);
                setListView(memoDateData);
                clearText();
            }
        });

        /**
         *  When updateMemoButton is clicked in scene this method creates a new event executes it.
         *  The event updates the database, listView using user input.
         */
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
                try {
                    databaseHandler.updateMemoDate(currentMemoDate);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                listView.refresh();

            }

        });

        /**
         * When updateMemoButton is clicked in scene this method creates a new event executes it.
         * The event deletes the memoDate from database and listView.
         */
        deleteMemoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(memoDateList.size() <= 0)
                    memoDateList = Variables.memoDateList;
                if(memoDateData == null)
                    memoDateData = Variables.memoDateData;

                for(int i = 0; i < memoDateList.size(); i++){
                    if(memoDateList.get(i).getMemoId() == currentMemoDate.getMemoId()){
                        System.out.println("id " + memoDateList.get(i).getMemoId() + "current id " + currentMemoDate.getMemoId());
                        if( databaseHandler == null)
                            databaseHandler = Variables.databaseHandler;

                        try {
                            databaseHandler.deleteMemoDate(memoDateList.get(i).getMemoId());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        memoDateList.remove(i);
                        memoDateData.remove(i);
                        i = memoDateList.size();
                    }
                }
                clearText();
                setListView(memoDateData);
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

    @FXML
    void onClickPaneAdd(MouseEvent event) {
        clearText();
    }

    @FXML
    void onClickPaneBack(MouseEvent event) throws IOException {
        helloApplication.startFolderView();
    }

}
