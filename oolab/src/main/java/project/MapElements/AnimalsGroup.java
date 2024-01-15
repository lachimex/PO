package project.MapElements;

import project.Maps.Vector2d;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AnimalsGroup {
    Vector2d position;
    List<Animal> animalList = new ArrayList<>();

    public AnimalsGroup(Vector2d position) {
        this.position = position;
    }

    public void addAnimal(Animal animal){
        animalList.add(animal);
    }
    public List<Animal> deleteDeadAnimals(int currentDay) {
        List<Animal> deadAnimalsList = new ArrayList<>();
        Iterator<Animal> iterator = animalList.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.getEnergy() <= 0) {
                animal.setDayOfDeath(currentDay);
                animal.setLifeSpan(currentDay - animal.getBornDay());
                deadAnimalsList.add(animal);
                iterator.remove();
            }
        }
        return deadAnimalsList;
    }

    public Animal figureOutEatingConflict(){
        if (getSize() > 1){
            animalList.sort(Comparator.comparingInt(Animal::getEnergy));
            List<Animal> animalsWithMostEnergy = new ArrayList<>(animalList.subList(0, 2));
            Animal animal1 = animalsWithMostEnergy.get(0);
            Animal animal2 = animalsWithMostEnergy.get(1);
            if (animal1.getEnergy() == animal2.getEnergy()){
                if (animal1.getAge() > animal2.getAge()){
                    return animal1;
                } else if (animal2.getAge() > animal1.getAge()) {
                    return animal2;
                }
                else{
                    if (animal1.getChildCounter() > animal2.getChildCounter()){
                        return animal1;
                    }
                    else if (animal2.getChildCounter() > animal1.getChildCounter()){
                        return animal2;
                    }
                    else{
                        Random random = new Random();
                        if (random.nextInt(2) == 0){
                            return animal1;
                        }
                        else{
                            return animal2;
                        }
                    }
                }
            }
        }
            return animalList.get(0);
    }

    public List<Animal> figureOutAnimalReproductionConflict(){
        Animal animal1;
        Animal animal2;
        if (getSize() > 2){
            animal1 = figureOutEatingConflict();
            animalList.remove(animal1);
            animal2 = figureOutEatingConflict();
            animalList.add(animal1);
        }
        else{
            animal1 = animalList.get(0);
            animal2 = animalList.get(1);
        }
        return new ArrayList<>(List.of(animal1, animal2));
    }


    public int getAverageEnergy(){
        AtomicInteger energy = new AtomicInteger();
        animalList.forEach(animal -> {
            energy.addAndGet(animal.getEnergy());
        });
        return energy.get() / getSize();
    }


    public int getSize(){
        return animalList.size();
    }

    public List<Animal> getAnimalList() {
        return animalList;
    }

    public List<List<Integer>> getGenotypes() {
        return animalList.stream()
                .map(Animal::getGenList)
                .collect(Collectors.toList());
    }
}
