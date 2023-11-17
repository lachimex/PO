package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {

    //given
    private RectangularMap rectangularMap = new RectangularMap(5, 5);
    private Animal animal1 = new Animal(new Vector2d(3, 3));
    private Animal animal2 = new Animal(new Vector2d(1, 1));
    private Animal animal3 = new Animal(new Vector2d(1, 1));


    @Test
    public void placeTest(){
        //then
        assertTrue(rectangularMap.place(animal1));
        assertTrue(rectangularMap.place(animal2));
        assertFalse(rectangularMap.place(animal3));
    }

    @Test
    public void objectAtTest(){
        rectangularMap.place(animal1);
        rectangularMap.place(animal2);

        assertEquals(rectangularMap.objectAt(animal1.getAnimalPosition()), animal1);
        assertEquals(rectangularMap.objectAt(animal2.getAnimalPosition()), animal2);
    }

    @Test
    public void isOccupiedTest(){
        rectangularMap.place(animal1);
        rectangularMap.place(animal2);
        assertTrue(rectangularMap.isOccupied(new Vector2d(3, 3)));
        assertTrue(rectangularMap.isOccupied(new Vector2d(1, 1)));
        assertFalse(rectangularMap.isOccupied(new Vector2d(0, 0)));
    }

    @Test
    public void canMoveToTest(){
        rectangularMap.place(animal1);
        rectangularMap.place(animal2);
        assertFalse(rectangularMap.canMoveTo(new Vector2d(3, 3)));
        assertTrue(rectangularMap.canMoveTo(new Vector2d(3, 4)));
    }

    @Test
    public void moveTest(){
        rectangularMap.place(animal1);
        rectangularMap.place(animal2);
        rectangularMap.move(animal1, MoveDirection.FORWARD);
        rectangularMap.move(animal2, MoveDirection.FORWARD);
        assertTrue(rectangularMap.isOccupied(new Vector2d(3, 4)));
        assertTrue(rectangularMap.isOccupied(new Vector2d(1, 2)));
    }
}
