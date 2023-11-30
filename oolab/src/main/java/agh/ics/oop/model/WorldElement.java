package agh.ics.oop.model;

public interface WorldElement {
    public boolean isAt(Vector2d otherPosition);
    public Vector2d getPosition();
}
