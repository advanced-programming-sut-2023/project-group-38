package model;

import com.google.gson.annotations.Expose;
import javafx.scene.layout.Pane;
import model.building.Building;
import model.unit.Unit;

import java.text.CollationElementIterator;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Map implements Cloneable {
    public static ArrayList<Map> Maps = new ArrayList<>();
    public static ArrayList<Map> DEFAULT_MAPS = new ArrayList<>(3);
    private ArrayList<Kingdom> players = new ArrayList<>();
    private ArrayList<Kingdom> deadPlayers = new ArrayList<>();
    private boolean endGame;
    private String mapName;

    private MapBlock[][] map;
    private Boolean[][] accessToRight;
    private Boolean[][] accessToDown;
    private int mapWidth;
    private int mapHeight;

    private transient HashMap<Building, Direction> gateDirection = new HashMap<>();

    private transient HashMap<Building, Flags> gateFlag = new HashMap<>();

    private transient Pane mapPane;
    private transient ArrayList<MapBlock> finalWay ;

    public Map(Integer mapWidth, Integer mapHeight, String mapName) {
        endGame = false;
        mapPane = new Pane();
//        mapPane.setLayoutX(100);
//        mapPane.setLayoutY(110);
//        mapPane.prefHeight(1080);
//        mapPane.prefWidth(1920);
        this.mapName = mapName;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        map = new MapBlock[mapWidth][mapHeight];
        accessToDown = new Boolean[mapWidth][mapHeight];
        for (int i = 0; i < mapWidth; i++)
            for (int j = 0; j < mapHeight; j++)
                accessToDown[i][j] = true;
        for (int i = 0; i < mapWidth; i++)
            accessToDown[i][mapHeight - 1] = false;
        accessToRight = new Boolean[mapWidth][mapHeight];
        for (int i = 0; i < mapWidth; i++)
            for (int j = 0; j < mapHeight; j++)
                accessToRight[i][j] = true;
        for (int i = 0; i < mapHeight; i++)
            accessToRight[mapWidth - 1][i] = false;

        for (int i = 0; i < mapWidth; i++)
            for (int j = 0; j < mapHeight; j++) {
                map[i][j] = new MapBlock(i, j);
                mapPane.getChildren().add(map[i][j].getGraphics());
                map[i][j].setVisualPosition();
            }
        Maps.add(this);
//        for (MapBlock[] mapBlockHeight : map)
//            for (MapBlock mapBlockWith : mapBlockHeight) mapBlockWith = new MapBlock();
    }

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public MapBlock[][] getMap() {
        return map;
    }

    public static void createDefaultMaps(){
        Map defaultMap1 = new Map(60 , 50 , "jungle");
        Map defaultMap2 = new Map(60 , 60 , "graveyard");
        DEFAULT_MAPS.clear();
        DEFAULT_MAPS.add(defaultMap1);
        DEFAULT_MAPS.add(defaultMap2);
        defaultMap1.changeType(0 , 0 , 59, 49, MapBlockType.GRASSLAND);
        defaultMap1.changeType(0 , 0 , 0, 49, MapBlockType.SEA);
        defaultMap1.changeType(1 , 0 , 1, 49, MapBlockType.BEACH);
        defaultMap1.changeType(59 , 0 , 59, 49, MapBlockType.SEA);
        defaultMap1.changeType(58 , 0 , 58, 49, MapBlockType.BEACH);
        defaultMap1.changeType(25 , 5 , 26, 45, MapBlockType.SHALLOW_WATER);
        defaultMap1.changeType(5 , 20 , 45, 21, MapBlockType.RIVER);
        defaultMap1.changeType(20 , 10 , 20, 40, MapBlockType.SLATE);
        defaultMap1.changeType(20 , 4 , 34, 11, MapBlockType.SLATE);
        defaultMap1.changeType(20 , 30 , 35, 30, MapBlockType.IRON);
        defaultMap1.changeType(33 , 33 , 35, 35, MapBlockType.SLATE);

        defaultMap2.changeType(0 , 0 , 59, 59 , MapBlockType.GRAVEL);
        defaultMap2.changeType(10, 10 , 49 , 49 , MapBlockType.SLATE);
        defaultMap2.changeType(20 , 20 , 39 , 39, MapBlockType.PLAIN);
        defaultMap2.changeType(25, 25 , 30 , 30 , MapBlockType.IRON);
        for (MapBlock[] mapBlockHeight : defaultMap1.map)
            for (MapBlock mapBlockWith : mapBlockHeight) mapBlockWith.addTree(Tree.OLIVE);

    }

    public Direction getGateDirection(Building building) {
        return gateDirection.get(building);
    }

    public void setGateDirection(Building building, Direction direction) {
        gateDirection.put(building, direction);
    }

    public Flags getGateFlag(Building building) {
        return gateFlag.get(building);
    }

    public void setGateFlag(Building building, Flags flags) {
        gateFlag.put(building, flags);
    }

    public String getMapName() {
        return mapName;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void addPlayer(Kingdom kingdom){
        players.add(kingdom);
    }

    public Kingdom getKingdomByOwner(User owner){
        for (Kingdom find: players) {
            if(find.getOwner().getUserName().equals(owner.getUserName()))
                return find;
        }
        return null;
    }

    public ArrayList<Kingdom> getPlayers() {
        return players;
    }

    public ArrayList<Kingdom> getDeadPlayers() {
        return deadPlayers;
    }

    public void setDeadPlayers(Kingdom deeds) {
        deadPlayers.add(deeds);
    }

    public MapBlock getMapBlockByLocation(int xPosition , int yPosition){
        if(xPosition < mapWidth && yPosition < mapHeight && xPosition >= 0 && yPosition >= 0)
            return map[xPosition][yPosition];
        return null;
    }

    public ArrayList<MapBlock> getFinalWay() {
        return finalWay;
    }

    public static Map getDefaultMap(int index) throws CloneNotSupportedException {
        if(index >= DEFAULT_MAPS.size())
            return null;
        return (Map)(DEFAULT_MAPS.get(index).clone());
    }

    public Pane getMapPane() {
        return mapPane;
    }

    //    public void changeType(int x , int y, MapBlockType type){
//        map[x][y].setMapBlockType(type);
//    }

    public void changeType(int x1 , int y1, int x2, int y2, MapBlockType type){
        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++) {
                map[i][j].setMapBlockType(type);
                if (type.isAccessible())
                    for (Direction direction : Direction.values())
                        changeAccess(i, j, direction, true);
                else
                    for (Direction direction : Direction.values())
                        changeAccess(i, j, direction, false);

            }

    }

    public void clearBlock(int x , int y){
        for (Direction value : Direction.values()) {
            changeAccess(x, y , value, true);
        }
        map[x][y] = new MapBlock(x , y);
        mapPane.getChildren().add(map[x][y].getGraphics());
        map[x][y].setVisualPosition();
    }

    public void changeAccess(int xPosition , int yPosition , Direction direction, boolean isAccessible){
        switch (direction){
            case WEST:
                if(xPosition != 0)
                    accessToRight[xPosition - 1][yPosition] = isAccessible;
                break;
            case EAST:
                accessToRight[xPosition][yPosition] = isAccessible;
                break;
            case NORTH:
                if(yPosition != 0)
                    accessToDown[xPosition][yPosition - 1] = isAccessible;
                break;
            case SOUTH:
                accessToDown[xPosition][yPosition] = isAccessible;

        }
    }

    public boolean checkAccess(int xPosition , int yPosition , Direction direction){
        switch (direction){
            case WEST:
                if(xPosition != 0)
                    return accessToRight[xPosition - 1][yPosition];
                break;
            case EAST:
                return accessToRight[xPosition][yPosition];
            case NORTH:
                if(yPosition != 0)
                    return accessToDown[xPosition][yPosition - 1];
                break;
            case SOUTH:
                return accessToDown[xPosition][yPosition];
        }
        return false;
    }

    public String getPartOfMap (int xPosition , int yPosition){
        String result = "map: in " + xPosition + "," + yPosition + '\n' + "    ";
        String resetColor = "\033[0m";
        //toDo change bounds
        for (int i = xPosition - 15; i <= (xPosition + 15); i++)
            result += String.format(" %3d  " , i);
        result += '\n';
        for (int j = yPosition - 8; j <= yPosition + 8; j++) {
            for (int fill = 0; fill < 3; fill++){
                if(fill == 1)
                    result += String.format("%3d " , j);
                else
                    result += "    ";
                for (int i = xPosition - 15; i <= (xPosition + 15); i++) {
                    MapBlock showedBlock;
                    if ((showedBlock = getMapBlockByLocation(i, j)) != null) {
                        if(fill == 1){
                            result += showedBlock.getMapBlockType().getColor() + "  "
                                    + showedBlock.getLatestDetails() + "  " + resetColor + " ";
                        }else {
                            result += showedBlock.getMapBlockType().getColor() + "     " + resetColor + " ";
                        }
                    }else
                        result += "\u001B[48;5;237m" + "XXXXX" + resetColor + " ";

                }
                result += '\n';
            }
            result += '\n';
        }
        return result.substring(0, result.length() - 2);
    }


    public MapBlock[][] getSurroundingArea(int xPosition, int yPosition, int range){
        MapBlock[][] output = new MapBlock[2 * range + 1][2 * range + 1];
        for (int j = yPosition - range; j <= yPosition + range; j++) {
            for (int i = xPosition - range; i <= (xPosition + range); i++) {
                if(i >=0 && j>= 0)
                    output[i - (xPosition - range)][j - (yPosition - range)] = map[i][j];
            }
        }
        return output;
    }

    public ArrayList<Unit> getEnemiesInSurroundingArea(int xPosition, int yPosition, Kingdom attacker, boolean justArchers, int range){
        ArrayList<Unit> enemies = new ArrayList<>();
        for (MapBlock[] mapBlocks : getSurroundingArea(xPosition, yPosition, range))
            for (MapBlock mapBlock : mapBlocks) {
                if (mapBlock == null)
                    continue;
                    for (Unit unit : mapBlock.getUnits())
                        if (!unit.getOwner().equals(attacker) &&
                                map[xPosition][yPosition].getOptimizedDistanceFrom(mapBlock.getxPosition(),
                                        mapBlock.getyPosition(), true) < unit.getOptimizedAttackRange()) {
                            enemies.add(unit);
                            if (justArchers && !unit.getUnitType().getCAN_DO_AIR_ATTACK())
                                enemies.remove(unit);
                        }
            }
        return enemies;
    }

    public ArrayList<Unit> getEnemiesInAttackRange(Unit attacker, boolean isNearestWanted){
        ArrayList<Unit> enemies = new ArrayList<>();
        ArrayList<Unit> nearestEnemies = new ArrayList<>();
        Integer nearestDistance = 100;
        Integer anotherDistance;
        for (MapBlock[] mapBlocks : getSurroundingArea(attacker.getXPosition(), attacker.getYPosition(), attacker.getMovesLeft() + attacker.getOptimizedAttackRange()))
            outer:
            for (MapBlock mapBlock : mapBlocks) {
                if (mapBlock == null)
                    continue;
                for (Unit unit : mapBlock.getUnits())
                    if (!unit.getOwner().equals(attacker.getOwner())) {
                        if (isNearestWanted && (anotherDistance = getShortestWayLength(attacker.getXPosition(), attacker.getYPosition(), unit.getXPosition(), unit.getYPosition(), attacker.getMovesLeft())) != null) {
                            if(anotherDistance < nearestDistance) {
                                nearestDistance = anotherDistance;
                                nearestEnemies = mapBlock.getUnits();
                                enemies.addAll(nearestEnemies);
                                continue outer;
                            }
                        }
                        enemies.add(unit);
                    }
            }

        if(isNearestWanted) {
            enemies.removeAll(nearestEnemies);
            enemies.addAll(0 ,nearestEnemies);
        }
        return enemies;
    }


    public Integer getShortestWayLength(int xPosition, int yPosition, int xOfDestination, int yOfDestination, Integer limit){
        boolean[][]mark = new boolean[mapWidth][mapHeight];
        AtomicInteger answer;
        finalWay = new ArrayList<>();
        ArrayList<MapBlock> way = new ArrayList<>();
        if(limit == null)
            answer = new AtomicInteger(mapWidth * mapHeight + 1);
        else
            answer = new AtomicInteger(limit + 1);
        if(xPosition < xOfDestination && yPosition < yOfDestination)
            getWaysLengthByEast(mark, xPosition, yPosition, 0, xOfDestination, yOfDestination, answer, true, way);
        else if (xPosition > xOfDestination && yPosition < yOfDestination)
            getWaysLengthByEast(mark, xOfDestination, yOfDestination, 0, xPosition, yPosition, answer, false, way);
        else if (xPosition >= xOfDestination && yPosition >= yOfDestination)
            getWaysLengthByEast(mark,  xOfDestination, yOfDestination, 0, xPosition, yPosition, answer, true, way);
        else
            getWaysLengthByEast(mark, xPosition, yPosition, 0, xOfDestination, yOfDestination, answer, false, way);
        if((limit == null && (answer.get() == (mapWidth * mapHeight + 1)) || answer.get() == (limit + 1)))
            return null;
        if(finalWay.get(0).equals(map[xOfDestination][yOfDestination]))
            Collections.reverse(finalWay);
            
        System.out.println(finalWay);
        System.out.println(answer.get());
        return answer.get();
    }

    private void getWaysLengthByEast(boolean[][]mark, int xPosition, int yPosition , int length , int xOfDestination, int yOfDestination , AtomicInteger minimum, boolean southPriority, ArrayList<MapBlock> way){
        if(length >= minimum.get())
            return;
        if(getMapBlockByLocation(xPosition , yPosition) == null)
            return;
        if(xPosition == xOfDestination && yPosition == yOfDestination) {
            minimum.set(length);
            way.add(map[xPosition][yPosition]);
            finalWay = new ArrayList<>();
            finalWay.addAll(way);
            way.remove(way.size() - 1);
            return;
        }
        if(mark[xPosition][yPosition] == true)
            return;
        mark[xPosition][yPosition] = true;
        way.add(map[xPosition][yPosition]);
        if(checkAccess(xPosition, yPosition, Direction.EAST))
            getWaysLengthByEast(mark, xPosition + 1, yPosition , length + 1 , xOfDestination, yOfDestination, minimum, true, way);
        if(southPriority){
            if (checkAccess(xPosition, yPosition, Direction.SOUTH))
                getWaysLengthByEast(mark, xPosition, yPosition + 1, length + 1, xOfDestination, yOfDestination, minimum, true, way);
            if (checkAccess(xPosition, yPosition, Direction.WEST))
                getWaysLengthByEast(mark, xPosition - 1, yPosition, length + 1, xOfDestination, yOfDestination, minimum, true, way);
        }
        if(checkAccess(xPosition, yPosition, Direction.NORTH))
            getWaysLengthByEast(mark, xPosition, yPosition - 1 , length + 1 , xOfDestination, yOfDestination, minimum, true, way);
        if (!southPriority) {
            if (checkAccess(xPosition, yPosition, Direction.SOUTH))
                getWaysLengthByEast(mark, xPosition, yPosition + 1, length + 1, xOfDestination, yOfDestination, minimum, true, way);
            if (checkAccess(xPosition, yPosition, Direction.WEST))
                getWaysLengthByEast(mark, xPosition - 1, yPosition, length + 1, xOfDestination, yOfDestination, minimum, true, way);
        }

        if(mark[xPosition][yPosition] == true) {
            mark[xPosition][yPosition] = false;
        }
        way.remove(map[xPosition][yPosition]);


    }

    public ArrayList<MapBlock> getBlocksInFourDirection(MapBlock origin){
        ArrayList<MapBlock> surrounding = new ArrayList<>();
        surrounding.add(map[origin.getxPosition()][origin.getxPosition() + 1]);
        surrounding.add(map[origin.getxPosition()][origin.getxPosition() - 1]);
        surrounding.add(map[origin.getxPosition() + 1][origin.getxPosition()]);
        surrounding.add(map[origin.getxPosition() - 1][origin.getxPosition()]);
        return surrounding;
    }

}
