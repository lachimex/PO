package agh.ics.oop.model;

import java.util.Objects;

public class StringTextMap {
    private String value;
    private MapDirection mapDirection;

    public StringTextMap(String value) {
        this.value = value;
        this.mapDirection = MapDirection.NORTH;
    }

    public StringTextMap(String value, MapDirection mapDirection){
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
        StringTextMap that = (StringTextMap) o;
        return Objects.equals(value, that.value) && mapDirection == that.mapDirection;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, mapDirection);
    }
}