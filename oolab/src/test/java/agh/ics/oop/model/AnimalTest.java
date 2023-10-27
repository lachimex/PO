package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
    @Test
    public void testMoveRotationRight(){
        //given
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(2,3));

        //when
        animal1.move(MoveDirection.RIGHT);
        animal2.move(MoveDirection.RIGHT);

        //then
        assertEquals(animal1.getAnimalPosition(), new Vector2d(2, 2));
        assertEquals(animal1.getAnimalOrientation(), MapDirection.EAST);
        assertEquals(animal2.getAnimalPosition(), new Vector2d(2, 3));
        assertEquals(animal2.getAnimalOrientation(), MapDirection.EAST);

    }
}
