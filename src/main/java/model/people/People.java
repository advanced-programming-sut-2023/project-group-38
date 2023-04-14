package model.people;

import model.MapBlock;

public class People {
    private Integer hp;
    protected MapBlock locationBlock;
    protected PeopleState peopleState;
    protected PeopleType peopleType;

    public People(Integer hp, MapBlock locationBlock) {
        this.hp = hp;
        this.locationBlock = locationBlock;
        this.peopleState = PeopleState.NOT_EMPLOYEE;
        this.peopleType = PeopleType.CIVILAN;
    }

    public Integer getHp() {
        return hp;
    }

    public Integer getXPosition() {
        return locationBlock.getxPosition();
    }

    public Integer getYPosition() {
        return locationBlock.getyPosition();
    }

    public MapBlock getLocationBlock() {
        return locationBlock;
    }

    public PeopleState getUnitState() {
        return peopleState;
    }

    public void setUnitState(PeopleState peopleState) {
        this.peopleState = peopleState;
    }

    public void setLocationBlock(MapBlock locationBlock) {
        this.locationBlock = locationBlock;
    }

    public PeopleState getPeopleState() {
        return peopleState;
    }

    public void setPeopleState(PeopleState peopleState) {
        this.peopleState = peopleState;
    }

    public PeopleType getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(PeopleType peopleType) {
        this.peopleType = peopleType;
    }

    public void moveTo(MapBlock destination){
        //toDo the function is not complete
    }
}
