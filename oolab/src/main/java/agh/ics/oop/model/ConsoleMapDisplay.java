package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{
    private int numberOfChanges = 1;
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        System.out.println(message + "\nNumber of changes: " + numberOfChanges++);
        System.out.println(worldMap);
    }
}
