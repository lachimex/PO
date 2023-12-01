package agh.ics.oop.model.textMap;

import agh.ics.oop.model.MapDirection;

import java.util.Objects;

public class TextMapElement {
    private String value;
    private MapDirection mapDirection;

    public TextMapElement(String value) {
        this.value = value;
        this.mapDirection = MapDirection.NORTH;
    }

    public TextMapElement(String value, MapDirection mapDirection){
        this.value = value;
        this.mapDirection = mapDirection;
    }

    public String getValue() {
        return value;
    }

    public MapDirection getMapDirection() {
        return mapDirection;
    }

    public void setMapDirection(MapDirection mapDirection) {
        this.mapDirection = mapDirection;
    }

    @Override
    public String toString(){
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextMapElement that = (TextMapElement) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, mapDirection);
    }
}
