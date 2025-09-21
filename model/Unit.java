package model;

import java.util.ArrayList;

public class Unit {
    private String code;
    private String name;
    private int credits;
    private String description;
    private ArrayList<String> prerequisites;

    public Unit(String code, String name, int credits, String description) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.description = description;
        this.prerequisites = new ArrayList<>();
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ArrayList<String> getPrerequisites() { return prerequisites; }
    public void setPrerequisites(ArrayList<String> prerequisites) { this.prerequisites = prerequisites; }

    public void addPrerequisite(String unitCode) {
        prerequisites.add(unitCode);
    }

    @Override
    public String toString() {
        return code + " - " + name + " (" + credits + " credits)";
    }
}