package project;

import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DarwinWindow extends Application {

    GlobalSettings globalSettings;

    public DarwinWindow(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
    }

    private DarwinPresenter presenter = new DarwinPresenter();
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("DarwinSim.fxml"));
        BorderPane viewRoot = loader.load();
        presenter = loader.getController();
        presenter.setGlobalSettings(globalSettings);
        configureStage(primaryStage, viewRoot);
        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Darwin simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public DarwinPresenter getPresenter(){
        return presenter;
    }

}

