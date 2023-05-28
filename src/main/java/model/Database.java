package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.stream.Collectors;

public class Database {
    public static void updateJson() {
        File file = new File("src/main/resources/info.json");
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            JSONArray jsonToArray = new JSONArray();
            JSONParser parser = new JSONParser();
            for (User user : User.users) {
                String jsonParser = gson.toJson(user);
                JSONObject jsonMakeObject = (JSONObject) parser.parse(jsonParser);
                jsonToArray.add(jsonMakeObject);
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonToArray.toString());
            fileWriter.close();
        }
        catch (Exception ignored) {
            System.out.println("Json can not be updated");
        }
    }
    public static void setArrayOfUsers() {
        String json = changeJsonToString();
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            JSONParser parser = new JSONParser();
            JSONArray jsonToArray = new JSONArray();
            User user;
            if (json != null) jsonToArray = (JSONArray) parser.parse(json);
            int counter = 0;
            for (Object jsonValue : jsonToArray) {
                user = gson.fromJson(jsonValue.toString(),User.class);
                new User(user.getUserName(), user.getNickName(), user.getPassword(), user.getEmail(),
                        user.getSlogan(), user.getSecurityQuestionNumber(), user.getAnswerToSecurityQuestion());
                User.users.get(counter).setLoggedIn(user.getLoggedIn());
                User.users.get(counter).setScore(user.getScore());
                counter++;
            }
        }
        catch (Exception ignored) {}
        for (User user : User.users) {
            User.isDelayed.put(user,false);
            User.loginDelays.put(user,-15);
        }
    }

    public static String changeJsonToString() {
        File file = new File("src/main/resources/info.json");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines().collect(Collectors.joining());
        } catch (IOException ignored) {
            return null;
        }
    }
}
