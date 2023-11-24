package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class World {
    public static void main(String[] args){
        try{
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(new Vector2d(3,4), new Vector2d(3, 4));
            Simulation simulation = new Simulation(positions, directions, new GrassField(10));
            simulation.run();
        } catch (IllegalArgumentException e){
            System.out.println(e);
        }
//        ArrayList<StringTextMap> stringList = new ArrayList<>();
//        stringList.add(new StringTextMap("ala"));
//        stringList.add(new StringTextMap("ma"));
//        stringList.add(new StringTextMap("sowoniedzwiedzia"));
//        TextMap textMap = new TextMap(stringList);
//        textMap.run(directions);
    }

}
