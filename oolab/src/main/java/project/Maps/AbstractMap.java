package project.Maps;

import project.Exceptions.AnimalNotDeadYetException;
import project.GlobalSettings;
import project.MapElements.Animal;
import project.MapElements.AnimalsGroup;
import project.MapElements.Plant;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractMap {
    protected final GlobalSettings globalSettings;
    protected int currentDay;
    protected int widthOfGreenArea;
    protected int startingRow;


    Map<Vector2d, AnimalsGroup> animalsMap = new HashMap<>();
    Map<Vector2d, Plant> plantMap = new HashMap<>();
    List<Animal> deadAnimals = new ArrayList<>();
    static Random random = new Random();

    public AbstractMap(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
        currentDay = 1;

        if (globalSettings.mapHeight() % 2 == 0){
            this.widthOfGreenArea = globalSettings.mapWidth() / 5 + 1;
            this.startingRow = globalSettings.mapWidth() / 2 - widthOfGreenArea / 2;
        }
        else{
            this.widthOfGreenArea = globalSettings.mapWidth() / 5;
            if (widthOfGreenArea == 0){
                widthOfGreenArea = 1;
            }
            this.startingRow = globalSettings.mapWidth() / 2 - widthOfGreenArea / 2;
        }
    }

    public void addAnimal(Vector2d position, Animal animal){
        if (animalsMap.containsKey(position)){
            animalsMap.get(position).addAnimal(animal);
        }
        else{
            animalsMap.put(position, new AnimalsGroup(position));
            animalsMap.get(position).addAnimal(animal);
        }
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
        Set<Vector2d> vector2dSet = animalsMap.keySet();
        vector2dSet.forEach(vector2d -> {
            List<Animal> deadAnimalsFromPosition = animalsMap.get(vector2d).deleteDeadAnimals(currentDay);
            deadAnimals.addAll(deadAnimalsFromPosition);
        });
    }

    public void animalReproduce() {
        animalsMap.forEach(((position, animalsGroup) -> {
            if (animalsGroup.getSize() >= 2){
                List<Animal> animalList = figureOutAnimalReproductionConflict(position);
                Animal animal = animalList.get(0).produce(animalList.get(1), currentDay);
                if (animal != null){
                    animalsGroup.addAnimal(animal);
                }
            }
        }));
    }

    public int getAverageEnergy(){
        if (animalsMap.isEmpty()){
            return 0;
        }
        AtomicInteger energy = new AtomicInteger(0);
        animalsMap.forEach((vector2d, animalsGroup) -> energy.addAndGet(animalsGroup.getAverageEnergy()));
        return energy.get() / animalsMap.keySet().size();
    }

    public List<Animal> figureOutAnimalReproductionConflict(Vector2d position){
        return animalsMap.get(position).figureOutAnimalReproductionConflict();
    }

    public Animal figureOutEatingConflict(Vector2d position){
        return animalsMap.get(position).figureOutEatingConflict();
    }

    protected void growPlant(){
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
        animalsMap.forEach((vector2d, animalsGroup) -> {
            for (List<Integer> list : animalsGroup.getGenotypes()){
                if (genotypeMap.containsKey(list)){
                    Integer value = genotypeMap.get(list);
                    genotypeMap.put(list, value+1);
                }
                else{
                    genotypeMap.put(list, 1);
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

    public Map<Vector2d, AnimalsGroup> getAnimalsMap() {
        return animalsMap;
    }

    public Map<Vector2d, Plant> getPlantMap() {
        return plantMap;
    }
    public int getAnimalNumber() {
        AtomicInteger counter = new AtomicInteger();
        animalsMap.forEach((vector2d, animalsGroup) -> {
            counter.addAndGet(animalsGroup.getSize());
        });
        return counter.get();
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
    public int getWidthOfGreenArea(){
        return widthOfGreenArea;
    }
    public int getStartingRowOfGreenArea(){
        return startingRow;
    }
}
