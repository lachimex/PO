package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.FileMapDisplay;
import agh.ics.oop.model.GrassField;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class InitSimulationPresenter {
    @FXML
    private TextField moveList;
    private SimulationApp simulationApp = new SimulationApp();

    public void displayAnotherSim() throws IOException {
        simulationApp.start(new Stage());
        List<MoveDirection> directions = OptionsParser.parse(moveList.getText().split(" "));
        List<Vector2d> positions = List.of(new Vector2d(0, 0));

        GrassField grassField = new GrassField(10);
        simulationApp.getPresenter().setWorldMap(grassField);
        grassField.registerObservator(simulationApp.getPresenter());
        grassField.registerObservator(new FileMapDisplay());
        grassField.registerObservator((worldMap, message) -> {
            System.out.println(LocalDateTime.now() + " " + message);
        });
        Simulation simulation = new Simulation(positions, directions, grassField);
        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
        simulationEngine.runAsync();
    }
}
