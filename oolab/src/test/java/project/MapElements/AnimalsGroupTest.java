package project.MapElements;

import org.junit.jupiter.api.Test;
import project.BehaviourVariant;
import project.GlobalSettings;
import project.Maps.MapVariant;
import project.Maps.Vector2d;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalsGroupTest {
    GlobalSettings globalSettings = new GlobalSettings(3, 3, MapVariant.GLOBE,
            1, 1, 1, 1, 1,
            1, 1, 1, 1,
            BehaviourVariant.LITTLE_BIT_OF_CRAZINESS, 1);
    Vector2d position = new Vector2d(2, 3);
    List<Integer> genList = Animal.generateRandomGenList(5);
    Animal animal1 = new Animal(genList, position, 60, globalSettings);
    Animal animal2 = new Animal(genList, position, 50, globalSettings);
    Animal animal3 = new Animal(genList, position, 0, globalSettings);
    AnimalsGroup animalsGroup = new AnimalsGroup(position);
    {
        animalsGroup.addAnimal(animal1);
        animalsGroup.addAnimal(animal2);
        animalsGroup.addAnimal(animal3);
    }
    @Test
    void deleteDeadAnimalsTest(){
        animalsGroup.deleteDeadAnimals(1);
        assertEquals(animalsGroup.getSize(), 2);
    }

    @Test
    void eatingConflictTest(){
        Animal animal = animalsGroup.figureOutEatingConflict();
        assertEquals(animal, animal1);
    }

    @Test
    void reproductionConflictTest(){
        List<Animal> animalList = animalsGroup.figureOutAnimalReproductionConflict();
        assertEquals(animalList.get(0), animal1);
        assertEquals(animalList.get(1), animal2);
    }

    @Test
    void averageEnergyTest(){
        assertEquals(animalsGroup.getAverageEnergy(), 110/3);
    }
}
