package agh.ics.oop.model;

@FunctionalInterface
public interface MapChangeListener {
    public void mapChanged(WorldMap worldMap, String message);
}
