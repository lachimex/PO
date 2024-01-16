package project.MapElements;

import project.Maps.Vector2d;

public class Plant implements MapElement {
    private Vector2d position;

    public Plant(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }
    @Override
    public String toString(){
        return position.toString();
    }
}
