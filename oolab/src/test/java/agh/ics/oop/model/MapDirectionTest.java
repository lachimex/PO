package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapDirectionTest {
    @Test
    public void next(){
        //given
        MapDirection mapDirection1 = MapDirection.NORTH;
        MapDirection mapDirection2 = MapDirection.EAST;
        MapDirection mapDirection3 = MapDirection.SOUTH;
        MapDirection mapDirection4 = MapDirection.WEST;

        //when
        mapDirection1 = mapDirection1.next();
        mapDirection2 = mapDirection2.next();
        mapDirection3 = mapDirection3.next();
        mapDirection4 = mapDirection4.next();

        //then
        Assertions.assertEquals(MapDirection.EAST, mapDirection1);
        Assertions.assertEquals(MapDirection.SOUTH, mapDirection2);
        Assertions.assertEquals(MapDirection.WEST, mapDirection3);
        Assertions.assertEquals(MapDirection.NORTH, mapDirection4);
    }
}
