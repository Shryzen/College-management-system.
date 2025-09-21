package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainMenu extends JFrame {
    private JButton btnManageUnits, btnManageStudents, btnManageInstructors, 
                   btnEnroll, btnReports, btnExit;

    public MainMenu() {
        setTitle("College Management System - Main Menu");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        btnManageUnits = new JButton("Manage Units");
        btnManageStudents = new JButton("Manage Students");
        btnManageInstructors = new JButton("Manage Instructors");
        btnEnroll = new JButton("Enroll Students");
        btnReports = new JButton("Generate Reports");
        btnExit = new JButton("Exit");

        add(btnManageUnits);
        add(btnManageStudents);
        add(btnManageInstructors);
        add(btnEnroll);
        add(btnReports);
        add(btnExit);

        // Add action listeners
        btnManageUnits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UnitManagementFrame().setVisible(true);
            }
        });

        btnManageStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentManagementFrame().setVisible(true);
            }
        });

       btnManageInstructors.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        new InstructorManagementFrame().setVisible(true);
    }
});

        btnEnroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EnrollmentFrame().setVisible(true);
            }
        });

        btnReports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportFrame().setVisible(true);
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}