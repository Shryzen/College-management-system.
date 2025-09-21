package controller;

import model.Unit;
import java.util.ArrayList;

public class UnitManager extends DataManager {
    private ArrayList<Unit> units;

    public UnitManager() {
        units = new ArrayList<>();
        loadUnits();
    }

    public void addUnit(Unit unit) {
        units.add(unit);
        saveUnits();
    }

    public ArrayList<Unit> getAllUnits() {
        return new ArrayList<>(units);
    }

    public Unit findUnitByCode(String code) {
        for (Unit unit : units) {
            if (unit.getCode().equalsIgnoreCase(code)) {
                return unit;
            }
        }
        return null;
    }

    public void sortUnitsByName() {
        for (int i = 0; i < units.size() - 1; i++) {
            for (int j = 0; j < units.size() - i - 1; j++) {
                if (units.get(j).getName().compareToIgnoreCase(units.get(j + 1).getName()) > 0) {
                    Unit temp = units.get(j);
                    units.set(j, units.get(j + 1));
                    units.set(j + 1, temp);
                }
            }
        }
        saveUnits();
    }

    private void saveUnits() {
        ArrayList<String> unitData = new ArrayList<>();
        for (Unit unit : units) {
            String data = unit.getCode() + "," + unit.getName() + "," + 
                         unit.getCredits() + "," + unit.getDescription() + "," +
                         String.join(";", unit.getPrerequisites());
            unitData.add(data);
        }
        saveToFile(unitData, "units.txt");
    }

    private void loadUnits() {
        ArrayList<String> lines = loadFromFile("units.txt");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                Unit unit = new Unit(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]);
                if (parts.length > 4 && !parts[4].isEmpty()) {
                    String[] prereqs = parts[4].split(";");
                    for (String prereq : prereqs) {
                        unit.addPrerequisite(prereq);
                    }
                }
                units.add(unit);
            }
        }
    }
}