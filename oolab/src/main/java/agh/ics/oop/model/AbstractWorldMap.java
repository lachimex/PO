package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap {
    protected Map<Vector2d, Animal> animalMap = new HashMap<>();
    public boolean place(Animal animal) {
        if (animalMap.containsValue(animal) || animalMap.containsKey(animal.getPosition())){
            return false;
        }
        else{
            animalMap.put(animal.getPosition(), animal);
            return true;
        }
    }

    public WorldElement objectAt(Vector2d position) {
        return animalMap.get(position);
    }

    public void move(Animal animal, MoveDirection direction, MoveValidator moveValidator){
        animalMap.remove(animal.getPosition());
        animal.move(direction, moveValidator);
        animalMap.put(animal.getPosition(), animal);
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public Collection<WorldElement> getElements(){
        return new ArrayList<>(animalMap.values());
    }
}
