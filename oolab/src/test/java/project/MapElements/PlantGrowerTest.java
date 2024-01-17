package project.MapElements;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.BehaviourVariant;
import project.GlobalSettings;
import project.Maps.MapVariant;
import project.Maps.Vector2d;

import java.util.HashMap;
import java.util.Map;

public class PlantGrowerTest {
    GlobalSettings globalSettings = new GlobalSettings(10, 10, MapVariant.GLOBE,
            1, 1, 1, 1, 1,
            1, 1, 1, 1,
            BehaviourVariant.LITTLE_BIT_OF_CRAZINESS, 1, false);
    PlantGrower plantGrower = new PlantGrower(new HashMap<>(), 1, 4, globalSettings);

    @Test
    public void testPlantGrow(){
        Map<Vector2d, Plant> plantMap =  plantGrower.growNPlants(10);
        Assertions.assertEquals(plantMap.keySet().size(), 10);
    }
}
