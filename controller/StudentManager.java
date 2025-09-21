package controller;

import model.Student;
import java.util.ArrayList;

public class StudentManager extends DataManager {
    private ArrayList<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
        loadStudents();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
    }

    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }
        return null;
    }

    private void saveStudents() {
        ArrayList<String> studentData = new ArrayList<>();
        for (Student student : students) {
            String data = student.getId() + "," + student.getName() + "," + 
                         student.getEmail() + "," + student.getProgram();
            studentData.add(data);
        }
        saveToFile(studentData, "students.txt");
    }

    private void loadStudents() {
        ArrayList<String> lines = loadFromFile("students.txt");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                Student student = new Student(parts[0], parts[1], parts[2], parts[3]);
                students.add(student);
            }
        }
    }
}