/**
 * @author Ganbayar Sumiyakhuu
 *
 * Description of code:
 * This class controls section_view.fxml.
 */
package com.example.remigoapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    @FXML
    private TextField memoInterval;

    @FXML
    private Label titleLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label intervalLabel;

    private MemoDate currentMemoDate = new MemoDate();
    private List<MemoDate> currentMemoDateList = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    private HelloApplication helloApplication;
    private Manager manager;
    private int currentType;

    /**
     * this method sets parameters initializes fxml.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloApplication = Variables.getHelloApplication();
        databaseHandler = Variables.getDatabaseHandler();
        manager = Variables.getManager();
        currentType = Variables.currentType;

        initCurrentList();

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
                memoDatePicker.setValue(LocalDate.now());

                switch (currentType){
                    case Constants.TYPE_MEMO_DATE:{
                        memoDatePicker.setValue(currentMemoDate.getNextRemindDate());
                    }
                        break;

                    case Constants.TYPE_MEMO_DAILY:{
                        MemoDaily currentMemoDaily = (MemoDaily) currentMemoDate;
                        int text = currentMemoDaily.getInterval();
                        memoInterval.setText(Integer.toString(text));
                        currentMemoDaily.resetInterval();
                        System.out.println(currentMemoDaily.getNextRemindDate());
                        try {
                            databaseHandler.updateMemoDate(currentMemoDaily);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                    case Constants.TYPE_EDUCATION:{
                        Education currentEducation = (Education) currentMemoDate;
                        currentEducation.resetInterval();
                    }

                    default:{

                        break;
                    }
                }


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
                inputChecker();

                if( manager == null)
                    manager = Variables.manager;

                switch (currentType){
                    case Constants.TYPE_MEMO:{

                        break;
                    }
                    case Constants.TYPE_MEMO_DATE:{
                        try {
                            newMemoDate=  manager.createMemoDate(memoTitle.getText(), memoText.getText(), LocalDate.now(),
                                    LocalDate.now(),memoDatePicker.getValue(), Variables.currentUser);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case Constants.TYPE_MEMO_DAILY:{
                        try{
                            newMemoDate = manager.createMemoDaily(memoTitle.getText(), memoText.getText(), LocalDate.now(),
                                    LocalDate.now(),memoDatePicker.getValue(),Integer.parseInt(memoInterval.getText()),Variables.currentUser);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case Constants.TYPE_EDUCATION:{
                        try{
                            newMemoDate = manager.createEducation(memoTitle.getText(), memoText.getText(), LocalDate.now(),
                                    LocalDate.now(),memoDatePicker.getValue(),2, 0,Variables.currentUser);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    default:{

                        break;
                    }
                }

                Variables.memoDateData.add(newMemoDate);
                initCurrentList();
                currentMemoDateList.add(newMemoDate);

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
                inputChecker();


                for(int i = 0; i < currentMemoDateList.size(); i++){
                    if(currentMemoDateList.get(i).getMemoId() == currentMemoDate.getMemoId()){
                        currentMemoDateList.get(i).setTitle(newTitleValue);
                        currentMemoDateList.get(i).setText(newTextValue);
                        currentMemoDateList.get(i).setNextRemindDate(newDateValue);
                        currentMemoDate = currentMemoDateList.get(i);
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



                for(int i = 0; i < currentMemoDateList.size(); i++){
                    if(currentMemoDateList.get(i).getMemoId() == currentMemoDate.getMemoId()){
                        System.out.println("id " + currentMemoDateList.get(i).getMemoId() + "current id " + currentMemoDate.getMemoId());
                        if( databaseHandler == null)
                            databaseHandler = Variables.databaseHandler;


                        currentMemoDateList.remove(i);
                        Variables.memoDateData.remove(i);
                        i = currentMemoDateList.size();
                    }
                }
                try {
                    databaseHandler.deleteMemoDate(currentMemoDate.getMemoId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                clearText();
                setListView(Variables.memoDateData);
                listView.refresh();
            }
        });

        memoInterval.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!s.matches("\\d*")) {
                    memoInterval.setText(t1.replaceAll("[^\\d]", ""));
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

    public void initCurrentList(){
//        System.out.println( Variables.currentType + " check");
        currentType = Variables.currentType;
        currentMemoDateList = new ArrayList<>();
        Variables.memoDateData.clear();

        listView.getSelectionModel().clearSelection();
        switch (currentType) {
            case Constants.TYPE_MEMO: {
                memoDatePicker.setVisible(false);
                dateLabel.setVisible(false);
                memoInterval.setVisible(false);
                intervalLabel.setVisible(false);
                break;
            }
            case Constants.TYPE_MEMO_DATE: {
                memoDatePicker.setVisible(true);
                dateLabel.setVisible(true);
                memoInterval.setVisible(false);
                intervalLabel.setVisible(false);
                break;
            }
            case Constants.TYPE_MEMO_DAILY: {
                memoDatePicker.setVisible(false);
                dateLabel.setVisible(false);
                memoInterval.setVisible(true);
                intervalLabel.setVisible(true);
                break;
            }

            case Constants.TYPE_EDUCATION: {
                memoDatePicker.setVisible(true);
                dateLabel.setVisible(false);
                memoInterval.setVisible(false);
                intervalLabel.setVisible(false);
                break;
            }
            default: {

                break;
            }
        }


        for(int i = 0; i < Variables.memoDateList.size(); i++){
            if(Variables.memoDateList.get(i).getType() == currentType) {
                currentMemoDateList.add(Variables.memoDateList.get(i));
                Variables.memoDateData.add(Variables.memoDateList.get(i));
            }
        }

        setListView(Variables.memoDateData);
        clearText();
    }

    private void inputChecker(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Wrong Input");
        if(memoTitle.getText() == ""|| memoTitle.getText() == null){
            alert.setContentText("Invalid Memo_Title");
            alert.showAndWait();
            return;
        }

        if(memoText.getText() == ""){
            alert.setContentText("Invalid Memo_Text");
            alert.showAndWait();
            return;
        }

        if(memoDatePicker.getValue() == null && currentType ==  Constants.TYPE_MEMO_DATE){
            alert.setContentText("Invalid Memo_Date");
            alert.showAndWait();
            return;
        }

        if(memoDatePicker.getValue() == null && currentType ==  Constants.TYPE_MEMO_DATE){
            alert.setContentText("Invalid Memo_Date");
            alert.showAndWait();
            return;
        }

        if(currentMemoDate == null && currentType ==  Constants.TYPE_MEMO_DATE){
            alert.setContentText("Select memo date");
            alert.showAndWait();
            return;
        }
    }


    @FXML
    void onClickPaneAdd(MouseEvent event) {
        clearText();
    }

    @FXML
    void onClickPaneBack(MouseEvent event) throws IOException {
        listView.getSelectionModel().clearSelection();
        helloApplication.startFolderView();
    }

}
