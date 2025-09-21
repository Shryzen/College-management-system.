package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.InstructorManager;
import model.Instructor;

public class InstructorManagementFrame extends JFrame {
    private JTextField txtId, txtName, txtEmail, txtDepartment;
    private JButton btnAdd, btnView, btnBack;
    private JTextArea txtAreaOutput;
    private InstructorManager instructorManager;

    public InstructorManagementFrame() {
        instructorManager = new InstructorManager();
        
        setTitle("Instructor Management");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Instructor ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);

        inputPanel.add(new JLabel("Department:"));
        txtDepartment = new JTextField();
        inputPanel.add(txtDepartment);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Add Instructor");
        btnView = new JButton("View All Instructors");
        btnBack = new JButton("Back to Main Menu");
        buttonPanel.add(btnAdd);
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
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInstructor();
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllInstructors();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void addInstructor() {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String email = txtEmail.getText();
            String department = txtDepartment.getText();

            if (id.isEmpty() || name.isEmpty() || email.isEmpty() || department.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            Instructor instructor = new Instructor(id, name, email, department);
            instructorManager.addInstructor(instructor);

            JOptionPane.showMessageDialog(this, "Instructor added successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void viewAllInstructors() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ALL INSTRUCTORS ===\n\n");
        
        for (Instructor instructor : instructorManager.getAllInstructors()) {
            sb.append("ID: ").append(instructor.getId()).append("\n");
            sb.append("Name: ").append(instructor.getName()).append("\n");
            sb.append("Email: ").append(instructor.getEmail()).append("\n");
            sb.append("Department: ").append(instructor.getDepartment()).append("\n");
            sb.append("---\n");
        }
        
        if (instructorManager.getAllInstructors().isEmpty()) {
            sb.append("No instructors found. Please add some instructors first.");
        }
        
        txtAreaOutput.setText(sb.toString());
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtDepartment.setText("");
    }
}