package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TextMapTest {

    private TextMap textMap = new TextMap();

    private StringTextMap stringTextMap1 = new StringTextMap("Ala");
    private StringTextMap stringTextMap2 = new StringTextMap("ma");
    private StringTextMap stringTextMap3 = new StringTextMap("sowoniedźwiedzia");

    @Test
    public void placeTest(){
        assertTrue(textMap.place(stringTextMap1));
        assertTrue(textMap.place(stringTextMap2));
        assertTrue(textMap.place(stringTextMap3));
        assertFalse(textMap.place(new StringTextMap("Ala")));
    }

    @Test
    public void isOccupiedTest(){
        textMap.place(stringTextMap1);
        textMap.place(stringTextMap2);
        assertFalse(textMap.isOccupied(2));
        assertTrue(textMap.isOccupied(1));
    }
    @Test
    public void canMoveToTest(){
        textMap.place(stringTextMap1);
        textMap.place(stringTextMap2);
        assertFalse(textMap.canMoveTo(2));
        assertTrue(textMap.canMoveTo(1));
    }
    @Test
    public void moveTest(){
        textMap.place(stringTextMap1);
        textMap.place(stringTextMap2);
        textMap.place(stringTextMap3);

        textMap.move(stringTextMap1, MoveDirection.FORWARD);
        textMap.move(stringTextMap2, MoveDirection.FORWARD);
        assertEquals(textMap.objectAt(0), stringTextMap1);
        assertEquals(textMap.objectAt(1), stringTextMap2);

        textMap.move(stringTextMap1, MoveDirection.RIGHT);
        textMap.move(stringTextMap3, MoveDirection.RIGHT);
        textMap.move(stringTextMap1, MoveDirection.FORWARD);
        textMap.move(stringTextMap3, MoveDirection.BACKWARD);

        assertEquals(textMap.objectAt(0), stringTextMap2);
        assertEquals(textMap.objectAt(1), stringTextMap3);
        assertEquals(textMap.objectAt(2), stringTextMap1);
    }

}
