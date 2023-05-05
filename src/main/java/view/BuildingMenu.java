package view;

import controller.BuildingController;
import controller.CommandParser;
import java.util.HashMap;

public class BuildingMenu {
    private final CommandParser commandParser;
    private final BuildingController buildingController;

    public BuildingMenu(BuildingController buildingController) {
        this.buildingController = buildingController;
        this.commandParser = new CommandParser();
    }

    public void mainBuildingClassRun() {
        HashMap<String, String> optionPass;
        String command, result;
        System.out.println(buildingController.buildingName());
        result = buildingController.redirect();
        if (result == null) {
            while (true) {
                command = CommandParser.getScanner().nextLine();
                if (commandParser.validate(command, "back", null) != null) return;
                else System.out.println("Invalid command");
            }
        }
    }

    public String defensiveBuildingRnu() {
        HashMap<String, String> optionPass;
        String command, result;
        System.out.println(buildingController.buildingHp());
        while (true) {
            command = CommandParser.getScanner().nextLine();
            if (commandParser.validate(command, "back", null) != null) return "back";
            if (commandParser.validate(command, "repair", null) != null)
                System.out.println(buildingController.repairBuilding());
            else System.out.println("Invalid command");
        }

    }

    public String campBuildingRnu() {
        HashMap<String, String> optionPass;
        String command, result;
        while (true) {
            command = CommandParser.getScanner().nextLine();
            if (commandParser.validate(command, "back", null) != null) return "back";
            if (commandParser.validate(command, "repair", null) != null)
                System.out.println(buildingController.repairBuilding());
            else if ((optionPass = commandParser.validate(command, "create unit","t|type/c|count")) != null)
                System.out.println(buildingController.createUnit(optionPass));
            else System.out.println("Invalid command");
        }
    }

    public String generalBuildingRun() {
        HashMap<String, String> optionPass;
        String command, result;
        while (true) {
            command = CommandParser.getScanner().nextLine();
            if (commandParser.validate(command, "back", null) != null) return "back";
            else System.out.println("Invalid command");
        }
    }

    public String stockBuildingRun() {
        HashMap<String, String> optionPass;
        String command, result;
        while (true) {
            command = CommandParser.getScanner().nextLine();
            if (commandParser.validate(command, "back", null) != null) return "back";
            if (commandParser.validate(command, "show stock content", null) != null)
                System.out.println(buildingController.showResources());
            else System.out.println("Invalid command");
        }
    }

    public String produceBuildingRun() {
        HashMap<String, String> optionPass;
        String command, result;
        while (true) {
            command = CommandParser.getScanner().nextLine();
            if (commandParser.validate(command, "back", null) != null) return "back";
            else if ((optionPass = commandParser.validate(command, "produce resource","t|type/c|count")) != null)
                System.out.println(buildingController.produceResource(optionPass));
            else System.out.println("Invalid command");
        }
    }
}
