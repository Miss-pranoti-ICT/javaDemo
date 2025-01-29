package com.example;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginAppGUI {


    // Database connection details
    static final String url = "jdbc:mysql://localhost:3306/logindb";
    static final String username = "root";
    static final String password = "1234";


    public static void main(String[] args) {
        // Create the main frame for the login screen
        JFrame frame = new JFrame("Login Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null); // Center the window


        // Create panel to add components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // Grid layout for form input


        // Create components
        JLabel userLabel = new JLabel("Username: ");
        JTextField userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password: ");
        JPasswordField passField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");


        // Add components to the panel
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel()); // Empty label for layout purposes
        panel.add(loginButton);


        // Add panel to the frame hellow i am checking git 
        frame.add(panel);


        // Action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputUsername = userField.getText();
                String inputPassword = new String(passField.getPassword());


                // Try connecting to the database and verifying credentials
                try (Connection conn = DriverManager.getConnection(url, username, password)) {
                    String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, inputUsername);
                    stmt.setString(2, inputPassword);


                    ResultSet rs = stmt.executeQuery();


                    if (rs.next()) {
                        // Successful login
                        JOptionPane.showMessageDialog(frame, "Welcome, " + inputUsername, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Invalid credentials
                        JOptionPane.showMessageDialog(frame, "Invalid username or password. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }


                    // Close resources
                    rs.close();
                    stmt.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Database connection failed: " + ex.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Set the frame visible
        frame.setVisible(true);
    }
}
