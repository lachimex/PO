package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private List<Vector2d> animalPositions;
    private List<MoveDirection> animalMoves;
    private List<Animal> animals = new ArrayList<>();
    public Simulation(List<Vector2d> animalPositions, List<MoveDirection> animalMoves) {
        this.animalPositions = animalPositions;
        this.animalMoves = animalMoves;
        for (Vector2d animalPosition : animalPositions){
            animals.add(new Animal(animalPosition));
        }
    }

    public void run(){
        for (int i = 0; i < animalMoves.size(); i++) {
            animals.get(i % animals.size()).move(animalMoves.get(i));
            System.out.println("Zwierzę " + i % animals.size() + " : " + animals.get(i % animals.size()));
        }
    }
}
