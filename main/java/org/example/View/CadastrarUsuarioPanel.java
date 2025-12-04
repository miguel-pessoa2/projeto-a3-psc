package org.example.View;

import org.example.Controller.AdminController;
import org.example.Model.CadastroData;
import org.example.Model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import javax.swing.border.EmptyBorder;

public class CadastrarUsuarioPanel extends JPanel {

    private JTextField nomeInput;
    private JPasswordField senhaInput;
    private JPasswordField confirmarSenhaInput;

    private JComboBox<String> acessoInput;
    private JComboBox<String> interesse1Input;
    private JComboBox<String> interesse2Input;

    private JButton salvarButton;
    private JButton cancelarButton;

    public CadastrarUsuarioPanel() {

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding

        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        nomeInput = new JTextField(20);
        senhaInput = new JPasswordField(20);
        confirmarSenhaInput = new JPasswordField(20);

        String[] interesses = {"IA Responsável", "Cibersegurança", "Privacidade & Ética"};
        interesse1Input = new JComboBox<>(interesses);
        interesse2Input = new JComboBox<>(interesses);

        String[] niveisAcesso = {"Usuário", "Administrador"};
        acessoInput = new JComboBox<>(niveisAcesso);

        salvarButton = new JButton("Salvar Cadastro");
        salvarButton.setBackground(new Color(60, 141, 188));
        salvarButton.setForeground(Color.WHITE);

        cancelarButton = new JButton("Cancelar");

        cancelarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        // --- 1. Título do Formulário ---
        JLabel titulo = new JLabel("Novo Usuário", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 5, 8, 5);

        // --- 2. Nome ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Nome/Username:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(nomeInput, gbc);
        gbc.weightx = 0.0;

        // --- 3. Senha ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(senhaInput, gbc);
        gbc.weightx = 0.0;

        // --- 4. Confirmar Senha ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Confirme a Senha:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(confirmarSenhaInput, gbc);
        gbc.weightx = 0.0;

        // --- 5. Nível de Acesso ---
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Nível de Acesso:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(acessoInput, gbc);
        gbc.weightx = 0.0;

        // --- 6. Interesse 1 ---
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Interesse 1:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(interesse1Input, gbc);
        gbc.weightx = 0.0;

        // --- 7. Interesse 2 ---
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Interesse 2:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(interesse2Input, gbc);
        gbc.weightx = 0.0;

        // --- 8. Botões de Ação ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(cancelarButton);
        buttonPanel.add(salvarButton);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 5, 0, 5);
        add(buttonPanel, gbc);
    }

    public CadastroData getFormData(){
        String nome = nomeInput.getText().trim();
        char[] senha = senhaInput.getPassword();
        char[] confirmacao = confirmarSenhaInput.getPassword();

        String acessoFormatado = switch((String) acessoInput.getSelectedItem()){
            case "Usuário" -> "usuario";
            case "Administrador" -> "admin";
            default -> null;
        };

        String interesse1Formatado = switch((String) interesse1Input.getSelectedItem()){
            case "IA Responsável" -> "iaresponsavel";
            case "Cibersegurança" -> "ciberseguranca";
            case "Privacidade & Ética" -> "privacidade&etica";
            default -> null;
        };

        String interesse2Formatado = switch((String) interesse2Input.getSelectedItem()){
            case "IA Responsável" -> "iaresponsavel";
            case "Cibersegurança" -> "ciberseguranca";
            case "Privacidade & Ética" -> "privacidade&etica";
            default -> null;
        };

        return new CadastroData(nome, senha, confirmacao, acessoFormatado, interesse1Formatado, interesse2Formatado);
    }
    public JButton getSalvarButton() {
        return salvarButton;
    }
}