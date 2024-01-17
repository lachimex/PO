package project.Maps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.BehaviourVariant;
import project.GlobalSettings;
import project.MapElements.Animal;
import project.MapElements.AnimalProducer;

import java.util.List;

public class AbstractMapTest {
    GlobalSettings globalSettings = new GlobalSettings(10, 10, MapVariant.GLOBE,
            1, 1, 1, 1, 1,
            1, 1, 1, 1,
            BehaviourVariant.LITTLE_BIT_OF_CRAZINESS, 1, false);
    GlobeMap globeMap = new GlobeMap(globalSettings); //i use globeMap as a representant of abstract class
    AnimalProducer animalProducer = new AnimalProducer(globalSettings);
    List<Integer> genList1 = List.of(1);
    List<Integer> genList2 = List.of(1, 5);
    Animal animal1 = new Animal(genList1, new Vector2d(2,3), 10, globalSettings);
    Animal animal2 = new Animal(genList2, new Vector2d(2,3), 20, globalSettings);
    Animal animal3 = new Animal(genList2, new Vector2d(2,3), 11, globalSettings);
    {
        globeMap.addAnimal(animal1.getPosition(), animal1);
        globeMap.addAnimal(animal2.getPosition(), animal2);
    }
    @Test
    public void testAnimalAdd(){
        Assertions.assertEquals(globeMap.getAnimalNumber(), 2);
    }

    @Test
    public void testAverageEnergy(){
        Assertions.assertEquals(globeMap.getAverageEnergy(), 15);
    }

    @Test
    public void testConflicts(){
        globeMap.addAnimal(animal3.getPosition(), animal3);
        Animal animal4 = animalProducer.produce(animal2, animal3, 1);
        globeMap.addAnimal(animal4.getPosition(), animal4);
        List<Animal> animalList = globeMap.figureOutAnimalReproductionConflict(animal1.getPosition());
        Assertions.assertEquals(animalList.get(0), animal2);
        Assertions.assertEquals(animalList.get(1), animal3);

        Animal wonAnimal = globeMap.figureOutEatingConflict(animal1.getPosition());
        Assertions.assertEquals(wonAnimal, animal2);
    }

    @Test
    public void testDeadAnimalsDeletion(){
        globeMap.addAnimal(animal3.getPosition(), animal3);
        Animal animal4 = animalProducer.produce(animal2, animal3, 1);
        globeMap.addAnimal(animal4.getPosition(), animal4);
        globeMap.moveEachAnimal();
        globeMap.moveEachAnimal();
        globeMap.deleteDeadAnimals();
        Assertions.assertEquals(globeMap.getAnimalNumber(), 3);
    }
}
