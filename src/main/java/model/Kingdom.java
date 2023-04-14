package model;

import model.building.Building;
import model.people.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class Kingdom {
    private Integer population;
    private Integer noneEmployed;
    private Integer fearRate;
    private Integer popularity;
    private Building headquarter;
    private HashMap<Food , Integer> foods = new HashMap<>();
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<Building> buildings = new ArrayList<>();
    private Integer foodRate;
    private Integer taxRate;
    private Flags flag;
    public Kingdom(Flags flag , Building headquarter) {
        population = 5;
        popularity = 0;
        foodRate = -2;
        this.flag = flag;
        this.headquarter = headquarter;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getNoneEmployed() {
        return noneEmployed;
    }

    public void setNoneEmployed(Integer noneEmployed) {
        this.noneEmployed = noneEmployed;
    }

    public Integer getFearRate() {
        return fearRate;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public Integer getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Integer taxRate) {
        this.taxRate = taxRate;
    }

    public Building getHeadquarter() {
        return headquarter;
    }

    public Integer getFoodRate() {
        return foodRate;
    }

    public Flags getFlag() {
        return flag;
    }

    public void setFoodRate(Integer foodRate) {
        this.foodRate = foodRate;
    }

    public void ChangeFearRate(Integer amount) {
        this.fearRate += amount;
    }

    public void ChangePopularity(Integer amount) {
        this.popularity += amount;
    }

    public void addFood(Food toAdd , int amount){
        foods.put(toAdd , amount);
    }

    public void addUnit(Unit toAdd){
        units.add(toAdd);
    }

    private void addBuilding(Building toAdd){
        buildings.add(toAdd);
    }

}
