package agh.ics.oop.model;

import agh.ics.oop.model.exceptions.PositionAlreadyOccupiedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {

    //given
    private RectangularMap rectangularMap = new RectangularMap(5, 5);
    private Animal animal1 = new Animal(new Vector2d(3, 3));
    private Animal animal2 = new Animal(new Vector2d(1, 1));
    private Animal animal3 = new Animal(new Vector2d(1, 1));

    private void placeAnimal1AndAnimal2(){
        try{
            rectangularMap.place(animal1);
            rectangularMap.place(animal2);
        } catch (PositionAlreadyOccupiedException e){
            System.out.println("Placing animal1 and animal2 was not satisfied");
        }
    }


    @Test
    public void placeTest(){
        //then
        try{
            assertTrue(rectangularMap.place(animal1));
            assertTrue(rectangularMap.place(animal2));
        } catch (PositionAlreadyOccupiedException e){
            fail();
        }
        Exception exception = assertThrows(PositionAlreadyOccupiedException.class, () -> {
            rectangularMap.place(animal3);
        });
        assertTrue(exception.getMessage().equals("Position (1, 1) is already occupied"));

    }

    @Test
    public void objectAtTest(){
        placeAnimal1AndAnimal2();

        assertEquals(rectangularMap.objectAt(animal1.getPosition()), animal1);
        assertEquals(rectangularMap.objectAt(animal2.getPosition()), animal2);
    }

    @Test
    public void isOccupiedTest(){
        placeAnimal1AndAnimal2();
        assertTrue(rectangularMap.isOccupied(new Vector2d(3, 3)));
        assertTrue(rectangularMap.isOccupied(new Vector2d(1, 1)));
        assertFalse(rectangularMap.isOccupied(new Vector2d(0, 0)));
    }

    @Test
    public void canMoveToTest(){
        placeAnimal1AndAnimal2();
        assertFalse(rectangularMap.canMoveTo(new Vector2d(3, 3)));
        assertTrue(rectangularMap.canMoveTo(new Vector2d(3, 4)));
    }

    @Test
    public void moveTest(){
        placeAnimal1AndAnimal2();
        rectangularMap.move(animal1, MoveDirection.FORWARD);
        rectangularMap.move(animal2, MoveDirection.FORWARD);
        assertTrue(rectangularMap.isOccupied(new Vector2d(3, 4)));
        assertTrue(rectangularMap.isOccupied(new Vector2d(1, 2)));
    }
}
