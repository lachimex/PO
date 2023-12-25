package project;

import agh.ics.oop.model.Grass;
import agh.ics.oop.model.WorldElement;
import agh.ics.oop.model.WorldMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import project.MapElements.Animal;
import project.MapElements.Plant;
import project.Maps.AbstractMap;
import project.Maps.MapInterface;
import project.Maps.Vector2d;

import java.util.List;
import java.util.Map;

public class DarwinPresenter {
    private GlobalSettings globalSettings;
    private MapInterface map;
    private int dayCounter = 1;
    @FXML
    private Label dayLabel;
    @FXML
    private GridPane mapGrid;

    public void setGlobalSettings(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
    }

    public void setMap(MapInterface map) {
        this.map = map;
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void drawMap() {
        clearGrid();
        int columns = globalSettings.mapWidth();
        int rows = globalSettings.mapHeight();
        for (int col = 0; col <= columns; col++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(35));
        }

        for (int row = 0; row <= rows; row++) {
            mapGrid.getRowConstraints().add(new RowConstraints(35));
        }


        Label label0 = new Label("y\\x");
        GridPane.setHalignment(label0, HPos.CENTER);
        mapGrid.add(label0, 0, 0);

        for (int i = globalSettings.mapWidth() - 1; i >= 0; i--) {
            Label label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, i + 1, 0);
        }

        for (int i = globalSettings.mapHeight() - 1; i >= 0; i--) {
            Label label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, rows - i);
        }

        Map<Vector2d, List<Animal>> animalsMap = map.getAnimalsMap();
        Map<Vector2d, Plant> plantMap = map.getPlantMap();
        plantMap.forEach((position, plant) -> {
            Label plantCell = new Label("plant");
            StackPane stackPane = new StackPane(plantCell);
            stackPane.setStyle("-fx-background-color: green");
            GridPane.setFillHeight(stackPane, true);
            GridPane.setFillWidth(stackPane, true);
            mapGrid.add(stackPane, position.getX() + 1, globalSettings.mapHeight() - position.getY());
        });
        animalsMap.forEach((position, animals) -> {
            Label animalCell = new Label("animal");
            StackPane stackPane = new StackPane(animalCell);
            stackPane.setStyle("-fx-background-color: #e27878");
            GridPane.setFillHeight(stackPane, true);
            GridPane.setFillWidth(stackPane, true);
            mapGrid.add(stackPane, position.getX() + 1, globalSettings.mapHeight() - position.getY());
        });
    }

    public void startTheSim(){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> {
                    dayLabel.setText(Integer.toString(dayCounter++));
                    drawMap();
                    map.deleteDeadAnimals();
                    map.moveEachAnimal();
                    map.plantConsumption();
                    map.animalReproduce();
                    map.growPlants();
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE); // Run indefinitely
        timeline.play();
       }
    }
