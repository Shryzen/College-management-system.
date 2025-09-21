package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Student;
import model.UnitOffering;
import model.Enrolment;

public class EnrollmentFrame extends JFrame {
    private JTextField txtStudentId, txtOfferingId;
    private JButton btnEnroll, btnView, btnBack;
    private JTextArea txtAreaOutput;
    private controller.EnrollmentManager enrollmentManager;

    public EnrollmentFrame() {
        enrollmentManager = new controller.EnrollmentManager();
        
        setTitle("Student Enrollment");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Student ID:"));
        txtStudentId = new JTextField();
        inputPanel.add(txtStudentId);

        inputPanel.add(new JLabel("Unit Offering ID:"));
        txtOfferingId = new JTextField();
        inputPanel.add(txtOfferingId);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnEnroll = new JButton("Enroll Student");
        btnView = new JButton("View All Enrollments");
        btnBack = new JButton("Back to Main Menu");
        buttonPanel.add(btnEnroll);
        buttonPanel.add(btnView);
        buttonPanel.add(btnBack);

        // Output area
        txtAreaOutput = new JTextArea();
        txtAreaOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaOutput);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Add action listeners
        btnEnroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enrollStudent();
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllEnrollments();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void enrollStudent() {
        try {
            String studentId = txtStudentId.getText();
            String offeringId = txtOfferingId.getText();

            controller.StudentManager studentManager = new controller.StudentManager();
            controller.UnitOfferingManager offeringManager = new controller.UnitOfferingManager();

            Student student = studentManager.findStudentById(studentId);
            UnitOffering offering = offeringManager.findUnitOfferingById(offeringId);

            if (student == null) {
                JOptionPane.showMessageDialog(this, "Student not found!");
                return;
            }

            if (offering == null) {
                JOptionPane.showMessageDialog(this, "Unit Offering not found!");
                return;
            }

            String enrolmentId = "ENR" + System.currentTimeMillis();
            Enrolment enrolment = new Enrolment(enrolmentId, student, offering);
            
            enrollmentManager.addEnrolment(enrolment);
            JOptionPane.showMessageDialog(this, "Student enrolled successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void viewAllEnrollments() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ALL ENROLLMENTS ===\n\n");
        
        for (Enrolment enrolment : enrollmentManager.getAllEnrolments()) {
            sb.append("Enrollment ID: ").append(enrolment.getEnrolmentId()).append("\n");
            sb.append("Student: ").append(enrolment.getStudent().getName()).append("\n");
            sb.append("Unit: ").append(enrolment.getUnitOffering().getUnit().getCode()).append(" - ")
              .append(enrolment.getUnitOffering().getUnit().getName()).append("\n");
            sb.append("Instructor: ").append(enrolment.getUnitOffering().getInstructor().getName()).append("\n");
            sb.append("Date: ").append(enrolment.getEnrolmentDate()).append("\n");
            sb.append("Grade: ").append(enrolment.getGrade()).append("\n");
            sb.append("---\n");
        }
        
        if (enrollmentManager.getAllEnrolments().isEmpty()) {
            sb.append("No enrollments found. Please enroll some students first.");
        }
        
        txtAreaOutput.setText(sb.toString());
    }

    private void clearFields() {
        txtStudentId.setText("");
        txtOfferingId.setText("");
    }
}