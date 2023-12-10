package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.w3c.dom.Node;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap worldMap;
    @FXML
    private GridPane mapGrid;
    @FXML
    private TextField moveList;
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
        int columns = worldMap.getCurrentBounds().upperRight().getX() - worldMap.getCurrentBounds().lowerLeft().getX() + 2;
        int rows = worldMap.getCurrentBounds().upperRight().getY() - worldMap.getCurrentBounds().lowerLeft().getY() + 2;
        for (int col = 0; col < columns; col++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(35));
        }

        for (int row = 0; row < rows; row++) {
            mapGrid.getRowConstraints().add(new RowConstraints(35));
        }

        Label label0 = new Label("x\\y");
        GridPane.setHalignment(label0, HPos.CENTER);
        mapGrid.add(label0, 0, 0);

        for (int i = 0; i < columns - 1; i++) {
            Label label = new Label(Integer.toString(worldMap.getCurrentBounds().lowerLeft().getX() + i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, i + 1, 0);
        }

        for (int i = 0; i < rows; i++) {
            Label label = new Label(Integer.toString(worldMap.getCurrentBounds().lowerLeft().getY() + i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, worldMap.getCurrentBounds().upperRight().getY() - i + 1);
        }

        List<WorldElement> worldElementList = worldMap.getElements().stream().toList();
        for (WorldElement element : worldElementList) {
            Label label = new Label(element.toString());
            GridPane.setHalignment(label, HPos.CENTER);
        }

    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(()-> {
            drawMap();
            moveLabel.setText(message);
        });
    }

    public void onSimulationStartClicked() {

        List<MoveDirection> directions = OptionsParser.parse(moveList.getText().split(" "));
        List<Vector2d> positions = List.of(new Vector2d(3,4), new Vector2d(1, 1));

        GrassField grassField = new GrassField(10);
        grassField.registerObservator(this);
        setWorldMap(grassField);
        Simulation simulation = new Simulation(positions, directions, grassField);
        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
        simulationEngine.runAsync();
    }
}
