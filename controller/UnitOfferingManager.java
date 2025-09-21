package controller;

import java.util.ArrayList;
import model.Instructor;
import model.Unit;
import model.UnitOffering;

public class UnitOfferingManager extends DataManager {
    private ArrayList<UnitOffering> unitOfferings;
    private UnitManager unitManager;
    private InstructorManager instructorManager;

    public UnitOfferingManager() {
        this.unitOfferings = new ArrayList<>();
        this.unitManager = new UnitManager();
        this.instructorManager = new InstructorManager();
        loadUnitOfferings();
    }

    public void addUnitOffering(UnitOffering offering) {
        unitOfferings.add(offering);
        saveUnitOfferings();
    }

    public ArrayList<UnitOffering> getAllUnitOfferings() {
        return new ArrayList<>(unitOfferings);
    }

    public UnitOffering findUnitOfferingById(String offeringId) {
        for (UnitOffering offering : unitOfferings) {
            if (offering.getOfferingId().equalsIgnoreCase(offeringId)) {
                return offering;
            }
        }
        return null;
    }

    private void saveUnitOfferings() {
        ArrayList<String> offeringData = new ArrayList<>();
        for (UnitOffering offering : unitOfferings) {
            String data = offering.getOfferingId() + "," + 
                         offering.getUnit().getCode() + "," +
                         offering.getYear() + "," +
                         offering.getSemester() + "," +
                         offering.getInstructor().getId();
            offeringData.add(data);
        }
        saveToFile(offeringData, "unit_offerings.txt");
    }

    private void loadUnitOfferings() {
        ArrayList<String> lines = loadFromFile("unit_offerings.txt");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 5) {
                Unit unit = unitManager.findUnitByCode(parts[1]);
                Instructor instructor = instructorManager.findInstructorById(parts[4]);
                if (unit != null && instructor != null) {
                    UnitOffering offering = new UnitOffering(parts[0], unit, 
                        Integer.parseInt(parts[2]), parts[3], instructor);
                    unitOfferings.add(offering);
                }
            }
        }
    }
}