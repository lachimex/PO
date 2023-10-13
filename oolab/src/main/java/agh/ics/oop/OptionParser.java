package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionParser {
    public static MoveDirection[] optionFilter(String[] args) {

        int outLength = 0;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "f", "b", "r", "l" -> outLength++;
            }
        }
        MoveDirection[] out = new MoveDirection[outLength];
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "f" -> out[i] = MoveDirection.FORWARD;
                case "b" -> out[i] = MoveDirection.BACKWARD;
                case "r" -> out[i] = MoveDirection.RIGHT;
                case "l" -> out[i] = MoveDirection.LEFT;
            }
        }
        return out;
    }
}
