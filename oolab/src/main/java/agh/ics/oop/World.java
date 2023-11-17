package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args){
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(new Vector2d(3,4));
        Simulation simulation = new Simulation(positions, directions, new RectangularMap(5, 5));
        simulation.run();
//        ArrayList<StringTextMap> stringList = new ArrayList<>();
//        stringList.add(new StringTextMap("ala"));
//        stringList.add(new StringTextMap("ma"));
//        stringList.add(new StringTextMap("sowoniedzwiedzia"));
//        TextMap textMap = new TextMap(stringList);
//        textMap.run(directions);
    }

}
