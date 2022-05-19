package com.example.remigoapp;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
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

    private List<MemoDate> memoDateList = new ArrayList<>();
    ObservableList<MemoDate> memoDateData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        MemoDate memoDate = new Education("TestMemoEdu", "TestingEdu",
//                1, LocalDate.now(), LocalDate.now(), LocalDate.now(), 1, 1);
//
//        listView.getItems().add(memoDate);

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
                System.out.println("Clicked on " + memoDate.getText());
            }
        });
    }
    private class CustomListCell extends ListCell<MemoDate> {
        private HBox content;
        private Text name;
        private Text price;

        public CustomListCell() {
            super();
            name = new Text();
            price = new Text();
            VBox vBox = new VBox(name, price);
            content = new HBox(vBox);
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(MemoDate item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter
                name.setText(item.getTitle());
                price.setText(item.getText());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }

    @FXML
    void onClickPaneAdd(MouseEvent event) {

    }

    @FXML
    void onClickPaneBack(MouseEvent event) {

    }

    public void setListView(ObservableList<MemoDate> memoDateData){
        listView.setItems(memoDateData);
        System.out.println(memoDateData.get(0).getTitle());
    }
}
