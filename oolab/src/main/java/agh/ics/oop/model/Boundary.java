package agh.ics.oop.model;

public record Boundary(Vector2d lowerLeft,
                       Vector2d upperRight) {

    public Boundary(Vector2d lowerLeft, Vector2d upperRight){
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }
}
