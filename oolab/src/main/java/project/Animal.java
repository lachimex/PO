package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal {
    GlobalSettings globalSettings;
    private List<Integer> genList;
    private List<Animal> descendantList = new ArrayList<>();
    private Random random = new Random();
    private MapDirection direction = MapDirection.NORTH;
    private int activeGen;
    private int energy;
    int childCounter;
    private int plantEatenCounter;
    private int age;
    boolean alive;
    private int dayOfDeath;
    public Animal(List<Integer> genList, int energy, GlobalSettings globalSettings) {
        this.genList = genList;
        this.globalSettings = globalSettings;
        this.energy = energy;
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

    void eat(){
        energy += globalSettings.energyGainOnEat();
    }

    Animal produce(Animal secondParent){
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
        return new Animal(gensOfChild, globalSettings.energyLossDuringReproduction() * 2, globalSettings);
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
}
