package agh.ics.oop.model;

public class Animal {
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

    public Vector2d getAnimalPosition(){
        return animalPosition;
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
