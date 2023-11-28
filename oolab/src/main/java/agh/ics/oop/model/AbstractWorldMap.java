package agh.ics.oop.model;

import agh.ics.oop.model.exceptions.PositionAlreadyOccupiedException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap{
    protected Map<Vector2d, Animal> animalMap = new HashMap<>();
    protected Boundary mapBounds;
    protected List<MapChangeListener> observatorList = new ArrayList<>();
    private final MapVisualizer mapVisualizer = new MapVisualizer(getWorldMap());

    public void registerObservator(MapChangeListener observator){
        observatorList.add(observator);
    }

    public void unregisterObservator(MapChangeListener observator){
        observatorList.remove(observator);
    }


    public void mapChanged(String message){
        for (MapChangeListener observer : observatorList){
            observer.mapChanged(getWorldMap(), message);
        }
    }

    public boolean place(Animal animal) throws PositionAlreadyOccupiedException{
        if (animalMap.containsKey(animal.getPosition())){
            throw new PositionAlreadyOccupiedException(animal.getPosition());
        }
        else{
            animalMap.put(animal.getPosition(), animal);
            mapChanged("Animal " + animal.getPosition() + " added to the map");
            return true;
        }
    }

    public WorldElement objectAt(Vector2d position){
        return animalMap.get(position);
    }

    protected void move(Animal animal, MoveDirection direction, MoveValidator moveValidator){
        Vector2d positionPrev = animal.getPosition();
        animalMap.remove(animal.getPosition());
        animal.move(direction, moveValidator);
        animalMap.put(animal.getPosition(), animal);
        if (!positionPrev.equals(animal.getPosition())){
            mapChanged(animal + positionPrev.toString() + " moved");
        }

    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public Collection<WorldElement> getElements(){
        return new ArrayList<>(animalMap.values());
    }

    public abstract Boundary getCurrentBounds();
    public abstract WorldMap getWorldMap();

    @Override
    public String toString(){
        return mapVisualizer.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight());
    }
}
