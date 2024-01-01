package project.MapElements;

import project.GlobalSettings;
import project.Maps.MapDirection;
import project.Maps.Vector2d;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements MapElement {
    GlobalSettings globalSettings;
    private List<Integer> genList;
    private List<Animal> descendantList = new ArrayList<>();
    private static Random random = new Random();
    private MapDirection direction = MapDirection.intToMapDirection(random.nextInt(8));
    private Vector2d position;
    private int activeGen;
    private int energy;
    int childCounter;
    private int plantEatenCounter;
    public int age;
    boolean alive;
    private int dayOfDeath;
    public Animal(List<Integer> genList, Vector2d position, int energy, GlobalSettings globalSettings) {
        this.genList = genList;
        this.globalSettings = globalSettings;
        this.energy = energy;
        this.position = position;
        this.childCounter = 0;
        this.plantEatenCounter = 0;
        this.age = 0;
        this.alive = true;
        for (int gen : genList){
            if (gen < 0 || gen > 7){
                throw new IllegalArgumentException("gen does not exist in genom type");
            }
        }
        if (genList.isEmpty()){
            throw new IllegalArgumentException("gen list cannot be empty");
        }
        else{
            this.activeGen = genList.get(random.nextInt(genList.size()));
        }
    }

    public void eat(){
        energy += globalSettings.energyGainOnEat();
        plantEatenCounter++;
    }

    public Animal produce(Animal secondParent){
        int side = random.nextInt(2); //side of gens from stronger animal 0: left 1: right
        Animal strongerAnimal;
        Animal weakerAnimal;
        int indexOfCrossingGens;
        if (this.energy < globalSettings.energyNeededToReproduce() || secondParent.getEnergy() < globalSettings.energyNeededToReproduce()){
            return null;
        }
        else{
            int sumParentEnergy = this.getEnergy() + secondParent.getEnergy();
            if (secondParent.getEnergy() >= this.getEnergy()){
                strongerAnimal = secondParent; //second parent is stronger than this
                weakerAnimal = this;
                if (side == 0){ //leftside of stronger animal
                    indexOfCrossingGens = (int) genList.size() * secondParent.getEnergy() / sumParentEnergy;
                }
                else{
                    indexOfCrossingGens = (int) genList.size() * (1 - secondParent.getEnergy() / sumParentEnergy);
                }
            }
            else{
                strongerAnimal = this; //this animal is stronger than second parent
                weakerAnimal = secondParent;
                if (side == 0){ //leftside of stronger animal
                    indexOfCrossingGens = (int) genList.size() * this.getEnergy() / sumParentEnergy;
                }
                else{
                    indexOfCrossingGens = (int) genList.size() * (1 - this.getEnergy() / sumParentEnergy);
                }
            }
        }
        this.setEnergy(this.getEnergy() - globalSettings.energyLossDuringReproduction());
        secondParent.setEnergy(this.getEnergy() - globalSettings.energyLossDuringReproduction());
        this.childCounter += 1;
        secondParent.childCounter += 1;
        List<Integer> gensOfChild = new ArrayList<>();
        if (side == 0){
            gensOfChild.addAll(strongerAnimal.genList.subList(0, indexOfCrossingGens + 1));
            gensOfChild.addAll(weakerAnimal.genList.subList(indexOfCrossingGens + 1, weakerAnimal.genList.size()));
        }
        else{
            gensOfChild.addAll(weakerAnimal.genList.subList(0, indexOfCrossingGens + 1));
            gensOfChild.addAll(strongerAnimal.genList.subList(indexOfCrossingGens + 1, weakerAnimal.genList.size()));
        }
        Animal child = new Animal(gensOfChild, this.position, globalSettings.energyLossDuringReproduction() * 2, globalSettings);
        child.mutate(globalSettings);
        return child;
    }

    public void move(){
        direction = MapDirection.intToMapDirection(
                genList.get(activeGen % genList.size()));
        position = position.add(direction.toUnitVector());
        activeGen++;
        energy--;
    }

    public int getEnergy(){
        return this.energy;
    }

    int getDescendants(){
        int descendants_number = 0;
        if (descendantList.isEmpty()){
            return descendants_number;
        }
        for (Animal descendant : descendantList){
            descendants_number += 1 + descendant.getDescendants();
        }
        return descendants_number;
    }

    void addDescendant(Animal descendant){
        descendantList.add(descendant);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    private void mutate(GlobalSettings globalSettings){
        int numberOfMutations = random.nextInt(globalSettings.minimalNumberOfMutations(), globalSettings.maximumNumberOfMutations());
        for (int i = 0; i < numberOfMutations; i++){
            int indexOfMutation = random.nextInt(globalSettings.genomLength());
            int toWhatGen = random.nextInt(8);
            this.genList.set(indexOfMutation, toWhatGen);
        }
    }

    public static List<Integer> generateRandomGenList(int numberOfGens){
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < numberOfGens; i++){
            out.add(random.nextInt(8));
        }
        return out;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public int getAge() {
        return age;
    }

    public int getChildCounter() {
        return childCounter;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public MapDirection getDirection() {
        return direction;
    }
}
