package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrassFieldTest {
    private GrassField grassField = new GrassField(10);
    private Animal animal1 = new Animal(new Vector2d(3, 3));
    private Animal animal2 = new Animal(new Vector2d(1, 1));
    private Animal animal3 = new Animal(new Vector2d(1, 1));

    @Test
    public void placeTest(){
        //then
        assertTrue(grassField.place(animal1));
        assertTrue(grassField.place(animal2));
        assertFalse(grassField.place(animal3));
    }

    @Test
    public void objectAtTest(){
        grassField.place(animal1);
        grassField.place(animal2);

        assertEquals(grassField.objectAt(animal1.getPosition()), animal1);
        assertEquals(grassField.objectAt(animal2.getPosition()), animal2);
    }

    @Test
    public void isOccupiedTest(){
        grassField.place(animal1);
        grassField.place(animal2);
        assertTrue(grassField.isOccupied(new Vector2d(3, 3)));
        assertTrue(grassField.isOccupied(new Vector2d(1, 1)));
        assertFalse(grassField.isOccupied(new Vector2d(0, 0)));
    }

    @Test
    public void canMoveToTest(){
        grassField.place(animal1);
        grassField.place(animal2);
        assertFalse(grassField.canMoveTo(new Vector2d(3, 3)));
        assertTrue(grassField.canMoveTo(new Vector2d(3, 4)));
    }

    @Test
    public void moveTest(){
        grassField.place(animal1);
        grassField.place(animal2);
        grassField.move(animal1, MoveDirection.FORWARD);
        grassField.move(animal2, MoveDirection.FORWARD);
        assertTrue(grassField.isOccupied(new Vector2d(3, 4)));
        assertTrue(grassField.isOccupied(new Vector2d(1, 2)));
    }
}

