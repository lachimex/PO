package project;

import project.Maps.MapVariant;

public record GlobalSettings(
        int mapHeight,
        int mapWidth,
        MapVariant mapVariant, //globe and underground tunnels
        int initialNumberOfPlants,
        int initialNumberOfAnimals,
        int initialEnergy,
        int numberOfPlantsEachDay,
        int energyGainOnEat,
        int energyNeededToReproduce,
        int energyLossDuringReproduction,
        int minimalNumberOfMutations,
        int maximumNumberOfMutations,
        MutationVariant mutationVariant, //full randomness and little bit of craziness
        int genomLength

) { }
