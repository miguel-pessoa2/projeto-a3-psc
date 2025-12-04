package org.example.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPanel extends JPanel {

    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginPanel() {
        initComponents();
        createUI();
    }

    private void initComponents() {
        userField = new JTextField(20);
        passField = new JPasswordField(20);
        loginButton = new JButton("Fazer Login");

        loginButton.setBackground(new Color(60, 141, 188)); // Azul moderno
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
    }

    private void createUI() {
        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(50, 50, 50, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Título/Header ---
        JLabel headerLabel = new JLabel("Acesso ao Sistema", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        this.add(headerLabel, gbc);

        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(8, 5, 8, 5);

        // --- 1. Campo Usuário ---
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(new JLabel("Usuário:"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        this.add(userField, gbc);

        // --- 2. Campo Senha ---
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.0;
        this.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        this.add(passField, gbc);

        // --- 3. Botão Login ---
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(loginButton, gbc);
    }

    //getters
    public String getSenhaInput(){
        return new String(passField.getPassword());
    }
    public String getUsuarioInput(){
        return userField.getText();
    }
    public JButton getLoginButton() {
        return loginButton;
    }
}