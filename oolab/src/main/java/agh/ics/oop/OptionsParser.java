package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> parse(List<String> args) throws IllegalArgumentException{
        List<MoveDirection> out = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            switch (args.get(i)) {
                case "f", "forward" -> out.add(MoveDirection.FORWARD);
                case "b", "backward" -> out.add(MoveDirection.BACKWARD);
                case "r", "right" -> out.add(MoveDirection.RIGHT);
                case "l", "left" -> out.add(MoveDirection.LEFT);
                default -> throw new IllegalArgumentException(args.get(i) + " is not legal move specification");
            }
        }
        return out;
    }
}
