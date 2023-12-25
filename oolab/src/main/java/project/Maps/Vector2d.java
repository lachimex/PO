package project.Maps;


import java.util.Objects;
import java.util.Random;

public class Vector2d {
    private final int x;
    private final int y;

    private static Random random = new Random();

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean precedes(Vector2d other){
        if (other == null){
            return false;
        }
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other){
        if (other == null){
            return false;
        }
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other){
        if (other == null){
            return null;
        }
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        if (other == null){
            return null;
        }
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d upperRight(Vector2d other){
        if (other == null){
            return null;
        }
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other){
        if (other == null){
            return null;
        }
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d that))
            return false;
        return this.x == that.x && this.y == that.y;
    }

    public int hashCode(){
        return Objects.hash(this.x, this.y);
    }

    public static Vector2d generateRandomVector(int boundX, int boundY){
        return new Vector2d(random.nextInt(boundX), random.nextInt(boundY));
    }
}
