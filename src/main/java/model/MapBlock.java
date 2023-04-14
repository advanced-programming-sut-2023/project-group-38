package model;

import model.building.Building;
import model.unit.Unit;

import java.util.ArrayList;

public class MapBlock {

    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<ResourceType> resourceTypes = new ArrayList<>();
    private MapBlockType mapBlockType;
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

    public ArrayList<ResourceType> getResourceTypes() {
        return resourceTypes;
    }

    public void setResourceTypes(ArrayList<ResourceType> resourceTypes) {
        this.resourceTypes = resourceTypes;
    }

    public MapBlockType getMapBlockType() {
        return mapBlockType;
    }

    public void setMapBlockType(MapBlockType mapBlockType) {
        this.mapBlockType = mapBlockType;
    }
}
