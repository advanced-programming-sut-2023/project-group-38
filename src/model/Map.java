package model;

import java.util.ArrayList;

public class Map {
    private ArrayList<Map> myMaps = new ArrayList<>();
    private Land[][] map;
    private ArrayList<Kingdom> kingdomsInMap;

    public Map(Integer mapWidth, Integer height) {
        map = new Land[height][mapWidth];
    }
}
