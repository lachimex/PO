package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.RectangularMap;
import agh.ics.oop.model.Vector2d;

import java.util.List;

public class World {
    public static void main(String[] args){
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(new Vector2d(3,4));
        Simulation simulation = new Simulation(positions, directions, new RectangularMap(5, 5));
        simulation.run();
    }

}
