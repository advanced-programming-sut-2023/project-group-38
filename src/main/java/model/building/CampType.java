package model.building;

public enum CampType {
    BARRACK(0,0);
    public final Integer COST_OF_LADDER_MAN;
    public final Integer COST_OF_ENGINEER;

    CampType(Integer COST_OF_LADDER_MAN, Integer COST_OF_ENGINEER) {
        this.COST_OF_LADDER_MAN = COST_OF_LADDER_MAN;
        this.COST_OF_ENGINEER = COST_OF_ENGINEER;
    }

}