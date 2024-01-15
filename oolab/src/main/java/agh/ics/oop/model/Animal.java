package agh.ics.oop.model;

public class Animal implements WorldElement{
    private MapDirection animalOrientation = MapDirection.NORTH;
    private Vector2d animalPosition;

    public Animal(){
        this.animalPosition = new Vector2d(2, 2);
    }

    public Animal(Vector2d position){
        this.animalPosition = position;
    }

    @Override
    public String toString() {
        return animalOrientation.toString();
    }

    public MapDirection getAnimalOrientation() {
        return animalOrientation;
    }

    public Vector2d getPosition(){
        return animalPosition;
    }

    @Override
    public String getFileName() {
        switch (animalOrientation){
            case SOUTH -> {
                return "down.png";
            }
            case WEST -> {
                return "left.png";
            }
            case EAST -> {
                return "right.png";
            }
            case NORTH -> {
                return "up.png";
            }
        }
        return null;
    }

    public boolean isAt(Vector2d position){
        return animalPosition.equals(position);
    }
    public void move(MoveDirection direction, MoveValidator moveValidator){
        switch (direction){
            case LEFT -> animalOrientation = animalOrientation.previous();
            case RIGHT -> animalOrientation = animalOrientation.next();
            case FORWARD -> {
                if (moveValidator.canMoveTo(animalPosition.add(animalOrientation.toUnitVector()))){
                    animalPosition = animalPosition.add(animalOrientation.toUnitVector());
                }
            }
            case BACKWARD -> {
                if (moveValidator.canMoveTo(animalPosition.subtract(animalOrientation.toUnitVector()))){
                    animalPosition = animalPosition.subtract(animalOrientation.toUnitVector());
                }
            }
        }
    }
}
