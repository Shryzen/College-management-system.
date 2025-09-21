package controller;

import java.io.*;
import java.util.ArrayList;

public class DataManager {
    protected void saveToFile(ArrayList<String> data, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data/" + filename))) {
            for (String line : data) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    protected ArrayList<String> loadFromFile(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        File file = new File("data/" + filename);
        if (!file.exists()) {
            return lines;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error loading from file: " + e.getMessage());
        }
        return lines;
    }
}