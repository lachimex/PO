package project.MapElements;

import project.BehaviourVariant;
import project.Exceptions.AnimalNotDeadYetException;
import project.GlobalSettings;
import project.Maps.MapDirection;
import project.Maps.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements MapElement {
    private GlobalSettings globalSettings;
    private List<Integer> genList;
    private List<Animal> childList = new ArrayList<>();
    private static Random random = new Random();
    private MapDirection direction;
    private Vector2d position;
    private int activeGen;
    private int energy;
    private int childCounter;
    private int plantEatenCounter;
    private int age;
    boolean alive;
    private Integer dayOfDeath = null;
    private Integer lifeSpan = null;
    private int bornDay;
    private boolean ifTracked;

    public Animal(List<Integer> genList, Vector2d position, int energy, GlobalSettings globalSettings) {
        this.genList = genList;
        this.globalSettings = globalSettings;
        this.energy = energy;
        this.position = position;
        this.childCounter = 0;
        this.plantEatenCounter = 0;
        this.age = 0;
        this.alive = true;
        for (int gen : genList) {
            if (gen < 0 || gen > 7) {
                throw new IllegalArgumentException("gen does not exist in genom type");
            }
        }
        if (genList.isEmpty()) {
            throw new IllegalArgumentException("gen list cannot be empty");
        } else {
            this.activeGen = genList.get(random.nextInt(genList.size()));
            this.direction = MapDirection.intToMapDirection(activeGen);
        }
    }

    public void eat() {
        energy += globalSettings.energyGainOnEat();
        plantEatenCounter++;
    }

    public void move() {
        direction = MapDirection.intToMapDirection(genList.get(activeGen % genList.size()));
        position = position.add(direction.toUnitVector());
        if (globalSettings.behaviourVariant().equals(BehaviourVariant.LITTLE_BIT_OF_CRAZINESS)) {
            if (random.nextInt(5) == 4) {
                activeGen += random.nextInt(genList.size());
            } else {
                activeGen++;
            }
        } else {
            activeGen++;
        }
        energy--;
    }

    public void addChild(Animal descendant) {
        childList.add(descendant);
    }

    public static List<Integer> generateRandomGenList(int numberOfGens) {
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < numberOfGens; i++) {
            out.add(random.nextInt(8));
        }
        return out;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public int getBornDay() {
        return bornDay;
    }

    public void setBornDay(int bornDay) {
        this.bornDay = bornDay;
    }

    public int getAge() {
        return age;
    }

    public int getChildCounter() {
        return childCounter;
    }

    public List<Animal> getChildList() {
        return childList;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public int getDayOfDeath() throws AnimalNotDeadYetException {
        if (dayOfDeath == null) {
            throw new AnimalNotDeadYetException(this);
        }
        return dayOfDeath;
    }

    public int getLifeSpan() throws AnimalNotDeadYetException {
        if (lifeSpan == null) {
            throw new AnimalNotDeadYetException(this);
        }
        return lifeSpan;
    }

    public void setLifeSpan(Integer lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public List<Integer> getGenList() {
        return genList;
    }

    public int getGenListSize() {
        return genList.size();
    }

    public void setChildCounter(int childCounter) {
        this.childCounter = childCounter;
    }

    public int getActiveGen() {
        return activeGen % genList.size();
    }

    public int getPlantEatenCounter() {
        return plantEatenCounter;
    }

    public boolean isTracked() {
        return ifTracked;
    }

    public void setIfTracked(boolean ifTracked) {
        this.ifTracked = ifTracked;
    }

    @Override
    public String toString() {
        return position.toString() + " " + direction.toString() + "Energy: " + energy;
    }
}
