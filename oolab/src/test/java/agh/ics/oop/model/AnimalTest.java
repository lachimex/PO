package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
    private MoveValidator moveValidator = new RectangularMap(5, 5);
    @Test
    public void testMoveRotation(){
        //given
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(2,3));

        //when
        animal1.move(MoveDirection.RIGHT, moveValidator);
        animal2.move(MoveDirection.RIGHT, moveValidator);

        //then
        assertEquals(animal1.getAnimalPosition(), new Vector2d(2, 2));
        assertEquals(animal1.getAnimalOrientation(), MapDirection.EAST);
        assertEquals(animal2.getAnimalPosition(), new Vector2d(2, 3));
        assertEquals(animal2.getAnimalOrientation(), MapDirection.EAST);

        //when
        animal1.move(MoveDirection.LEFT, moveValidator);
        animal1.move(MoveDirection.LEFT, moveValidator);
        animal2.move(MoveDirection.LEFT, moveValidator);
        animal2.move(MoveDirection.LEFT, moveValidator);

        //then
        assertEquals(new Vector2d(2, 2), animal1.getAnimalPosition());
        assertEquals(MapDirection.WEST, animal1.getAnimalOrientation());
        assertEquals(new Vector2d(2, 3), animal2.getAnimalPosition());
        assertEquals(MapDirection.WEST, animal2.getAnimalOrientation());
    }

    @Test
    public void testMoveForward(){
        //given
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(2,3));

        //when
        animal1.move(MoveDirection.FORWARD, moveValidator);
        animal2.move(MoveDirection.FORWARD, moveValidator);

        //then
        assertEquals(new Vector2d(2, 3), animal1.getAnimalPosition());
        assertEquals(new Vector2d(2, 4), animal2.getAnimalPosition());

        //when
        animal1.move(MoveDirection.RIGHT, moveValidator);
        animal1.move(MoveDirection.FORWARD, moveValidator);
        animal2.move(MoveDirection.LEFT, moveValidator);
        animal2.move(MoveDirection.FORWARD, moveValidator);

        //then
        assertEquals(new Vector2d(3, 3), animal1.getAnimalPosition());
        assertEquals(new Vector2d(1, 4), animal2.getAnimalPosition());
    }

    @Test
    public void testMoveBackward(){
        //given
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(2,3));

        //when
        animal1.move(MoveDirection.BACKWARD, moveValidator);
        animal2.move(MoveDirection.BACKWARD, moveValidator);

        //then
        assertEquals(new Vector2d(2, 1), animal1.getAnimalPosition());
        assertEquals(new Vector2d(2, 2), animal2.getAnimalPosition());

        //when
        animal1.move(MoveDirection.RIGHT, moveValidator);
        animal1.move(MoveDirection.BACKWARD, moveValidator);
        animal2.move(MoveDirection.LEFT, moveValidator);
        animal2.move(MoveDirection.BACKWARD, moveValidator);

        //then
        assertEquals(new Vector2d(1, 1), animal1.getAnimalPosition());
        assertEquals(new Vector2d(3, 2), animal2.getAnimalPosition());
    }

    @Test
    public void testBoundaries(){
        //given
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(0, 0));

        //when
        animal1.move(MoveDirection.FORWARD, moveValidator);
        animal1.move(MoveDirection.FORWARD, moveValidator);
        animal1.move(MoveDirection.FORWARD, moveValidator);
        animal2.move(MoveDirection.BACKWARD, moveValidator);

        //then
        assertEquals(new Vector2d(2, 4), animal1.getAnimalPosition());
        assertEquals(new Vector2d(0, 0), animal2.getAnimalPosition());
    }
}