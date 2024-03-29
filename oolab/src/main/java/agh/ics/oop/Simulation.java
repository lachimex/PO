package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.exceptions.PositionAlreadyOccupiedException;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable{

    private WorldMap worldMap;
    private List<MoveDirection> animalMoves;
    private List<Animal> animals = new ArrayList<>();
    public Simulation(List<Vector2d> animalPositions, List<MoveDirection> animalMoves, WorldMap worldMap) {
        this.animalMoves = animalMoves;
        this.worldMap = worldMap;
        for (Vector2d animalPosition : animalPositions){
            Animal animal = new Animal(animalPosition);
            try {
                worldMap.place(animal);
                animals.add(animal);
            } catch (PositionAlreadyOccupiedException ignored){
                System.out.println(ignored.getMessage());
            }
        }
    }

    public void run(){
        for (int i = 0; i < animalMoves.size(); i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Animal animal = animals.get(i % animals.size());
            worldMap.move(animal, animalMoves.get(i));
        }

    }
}
