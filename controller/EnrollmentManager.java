package controller;

import model.Enrolment;
import model.Student;
import model.UnitOffering;
import java.util.ArrayList;

public class EnrollmentManager extends DataManager {
    private ArrayList<Enrolment> enrolments;
    private StudentManager studentManager;
    private UnitOfferingManager offeringManager;

    public EnrollmentManager() {
        this.enrolments = new ArrayList<>();
        this.studentManager = new StudentManager();
        this.offeringManager = new UnitOfferingManager();
        loadEnrolments();
    }

    public void addEnrolment(Enrolment enrolment) {
        if (enrolment == null) {
            System.err.println("Error: Cannot add null enrolment");
            return;
        }
        
        // Check if student exists
        if (enrolment.getStudent() == null || studentManager.findStudentById(enrolment.getStudent().getId()) == null) {
            System.err.println("Error: Student does not exist for enrolment");
            return;
        }
        
        // Check if unit offering exists
        if (enrolment.getUnitOffering() == null || offeringManager.findUnitOfferingById(enrolment.getUnitOffering().getOfferingId()) == null) {
            System.err.println("Error: Unit offering does not exist for enrolment");
            return;
        }

        enrolments.add(enrolment);
        saveEnrolments();
    }

    public ArrayList<Enrolment> getEnrolmentsByStudent(Student student) {
        ArrayList<Enrolment> result = new ArrayList<>();
        if (student == null) {
            return result;
        }
        
        for (Enrolment enrolment : enrolments) {
            if (enrolment.getStudent() != null && 
                enrolment.getStudent().getId().equals(student.getId())) {
                result.add(enrolment);
            }
        }
        return result;
    }

    public ArrayList<Enrolment> getEnrolmentsByStudentId(String studentId) {
        ArrayList<Enrolment> result = new ArrayList<>();
        if (studentId == null || studentId.isEmpty()) {
            return result;
        }
        
        Student student = studentManager.findStudentById(studentId);
        if (student == null) {
            return result;
        }
        
        return getEnrolmentsByStudent(student);
    }

    public ArrayList<Enrolment> getEnrolmentsByUnit(String unitCode) {
        ArrayList<Enrolment> result = new ArrayList<>();
        if (unitCode == null || unitCode.isEmpty()) {
            return result;
        }
        
        for (Enrolment enrolment : enrolments) {
            if (enrolment.getUnitOffering() != null && 
                enrolment.getUnitOffering().getUnit() != null &&
                enrolment.getUnitOffering().getUnit().getCode().equalsIgnoreCase(unitCode)) {
                result.add(enrolment);
            }
        }
        return result;
    }

    public ArrayList<Enrolment> getEnrolmentsByOffering(String offeringId) {
        ArrayList<Enrolment> result = new ArrayList<>();
        if (offeringId == null || offeringId.isEmpty()) {
            return result;
        }
        
        for (Enrolment enrolment : enrolments) {
            if (enrolment.getUnitOffering() != null && 
                enrolment.getUnitOffering().getOfferingId().equalsIgnoreCase(offeringId)) {
                result.add(enrolment);
            }
        }
        return result;
    }

    public ArrayList<Enrolment> getAllEnrolments() {
        return new ArrayList<>(enrolments);
    }

    public int getTotalEnrolments() {
        return enrolments.size();
    }

    public boolean removeEnrolment(String enrolmentId) {
        if (enrolmentId == null || enrolmentId.isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < enrolments.size(); i++) {
            Enrolment enrolment = enrolments.get(i);
            if (enrolment != null && enrolment.getEnrolmentId().equals(enrolmentId)) {
                enrolments.remove(i);
                saveEnrolments();
                return true;
            }
        }
        return false;
    }

    public Enrolment findEnrolmentById(String enrolmentId) {
        if (enrolmentId == null || enrolmentId.isEmpty()) {
            return null;
        }
        
        for (Enrolment enrolment : enrolments) {
            if (enrolment != null && enrolment.getEnrolmentId().equals(enrolmentId)) {
                return enrolment;
            }
        }
        return null;
    }

    public boolean updateEnrolmentGrade(String enrolmentId, String grade) {
        if (enrolmentId == null || enrolmentId.isEmpty() || grade == null) {
            return false;
        }
        
        Enrolment enrolment = findEnrolmentById(enrolmentId);
        if (enrolment != null) {
            enrolment.setGrade(grade);
            saveEnrolments();
            return true;
        }
        return false;
    }

    private void saveEnrolments() {
        ArrayList<String> enrolmentData = new ArrayList<>();
        for (Enrolment enrolment : enrolments) {
            if (enrolment != null && 
                enrolment.getStudent() != null && 
                enrolment.getUnitOffering() != null) {
                
                String data = enrolment.getEnrolmentId() + "," + 
                             enrolment.getStudent().getId() + "," +
                             enrolment.getUnitOffering().getOfferingId() + "," +
                             enrolment.getEnrolmentDate() + "," +
                             (enrolment.getGrade() != null ? enrolment.getGrade() : "Not Graded");
                enrolmentData.add(data);
            }
        }
        saveToFile(enrolmentData, "enrolments.txt");
    }

    private void loadEnrolments() {
        ArrayList<String> lines = loadFromFile("enrolments.txt");
        for (String line : lines) {
            if (line != null && !line.trim().isEmpty()) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        String enrolmentId = parts[0];
                        String studentId = parts[1];
                        String offeringId = parts[2];
                        String enrolmentDate = parts[3];
                        String grade = parts[4];

                        Student student = studentManager.findStudentById(studentId);
                        UnitOffering offering = offeringManager.findUnitOfferingById(offeringId);

                        if (student != null && offering != null) {
                            Enrolment enrolment = new Enrolment(enrolmentId, student, offering);
                            
                            // Parse date if available
                            if (enrolmentDate != null && !enrolmentDate.equals("null")) {
                                try {
                                    enrolment.setEnrolmentDate(java.time.LocalDate.parse(enrolmentDate));
                                } catch (Exception e) {
                                    System.err.println("Error parsing date: " + enrolmentDate);
                                }
                            }
                            
                            // Set grade if available
                            if (grade != null && !grade.isEmpty() && !grade.equals("null")) {
                                enrolment.setGrade(grade);
                            }
                            
                            enrolments.add(enrolment);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error loading enrolment from line: " + line);
                    e.printStackTrace();
                }
            }
        }
    }

    public void clearAllEnrolments() {
        enrolments.clear();
        saveEnrolments();
    }

    public boolean isStudentEnrolledInUnit(String studentId, String unitCode) {
        if (studentId == null || unitCode == null) {
            return false;
        }
        
        for (Enrolment enrolment : enrolments) {
            if (enrolment != null && 
                enrolment.getStudent() != null &&
                enrolment.getStudent().getId().equals(studentId) &&
                enrolment.getUnitOffering() != null &&
                enrolment.getUnitOffering().getUnit() != null &&
                enrolment.getUnitOffering().getUnit().getCode().equalsIgnoreCase(unitCode)) {
                return true;
            }
        }
        return false;
    }
}