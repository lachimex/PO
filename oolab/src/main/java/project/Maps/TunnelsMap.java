package project.Maps;

import project.GlobalSettings;
import project.MapElements.Animal;
import project.MapElements.AnimalsGroup;

import java.util.*;

public class TunnelsMap extends AbstractMap implements MapInterface {

    private Map<Vector2d, Vector2d> tunnelMap = new HashMap<>();

    public TunnelsMap(GlobalSettings globalSettings) {
        super(globalSettings);
        for (int i = 0; i < globalSettings.mapWidth() * globalSettings.mapHeight() / 200 + 1; i++) {
            Vector2d entry = Vector2d.generateRandomVector(globalSettings.mapWidth(), globalSettings.mapHeight());
            Vector2d exit = Vector2d.generateRandomVector(globalSettings.mapWidth(), globalSettings.mapHeight());
            tunnelMap.put(entry, exit);
        }
    }


    @Override
    public void moveEachAnimal() {
        List<AnimalsGroup> values = new ArrayList<>(animalsMap.values());
        super.animalsMap.clear();
        values.forEach(animalsGroup -> {
            for (Animal animal : animalsGroup.getAnimalList()) {
                Vector2d prevPosition = animal.getPosition();
                animal.move();
                if (tunnelMap.containsKey(animal.getPosition())) {
                    animal.setPosition(tunnelMap.get(animal.getPosition()));
                } else {
                    if (animal.getPosition().getX() >= globalSettings.mapWidth()) {
                        animal.setPosition(new Vector2d(0, animal.getPosition().getY()));
                    }
                    if (animal.getPosition().getX() < 0) {
                        animal.setPosition(new Vector2d(globalSettings.mapWidth() - 1, animal.getPosition().getY()));
                    }
                    if (animal.getPosition().getY() < 0) {
                        animal.setDirection(animal.getDirection().opposite());
                        animal.setPosition(prevPosition);
                    }
                    if (animal.getPosition().getY() >= globalSettings.mapHeight()) {
                        animal.setDirection(animal.getDirection().opposite());
                        animal.setPosition(prevPosition);
                    }
                }
                super.addAnimal(animal.getPosition(), animal);
            }
        });
    }

    public Map<Vector2d, Vector2d> getTunnelMap() {
        return tunnelMap;
    }
}
