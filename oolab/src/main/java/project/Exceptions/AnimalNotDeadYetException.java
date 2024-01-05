package project.Exceptions;

import project.MapElements.Animal;

public class AnimalNotDeadYetException extends Exception{
    public AnimalNotDeadYetException(Animal animal){
        super("Animal is not dead yet");
    }
}
