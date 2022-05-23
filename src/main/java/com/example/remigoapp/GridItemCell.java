package com.example.remigoapp;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import org.controlsfx.control.GridCell;

public class GridItemCell extends GridCell<Section>{
    private String path = "M 7.05 40 Q 5.85 40 4.95 39.075 Q 4.05 38.15 4.05 37 V 11 Q 4.05 9.85 4.95 8.925 Q 5.85 8 7.05 8 H 21.05 L 24.05 11 H 41.05 Q 42.2 11 43.125 11.925 Q 44.05 12.85 44.05 14 V 37 Q 44.05 38.15 43.125 39.075 Q 42.2 40 41.05 40 Z M 7.05 11 V 37 Q 7.05 37 7.05 37 Q 7.05 37 7.05 37 H 41.05 Q 41.05 37 41.05 37 Q 41.05 37 41.05 37 V 14 Q 41.05 14 41.05 14 Q 41.05 14 41.05 14 H 22.8 L 19.8 11 H 7.05 Q 7.05 11 7.05 11 Q 7.05 11 7.05 11 Z M 7.05 11 Q 7.05 11 7.05 11 Q 7.05 11 7.05 11 V 14 Q 7.05 14 7.05 14 Q 7.05 14 7.05 14 V 37 Q 7.05 37 7.05 37 Q 7.05 37 7.05 37 Q 7.05 37 7.05 37 Q 7.05 37 7.05 37 Z";
    @Override
    protected void updateItem(Section section, boolean b) {
        super.updateItem(section, b);
        if(b){
            setText("0");
            return;
        }

        SVGPath svgPath = new SVGPath();
        svgPath.setScaleX(3);
        svgPath.setScaleY(3);
        svgPath.setTranslateX(50);

        svgPath.setContent(path);
        setText("Items: " + section.getList().size());
        getStyleClass().add("bold");
        int type = section.getType();

        switch (type){
            case Constants.TYPE_MEMO:{
                svgPath.setFill(Color.GRAY);
                break;
            }case Constants.TYPE_MEMO_DATE:{
                svgPath.setFill(Color.ORANGE);
                break;
            }case Constants.TYPE_MEMO_DAILY:{
                svgPath.setFill(Color.YELLOW);
                break;
            }case Constants.TYPE_EDUCATION:{
                svgPath.setFill(Color.BLUE);
                break;
            } default:{
                break;
            }
        }
        setGraphic(svgPath);
        this.setAlignment(Pos.CENTER);
    }
}
