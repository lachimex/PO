package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{
    private int numberOfChanges = 1;
    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        System.out.println(message + "\nNumber of changes: " + numberOfChanges++ + "\n" + worldMap.getId());
        System.out.println(worldMap);
    }
}
