package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class SettingsPresenter {

    @FXML
    private TextField mapHeight;

    @FXML
    private TextField mapWidth;

    @FXML
    private ChoiceBox<MapVariant> mapVariant;

    @FXML
    private TextField initNumOfPlants;

    @FXML
    private TextField initNumOfAnimals;

    @FXML
    private TextField initEnergy;

    @FXML
    private TextField energyGainOnEat;

    @FXML
    private TextField energyNeededToReproduce;

    @FXML
    private TextField energyLossDuringReproduction;

    @FXML
    private TextField minMutations;

    @FXML
    private TextField maxMutations;

    @FXML
    private ChoiceBox<MutationVariant> mutationVariant;

    @FXML
    private TextField genomLength;

    @FXML
    private void initialize(){
        ObservableList<MapVariant> mapVariants = FXCollections.observableArrayList(
                MapVariant.GLOBE,
                MapVariant.UNDERGROUND_TUNNELS
        );
        mapVariant.setItems(mapVariants);
        ObservableList<MutationVariant> mutationVariants = FXCollections.observableArrayList(
                MutationVariant.FULL_RANDOMNESS,
                MutationVariant.LITTLE_BIT_OF_CRAZINESS
        );
        mutationVariant.setItems(mutationVariants);
    }

    public void startTheSim(){
        GlobalSettings globalSettings = new GlobalSettings(
                Integer.parseInt(mapHeight.getText()),
                Integer.parseInt(mapWidth.getText()),
                mapVariant.getValue(),
                Integer.parseInt(initNumOfPlants.getText()),
                Integer.parseInt(initNumOfAnimals.getText()),
                Integer.parseInt(initEnergy.getText()),
                Integer.parseInt(energyGainOnEat.getText()),
                Integer.parseInt(energyNeededToReproduce.getText()),
                Integer.parseInt(energyLossDuringReproduction.getText()),
                Integer.parseInt(minMutations.getText()),
                Integer.parseInt(maxMutations.getText()),
                mutationVariant.getValue(),
                Integer.parseInt(genomLength.getText())
        );
    }
}

