package org.example.View;

import org.example.Controller.MainController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    // Componentes atualizados
    private JTextField userField;
    private JPasswordField passField; // Usando JPasswordField para segurança
    private JButton loginButton;

    // Construtor
    public LoginPanel(MainController controller) {
        initComponents();
        createUI();
    }

    private void initComponents() {
        userField = new JTextField(20);
        passField = new JPasswordField(20);
        loginButton = new JButton("Fazer Login ➡️");

        // Estilizando o botão
        loginButton.setBackground(new Color(60, 141, 188)); // Azul moderno
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
    }

    private void createUI() {
        // Usando GridBagLayout para controle preciso de alinhamento e espaçamento
        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(50, 50, 50, 50)); // Padding externo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Espaçamento entre as linhas
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz com que os campos preencham a largura

        // --- Título/Header ---
        JLabel headerLabel = new JLabel("Acesso ao Sistema", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.insets = new Insets(0, 0, 30, 0); // Espaço abaixo do título
        this.add(headerLabel, gbc);

        // Resetar para as linhas do formulário
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(8, 5, 8, 5); // Espaçamento padrão

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
        gbc.gridwidth = 2; // Ocupa as duas colunas
        gbc.weightx = 1.0;
        gbc.insets = new Insets(20, 0, 0, 0); // Mais espaço acima do botão
        gbc.fill = GridBagConstraints.NONE; // Não estica o botão pela largura
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza o botão
        this.add(loginButton, gbc);
    }

    // --- MÉTODOS FUNCIONAIS (SEM ALTERAÇÃO NA LÓGICA) ---

    // Este método agora retorna o texto do JPasswordField (char[] convertido para String)
    public String getPassInput(){
        // Nota: É mais seguro pegar o char[] e convertê-lo imediatamente para String
        return new String(passField.getPassword());
    }

    public String getUserInput(){
        return userField.getText();
    }

    public void setLoginListener(ActionListener listener){
        loginButton.addActionListener(listener);
    }
}