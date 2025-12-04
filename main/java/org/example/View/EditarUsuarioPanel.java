package org.example.View;

import org.example.Model.Usuario;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class EditarUsuarioPanel extends JPanel {

    private final Usuario usuarioOriginal;

    // Componentes de Entrada
    private JTextField nomeInput;
    private JComboBox<String> acessoInput;
    private JComboBox<String> interesse1Input;
    private JComboBox<String> interesse2Input;

    // Componentes de Ação
    private JButton salvarButton;
    private JButton cancelarButton;
    private JButton alterarSenhaButton;

    public EditarUsuarioPanel(Usuario usuario) {
        this.usuarioOriginal = usuario;

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        initComponents();
        preencherDados();
        layoutComponents();
        setupListeners();
    }

    private void initComponents() {
        nomeInput = new JTextField(20);

        String[] interesses = {"IA Responsável", "Cibersegurança", "Privacidade & Ética"};
        interesse1Input = new JComboBox<>(interesses);
        interesse2Input = new JComboBox<>(interesses);

        String[] niveisAcesso = {"Usuário", "Administrador"};
        acessoInput = new JComboBox<>(niveisAcesso);

        salvarButton = new JButton("Salvar Alterações");
        salvarButton.setBackground(new Color(60, 141, 188));
        salvarButton.setForeground(Color.WHITE);

        cancelarButton = new JButton("Cancelar");
        alterarSenhaButton = new JButton("Alterar Senha");
    }

    private void preencherDados() {
        nomeInput.setText(usuarioOriginal.getNome());

        String acessoFormatado = switch(usuarioOriginal.getAcesso()){
            case "usuario" -> "Usuário";
            case "admin" -> "Administrador";
            default -> "não definido";
        };

        String interesse1Formatado = switch(usuarioOriginal.getInteresse1()){
            case "iaresponsavel" -> "IA Responsável";
            case "ciberseguranca" -> "Cibersegurança";
            case "privacidade&etica" -> "Privacidade % Ética";
            default -> null;
        };

        String interesse2Formatado = switch(usuarioOriginal.getInteresse2()){
            case "iaresponsavel" -> "IA Responsável";
            case "ciberseguranca" -> "Cibersegurança";
            case "privacidade&etica" -> "Privacidade % Ética";
            default -> null;
        };

        interesse1Input.setSelectedItem(interesse1Formatado);
        interesse2Input.setSelectedItem(interesse2Formatado);
        acessoInput.setSelectedItem(acessoFormatado);
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        int linhaAtual = 0;

        JLabel titulo = new JLabel("Editar Usuário: ID " + usuarioOriginal.getId(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = linhaAtual++;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 5, 8, 5);

        gbc.gridx = 0; gbc.gridy = linhaAtual; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 0.0;
        add(new JLabel("Nome/Username:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
        add(nomeInput, gbc);
        linhaAtual++;

        gbc.gridx = 0; gbc.gridy = linhaAtual; gbc.gridwidth = 1; gbc.weightx = 0.0;
        add(new JLabel("Nível de Acesso:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
        add(acessoInput, gbc);
        linhaAtual++;

        gbc.gridx = 0; gbc.gridy = linhaAtual++;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 12, 5);

        add(alterarSenhaButton, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        gbc.gridx = 0; gbc.gridy = linhaAtual; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 0.0;
        add(new JLabel("Interesse 1:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
        add(interesse1Input, gbc);
        linhaAtual++;

        gbc.gridx = 0; gbc.gridy = linhaAtual; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 0.0;
        add(new JLabel("Interesse 2:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
        add(interesse2Input, gbc);
        linhaAtual++;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(cancelarButton);
        buttonPanel.add(salvarButton);

        gbc.gridx = 0; gbc.gridy = linhaAtual;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 5, 0, 5);
        add(buttonPanel, gbc);
    }

    private void setupListeners() {

        cancelarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });

        alterarSenhaButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "A função de Alterar Senha será implementada em outro diálogo.",
                    "Ação Futura",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public Usuario getUserData(){
        String novoNome = nomeInput.getText();

        String novoAcessoFormatado = switch((String) acessoInput.getSelectedItem()){
            case "Usuário" -> "usuario";
            case "Administrador" -> "admin";
            default ->  null;
        };

        String novoInteresse1Formatado = switch((String) interesse1Input.getSelectedItem()){
            case "IA Responsável" -> "iaresponsavel";
            case "Cibersegurança" -> "ciberseguranca";
            case "Privacidade & Ética" -> "privacidade&etica";
            default -> null;
        };

        String novoInteresse2Formatado = switch((String) interesse2Input.getSelectedItem()){
            case "IA Responsável" -> "iaresponsavel";
            case "Cibersegurança" -> "ciberseguranca";
            case "Privacidade & Ética" -> "privacidade&etica";
            default -> null;
        };

        return new Usuario(
                usuarioOriginal.getId(),
                novoAcessoFormatado,
                novoNome,
                novoInteresse1Formatado,
                novoInteresse2Formatado,
                usuarioOriginal.isAtivo()
        );
    }
    public JButton getSalvarButton(){
        return salvarButton;
    }

}