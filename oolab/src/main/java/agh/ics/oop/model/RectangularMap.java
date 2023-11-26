package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.Collection;

public class RectangularMap extends AbstractWorldMap implements WorldMap{
    private int height;
    private int width;

    public RectangularMap(int width, int height){
        this.height = height;
        this.width = width;
        this.mapBounds = new Boundary(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        super.move(animal, direction, this);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animalMap.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position) &&
                position.follows(new Vector2d(0, 0)) &&
                position.precedes(new Vector2d(width - 1, height - 1));
    }

    @Override
    public Collection<WorldElement> getElements() {
        return super.getElements();
    }

    @Override
    public Boundary getCurrentBounds() {
        return mapBounds;
    }

    @Override
    public WorldMap getWorldMap() {
        return this;
    }

}
