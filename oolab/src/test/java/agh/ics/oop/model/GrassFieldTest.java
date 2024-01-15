package agh.ics.oop.model;

import agh.ics.oop.model.exceptions.PositionAlreadyOccupiedException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrassFieldTest {
    private GrassField grassField = new GrassField(10);
    private Animal animal1 = new Animal(new Vector2d(3, 3));
    private Animal animal2 = new Animal(new Vector2d(1, 1));
    private Animal animal3 = new Animal(new Vector2d(1, 1));

    private void placeAnimal1AndAnimal2(){
        try{
            grassField.place(animal1);
            grassField.place(animal2);
        } catch (PositionAlreadyOccupiedException e){
            System.out.println("Placing animal1 and animal2 was not satisfied");
        }
    }


    @Test
    public void placeTest(){
        //then
        try{
            assertTrue(grassField.place(animal1));
            assertTrue(grassField.place(animal2));
        } catch (PositionAlreadyOccupiedException e){
            fail();
        }
        Exception exception = assertThrows(PositionAlreadyOccupiedException.class, () -> {
            grassField.place(animal3);
        });
        assertEquals(exception.getMessage(), "Position (1, 1) is already occupied");
    }

    @Test
    public void objectAtTest(){
        placeAnimal1AndAnimal2();

        assertEquals(grassField.objectAt(animal1.getPosition()), Optional.of(animal1));
        assertEquals(grassField.objectAt(animal2.getPosition()), Optional.of(animal2));
    }

    @Test
    public void isOccupiedTest(){
        placeAnimal1AndAnimal2();
        assertTrue(grassField.isOccupied(new Vector2d(3, 3)));
        assertTrue(grassField.isOccupied(new Vector2d(1, 1)));
        assertFalse(grassField.isOccupied(new Vector2d(0, 0)));
    }

    @Test
    public void canMoveToTest(){
        placeAnimal1AndAnimal2();
        assertFalse(grassField.canMoveTo(new Vector2d(3, 3)));
        assertTrue(grassField.canMoveTo(new Vector2d(3, 4)));
    }

    @Test
    public void moveTest(){
        placeAnimal1AndAnimal2();
        grassField.move(animal1, MoveDirection.FORWARD);
        grassField.move(animal2, MoveDirection.FORWARD);
        assertTrue(grassField.isOccupied(new Vector2d(3, 4)));
        assertTrue(grassField.isOccupied(new Vector2d(1, 2)));
    }
}

