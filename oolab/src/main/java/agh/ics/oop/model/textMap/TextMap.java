package agh.ics.oop.model.textMap;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextMap implements WorldMap<String, Integer>{

    private List<TextMapElement> stringList = new ArrayList<>();

    @Override
    public boolean place(String string) {
        TextMapElement textMapElement = new TextMapElement(string);
        if (stringList.contains(textMapElement)){
            return false;
        }
        stringList.add(textMapElement);
        return true;
    }

    @Override
    public void move(String string, MoveDirection moveDirection) {
        TextMapElement textMapElement = new TextMapElement(string);
        if (!stringList.contains(textMapElement)){
            return;
        }
        else{
            for (TextMapElement element : stringList){
                if (element.equals(textMapElement)){
                    textMapElement = element;
                    break;
                }
            }
        }
        if(moveDirection == MoveDirection.LEFT){
            textMapElement.setMapDirection(textMapElement.getMapDirection().previous());
        }
        else if(moveDirection == MoveDirection.RIGHT){
            textMapElement.setMapDirection(textMapElement.getMapDirection().next());
        }
        else if (textMapElement.getMapDirection() == MapDirection.EAST){
            switch (moveDirection){
                case FORWARD -> {
                    if (canMoveTo(stringList.indexOf(textMapElement) + 1)){
                        swapElems(stringList.indexOf(textMapElement) + 1, stringList.indexOf(textMapElement));
                    }
                }
                case BACKWARD -> {
                    if (canMoveTo(stringList.indexOf(textMapElement) - 1)){
                        swapElems(stringList.indexOf(textMapElement) - 1, stringList.indexOf(textMapElement));
                    }
                }
            }

        } else if (textMapElement.getMapDirection() == MapDirection.WEST) {
            switch (moveDirection){
                case FORWARD -> {
                    if (canMoveTo(stringList.indexOf(textMapElement) - 1)){
                        swapElems(stringList.indexOf(textMapElement) - 1, stringList.indexOf(textMapElement));
                    }
                }
                case BACKWARD -> {
                    if (canMoveTo(stringList.indexOf(textMapElement) + 1)){
                        swapElems(stringList.indexOf(textMapElement) + 1, stringList.indexOf(textMapElement));
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
    public Object objectAt(Integer position) {
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
        TextMapElement string1 = stringList.get(position1);
        TextMapElement string2 = stringList.get(position2);
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
            move(stringList.get(i % stringList.size()).getValue(), directions.get(i));
            System.out.println(this);
        }
    }
}