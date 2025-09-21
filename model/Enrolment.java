package model;

import java.time.LocalDate;

public class Enrolment {
    private String enrolmentId;
    private Student student;
    private UnitOffering unitOffering;
    private LocalDate enrolmentDate;
    private String grade;

    public Enrolment(String enrolmentId, Student student, UnitOffering unitOffering) {
        this.enrolmentId = enrolmentId;
        this.student = student;
        this.unitOffering = unitOffering;
        this.enrolmentDate = LocalDate.now();
        this.grade = "Not Graded";
    }

    public String getEnrolmentId() { return enrolmentId; }
    public void setEnrolmentId(String enrolmentId) { this.enrolmentId = enrolmentId; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public UnitOffering getUnitOffering() { return unitOffering; }
    public void setUnitOffering(UnitOffering unitOffering) { this.unitOffering = unitOffering; }
    public LocalDate getEnrolmentDate() { return enrolmentDate; }
    public void setEnrolmentDate(LocalDate enrolmentDate) { this.enrolmentDate = enrolmentDate; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return student.getName() + " enrolled in " + unitOffering.getUnit().getCode() + " on " + enrolmentDate;
    }
}