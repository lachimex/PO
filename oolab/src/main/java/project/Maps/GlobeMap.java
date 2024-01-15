package project.Maps;


import javafx.application.Platform;
import project.GlobalSettings;
import project.MapElements.Animal;
import project.MapElements.AnimalsGroup;

import java.util.*;

public class GlobeMap extends AbstractMap implements MapInterface{
    public GlobeMap(GlobalSettings globalSettings) {
        super(globalSettings);
    }

    @Override
    public void deleteDeadAnimals() {
        super.deleteDeadAnimals();
    }

    @Override
    public void moveEachAnimal() {
        List<AnimalsGroup> values = new ArrayList<>(animalsMap.values());
        super.animalsMap.clear();
        values.forEach(animalsGroup -> {
            for (Animal animal : animalsGroup.getAnimalList()){
                Vector2d prevPosition = animal.getPosition();
                animal.move();
                if (animal.getPosition().getX() >= globalSettings.mapWidth()){
                    animal.setPosition(new Vector2d(0, animal.getPosition().getY()));
                }
                if (animal.getPosition().getX() < 0){
                    animal.setPosition(new Vector2d(globalSettings.mapWidth() - 1, animal.getPosition().getY()));
                }
                if (animal.getPosition().getY() < 0){
                    animal.setDirection(animal.getDirection().opposite());
                    animal.setPosition(prevPosition);
                }
                if (animal.getPosition().getY() >= globalSettings.mapHeight()){
                    animal.setDirection(animal.getDirection().opposite());
                    animal.setPosition(prevPosition);
                }
                super.addAnimal(animal.getPosition(), animal);
            }
        });
    }

    @Override
    public void plantConsumption() {
        animalsMap.forEach((position, animals) -> {
            Animal wonAnimal;
            if (plantMap.containsKey(position)){
                wonAnimal = super.figureOutEatingConflict(position);
            if (wonAnimal != null){
                wonAnimal.eat();
                plantMap.remove(position);
            }
        }});
    }

    @Override
    public void growPlants() {
        for (int i = 0; i < globalSettings.numberOfPlantsEachDay(); i++){
            super.growPlant();
        }
    }

}
