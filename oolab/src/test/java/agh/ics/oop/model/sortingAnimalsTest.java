package agh.ics.oop.model;

import agh.ics.oop.model.exceptions.PositionAlreadyOccupiedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class sortingAnimalsTest {

    @Test
    public void testSorting(){
        GrassField grassField = new GrassField(1);
        Animal animal1 = new Animal(new Vector2d(0, 0));
        Animal animal2 = new Animal(new Vector2d(7, 3));
        Animal animal3 = new Animal(new Vector2d(2, 1));
        Animal animal4 = new Animal(new Vector2d(2, 7));
        try {
            grassField.place(animal3);
        } catch (PositionAlreadyOccupiedException e) {
            throw new RuntimeException(e);
        }
        try {
            grassField.place(animal2);
        } catch (PositionAlreadyOccupiedException e) {
            throw new RuntimeException(e);
        }
        try {
            grassField.place(animal1);
        } catch (PositionAlreadyOccupiedException e) {
            throw new RuntimeException(e);
        }
        try {
            grassField.place(animal4);
        } catch (PositionAlreadyOccupiedException e) {
            throw new RuntimeException(e);
        }
        List<Animal> animalList = grassField.getOrderedAnimals().stream().toList();
        Assertions.assertArrayEquals(animalList.toArray(), List.of(animal1, animal3, animal4, animal2).toArray());
    }


}
