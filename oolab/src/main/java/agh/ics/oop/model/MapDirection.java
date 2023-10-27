package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    @Override
    public String toString() {
        switch (this){
            case NORTH -> {return "polnoc";}
            case EAST -> {return "wschod";}
            case SOUTH -> {return "poludnie";}
            case WEST -> {return "zachod";}
            default -> throw new IllegalArgumentException("direction not exist in real world");
        }
    }

    public MapDirection next(){
        switch (this){
            case NORTH -> {return MapDirection.EAST;}
            case EAST -> {return MapDirection.SOUTH;}
            case SOUTH -> {return MapDirection.WEST;}
            case WEST -> {return MapDirection.NORTH;}
            default -> throw new IllegalArgumentException("direction not exist in real world");
        }
    }

    public MapDirection previous(){
        switch (this){
            case NORTH -> {return MapDirection.WEST;}
            case EAST -> {return MapDirection.NORTH;}
            case SOUTH -> {return MapDirection.EAST;}
            case WEST -> {return MapDirection.SOUTH;}
            default -> throw new IllegalArgumentException("direction not exist in real world");
        }
    }

    Vector2d toUnitVector(){
        switch (this){
            case NORTH -> {return new Vector2d(0, 1);}
            case EAST -> {return new Vector2d(1, 0);}
            case SOUTH -> {return new Vector2d(0, -1);}
            case WEST -> {return new Vector2d(-1, 0);}
            default -> throw new IllegalArgumentException("direction not exist in real world");
        }
    }
}
