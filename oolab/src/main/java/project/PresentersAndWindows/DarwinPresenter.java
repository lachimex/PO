package project.PresentersAndWindows;

import agh.ics.oop.model.Grass;
import agh.ics.oop.model.WorldElement;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import project.Exceptions.AnimalNotDeadYetException;
import project.GlobalSettings;
import project.MapElements.Animal;
import project.MapElements.AnimalsGroup;
import project.MapElements.Plant;
import project.Maps.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DarwinPresenter {
    private GlobalSettings globalSettings;
    private MapInterface map;
    boolean paused = false;
    private int dayCounter = 1;
    private Set<Vector2d> occupiedPlacesSet = new HashSet<>();
    @FXML
    private Label dayLabel;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Button pauseResumeButton;
    @FXML
    private Label info;
    @FXML
    private Label animalSpecificInfo;
    private Timeline timeline;
    Animal trackedAnimal;
    boolean savingToFile = false;
    int numOfSim;

    public void handlePauseResumeRequest() {
        if (paused) {
            pauseResumeButton.setText("pauza");
            paused = false;
        } else {
            pauseResumeButton.setText("wznow");
            paused = true;
        }
        Platform.runLater(this::drawMap);
    }

    private void displayInfo() {
        info.setText("Animals: " + map.getAnimalNumber()
                + "\nPlants: " + map.getPlantNumber()
                + "\nFree fields: " + (globalSettings.mapHeight() * globalSettings.mapWidth() - occupiedPlacesSet.size())
                + "\nAverage energy: " + map.getAverageEnergy()
                + "\nMost popular genotype: " + map.getMostPopularGenotype()
                + "\nAverage Lifespan: " + map.getAverageLifeSpan());
    }

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
        displayInfo();
        if (trackedAnimal != null) {
            animalInfo(trackedAnimal);
        }
        mapGrid.setAlignment(Pos.CENTER);
        int columns = globalSettings.mapWidth();
        int rows = globalSettings.mapHeight();
        int cellSize = (int) Math.min((double) 900 / columns, (double) 900 / rows); // max size 900x900
        for (int col = 0; col <= columns; col++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellSize));
        }

        for (int row = 0; row <= rows; row++) {
            mapGrid.getRowConstraints().add(new RowConstraints(cellSize));
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
        if (paused) {
            for (int i = map.getStartingRowOfGreenArea(); i < map.getStartingRowOfGreenArea() + map.getWidthOfGreenArea(); i++) {
                for (int j = 0; j < globalSettings.mapWidth(); j++) {
                    Rectangle rectangle = new Rectangle(cellSize, cellSize);
                    rectangle.setFill(Color.web("#95e095"));
                    rectangle.setMouseTransparent(true);
                    mapGrid.add(rectangle, j + 1, i);
                }
            }
        }
        Map<Vector2d, AnimalsGroup> animalsMap = map.getAnimalsMap();
        Map<Vector2d, Plant> plantMap = map.getPlantMap();
        int maxDisplayedEnergy = globalSettings.initialEnergy() * 3;
        occupiedPlacesSet.clear();
        plantMap.forEach((position, plant) -> {
            occupiedPlacesSet.add(position);
            Circle plantCell = new Circle();
            VBox vBox = new VBox(plantCell);
            vBox.setMaxWidth(0.6 * cellSize);
            vBox.setMaxHeight(0.6 * cellSize);
            vBox.setAlignment(Pos.CENTER);
            GridPane.setHalignment(vBox, HPos.CENTER);
            GridPane.setValignment(vBox, VPos.CENTER);
            vBox.setStyle("-fx-background-color: green");
            mapGrid.add(vBox, position.getX() + 1, globalSettings.mapHeight() - position.getY());
        });
        animalsMap.forEach((position, animalsGroup) -> {
            occupiedPlacesSet.add(position);
            Circle animalCell = new Circle();
            Animal strongestAnimal;
            strongestAnimal = animalsGroup.figureOutEatingConflict();
            VBox vBox = new VBox(animalCell);
            vBox.setMaxWidth(0.6 * cellSize);
            vBox.setMaxHeight(0.6 * cellSize);
            vBox.setAlignment(Pos.CENTER);
            GridPane.setHalignment(vBox, HPos.CENTER);
            GridPane.setValignment(vBox, VPos.CENTER);
            Rectangle invRect = new Rectangle(cellSize, cellSize, Color.TRANSPARENT);
            invRect.setMouseTransparent(false);
            invRect.setFocusTraversable(true);
            vBox.setMouseTransparent(true);
            invRect.setOnMouseEntered(e -> {
                if (paused) {
                    invRect.setCursor(Cursor.HAND);
                }
            });
            invRect.setOnMouseExited(e -> {
                invRect.setCursor(Cursor.DEFAULT);
            });
            invRect.setOnMouseClicked(event -> {
                if (paused) {
                    if (trackedAnimal != null) {
                        trackedAnimal.setIfTracked(false);
                    }
                    strongestAnimal.setIfTracked(true);
                    trackedAnimal = strongestAnimal;
                    animalInfo(trackedAnimal);
                    Platform.runLater(this::drawMap);
                }
            });
            double rgbScalar = 255 * (1 - (Math.min((double) strongestAnimal.getEnergy() / maxDisplayedEnergy, 1)));
            if (paused) {
                if (strongestAnimal.getGenList().toString().equals(map.getMostPopularGenotype())) {
                    vBox.setStyle("-fx-background-color: rgb(135,24,197)");
                } else {
                    vBox.setStyle("-fx-background-color: rgb(255, " + rgbScalar + ", 0)"); //closer to yellow -> less energy, closer to red -> more energy
                }
            }
            if (strongestAnimal == trackedAnimal) { //here I compare reference intentionally
                vBox.setStyle("-fx-background-color: rgb(0,0,0)");
            } else if (!paused){
                vBox.setStyle("-fx-background-color: rgb(255, " + rgbScalar + ", 0)"); //closer to yellow -> less energy, closer to red -> more energy
            }
            mapGrid.add(vBox, position.getX() + 1, globalSettings.mapHeight() - position.getY());
            mapGrid.add(invRect, position.getX() + 1, globalSettings.mapHeight() - position.getY());
        });

        if (globalSettings.mapVariant().equals(MapVariant.UNDERGROUND_TUNNELS)) {
            Map<Vector2d, Vector2d> tunnelsMap = ((TunnelsMap) map).getTunnelMap();
            tunnelsMap.forEach((entry, exit) -> {
                Label tunnelCell1 = new Label();
                Label tunnelCell2 = new Label();
                StackPane stackPane1 = new StackPane(tunnelCell1);
                StackPane stackPane2 = new StackPane(tunnelCell2);
                stackPane1.setStyle("-fx-background-color: #de7954"); //entry
                stackPane2.setStyle("-fx-background-color: #7e6059"); //exit
                GridPane.setFillHeight(stackPane1, true);
                GridPane.setFillWidth(stackPane2, true);
                mapGrid.add(stackPane1, entry.getX() + 1, globalSettings.mapHeight() - entry.getY());
                mapGrid.add(stackPane2, exit.getX() + 1, globalSettings.mapHeight() - exit.getY());
            });
        }
    }

    public void animalInfo(Animal animal) {
        String daysOfLivingInfo;
        String dayOfDeathInfo;
        try {
            daysOfLivingInfo = "Days of living: " + animal.getLifeSpan() + "\n";
        } catch (AnimalNotDeadYetException e) {
            daysOfLivingInfo = "";
        }
        try {
            dayOfDeathInfo = "Day of death: " + animal.getDayOfDeath() + "\n";
        } catch (AnimalNotDeadYetException e) {
            dayOfDeathInfo = "";
        }
        animalSpecificInfo.setText(
                "Genom: " + animal.getGenList() + "\n" +
                        "Active Gen: " + animal.getActiveGen() + "\n" +
                        "Energy: " + animal.getEnergy() + "\n" +
                        "Plants Eaten: " + animal.getPlantEatenCounter() + "\n" +
                        "Children: " + animal.getChildCounter() + "\n" +
                        "Descendants: " + animal.getDescendants() + "\n" +
                        daysOfLivingInfo + dayOfDeathInfo

        );
    }

    public int getNumOfSim() {
        return numOfSim;
    }

    public void setNumOfSim(int numOfSim) {
        this.numOfSim = numOfSim;
    }

    public void enableSavingToCSV() {
        savingToFile = true;
        Path filePath = Path.of("data_from_map" + numOfSim + ".csv");
        try {
            Files.write(filePath, Collections.emptyList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveDataToFile() {
        Path filePath = Path.of("data_from_map" + numOfSim + ".csv");
        try {
            if (Files.size(filePath) == 0) { //it procedes when file exists but it is empty
                try (FileWriter out = new FileWriter(String.valueOf(filePath), true)) {
                    out.write("Animals, Plants, Free fields, Average energy, Most popular genotype, Average Lifespan\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try (FileWriter out = new FileWriter(String.valueOf(filePath), true)) {
                    out.write(map.getAnimalNumber() + ", " + map.getPlantNumber() + ", "
                            + (globalSettings.mapHeight() * globalSettings.mapWidth() - occupiedPlacesSet.size()) + ", "
                            + map.getAverageEnergy() + ", " + map.getAverageLifeSpan() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) { //it catches when file doesnt exist
            e.printStackTrace();
        }
    }

    public void startTheSim(){
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> {
                    if (!map.canSimRunAgain()){
                        timeline.stop();
                    }
                    if (!paused){
                        dayLabel.setText(Integer.toString(dayCounter));
                        map.setCurrentDay(dayCounter);
                        dayCounter += 1;
                        map.deleteDeadAnimals();
                        map.moveEachAnimal();
                        map.plantConsumption();
                        map.animalReproduce();
                        map.growPlants();
                        drawMap();
                        if (savingToFile){
                            saveDataToFile();
                        }
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE); // Run indefinitely
        timeline.play();
       }


}

