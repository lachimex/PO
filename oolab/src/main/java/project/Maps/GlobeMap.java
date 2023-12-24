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
                animal.move();
                super.addAnimal(animal.getPosition(), animal);
            }
        });
    }

    @Override
    public void plantConsumption() {
        animalsMap.forEach((position, animals) ->
        {
            for (Animal animal : animals){
                if (plantMap.containsKey(position)){
                    plantMap.remove(position);
                    animal.setEnergy(animal.getEnergy() + globalSettings.energyGainOnEat());
                }
                else{
                    break;
                }
            }
        });
    }

    @Override
    public void animalReproduce() {

    }

    @Override
    public void growPlants() {

    }
}
