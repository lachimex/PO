package project.Exceptions;  // nazwy pakietów małymi literami; jaki jest sens wydzielać wyjątki do osobnego pakietu?

import project.MapElements.Animal;

public class AnimalNotDeadYetException extends Exception{
    public AnimalNotDeadYetException(Animal animal){
        super("Animal is not dead yet");
    }
}
