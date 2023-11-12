package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionParserTest {
    @Test
    public void convertDataTest(){
        //given
        String[] stringTab = {"f", "r", "b", "l"};

        //when
        List<MoveDirection> stringList = OptionsParser.parse(stringTab);

        //then
        List<MoveDirection> compareList = new ArrayList<>();
        compareList.add(MoveDirection.FORWARD);
        compareList.add(MoveDirection.RIGHT);
        compareList.add(MoveDirection.BACKWARD);
        compareList.add(MoveDirection.LEFT);
        assertEquals(compareList, stringList);
    }
}
