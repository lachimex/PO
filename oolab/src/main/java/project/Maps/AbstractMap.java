package project.Maps;

import project.GlobalSettings;
import project.MapElements.Animal;
import project.MapElements.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractMap {
    private final GlobalSettings globalSettings;

    public AbstractMap(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
    }

    Map<Vector2d, List<Animal>> animalsMap = new HashMap<>();
    Map<Vector2d, Plant> plantMap = new HashMap<>();
    private void addAnimal(Vector2d position, Animal animal){
        List<Animal> newAnimalList;
        if (animalsMap.containsKey(position)){
            newAnimalList = animalsMap.get(position);
        }
        else{
            newAnimalList = new ArrayList<>();
        }
        newAnimalList.add(animal);
        animalsMap.put(position, newAnimalList);
    }

    private void addPlant(Vector2d position, Plant plant){
        plantMap.put(position, plant);
    }

    int getNumberOfEmptyFields(){
        int out = 0;
        for (int i = 0; i < globalSettings.mapHeight(); i++){
            for (int j = 0; j < globalSettings.mapWidth(); j++){
                if (plantMap.containsKey(new Vector2d(i, j))){
                    continue;
                }
                if (animalsMap.containsKey(new Vector2d(i, j))){
                    continue;
                }
                out++;
            }
        }
        return out;
    }

    int getAverageEnergy(){
        AtomicInteger energy = new AtomicInteger(0);
        AtomicInteger animalsCounter = new AtomicInteger(0);
        animalsMap.forEach((vector2d, animals) -> animals.forEach(animal -> energy.addAndGet(animal.getEnergy())));
        animalsMap.forEach((vector2d, animals) -> animals.forEach(animal -> animalsCounter.addAndGet(1)));
        return energy.get() / animalsCounter.get();
    }
}
