package project.Maps;

import project.MapElements.Animal;
import project.MapElements.Plant;

import java.util.List;
import java.util.Map;

public interface MapInterface {
    void deleteDeadAnimals();
    void moveEachAnimal();
    void plantConsumption();
    void animalReproduce();
    void growPlants();

    Map<Vector2d, List<Animal>> getAnimalsMap();
    Map<Vector2d, Plant> getPlantMap();

    int getAnimalNumber();
    int getPlantNumber();
    Animal figureOutEatingConflict(Vector2d position);
}
