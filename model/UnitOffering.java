package model;

public class UnitOffering {
    private String offeringId;
    private Unit unit;
    private int year;
    private String semester;
    private Instructor instructor;

    public UnitOffering(String offeringId, Unit unit, int year, String semester, Instructor instructor) {
        this.offeringId = offeringId;
        this.unit = unit;
        this.year = year;
        this.semester = semester;
        this.instructor = instructor;
    }

    public String getOfferingId() { return offeringId; }
    public void setOfferingId(String offeringId) { this.offeringId = offeringId; }
    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    @Override
    public String toString() {
        return unit.getCode() + " - " + semester + " " + year + " (" + instructor.getName() + ")";
    }
}