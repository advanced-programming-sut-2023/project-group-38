package model.people;

import model.Kingdom;
import model.MapBlock;

public class Unit extends People{
    private UnitType unitType;
    private Kingdom owner;

    public Unit(Integer hp, MapBlock locationBlock, UnitType unitType, PeopleState peopleState, Kingdom owner) {
        super(hp, locationBlock);
        this.unitType = unitType;
        this.peopleState = peopleState;
        this.owner = owner;
        hp = unitType.getHP_IN_START();
    }

    public UnitType getUnitType() {
        return unitType;
    }



    public Kingdom getOwner() {
        return owner;
    }



    public void fight(Unit enemy){
    }

}
