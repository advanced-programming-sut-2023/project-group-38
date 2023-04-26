package model;

import model.building.Building;
import model.building.BuildingType;
import model.unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class MapBlock {

    private ArrayList<Building> buildings;
    private ArrayList<Unit> units;
    private ArrayList<ResourceType> resources;
    private MapBlockType mapBlockType;
    private Integer xPosition;
    private Integer yPosition;
    private HashMap<Tree , Integer> numberOfTrees;
    public MapBlock(Integer xPosition, Integer yPosition) {
        this.mapBlockType = MapBlockType.EARTH;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        resources = new ArrayList<>();
        units = new ArrayList<>();
        buildings = new ArrayList<>();
        numberOfTrees = new HashMap<>();
        for (Tree tree: Tree.values()) {
            numberOfTrees.put(tree , 0);
        }
    }

    public MapBlock() {
        mapBlockType = MapBlockType.EARTH;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public ArrayList<ResourceType> getResources() {
        return resources;
    }

    public void setResources(ArrayList<ResourceType> resources) {
        this.resources = resources;
    }

    public MapBlockType getMapBlockType() {
        return mapBlockType;
    }

    public void setMapBlockType(MapBlockType mapBlockType) {
        this.mapBlockType = mapBlockType;
    }

    public Integer getxPosition() {
        return xPosition;
    }

    public Integer getyPosition() {
        return yPosition;
    }

    public void addUnitHere(Unit toAdd){
        units.add(toAdd);
    }

    public void removeUnitFromHere(Unit toRemove){
        units.remove(toRemove);
    }

    public Unit getLastUnitArrived(){
        return units.get(units.size() - 1);
    }

    public void addBuilding(Building toAdd){
        buildings.add(toAdd);
    }

    public void processNextTurn(){
    }

    public void addTree(Tree tree){
        numberOfTrees.put(tree , numberOfTrees.get(tree) + 1);
    }

    public static MapBlockType findEnumByLandType(String landType) {
        for (MapBlockType searchForType : MapBlockType.values())
            if (searchForType.name().toLowerCase().replaceAll("_"," ").equals(landType))
                return searchForType;
        return null;
    }

}
