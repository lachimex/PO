package project;

import javafx.application.Application;
import project.PresentersAndWindows.SettingsWindow;

public class ApplicationLaunch {
    public static void main(String[] args) {
        Application.launch(SettingsWindow.class, args);
    }
}
