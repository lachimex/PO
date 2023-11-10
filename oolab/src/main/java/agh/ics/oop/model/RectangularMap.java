package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap implements WorldMap{
    private Map<Vector2d, Animal> animals = new HashMap<>();
    private int height;
    private int width;

    public RectangularMap(int width, int height){
        this.height = height;
        this.width = width;
    }

    @Override
    public boolean place(Animal animal) {
        if (isOccupied(animal.getAnimalPosition())){
            return false;
        }
        else{
            animals.put(animal.getAnimalPosition(), animal);
            return true;
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        animals.remove(animal.getAnimalPosition());
        animal.move(direction, this);
        animals.put(animal.getAnimalPosition(), animal);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public Animal objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public String toString(){
        return new MapVisualizer(this).draw(new Vector2d(0,0), new Vector2d(width - 1 , height - 1));
    }
}
