package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionsParser {
    public static List<MoveDirection> parse(String[] args) throws IllegalArgumentException{
        return Stream.of(args)
                .flatMap(arg -> {
                    switch (arg) {
                        case "f", "forward" -> {
                            return Stream.of(MoveDirection.FORWARD);
                        }
                        case "b", "backward" -> {
                            return Stream.of(MoveDirection.BACKWARD);
                        }
                        case "r", "right" -> {
                            return Stream.of(MoveDirection.RIGHT);
                        }
                        case "l", "left" -> {
                            return Stream.of(MoveDirection.LEFT);
                        }
                        default -> throw new IllegalArgumentException(arg + " is not a legal move specification");
                    }
                })
                .collect(Collectors.toList());
    }
}
