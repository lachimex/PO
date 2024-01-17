package project.MapElements;

import org.junit.jupiter.api.Test;
import project.BehaviourVariant;
import project.GlobalSettings;
import project.Maps.MapVariant;
import project.Maps.Vector2d;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    GlobalSettings globalSettings = new GlobalSettings(3, 3, MapVariant.GLOBE,
            1, 1, 1, 1, 1,
            1, 1, 1, 1,
            BehaviourVariant.LITTLE_BIT_OF_CRAZINESS, 1, false);

    @Test
    void testAnimalInitialization() {
        Vector2d position = new Vector2d(2, 3);
        List<Integer> genList = Animal.generateRandomGenList(5);

        Animal animal = new Animal(genList, position, 50, globalSettings);

        assertNotNull(animal);
        assertEquals(position, animal.getPosition());
        assertEquals(50, animal.getEnergy());
        assertEquals(0, animal.getChildCounter());
        assertEquals(0, animal.getPlantEatenCounter());
    }

    @Test
    void testEat() {
        Vector2d position = new Vector2d(2, 3);
        List<Integer> genList = Animal.generateRandomGenList(5);
        Animal animal = new Animal(genList, position, 50, globalSettings);

        int initialEnergy = animal.getEnergy();
        int initialPlantEatenCounter = animal.getPlantEatenCounter();

        animal.eat();

        assertEquals(initialEnergy + globalSettings.energyGainOnEat(), animal.getEnergy());
        assertEquals(initialPlantEatenCounter + 1, animal.getPlantEatenCounter());
    }

    @Test
    void testProduce(){
        Vector2d position = new Vector2d(2, 3);
        List<Integer> genList = Animal.generateRandomGenList(5);
        Animal animal1 = new Animal(genList, position, 50, globalSettings);
        Animal animal2 = new Animal(genList, position, 50, globalSettings);
        AnimalProducer animalProducer = new AnimalProducer(globalSettings);
        Animal animal3 = animalProducer.produce(animal1, animal2, 1);
        assertEquals(animal3.getAge(), 0);
        assertEquals(animal3.getEnergy(), 2 * globalSettings.energyLossDuringReproduction());
        assertEquals(animal3.getBornDay(), 1);
        assertEquals(animal3.getPosition(), position);
        assertEquals(animal1.getChildCounter(), 1);
        assertEquals(animal2.getChildCounter(), 1);
    }
}