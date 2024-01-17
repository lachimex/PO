package project.MapElements;

import project.GlobalSettings;
import project.Maps.Vector2d;

import java.util.*;

public class PlantGrower {
    private Set<Vector2d> fieldsSet = new HashSet<>();
    private List<Vector2d> emptyFieldsOutsideJungle = new ArrayList<>();
    private List<Vector2d> emptyFieldsInJungle = new ArrayList<>();
    private Map<Vector2d, Plant> plantMap;
    private int widthOfGreenArea;
    private int startingRow;
    private Random random = new Random();

    public PlantGrower(Map<Vector2d, Plant> plantMap, int widthOfGreenArea, int startingRow, GlobalSettings globalSettings) {
        this.plantMap = plantMap;
        this.widthOfGreenArea = widthOfGreenArea;
        this.startingRow = startingRow;
        for (int i = 0; i < globalSettings.mapHeight(); i++) {
            for (int j = 0; j < globalSettings.mapWidth(); j++) {
                fieldsSet.add(new Vector2d(j, i));
            }
        }
    }

    private Vector2d randomPlantVector(List<Vector2d> emptyFieldsOutsideJungle, List<Vector2d> emptyFieldsInJungle) {
        if (emptyFieldsOutsideJungle.isEmpty() && emptyFieldsInJungle.isEmpty()) {
            return null;
        }
        int n = random.nextInt(5);
        if (!emptyFieldsInJungle.isEmpty()) {
            if (n != 4) {
                Vector2d removed = emptyFieldsInJungle.remove(random.nextInt(emptyFieldsInJungle.size()));
                return removed;
            }
        }
        Vector2d removed = emptyFieldsOutsideJungle.remove(random.nextInt(emptyFieldsOutsideJungle.size()));
        return removed;
    }

    public Map<Vector2d, Plant> growNPlants(int n) {
        Set<Vector2d> plantSet = plantMap.keySet();
        Set<Vector2d> freeFields = new HashSet<>(fieldsSet);
        freeFields.removeAll(plantSet);
        emptyFieldsInJungle.clear();
        emptyFieldsOutsideJungle.clear();
        freeFields.forEach(vector2d -> {
            if (startingRow <= vector2d.getY() && vector2d.getY() < startingRow + widthOfGreenArea) {
                emptyFieldsInJungle.add(vector2d);
            } else {
                emptyFieldsOutsideJungle.add(vector2d);
            }
        });
        for (int i = 0; i < n; i++) {
            Vector2d plantVector = randomPlantVector(emptyFieldsOutsideJungle, emptyFieldsInJungle);
            if (plantVector != null) {
                plantMap.put(plantVector, new Plant(plantVector));
            }
        }
        return plantMap;
    }
}
