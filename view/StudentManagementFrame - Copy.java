package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.StudentManager;
import model.Student;

public class StudentManagementFrame extends JFrame {
    private JTextField txtId, txtName, txtEmail, txtProgram;
    private JButton btnAdd, btnView, btnBack;
    private JTextArea txtAreaOutput;
    private StudentManager studentManager;

    public StudentManagementFrame() {
        studentManager = new StudentManager();
        
        setTitle("Student Management");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Student ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);

        inputPanel.add(new JLabel("Program:"));
        txtProgram = new JTextField();
        inputPanel.add(txtProgram);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Add Student");
        btnView = new JButton("View All Students");
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
                addStudent();
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllStudents();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void addStudent() {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String email = txtEmail.getText();
            String program = txtProgram.getText();

            Student student = new Student(id, name, email, program);
            studentManager.addStudent(student);

            JOptionPane.showMessageDialog(this, "Student added successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void viewAllStudents() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ALL STUDENTS ===\n\n");
        
        for (Student student : studentManager.getAllStudents()) {
            sb.append("ID: ").append(student.getId()).append("\n");
            sb.append("Name: ").append(student.getName()).append("\n");
            sb.append("Email: ").append(student.getEmail()).append("\n");
            sb.append("Program: ").append(student.getProgram()).append("\n");
            sb.append("---\n");
        }
        
        txtAreaOutput.setText(sb.toString());
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtProgram.setText("");
    }
}