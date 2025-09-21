package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.UnitManager;
import model.Unit;

public class UnitManagementFrame extends JFrame {
    private JTextField txtCode, txtName, txtCredits, txtDescription;
    private JButton btnAdd, btnView, btnBack;
    private JTextArea txtAreaOutput;
    private UnitManager unitManager;

    public UnitManagementFrame() {
        unitManager = new UnitManager();
        
        setTitle("Unit Management");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("Unit Code:"));
        txtCode = new JTextField();
        inputPanel.add(txtCode);

        inputPanel.add(new JLabel("Unit Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Credits:"));
        txtCredits = new JTextField();
        inputPanel.add(txtCredits);

        inputPanel.add(new JLabel("Description:"));
        txtDescription = new JTextField();
        inputPanel.add(txtDescription);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Add Unit");
        btnView = new JButton("View All Units");
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
                addUnit();
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllUnits();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void addUnit() {
        try {
            String code = txtCode.getText();
            String name = txtName.getText();
            int credits = Integer.parseInt(txtCredits.getText());
            String description = txtDescription.getText();

            Unit unit = new Unit(code, name, credits, description);
            unitManager.addUnit(unit);

            JOptionPane.showMessageDialog(this, "Unit added successfully!");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid credits number!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void viewAllUnits() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ALL UNITS ===\n\n");
        
        for (Unit unit : unitManager.getAllUnits()) {
            sb.append(unit.toString()).append("\n");
            sb.append("Description: ").append(unit.getDescription()).append("\n");
            sb.append("Prerequisites: ").append(unit.getPrerequisites()).append("\n");
            sb.append("---\n");
        }
        
        txtAreaOutput.setText(sb.toString());
    }

    private void clearFields() {
        txtCode.setText("");
        txtName.setText("");
        txtCredits.setText("");
        txtDescription.setText("");
    }
}