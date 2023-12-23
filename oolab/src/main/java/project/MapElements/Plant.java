package project.MapElements;

import project.Maps.Vector2d;

public class Plant implements MapElement {
    private Vector2d position;
    @Override
    public Vector2d getPosition() {
        return position;
    }
}
