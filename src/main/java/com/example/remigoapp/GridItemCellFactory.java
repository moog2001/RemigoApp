package com.example.remigoapp;

import javafx.scene.control.MenuItem;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

public class GridItemCellFactory implements Callback<GridView<MenuItem>, GridCell<MenuItem>> {

    @Override
    public GridCell<MenuItem> call(GridView<MenuItem> listview) {
        return new GridItemCell();
    }
}