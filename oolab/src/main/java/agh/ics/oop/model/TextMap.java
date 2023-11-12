package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextMap implements WorldMap<StringTextMap, Integer>{

    private List<StringTextMap> stringList;

    public TextMap(List<StringTextMap> stringList) {
        this.stringList = stringList;
    }

    @Override
    public boolean place(StringTextMap string) {
        stringList.add(string);
        return true;
    }

    @Override
    public void move(StringTextMap string, MoveDirection moveDirection) {
        if(moveDirection == MoveDirection.LEFT){
            string.setMapDirection(string.getMapDirection().previous());
        }
        else if(moveDirection == MoveDirection.RIGHT){
            string.setMapDirection(string.getMapDirection().next());
        }
        else if (string.getMapDirection() == MapDirection.EAST){
            switch (moveDirection){
                case FORWARD -> {
                    if (canMoveTo(stringList.indexOf(string) + 1)){
                        swapElems(stringList.indexOf(string) + 1, stringList.indexOf(string));
                    }
                }
                case BACKWARD -> {
                    if (canMoveTo(stringList.indexOf(string) - 1)){
                        swapElems(stringList.indexOf(string) - 1, stringList.indexOf(string));
                    }
                }
            }

        } else if (string.getMapDirection() == MapDirection.WEST) {
            switch (moveDirection){
                case FORWARD -> {
                    if (canMoveTo(stringList.indexOf(string) - 1)){
                        swapElems(stringList.indexOf(string) - 1, stringList.indexOf(string));
                    }
                }
                case BACKWARD -> {
                    if (canMoveTo(stringList.indexOf(string) + 1)){
                        swapElems(stringList.indexOf(string) + 1, stringList.indexOf(string));
                    }
                }
            }
        }
    }

    @Override
    public boolean isOccupied(Integer position) {
        return position < stringList.size();
    }

    @Override
    public StringTextMap objectAt(Integer position) {
        if (position >= stringList.size()){
            return null;
        }
        return stringList.get(position);
    }

    @Override
    public boolean canMoveTo(Integer position) {
        return position < stringList.size() && 0 <= position;
    }

    private void swapElems(Integer position1, Integer position2){
        StringTextMap string1 = stringList.get(position1);
        StringTextMap string2 = stringList.get(position2);
        stringList.set(position1, string2);
        stringList.set(position2, string1);
    }

    @Override
    public String toString(){
        String out = "";
        for (int i = 0; i < stringList.size(); i++) {
            out += stringList.get(i).getValue() + " ";
        }
        return out;
    }

    public void run(List<MoveDirection> directions){
        for (int i = 0; i < directions.size(); i++) {
            move(stringList.get(i % stringList.size()), directions.get(i));
            System.out.println(this);
        }
    }
}
