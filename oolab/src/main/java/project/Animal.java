package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal {
    private List<Integer> genList;
    private List<Animal> descendantList = new ArrayList<>();
    private Random random = new Random();
    private MapDirection direction = MapDirection.NORTH;
    private int activeGen;
    private int energy;
    private int childCounter;
    private int plantEatenCounter;
    private int age;
    boolean alive;
    private int dayOfDeath;
    public Animal(List<Integer> genList, int initialEnergy) {
        this.genList = genList;
        this.energy = initialEnergy;
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

    void eat(int energyAdded){
        energy += energyAdded;
    }

    Animal produce(Animal secondParent, int energyNeededToReproduce, int energyLossDuringReproduction){
        if (this.energy < energyNeededToReproduce || secondParent.getEnergy() < energyNeededToReproduce){
            return null;
        }
        else{
            int sumParentEnergy = this.getEnergy() + secondParent.getEnergy();
            int side = random.nextInt(2);
            int indexOfCrossingGens;
            if (secondParent.getEnergy() >= this.getEnergy()){ //second parent is stronger than this
                if (side == 0){ //leftside of stronger animal
                    indexOfCrossingGens = (int) genList.size() * secondParent.getEnergy() / sumParentEnergy;
                }
                else{
                    indexOfCrossingGens = (int) genList.size() * (1 - secondParent.getEnergy() / sumParentEnergy);
                }
            }
            else{ //this animal is stronger than second parent
                if (side == 0){ //leftside of stronger animal
                    indexOfCrossingGens = (int) genList.size() * this.getEnergy() / sumParentEnergy;
                }
                else{
                    indexOfCrossingGens = (int) genList.size() * (1 - this.getEnergy() / sumParentEnergy);
                }
            }
        }
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

}
