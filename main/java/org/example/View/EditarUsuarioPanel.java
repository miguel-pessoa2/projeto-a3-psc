package org.example.View;

import org.example.Controller.AdminController;
import org.example.Model.Usuario;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class EditarUsuarioPanel extends JPanel {

    private final Usuario usuarioOriginal;
    private final AdminController controller;

    // Componentes de Entrada
    private JTextField nomeInput;
    private JComboBox<String> acessoInput;
    private JComboBox<String> interesse1Input;
    private JComboBox<String> interesse2Input;

    // Componentes de Ação
    private JButton salvarButton;
    private JButton cancelarButton;
    private JButton alterarSenhaButton;

    public EditarUsuarioPanel(AdminController controller, Usuario usuario) {
        this.controller = controller;
        this.usuarioOriginal = usuario; // Objeto a ser preenchido e editado

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
        interesse1Input = new JComboBox<String>(interesses);
        interesse2Input = new JComboBox<String>(interesses);

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

        String acessoFormatado;
        switch (usuarioOriginal.getAcesso()){
            case "usuario" -> acessoFormatado = "Usuário";
            case "admin" -> acessoFormatado = "Administrador";
            default -> acessoFormatado = "não definido";
        }

        String interesse1Formatado;
        switch(usuarioOriginal.getInteresse1()){
            case "iaresponsavel" -> interesse1Formatado = "IA Responsável";
            case "ciberseguranca" -> interesse1Formatado = "Cibersegurança";
            case "privacidade&etica" -> interesse1Formatado = "Privacidade % Ética";
            default -> interesse1Formatado = null;
        }

        String interesse2Formatado;
        switch(usuarioOriginal.getInteresse2()){
            case "iaresponsavel" -> interesse2Formatado = "IA Responsável";
            case "ciberseguranca" -> interesse2Formatado = "Cibersegurança";
            case "privacidade&etica" -> interesse2Formatado = "Privacidade % Ética";
            default -> interesse2Formatado = null;
        }

        interesse1Input.setSelectedItem(interesse1Formatado);
        interesse2Input.setSelectedItem(interesse2Formatado);
        acessoInput.setSelectedItem(acessoFormatado);
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        // --- 1. Título do Formulário ---
        JLabel titulo = new JLabel("Editar Usuário: ID " + usuarioOriginal.getId(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3; // 3 colunas para o título
        gbc.insets = new Insets(0, 0, 20, 0);
        add(titulo, gbc);

        // Resetar gridwidth e insets
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 5, 8, 5);

        // --- 2. Nome ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Nome/Username:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0; // Ocupa duas colunas
        add(nomeInput, gbc);
        gbc.gridwidth = 1; gbc.weightx = 0.0;

        // --- 3. Nível de Acesso ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Nível de Acesso:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
        add(acessoInput, gbc);
        gbc.gridwidth = 1; gbc.weightx = 0.0;

        // --- A posição do Status (gridy=3) foi removida, o próximo item será gridy=3 ---

        // --- 4. Alterar Senha (Ação separada) ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(15, 5, 5, 5);
        add(alterarSenhaButton, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.gridwidth = 1;

        // --- 5. Interesse 1 ---
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Interesse 1:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
        add(interesse1Input, gbc);
        gbc.gridwidth = 1; gbc.weightx = 0.0;

        // --- 6. Interesse 2 ---
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Interesse 2:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
        add(interesse2Input, gbc);
        gbc.gridwidth = 1; gbc.weightx = 0.0;

        // --- 7. Botões de Ação (Rodapé) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(cancelarButton);
        buttonPanel.add(salvarButton);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 3; // Ocupa 3 colunas
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 5, 0, 5);
        add(buttonPanel, gbc);
    }

    private void setupListeners() {

        salvarButton.addActionListener(e -> {
            // 1. Coletar os novos dados

            String novoNome = nomeInput.getText();

            String novoAcessoFormatado;
            switch((String) acessoInput.getSelectedItem()){
                case "Usuário" -> novoAcessoFormatado = "usuario";
                case "Administrador" -> novoAcessoFormatado = "admin";
                default -> novoAcessoFormatado = null;
            }

            String novoInteresse1Formatado;
            switch((String) interesse1Input.getSelectedItem()){
                case "IA Responsável" -> novoInteresse1Formatado = "iaresponsavel";
                case "Cibersegurança" -> novoInteresse1Formatado = "ciberseguranca";
                case "Privacidade & Ética" -> novoInteresse1Formatado = "privacidade&etica";
                default -> novoInteresse1Formatado = null;
            }

            String novoInteresse2Formatado;
            switch((String) interesse2Input.getSelectedItem()){
                case "IA Responsável" -> novoInteresse2Formatado = "iaresponsavel";
                case "Cibersegurança" -> novoInteresse2Formatado = "ciberseguranca";
                case "Privacidade & Ética" -> novoInteresse2Formatado = "privacidade&etica";
                default -> novoInteresse2Formatado = null;
            }

            Usuario novoUsuario = new Usuario(
                    usuarioOriginal.getId(),
                    novoAcessoFormatado,
                    novoNome,
                    novoInteresse1Formatado,
                    novoInteresse2Formatado,
                    usuarioOriginal.getAtivo()
            );

            String response = controller.salvarEdicaoUsuario(novoUsuario);
            if(response.equals("ok")){
                JOptionPane.showMessageDialog(null, "Alterações Salvas!");
                controller.recarregarTabelaUsuarios();
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            }
            else if(response.equals("fail")){
                JOptionPane.showMessageDialog(null, "Erro ao salvar dados.");
            }

        });

        cancelarButton.addActionListener(e -> {
            // Fecha o JDialog
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });

        alterarSenhaButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "A função de Alterar Senha será implementada em outro diálogo.", "Ação Futura", JOptionPane.INFORMATION_MESSAGE);
        });
    }

}