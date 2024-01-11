package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap worldMap;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label moveLabel;

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void drawMap() {
        clearGrid();
        int columns = worldMap.getCurrentBounds().upperRight().getX() - worldMap.getCurrentBounds().lowerLeft().getX() + 1;
        int rows = worldMap.getCurrentBounds().upperRight().getY() - worldMap.getCurrentBounds().lowerLeft().getY() + 1;
        for (int col = 0; col <= columns; col++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(35));
        }

        for (int row = 0; row <= rows; row++) {
            mapGrid.getRowConstraints().add(new RowConstraints(35));
        }

        Label label0 = new Label("y\\x");
        GridPane.setHalignment(label0, HPos.CENTER);
        mapGrid.add(label0, 0, 0);

        for (int i = 0; i < columns; i++) {
            Label label = new Label(Integer.toString(worldMap.getCurrentBounds().lowerLeft().getX() + i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, i + 1, 0);
        }

        for (int i = 0; i < rows; i++) {
            Label label = new Label(Integer.toString(worldMap.getCurrentBounds().lowerLeft().getY() + i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, rows - i);
        }

        List<WorldElement> worldElementList = worldMap.getElements().stream().toList();
        for (WorldElement element : worldElementList) {
            if (element instanceof Grass && worldMap.objectAt(element.getPosition()).map(worldElement -> worldElement instanceof Animal).get()) {
                continue;
            }
            WorldElementBox worldElementBox = new WorldElementBox(element);
            VBox vbox = worldElementBox.getImageBox();
            mapGrid.add(vbox,
                    element.getPosition().getX() + 1 - worldMap.getCurrentBounds().lowerLeft().getX(),
                    rows - element.getPosition().getY() + worldMap.getCurrentBounds().lowerLeft().getY());
        }

    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(()-> {
            drawMap();
            moveLabel.setText(message);
        });
    }
}
