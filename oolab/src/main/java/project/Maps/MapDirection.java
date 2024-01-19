package project.Maps;


public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    @Override
    public String toString() {
        switch (this){
            case NORTH -> {return "N";}
            case NORTH_EAST -> {return "NE";}
            case EAST -> {return "E";}
            case SOUTH_EAST -> {return "SE";}
            case SOUTH -> {return "S";}
            case SOUTH_WEST -> {return "SW";}
            case WEST -> {return "W";}
            case NORTH_WEST -> {return "NW";}  // gdyby tylko to się dało jakoś skrócić...
            default -> throw new IllegalArgumentException("direction not exist in real world");
        }
    }

    public MapDirection opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case NORTH_EAST -> SOUTH_WEST;
            case EAST -> WEST;
            case SOUTH_EAST -> NORTH_WEST;
            case SOUTH -> NORTH;
            case SOUTH_WEST -> NORTH_EAST;
            case WEST -> EAST;
            case NORTH_WEST -> SOUTH_EAST;
            default -> throw new IllegalArgumentException("Direction not exist in real world");
        };
    }


    public MapDirection next(){
        switch (this){
            case NORTH -> {return NORTH_EAST;}
            case NORTH_EAST -> {return EAST;}
            case EAST -> {return SOUTH_EAST;}
            case SOUTH_EAST -> {return SOUTH;}
            case SOUTH -> {return SOUTH_WEST;}
            case SOUTH_WEST -> {return WEST;}
            case WEST -> {return NORTH_WEST;}
            case NORTH_WEST -> {return NORTH;}
            default -> throw new IllegalArgumentException("direction not exist in real world");
        }
    }

    public MapDirection previous(){
        switch (this){
            case NORTH -> {return NORTH_WEST;}
            case NORTH_WEST -> {return WEST;}
            case WEST -> {return SOUTH_WEST;}
            case SOUTH_WEST -> {return SOUTH;}
            case SOUTH -> {return SOUTH_EAST;}
            case SOUTH_EAST -> {return EAST;}
            case EAST -> {return NORTH_EAST;}
            case NORTH_EAST -> {return NORTH;}
            default -> throw new IllegalArgumentException("direction not exist in real world");
        }
    }

    public project.Maps.Vector2d toUnitVector(){
        switch (this){
            case NORTH -> {return new Vector2d(0, 1);}
            case NORTH_EAST -> {return new Vector2d(1, 1);}
            case EAST -> {return new Vector2d(1, 0);}
            case SOUTH_EAST -> {return new Vector2d(1, -1);}
            case SOUTH -> {return new Vector2d(0, -1);}
            case SOUTH_WEST -> {return new Vector2d(-1, -1);}
            case WEST -> {return new Vector2d(-1, 0);}
            case NORTH_WEST -> {return new Vector2d(-1, 1);}
            default -> throw new IllegalArgumentException("direction not exist in real world");
        }
    }

    public static MapDirection intToMapDirection(int n){
        switch (n){
            case 0 -> {
                return NORTH;
            }
            case 1 -> {
                return NORTH_EAST;
            }
            case 2 -> {
                return EAST;
            }
            case 3 -> {
                return SOUTH_EAST;
            }
            case 4 -> {
                return SOUTH;
            }
            case 5 -> {
                return SOUTH_WEST;
            }
            case 6 -> {
                return WEST;
            }
            case 7 -> {
                return NORTH_WEST;
            }
            default ->
                throw new IllegalArgumentException("int to map direction has to be in range <0, 7>");
        }
    }
}
