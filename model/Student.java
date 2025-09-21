package model;

public class Student extends Person {
    private String program;

    public Student(String id, String name, String email, String program) {
        super(id, name, email);
        this.program = program;
    }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    @Override
    public String toString() {
        return super.toString() + " - " + program;
    }
}