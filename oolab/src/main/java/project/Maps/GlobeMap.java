package project.Maps;

import project.GlobalSettings;
import project.MapElements.Animal;

import java.util.List;

public class GlobeMap extends AbstractMap implements MapInterface{
    public GlobeMap(GlobalSettings globalSettings) {
        super(globalSettings);
    }

    @Override
    public void deleteDeadAnimals() {
        animalsMap.forEach((vector2d, animals) -> animals.forEach(animal -> {
            if(animal.getEnergy() <= 0){
                animal.setDayOfDeath(currentDay);
                animals.remove(animal);
            }
        }));
    }

    @Override
    public void moveEachAnimal() {
        animalsMap.forEach((position, animals) ->
        {
            List<Animal> removed = animalsMap.remove(position);
            for (Animal animal : removed){
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
            if (plantMap.containsKey(position)){
                Animal animal = super.figureOutEatingConflict(position);
                animal.eat();
                plantMap.remove(position);
            }
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
        super.growPlants();
    }
}
