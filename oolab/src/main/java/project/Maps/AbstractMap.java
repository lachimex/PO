package project.Maps;

import project.Exceptions.AnimalNotDeadYetException;
import project.GlobalSettings;
import project.MapElements.Animal;
import project.MapElements.Plant;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractMap {
    protected final GlobalSettings globalSettings;
    protected int currentDay;


    Map<Vector2d, List<Animal>> animalsMap = new HashMap<>();
    Map<Vector2d, Plant> plantMap = new HashMap<>();
    List<Animal> deadAnimals = new ArrayList<>();
    static Random random = new Random();

    public AbstractMap(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
        currentDay = 1;
    }

    public void addAnimal(Vector2d position, Animal animal){
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

    public void addPlant(Vector2d position, Plant plant){
        plantMap.put(position, plant);
    }

    public void prepareMap(int numberOfAnimals, int numberOfPlants){
        for (int i = 0; i < numberOfAnimals; i++){
            Vector2d randomVector = Vector2d.generateRandomVector(globalSettings.mapWidth(), globalSettings.mapHeight());
            Animal newAnimal = new Animal(Animal.generateRandomGenList(globalSettings.genomLength()),
                    randomVector,
                    globalSettings.initialEnergy(),
                    globalSettings);
            newAnimal.setBornDay(currentDay);
            addAnimal(randomVector, newAnimal);
        }
        for (int i = 0; i < numberOfPlants; i++){
            growPlant();
        }
    }

    public void deleteDeadAnimals() {
        Collection<List<Animal>> values = new ArrayList<>(animalsMap.values());
        animalsMap.clear();
        values.forEach(animalList -> {
            for (Animal animal : animalList){
                if (animal.getEnergy() > 0){
                    addAnimal(animal.getPosition(), animal);
                }
                else{
                    animal.setDayOfDeath(currentDay);
                    animal.setLifeSpan(currentDay - animal.getBornDay());
                    deadAnimals.add(animal);
                }
            }});
    }

    public void animalReproduce() {
        animalsMap.forEach(((position, animals) -> {
            if (animals.size() >= 2){
                List<Animal> animalList = figureOutAnimalReproductionConflict(position);
                Animal animal = animalList.get(0).produce(animalList.get(1));
                if (animal != null){
                    animals.add(animal);
                    animalsMap.put(animal.getPosition(), animals);
                }
            }
        }));
    }

    public int getAverageEnergy(){
        AtomicInteger energy = new AtomicInteger(0);
        AtomicInteger animalsCounter = new AtomicInteger(0);
        animalsMap.forEach((vector2d, animals) -> animals.forEach(animal -> energy.addAndGet(animal.getEnergy())));
        animalsMap.forEach((vector2d, animals) -> animals.forEach(animal -> animalsCounter.addAndGet(1)));
        return energy.get() / animalsCounter.get();
    }

    public List<Animal> figureOutAnimalReproductionConflict(Vector2d position){
        Animal animal1;
        Animal animal2;
        if (animalsMap.get(position).size() > 2){
            animal1 = figureOutEatingConflict(position);
            animalsMap.get(position).remove(animal1);
            animal2 = figureOutEatingConflict(position);
            animalsMap.get(position).add(animal1);
        }
        else{
            animal1 = animalsMap.get(position).get(0);
            animal2 = animalsMap.get(position).get(1);
        }
        return new ArrayList<>(List.of(animal1, animal2));
    }

    public Animal figureOutEatingConflict(Vector2d position){
        List<Animal> animalList = animalsMap.get(position);
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
        else{
            return animal1;
        }
    }
    protected void growPlant(){
        int widthOfGreenArea;
        int startingRow;
        if (globalSettings.mapHeight() % 2 == 0){
            widthOfGreenArea = globalSettings.mapWidth() / 5 + 1;
            startingRow = globalSettings.mapWidth() / 2 - widthOfGreenArea / 2;
        }
        else{
            widthOfGreenArea = globalSettings.mapWidth() / 5;
            if (widthOfGreenArea == 0){
                widthOfGreenArea = 1;
            }
            startingRow = globalSettings.mapWidth() / 2 - widthOfGreenArea / 2;
        }
        int x = random.nextInt(globalSettings.mapWidth());
        int y;
        int n = random.nextInt(5);
        if (n == 4){
            y = random.nextInt(globalSettings.mapHeight());
            while (y >= startingRow && y <= startingRow + widthOfGreenArea){
                y = random.nextInt(globalSettings.mapHeight());
            }
        }
        else{
            y = random.nextInt(startingRow, startingRow + widthOfGreenArea);
        }
        plantMap.put(new Vector2d(x, y), new Plant());
    }

    public String getMostPopularGenotype() {
        Map<List<Integer>, Integer> genotypeMap = new HashMap<>();
        animalsMap.forEach((vector2d, animalList) -> {
            for (Animal animal : animalList){
                if (genotypeMap.containsKey(animal.getGenList())){
                    Integer value = genotypeMap.get(animal.getGenList());
                    genotypeMap.put(animal.getGenList(), value+1);
                }
                else{
                    genotypeMap.put(animal.getGenList(), 1);
                }
            }
        });
        List<List<Integer>> lists = genotypeMap.keySet().stream().toList();
        List<Integer> mostPopularGenotype = lists.get(0);
        int mostNumber = genotypeMap.get(lists.get(0));
        for (List<Integer> list : lists){
            if (genotypeMap.get(list) > mostNumber){
                mostNumber = genotypeMap.get(list);
                mostPopularGenotype = list;
            }
        }
        return mostPopularGenotype.toString();
    }

    public Map<Vector2d, List<Animal>> getAnimalsMap() {
        return animalsMap;
    }

    public Map<Vector2d, Plant> getPlantMap() {
        return plantMap;
    }
    public int getAnimalNumber() {
        int count = 0;
        Collection<List<Animal>> values = new ArrayList<>(animalsMap.values());
        for (List animalList : values){
            count += animalList.size();
        }
        return count;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public int getAverageLifeSpan(){
        if (deadAnimals.isEmpty()){
            return 0;
        }
        int totalAge = 0;
        for (Animal deadAnimal: deadAnimals){
            try{
                totalAge += deadAnimal.getLifeSpan();
            }
            catch (AnimalNotDeadYetException e){
                e.printStackTrace();
            }
        }
        return totalAge / deadAnimals.size();
    }
    public int getPlantNumber() {
        return plantMap.values().size();
    }

    public int getCurrentDay(){
        return currentDay;
    }
}
