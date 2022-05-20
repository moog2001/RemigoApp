package com.example.remigoapp;

import javafx.scene.layout.Region;
import org.controlsfx.control.GridCell;

public class GridItemCell extends GridCell {
    @Override
    protected void updateItem(Object o, boolean b) {
        super.updateItem(o, b);
        Region icon = new Region();
        icon.getStyleClass().add("folder");
        setGraphic(icon);
    }
}
