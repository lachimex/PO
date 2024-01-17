package project.MapElements;

import project.GlobalSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalProducer {
    GlobalSettings globalSettings;
    private final Random random = new Random();

    public AnimalProducer(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
    }

    private void mutate(Animal animal, GlobalSettings globalSettings) {
        int numberOfMutations = random.nextInt(globalSettings.minimalNumberOfMutations(), globalSettings.maximumNumberOfMutations() + 1);
        for (int i = 0; i < numberOfMutations; i++) {
            int indexOfMutation = random.nextInt(globalSettings.genomLength());
            int toWhatGen = random.nextInt(8);
            animal.getGenList().set(indexOfMutation, toWhatGen);
        }
    }

    public Animal produce(Animal firstParent, Animal secondParent, int currentDay){
        int side = random.nextInt(2); //side of gens from stronger animal 0: left 1: right
        Animal strongerAnimal;
        Animal weakerAnimal;
        int indexOfCrossingGens;
        if (firstParent.getEnergy() < globalSettings.energyNeededToReproduce() || secondParent.getEnergy() < globalSettings.energyNeededToReproduce()) {
            return null;
        } else {
            int sumParentEnergy = firstParent.getEnergy() + secondParent.getEnergy();
            if (secondParent.getEnergy() >= firstParent.getEnergy()) {
                strongerAnimal = secondParent; //second parent is stronger than this
                weakerAnimal = firstParent;
                if (side == 0) { //leftside of stronger animal
                    indexOfCrossingGens = (int) firstParent.getGenListSize() * secondParent.getEnergy() / sumParentEnergy;
                } else {
                    indexOfCrossingGens = (int) secondParent.getGenListSize() * (1 - secondParent.getEnergy() / sumParentEnergy);
                }
            } else {
                strongerAnimal = firstParent; //this animal is stronger than second parent
                weakerAnimal = secondParent;
                if (side == 0) { //leftside of stronger animal
                    indexOfCrossingGens = (int) firstParent.getGenListSize() * firstParent.getEnergy() / sumParentEnergy;
                } else {
                    indexOfCrossingGens = (int) firstParent.getGenListSize() * (1 - firstParent.getEnergy() / sumParentEnergy);
                }
            }
        }
        firstParent.setEnergy(firstParent.getEnergy() - globalSettings.energyLossDuringReproduction());
        secondParent.setEnergy(secondParent.getEnergy() - globalSettings.energyLossDuringReproduction());
        firstParent.setChildCounter(firstParent.getChildCounter() + 1);
        secondParent.setChildCounter(secondParent.getChildCounter() + 1);
        List<Integer> gensOfChild = new ArrayList<>();
        if (side == 0) {
            gensOfChild.addAll(strongerAnimal.getGenList().subList(0, indexOfCrossingGens));
            gensOfChild.addAll(weakerAnimal.getGenList().subList(indexOfCrossingGens, weakerAnimal.getGenList().size()));
        } else {
            gensOfChild.addAll(weakerAnimal.getGenList().subList(0, indexOfCrossingGens));
            gensOfChild.addAll(strongerAnimal.getGenList().subList(indexOfCrossingGens, weakerAnimal.getGenList().size()));
        }
        Animal child = new Animal(gensOfChild, firstParent.getPosition(), globalSettings.energyLossDuringReproduction() * 2, globalSettings);
        mutate(child, globalSettings);
        firstParent.addChild(child);
        secondParent.addChild(child);
        child.setBornDay(currentDay);
        return child;
    }
}
