package project.MapElements;

import java.util.Comparator;
import java.util.Random;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal animal1, Animal animal2) {
        if (animal1.getEnergy() != animal2.getEnergy()) {
            return animal1.getEnergy() - animal2.getEnergy();
        } else {
            if (animal1.getAge() != animal2.getAge()) {
                return animal1.getAge() - animal2.getAge();
            } else {
                if (animal1.getChildCounter() != animal2.getChildCounter()) {
                    return animal1.getChildCounter() - animal2.getChildCounter();
                } else {
                    Random random = new Random();
                    return random.nextInt(2);
                }
            }
        }
    }
}
