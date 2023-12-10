package agh.ics.oop.model;

import agh.ics.oop.model.exceptions.PositionAlreadyOccupiedException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap implements WorldMap{
    private Map<Vector2d, Grass> grassMap = new HashMap<>();
    private Set<Vector2d> grassPositions = new HashSet<>();

    public GrassField(int numberOfGrass){
        int maxWidth = (int)sqrt(10 * numberOfGrass);
        int maxHeight = (int)sqrt(10 * numberOfGrass);
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxWidth, maxHeight, numberOfGrass);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grassMap.put(grassPosition, new Grass(grassPosition));
            grassPositions.add(grassPosition);
        }
        mapId = nextMapId++;
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
        if (animalMap.containsKey(position)) {
            return super.objectAt(position);
        } else return grassMap.getOrDefault(position, null);
    }

    @Override
    public Collection<WorldElement> getElements() {
        Set<Grass> grassSet = new LinkedHashSet<>(grassMap.values());
        List<WorldElement> worldElementList = new ArrayList<>(super.getElements());
        worldElementList.addAll(grassSet);
        Collections.reverse(worldElementList);
        return worldElementList;
    }


    @Override
    public Boundary getCurrentBounds() {
        grassPositions.addAll(animalMap.keySet());
        if (grassPositions.isEmpty()){
            return new Boundary(new Vector2d(0, 0), new Vector2d(1, 1));
        }
        ArrayList<Vector2d> vector2dArrayList = new ArrayList<>(grassPositions);
        Vector2d mapLowerLeft = vector2dArrayList.get(0);
        Vector2d mapUpperRight = vector2dArrayList.get(0);
        for (Vector2d vector : vector2dArrayList){
            mapLowerLeft = mapLowerLeft.lowerLeft(vector);
            mapUpperRight = mapUpperRight.upperRight(vector);
        }
        return new Boundary(mapLowerLeft, mapUpperRight);
    }

    @Override
    public WorldMap getWorldMap() {
        return this;
    }
}
