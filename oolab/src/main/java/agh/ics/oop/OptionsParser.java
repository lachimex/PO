package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> parse(String[] args) {
        List<MoveDirection> out = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "f" -> out.add(MoveDirection.FORWARD);
                case "b" -> out.add(MoveDirection.BACKWARD);
                case "r" -> out.add(MoveDirection.RIGHT);
                case "l" -> out.add(MoveDirection.LEFT);
            }
        }
        return out;
    }
}
