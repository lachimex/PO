package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class World {
    public static void main(String[] args){
        try{
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(new Vector2d(3,4));

            GrassField grassField = new GrassField(10);
            RectangularMap rectangularMap = new RectangularMap(10, 10);
            rectangularMap.registerObservator(new ConsoleMapDisplay());
            grassField.registerObservator(new ConsoleMapDisplay());

            List<Simulation> simulationList = new ArrayList<>();
            simulationList.add(new Simulation(positions, directions, rectangularMap));
            simulationList.add(new Simulation(positions, directions, grassField));

            SimulationEngine simulationEngine = new SimulationEngine(simulationList);
            simulationEngine.runAsync();

        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

}
