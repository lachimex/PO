package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args){
        ConsoleMapDisplay mapDisplayer = new ConsoleMapDisplay();
//        try{
//            List<MoveDirection> directions = OptionsParser.parse(args);
//            List<Vector2d> positions = List.of(new Vector2d(3,4));
//
//            GrassField grassField = new GrassField(10);
//            RectangularMap rectangularMap = new RectangularMap(10, 10);
//            rectangularMap.registerObservator(mapDisplayer);
//            grassField.registerObservator(mapDisplayer);
//
//            List<Simulation> simulationList = new ArrayList<>();
//            simulationList.add(new Simulation(positions, directions, rectangularMap));
//            simulationList.add(new Simulation(positions, directions, grassField));
//            for (int i = 0; i < 1000; i++) {
//                GrassField grassField1 = new GrassField(10);
//                grassField1.registerObservator(mapDisplayer);
//                simulationList.add(new Simulation(positions, directions, grassField1));
//            }
//
//            SimulationEngine simulationEngine = new SimulationEngine(simulationList);
//            simulationEngine.runAsyncInThreadPool();
//
//            try{
//                simulationEngine.awaitSimulationsEnd();
//            } catch (InterruptedException e){
//                e.printStackTrace();
//            }
//
//        } catch (IllegalArgumentException e){
//            System.out.println(e.getMessage());
//        }
        Application.launch(SimulationApp.class, args);
        System.out.println("SYSTEM HAS ENDED ITS LIFE");
    }

}
