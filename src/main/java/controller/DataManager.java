package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataManager {
    private static DataManager sharedInstance;
    private DataManager() {}

    public static DataManager shared() {
        if (sharedInstance == null) {
            sharedInstance = new DataManager();
        }
        return sharedInstance;
    }

    public static void saveData() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(sharedInstance);
        try (PrintStream out = new PrintStream(new FileOutputStream("data.txt"))) {
            out.print(json);
        } catch (IOException e) {
            System.out.println("Unexpected exception happened in saving data: " + e.getLocalizedMessage());
        }
    }

    public static void loadData() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("data.txt")));
            Gson gson = new Gson();
            sharedInstance = gson.fromJson(json, DataManager.class);
        } catch (IOException e) {
            System.out.println("Unexpected exception happened in loading data: " + e.getLocalizedMessage());
        }
    }

}
