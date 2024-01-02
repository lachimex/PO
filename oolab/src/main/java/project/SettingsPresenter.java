package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.Maps.GlobeMap;
import project.Maps.MapVariant;
import project.Maps.TunnelsMap;

import java.io.IOException;

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
    private TextField numOfPlantsEachDay;

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
    private ChoiceBox<BehaviourVariant> mutationVariant;

    @FXML
    private TextField genomLength;
    @FXML
    private Label errorInfo;

    private DarwinWindow darwinWindow;

    @FXML
    private void initialize(){
        ObservableList<MapVariant> mapVariants = FXCollections.observableArrayList(
                MapVariant.GLOBE,
                MapVariant.UNDERGROUND_TUNNELS
        );
        mapVariant.setItems(mapVariants);
        mapVariant.setValue(MapVariant.GLOBE);
        ObservableList<BehaviourVariant> mutationVariants = FXCollections.observableArrayList(
                BehaviourVariant.COMPLETE_PREDESTINATION,
                BehaviourVariant.LITTLE_BIT_OF_CRAZINESS
        );
        mutationVariant.setItems(mutationVariants);
        mutationVariant.setValue(BehaviourVariant.COMPLETE_PREDESTINATION);
    }

    public void startTheSim() throws IOException, InterruptedException {
        GlobalSettings globalSettings;
        if (checkExistenceOfAllValues()) {
            globalSettings = new GlobalSettings(
                    Integer.parseInt(mapHeight.getText().replaceAll("\\s", "")),
                    Integer.parseInt(mapWidth.getText().replaceAll("\\s", "")),
                    mapVariant.getValue(),
                    Integer.parseInt(initNumOfPlants.getText().replaceAll("\\s", "")),
                    Integer.parseInt(initNumOfAnimals.getText().replaceAll("\\s", "")),
                    Integer.parseInt(initEnergy.getText().replaceAll("\\s", "")),
                    Integer.parseInt(numOfPlantsEachDay.getText().replaceAll("\\s", "")),
                    Integer.parseInt(energyGainOnEat.getText().replaceAll("\\s", "")),
                    Integer.parseInt(energyNeededToReproduce.getText().replaceAll("\\s", "")),
                    Integer.parseInt(energyLossDuringReproduction.getText().replaceAll("\\s", "")),
                    Integer.parseInt(minMutations.getText().replaceAll("\\s", "")),
                    Integer.parseInt(maxMutations.getText().replaceAll("\\s", "")),
                    mutationVariant.getValue(),
                    Integer.parseInt(genomLength.getText().replaceAll("\\s", ""))
            );
        if (checkSettings(globalSettings)){
                errorInfo.setText("wszystko gra, startujemy z symulacja");
                darwinWindow = new DarwinWindow(globalSettings);
                darwinWindow.start(new Stage());
                if (globalSettings.mapVariant().equals(MapVariant.GLOBE)){
                    GlobeMap globeMap = new GlobeMap(globalSettings);
                    globeMap.prepareMap(globalSettings.initialNumberOfAnimals(), globalSettings.initialNumberOfPlants());
                    darwinWindow.getPresenter().setMap(globeMap);
                } else{
                    TunnelsMap tunnelsMap = new TunnelsMap(globalSettings);
                    tunnelsMap.prepareMap(globalSettings.initialNumberOfAnimals(), globalSettings.initialNumberOfPlants());
                    darwinWindow.getPresenter().setMap(tunnelsMap);
                }
                darwinWindow.getPresenter().startTheSim();
            }
        }
    }
    boolean checkSettings(GlobalSettings settings) {
        if (settings.mapHeight() <= 0) {
            mapHeight.setStyle("-fx-border-color: red; -fx-max-width: 250;");
            errorInfo.setText("Wysokość musi być większa od 0.");
            return false;
        } else {
            mapHeight.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (settings.mapWidth() <= 0) {
            mapWidth.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Szerokość mapy musi być większa od 0.");
            return false;
        } else {
            mapWidth.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (settings.initialNumberOfPlants() < 0) {
            initNumOfPlants.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Liczba początkowa roślin nie może być ujemna.");
            return false;
        } else {
            initNumOfPlants.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (settings.initialNumberOfAnimals() <= 0) {
            initNumOfAnimals.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Liczba początkowa zwierzat musi być dodatnia.");
            return false;
        } else {
            initNumOfAnimals.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (settings.initialEnergy() < 0) {
            initEnergy.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Początkowa energia nie może być ujemna.");
            return false;
        } else {
            initEnergy.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (settings.numberOfPlantsEachDay() <= 0){
            numOfPlantsEachDay.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Ilosc roslin rosnacych kazdego dnia musi byc wieksza od 0");
            return false;
        }
        else{
            numOfPlantsEachDay.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (settings.energyGainOnEat() < 0) {
            energyGainOnEat.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Wartość zysku energii przy jedzeniu nie może być ujemna.");
            return false;
        } else {
            energyGainOnEat.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (settings.energyNeededToReproduce() < 0) {
            energyNeededToReproduce.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Wartość energii potrzebnej do reprodukcji nie może być ujemna.");
            return false;
        } else {
            energyNeededToReproduce.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (settings.energyLossDuringReproduction() < 0) {
            energyLossDuringReproduction.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Strata energii podczas reprodukcji nie może być ujemna.");
            return false;
        } else {
            energyLossDuringReproduction.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (settings.minimalNumberOfMutations() < 0) {
            minMutations.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Minimalna liczba mutacji nie może być ujemna.");
            return false;
        } else {
            minMutations.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (settings.maximumNumberOfMutations() < 0) {
            maxMutations.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Maksymalna liczba mutacji nie może być ujemna.");
            return false;
        } else {
            maxMutations.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (settings.genomLength() <= 0) {
            genomLength.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Długość genomu musi być większa od 0.");
            return false;
        } else {
            genomLength.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (mapVariant.getValue() == null){
            mapVariant.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Wybierz wariant mapy");
            return false;
        }
        else{
            mapVariant.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }

        if (mutationVariant.getValue() == null){
            mapVariant.setStyle("-fx-border-color: red; -fx-max-width: 250");
            errorInfo.setText("Wybierz wariant mutacji");
            return false;
        }
        else{
            mutationVariant.setStyle("-fx-border-color: grey; -fx-max-width: 250");
        }
        return true;
    }

    boolean checkExistenceOfAllValues() {
        if (mapHeight.getText().isEmpty()
                || mapWidth.getText().isEmpty()
                || initNumOfPlants.getText().isEmpty()
                || initNumOfAnimals.getText().isEmpty()
                || initEnergy.getText().isEmpty()
                || numOfPlantsEachDay.getText().isEmpty()
                || energyGainOnEat.getText().isEmpty()
                || energyNeededToReproduce.getText().isEmpty()
                || energyLossDuringReproduction.getText().isEmpty()
                || minMutations.getText().isEmpty()
                || maxMutations.getText().isEmpty()
                || genomLength.getText().isEmpty()) {

            errorInfo.setText("Wypelnij wszystkie pola.");
            return false;
        }

        return true;
    }

}
