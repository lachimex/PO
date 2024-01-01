package project.Maps;


import javafx.application.Platform;
import project.GlobalSettings;
import project.MapElements.Animal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GlobeMap extends AbstractMap implements MapInterface{
    public GlobeMap(GlobalSettings globalSettings) {
        super(globalSettings);
    }

    @Override
    public void deleteDeadAnimals() {
        Collection<List<Animal>> values = new ArrayList<>(animalsMap.values());
        super.animalsMap.clear();
        values.forEach(animalList -> {
            for (Animal animal : animalList){
                if (animal.getEnergy() > 0){
                    super.addAnimal(animal.getPosition(), animal);
                }
        }});
    }

    @Override
    public void moveEachAnimal() {
        Collection<List<Animal>> values = new ArrayList<>(animalsMap.values());
        super.animalsMap.clear();
        values.forEach(animalList -> {
            for (Animal animal : animalList){
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
            Animal wonAnimal = null;
            if (plantMap.containsKey(position) && animals.size() > 1){
                wonAnimal = super.figureOutEatingConflict(position);
            }
            else if (plantMap.containsKey(position)){
                wonAnimal = animals.get(0);
            }
            if (wonAnimal != null){
                wonAnimal.eat();
            }
            plantMap.remove(position);
        });
    }

    @Override
    public void animalReproduce() {
        animalsMap.forEach(((position, animals) -> {
            if (animals.size() > 2){
                List<Animal> animalList = super.figureOutAnimalReproductionConflict(position);
                animalList.get(0).produce(animalList.get(1));
            }
        }));
    }

    @Override
    public void growPlants() {
        for (int i = 0; i < globalSettings.numberOfPlantsEachDay(); i++){
            super.growPlant();
        }
    }
}
