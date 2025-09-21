package controller;

import java.util.ArrayList;
import model.Instructor;

public class InstructorManager extends DataManager {
    private ArrayList<Instructor> instructors;

    public InstructorManager() {
        instructors = new ArrayList<>();
        loadInstructors();
    }

    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
        saveInstructors();
    }

    public ArrayList<Instructor> getAllInstructors() {
        return new ArrayList<>(instructors);
    }

    public Instructor findInstructorById(String id) {
        for (Instructor instructor : instructors) {
            if (instructor.getId().equalsIgnoreCase(id)) {
                return instructor;
            }
        }
        return null;
    }

    private void saveInstructors() {
        ArrayList<String> instructorData = new ArrayList<>();
        for (Instructor instructor : instructors) {
            String data = instructor.getId() + "," + instructor.getName() + "," + 
                         instructor.getEmail() + "," + instructor.getDepartment();
            instructorData.add(data);
        }
        saveToFile(instructorData, "instructors.txt");
    }

    private void loadInstructors() {
        ArrayList<String> lines = loadFromFile("instructors.txt");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                Instructor instructor = new Instructor(parts[0], parts[1], parts[2], parts[3]);
                instructors.add(instructor);
            }
        }
    }
}