package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap implements WorldMap{
    private Map<Vector2d, Grass> grassMap = new HashMap<>();

    public GrassField(int numberOfGrass){
        int maxWidth = (int)sqrt(10 * numberOfGrass);
        int maxHeight = (int)sqrt(10 * numberOfGrass);
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxWidth, maxHeight, numberOfGrass);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grassMap.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof Animal);
    }


    @Override
    public void move(Animal animal, MoveDirection moveDirection) {
        super.move(animal, moveDirection, this);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (animalMap.containsKey(position)){
            return super.objectAt(position);
        } else return grassMap.getOrDefault(position, null);
    }

    @Override
    public String toString(){
        Set<Vector2d> objectPositions = new HashSet<>();
        objectPositions.addAll(grassMap.keySet());
        objectPositions.addAll(animalMap.keySet());
        if (objectPositions.isEmpty()){
            return new MapVisualizer(this).draw(new Vector2d(0,0), new Vector2d(1 , 1));
        }
        ArrayList<Vector2d> vector2dArrayList = new ArrayList<>(objectPositions);
        Vector2d lowerLeft = vector2dArrayList.get(0);
        Vector2d upperRight = vector2dArrayList.get(0);
        for (Vector2d vector : vector2dArrayList){
            lowerLeft = lowerLeft.lowerLeft(vector);
            upperRight = upperRight.upperRight(vector);
        }
        return new MapVisualizer(this).draw(lowerLeft, upperRight);
    }

    @Override
    public Collection<WorldElement> getElements() {
        Set<Grass> grassSet = new HashSet<>(grassMap.values());
        List<WorldElement> worldElementList = new ArrayList<>(super.getElements());
        worldElementList.addAll(grassSet);
        return worldElementList;
    }
}
