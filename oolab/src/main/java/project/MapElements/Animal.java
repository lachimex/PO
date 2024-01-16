package project.MapElements;

import project.BehaviourVariant;
import project.Exceptions.AnimalNotDeadYetException;
import project.GlobalSettings;
import project.Maps.MapDirection;
import project.Maps.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements MapElement {
    GlobalSettings globalSettings;
    private List<Integer> genList;
    private List<Animal> childList = new ArrayList<>();
    private static Random random = new Random();
    private MapDirection direction;
    private Vector2d position;
    private int activeGen;
    private int energy;
    int childCounter;
    private int plantEatenCounter;
    public int age;
    boolean alive;
    private Integer dayOfDeath = null;
    private Integer lifeSpan = null;
    private int bornDay;

    private boolean ifTracked;

    public Animal(List<Integer> genList, Vector2d position, int energy, GlobalSettings globalSettings) {
        this.genList = genList;
        this.globalSettings = globalSettings;
        this.energy = energy;
        this.position = position;
        this.childCounter = 0;
        this.plantEatenCounter = 0;
        this.age = 0;
        this.alive = true;
        for (int gen : genList) {
            if (gen < 0 || gen > 7) {
                throw new IllegalArgumentException("gen does not exist in genom type");
            }
        }
        if (genList.isEmpty()) {
            throw new IllegalArgumentException("gen list cannot be empty");
        } else {
            this.activeGen = genList.get(random.nextInt(genList.size()));
            this.direction = MapDirection.intToMapDirection(activeGen);
        }
    }

    public void eat() {
        energy += globalSettings.energyGainOnEat();
        plantEatenCounter++;
    }

    public Animal produce(Animal secondParent, int currentDay) {
        int side = random.nextInt(2); //side of gens from stronger animal 0: left 1: right
        Animal strongerAnimal;
        Animal weakerAnimal;
        int indexOfCrossingGens;
        if (this.energy < globalSettings.energyNeededToReproduce() || secondParent.getEnergy() < globalSettings.energyNeededToReproduce()) {
            return null;
        } else {
            int sumParentEnergy = this.getEnergy() + secondParent.getEnergy();
            if (secondParent.getEnergy() >= this.getEnergy()) {
                strongerAnimal = secondParent; //second parent is stronger than this
                weakerAnimal = this;
                if (side == 0) { //leftside of stronger animal
                    indexOfCrossingGens = (int) genList.size() * secondParent.getEnergy() / sumParentEnergy;
                } else {
                    indexOfCrossingGens = (int) genList.size() * (1 - secondParent.getEnergy() / sumParentEnergy);
                }
            } else {
                strongerAnimal = this; //this animal is stronger than second parent
                weakerAnimal = secondParent;
                if (side == 0) { //leftside of stronger animal
                    indexOfCrossingGens = (int) genList.size() * this.getEnergy() / sumParentEnergy;
                } else {
                    indexOfCrossingGens = (int) genList.size() * (1 - this.getEnergy() / sumParentEnergy);
                }
            }
        }
        this.setEnergy(this.getEnergy() - globalSettings.energyLossDuringReproduction());
        secondParent.setEnergy(this.getEnergy() - globalSettings.energyLossDuringReproduction());
        this.childCounter += 1;
        secondParent.childCounter += 1;
        List<Integer> gensOfChild = new ArrayList<>();
        if (side == 0) {
            gensOfChild.addAll(strongerAnimal.genList.subList(0, indexOfCrossingGens));
            gensOfChild.addAll(weakerAnimal.genList.subList(indexOfCrossingGens, weakerAnimal.genList.size()));
        } else {
            gensOfChild.addAll(weakerAnimal.genList.subList(0, indexOfCrossingGens));
            gensOfChild.addAll(strongerAnimal.genList.subList(indexOfCrossingGens, weakerAnimal.genList.size()));
        }
        Animal child = new Animal(gensOfChild, this.position, globalSettings.energyLossDuringReproduction() * 2, globalSettings);
        child.mutate(globalSettings);
        this.addChild(child);
        secondParent.addChild(child);
        child.setBornDay(currentDay);
        return child;
    }

    public void move() {
        direction = MapDirection.intToMapDirection(
                genList.get(activeGen % genList.size()));
        position = position.add(direction.toUnitVector());
        if (globalSettings.behaviourVariant().equals(BehaviourVariant.LITTLE_BIT_OF_CRAZINESS)) {
            if (random.nextInt(5) == 4) {
                activeGen += random.nextInt(genList.size());
            } else {
                activeGen++;
            }
        } else {
            activeGen++;
        }
        energy--;
    }

    public int getDescendants() {
        int descendants_number = 0;
        if (childList.isEmpty()){
            return descendants_number;
        }
        for (Animal child : childList){
            descendants_number += 1 + child.getDescendants();
        }
        return descendants_number;
    }
    
    void addChild(Animal descendant){
        childList.add(descendant);
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    private void mutate(GlobalSettings globalSettings) {
        int numberOfMutations = random.nextInt(globalSettings.minimalNumberOfMutations(), globalSettings.maximumNumberOfMutations() + 1);
        for (int i = 0; i < numberOfMutations; i++) {
            int indexOfMutation = random.nextInt(globalSettings.genomLength());
            int toWhatGen = random.nextInt(8);
            this.genList.set(indexOfMutation, toWhatGen);
        }
    }

    public static List<Integer> generateRandomGenList(int numberOfGens) {
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < numberOfGens; i++) {
            out.add(random.nextInt(8));
        }
        return out;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public int getBornDay() {
        return bornDay;
    }

    public void setBornDay(int bornDay) {
        this.bornDay = bornDay;
    }

    public int getAge() {
        return age;
    }

    public int getChildCounter() {
        return childCounter;
    }

    public List<Animal> getChildList(){
        return childList;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public int getDayOfDeath() throws AnimalNotDeadYetException {
        if (dayOfDeath == null) {
            throw new AnimalNotDeadYetException(this);
        }
        return dayOfDeath;
    }

    public int getLifeSpan() throws AnimalNotDeadYetException {
        if (lifeSpan == null) {
            throw new AnimalNotDeadYetException(this);
        }
        return lifeSpan;
    }

    public void setLifeSpan(Integer lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public List<Integer> getGenList() {
        return genList;
    }

    public int getActiveGen() {
        return activeGen;
    }

    public int getPlantEatenCounter() {
        return plantEatenCounter;
    }

    public boolean isTracked() {
        return ifTracked;
    }

    public void setIfTracked(boolean ifTracked) {
        this.ifTracked = ifTracked;
    }

    @Override
    public String toString(){
        return position.toString() + " " + direction.toString();
    }
}
