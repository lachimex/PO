package agh.ics.oop.model;

import javafx.application.Platform;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileMapDisplay implements MapChangeListener{

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(()-> {
            try (FileWriter out = new FileWriter("map_" + worldMap.getId() + ".log", true)) {
                out.write(message + "\n" + worldMap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}
