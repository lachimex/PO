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
        return animalPosition.toString() + " " + animalOrientation.toString();
    }

    public boolean isAt(Vector2d position){
        return animalPosition.equals(position);
    }

    public void move(MoveDirection direction){
        switch (direction){
            case LEFT -> animalOrientation = animalOrientation.previous();
            case RIGHT -> animalOrientation = animalOrientation.next();
            case FORWARD -> {
                if (animalPosition.add(animalOrientation.toUnitVector()).precedes(new Vector2d(4, 4))
                    && animalPosition.add(animalOrientation.toUnitVector()).follows(new Vector2d(0, 0))){
                    animalPosition = animalPosition.add(animalOrientation.toUnitVector());
                }
            }
            case BACKWARD -> {
                if (animalPosition.subtract(animalOrientation.toUnitVector()).precedes(new Vector2d(4, 4))
                        && animalPosition.subtract(animalOrientation.toUnitVector()).follows(new Vector2d(0, 0))){
                    animalPosition = animalPosition.subtract(animalOrientation.toUnitVector());
                }
            }
        }
    }
}
