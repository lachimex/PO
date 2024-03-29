package agh.ics.oop.model.exceptions;

import agh.ics.oop.model.Vector2d;

public class PositionAlreadyOccupiedException extends Exception{
    public PositionAlreadyOccupiedException(Vector2d position){
        super("Position (" + position.getX() + ", " + position.getY() + ") is already occupied");
    }

}
