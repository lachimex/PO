package agh.ics.oop.model.textMap;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextMapTest {
    private TextMap textMap = new TextMap();

    private TextMapElement textMapElement1 = new TextMapElement("Ala");
    private TextMapElement stringTextMap2 = new TextMapElement("ma");
    private TextMapElement stringTextMap3 = new TextMapElement("sowoniedźwiedzia");

    private void placeElements(){
        textMap.place("Ala");
        textMap.place("ma");
    }

    @Test
    public void placeTest(){
        assertTrue(textMap.place("Ala"));
        assertTrue(textMap.place("ma"));
        assertTrue(textMap.place("sowoniedźwiedzia"));
        assertFalse(textMap.place("Ala"));
    }

    @Test
    public void isOccupiedTest(){
        placeElements();
        assertFalse(textMap.isOccupied(2));
        assertTrue(textMap.isOccupied(1));
    }
    @Test
    public void canMoveToTest(){
        placeElements();
        assertFalse(textMap.canMoveTo(2));
        assertTrue(textMap.canMoveTo(1));
    }
    @Test
    public void moveTest(){
        placeElements();
        textMap.place("sowoniedźwiedzia");

        textMap.move("Ala", MoveDirection.FORWARD);
        textMap.move("ma", MoveDirection.FORWARD);
        assertEquals(textMap.objectAt(0), textMapElement1);
        assertEquals(textMap.objectAt(1), stringTextMap2);

        textMap.move("Ala", MoveDirection.RIGHT);
        textMap.move("sowoniedźwiedzia", MoveDirection.RIGHT);
        textMap.move("Ala", MoveDirection.FORWARD);
        textMap.move("sowoniedźwiedzia", MoveDirection.BACKWARD);

        assertEquals(textMap.objectAt(0), stringTextMap2);
        assertEquals(textMap.objectAt(1), stringTextMap3);
        assertEquals(textMap.objectAt(2), textMapElement1);
    }
}
