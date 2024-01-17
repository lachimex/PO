package project.PresentersAndWindows;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsWindow extends Application {

    private SettingsPresenter presenter = new SettingsPresenter();

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("Settings.fxml"));
        BorderPane viewRoot = loader.load();
        presenter = loader.getController();
        configureStage(primaryStage, viewRoot);
        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot, 1024, 728);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
