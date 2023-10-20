package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2dTest {
    @Test
    public void testEquals(){
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(-1, -1);
        Vector2d vector3 = new Vector2d(1, 1);

        //when
        boolean v1 = vector1.equals(vector2);
        boolean v2 = vector1.equals(vector3);
        boolean v3 = vector2.equals(vector3);

        //then
        Assertions.assertFalse(v1);
        Assertions.assertTrue(v2);
        Assertions.assertFalse(v3);
    }

    @Test
    public void testToString(){
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(-1, -1);
        Vector2d vector3 = new Vector2d(100, -100);

        //when
        String s1 = vector1.toString();
        String s2 = vector2.toString();
        String s3 = vector3.toString();

        //then
        Assertions.assertEquals(s1, "(1, 1)");
        Assertions.assertEquals(s2, "(-1, -1)");
        Assertions.assertEquals(s3, "(100, -100)");
    }

    @Test
    public void testPrecedes(){
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(-1, -1);
        Vector2d vector3 = new Vector2d(100, -100);

        //when
        boolean c1 = vector1.precedes(vector2);
        boolean c2 = vector1.precedes(vector3);
        boolean c3 = vector2.precedes(vector3);
        boolean c4 = vector2.precedes(vector1);
        boolean c5 = vector3.precedes(vector1);
        boolean c6 = vector3.precedes(vector2);

        //then
        Assertions.assertFalse(c1);
        Assertions.assertFalse(c2);
        Assertions.assertFalse(c3);
        Assertions.assertFalse(c5);
        Assertions.assertFalse(c6);
        Assertions.assertTrue(c4);
    }

    @Test
    public void testFollows(){
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(-1, -1);
        Vector2d vector3 = new Vector2d(100, -100);

        //when
        boolean c1 = vector1.follows(vector2);
        boolean c2 = vector1.follows(vector3);
        boolean c3 = vector2.follows(vector3);
        boolean c4 = vector2.follows(vector1);
        boolean c5 = vector3.follows(vector1);
        boolean c6 = vector3.follows(vector2);

        //then
        Assertions.assertFalse(c4);
        Assertions.assertFalse(c2);
        Assertions.assertFalse(c3);
        Assertions.assertFalse(c5);
        Assertions.assertFalse(c6);
        Assertions.assertTrue(c1);
    }

    @Test
    public void testUpperRight(){
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(100, -100);

        //when
        Vector2d vector3 = vector1.upperRight(vector2);

        //then
        Assertions.assertEquals(vector3, new Vector2d(100, 1));
    }

    @Test
    public void testLowerLeft(){
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(100, -100);

        //when
        Vector2d vector3 = vector1.lowerLeft(vector2);

        //then
        Assertions.assertEquals(vector3, new Vector2d(1, -100));
    }

    @Test
    public void testAdd(){
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(100, -100);

        //when
        Vector2d vector3 = vector1.add(vector2);

        //then
        Assertions.assertEquals(vector3, new Vector2d(101, -99));
    }

    @Test
    public void testSubstract(){
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(100, -100);

        //when
        Vector2d vector3 = vector1.substract(vector2);

        //then
        Assertions.assertEquals(vector3, new Vector2d(-99, 101));
    }

    @Test
    public void testOpposite(){
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(100, -100);
        Vector2d vector3 = new Vector2d(0, 0);

        //when
        Vector2d vector4 = vector1.opposite();
        Vector2d vector5 = vector2.opposite();
        Vector2d vector6 = vector3.opposite();

        //then
        Assertions.assertEquals(vector4, new Vector2d(-1, -1));
        Assertions.assertEquals(vector5, new Vector2d(-100, 100));
        Assertions.assertEquals(vector6, new Vector2d(0, 0));
    }
}
