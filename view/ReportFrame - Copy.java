package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.EnrollmentManager;
import controller.StudentManager;
import controller.UnitManager;

public class ReportFrame extends JFrame {
    private JButton btnStudentReport, btnUnitReport, btnBack;
    private JTextArea txtAreaOutput;
    private EnrollmentManager enrollmentManager;
    private StudentManager studentManager;
    private UnitManager unitManager;

    public ReportFrame() {
        // Initialize all managers
        studentManager = new StudentManager();
        unitManager = new UnitManager();
        enrollmentManager = new EnrollmentManager(); // This now has its own dependencies
        
        setTitle("Reports");
        setSize(700, 600); // Increased size for better visibility
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnStudentReport = new JButton("Student Enrollment Report");
        btnUnitReport = new JButton("Unit Enrollment Report");
        btnBack = new JButton("Back to Main Menu");
        buttonPanel.add(btnStudentReport);
        buttonPanel.add(btnUnitReport);
        buttonPanel.add(btnBack);

        // Output area
        txtAreaOutput = new JTextArea();
        txtAreaOutput.setEditable(false);
        txtAreaOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtAreaOutput);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add action listeners
        btnStudentReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateStudentReport();
            }
        });

        btnUnitReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateUnitReport();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void generateStudentReport() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("=== STUDENT ENROLLMENT REPORT ===\n\n");
            
            java.util.List<model.Student> students = studentManager.getAllStudents();
            
            if (students.isEmpty()) {
                sb.append("No students found in the system.\n");
                sb.append("Please add students first from the Student Management menu.");
            } else {
                for (model.Student student : students) {
                    sb.append("STUDENT: ").append(student.getName()).append(" (").append(student.getId()).append(")\n");
                    sb.append("Program: ").append(student.getProgram()).append("\n");
                    sb.append("Email: ").append(student.getEmail()).append("\n");
                    
                    java.util.List<model.Enrolment> enrolments = enrollmentManager.getEnrolmentsByStudent(student);
                    sb.append("Total Enrollments: ").append(enrolments.size()).append("\n");
                    
                    if (!enrolments.isEmpty()) {
                        sb.append("Enrolled Units:\n");
                        for (model.Enrolment enrolment : enrolments) {
                            sb.append("  - ").append(enrolment.getUnitOffering().getUnit().getCode())
                              .append(": ").append(enrolment.getUnitOffering().getUnit().getName())
                              .append(" (Grade: ").append(enrolment.getGrade()).append(")\n");
                        }
                    }
                    sb.append("---".repeat(20)).append("\n");
                }
            }
            
            txtAreaOutput.setText(sb.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generating student report: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void generateUnitReport() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("=== UNIT ENROLLMENT REPORT ===\n\n");
            
            java.util.List<model.Unit> units = unitManager.getAllUnits();
            
            if (units.isEmpty()) {
                sb.append("No units found in the system.\n");
                sb.append("Please add units first from the Unit Management menu.");
            } else {
                for (model.Unit unit : units) {
                    sb.append("UNIT: ").append(unit.getCode()).append(" - ").append(unit.getName()).append("\n");
                    sb.append("Credits: ").append(unit.getCredits()).append("\n");
                    sb.append("Description: ").append(unit.getDescription()).append("\n");
                    
                    java.util.List<model.Enrolment> enrolments = enrollmentManager.getEnrolmentsByUnit(unit.getCode());
                    sb.append("Total Enrollments: ").append(enrolments.size()).append("\n");
                    
                    if (!enrolments.isEmpty()) {
                        sb.append("Enrolled Students:\n");
                        for (model.Enrolment enrolment : enrolments) {
                            sb.append("  - ").append(enrolment.getStudent().getName())
                              .append(" (").append(enrolment.getStudent().getId()).append(")\n");
                        }
                    }
                    sb.append("---".repeat(20)).append("\n");
                }
            }
            
            txtAreaOutput.setText(sb.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generating unit report: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}