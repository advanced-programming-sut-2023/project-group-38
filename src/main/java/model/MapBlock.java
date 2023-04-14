package model;

import model.building.Building;
import model.people.Unit;

import java.util.ArrayList;

public class MapBlock {

    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<ResourceType> resourceTypes = new ArrayList<>();
    private MapBlockType mapBlockType;
    private Integer xPosition;
    private Integer yPosition;

    public MapBlock(Integer xPosition, Integer yPosition) {
        this.mapBlockType = MapBlockType.EARTH;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
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

}
