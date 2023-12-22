package project;

public record GlobalSettings(
        int energyNeededToReproduce,
        int energyLossDuringReproduction,
        int initialEnergy,
        int energyGainOnEat
) { }
