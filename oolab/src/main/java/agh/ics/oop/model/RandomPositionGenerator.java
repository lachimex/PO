package agh.ics.oop.model;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

import static java.lang.Math.sqrt;

public class RandomPositionGenerator implements Iterable<Vector2d>{
    private List<Vector2d> list = new ArrayList<>();
    private int grassCount;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount) {
        this.grassCount = grassCount;
        for (int i = 0; i <= maxWidth; i++){
            for (int j = 0; j <= maxHeight; j++){
                list.add(new Vector2d(i, j));
            }
        }
        Collections.shuffle(list);
        list = list.subList(0, grassCount);
    }

    @NotNull
    @Override
    public Iterator <Vector2d> iterator() {
        Iterator <Vector2d> iter = new Iterator <> () {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < grassCount;
            }

            @Override
            public Vector2d next() {
                if (hasNext()) return list.get(currentIndex++);
                else throw new NoSuchElementException();
            }
        };
        return iter;
    }

}
