package agh.ics.oop.model;

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
}