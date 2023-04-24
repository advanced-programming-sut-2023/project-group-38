package view;

import controller.CommandParser;
import controller.Controller;
import controller.LoginController;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;

public class LoginMenu {
    private Controller controller;
    private final CommandParser commandParser;
    public LoginMenu (Controller controller) {
        this.controller = controller;
        commandParser = new CommandParser();
    }
    public String run() {
        HashMap<String, String> optionPass;
        String command ,result;
        while (true) {
            command = CommandParser.getScanner().nextLine();
            if (commandParser.validate(command,"exit",null) != null) return "exit";
            if (commandParser.validate(command,"show current menu",null) != null) System.out.println("Login Menu");
            else if ((optionPass = commandParser.validate(command,"user create","u|username/?p|password/n|nickname/e|email/s|slogan")) != null)
                System.out.println(controller.createUser(optionPass));
            else if ((optionPass = commandParser.validate(command,"user login","u|username/p|password/s|stay-logged-in")) != null) {
                result = controller.login(optionPass);
                if (result.equals("login")) {System.out.println("User successfully logged in."); return "login";}
                else System.out.println(result);
            }
            else System.out.println("Invalid command!");
        }
    }
}
