package project.Maps;

import project.MapElements.Animal;
import project.MapElements.AnimalsGroup;
import project.MapElements.Plant;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MapInterface {
    void deleteDeadAnimals();
    void moveEachAnimal();
    void plantConsumption();
    void animalReproduce();
    void growPlants();

    Map<Vector2d, AnimalsGroup> getAnimalsMap();
    Map<Vector2d, Plant> getPlantMap();

    int getAnimalNumber();
    int getPlantNumber();
    int getAverageEnergy();
    String getMostPopularGenotype();
    int getAverageLifeSpan();
    void setCurrentDay(int dayCounter);
    Animal figureOutEatingConflict(Vector2d position);
    int getWidthOfGreenArea();
    int getStartingRowOfGreenArea();
    int getDescendantsFromAnimal(Animal animal, Set<Animal> controlSet);
}
