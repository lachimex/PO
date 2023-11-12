package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class TextMap implements WorldMap<String, Integer>{

    private List<String> stringList = new ArrayList<>();

    public TextMap(List<String> stringList) {
        this.stringList = stringList;
    }

    @Override
    public boolean place(String string) {
        stringList.add(string);
        return true;
    }

    @Override
    public void move(String string, MoveDirection moveDirection) {

    }

    @Override
    public boolean isOccupied(Integer position) {
        return position < stringList.size();
    }

    @Override
    public String objectAt(Integer position) {
        if (position >= stringList.size()){
            return null;
        }
        return stringList.get(position);
    }

    @Override
    public boolean canMoveTo(Integer position) {
        return true;
    }
}
