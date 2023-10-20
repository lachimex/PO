package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class World {

    public static void Run(MoveDirection[] arguments){
        for (int i = 0; i < arguments.length; i++){
            switch (arguments[i]){
                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak idzie w prawo");
                case LEFT -> System.out.println("Zwierzak idzie w lewo");
            }
        }
    }
    public static void main(String[] args){
        System.out.println("Start");
        MoveDirection[] new_args = OptionParser.optionFilter(args);
        Run(new_args);
        System.out.println("Stop");
    }

}
