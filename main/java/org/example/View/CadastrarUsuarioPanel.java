package org.example.View;

import org.example.Controller.AdminController;
import org.example.Model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("ALL")
public class CadastrarUsuarioPanel extends JPanel {

    private JTextField nomeInput;
    private JPasswordField senhaInput;
    private JPasswordField confirmarSenhaInput;

    private JComboBox<String> acessoInput;
    private JComboBox<String> interesse1Input;
    private JComboBox<String> interesse2Input;

    private JButton salvarButton;
    private JButton cancelarButton;

    private final AdminController controller;

    public CadastrarUsuarioPanel(AdminController controller) {
        this.controller = controller;

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
        salvarButton.addActionListener(e -> salvarCadastro());

        cancelarButton = new JButton("Cancelar");

        // Listener para fechar o diálogo ao cancelar (lógica na seção 2)
        cancelarButton.addActionListener(e -> {
            // Este botão deve fechar a janela onde ele está contido (o JDialog)
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5); // Espaçamento entre componentes

// --- 1. Título do Formulário ---
        JLabel titulo = new JLabel("Novo Usuário", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(titulo, gbc);

        // Resetar gridwidth e insets
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 5, 8, 5);

        // --- 2. Nome (Linha 1) ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Nome/Username:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(nomeInput, gbc);
        gbc.weightx = 0.0;

        // --- 3. Senha (Linha 2) ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(senhaInput, gbc);
        gbc.weightx = 0.0;

        // --- 4. Confirmar Senha (Linha 3 - NOVO) ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Confirme a Senha:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(confirmarSenhaInput, gbc);
        gbc.weightx = 0.0;

        // As linhas seguintes foram incrementadas em 1 para acomodar o novo campo

        // --- 5. Nível de Acesso (Linha 4) ---
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Nível de Acesso:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(acessoInput, gbc);
        gbc.weightx = 0.0;

        // --- 6. Interesse 1 (Linha 5) ---
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Interesse 1:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(interesse1Input, gbc);
        gbc.weightx = 0.0;

        // --- 7. Interesse 2 (Linha 6) ---
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Interesse 2:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(interesse2Input, gbc);
        gbc.weightx = 0.0;

        // --- 8. Botões de Ação (Linha 7) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(cancelarButton);
        buttonPanel.add(salvarButton);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 5, 0, 5);
        add(buttonPanel, gbc);
    }

    public void salvarCadastro(){
        // 1. Coleta e Limpeza de Dados
        String nome = nomeInput.getText().trim();
        char[] senha = senhaInput.getPassword();
        char[] confirmacao = confirmarSenhaInput.getPassword();

        // 2. Validação Básica
        if (nome.isBlank()) {
            JOptionPane.showMessageDialog(null, "O campo 'Nome/Username' deve ser preenchido.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Valida se os campos de senha foram preenchidos
        if (senha.length == 0) {
            JOptionPane.showMessageDialog(null, "O campo 'Senha' deve ser preenchido.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (confirmacao.length == 0) {
            JOptionPane.showMessageDialog(null, "O campo 'Confirme a Senha' deve ser preenchido.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Validação de Confirmação de Senha (CRÍTICO)
        if (!Arrays.equals(senha, confirmacao)) {
            // Limpa os campos de senha por segurança
            senhaInput.setText("");
            confirmarSenhaInput.setText("");

            JOptionPane.showMessageDialog(null, "As senhas digitadas não coincidem. Por favor, tente novamente.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String acessoFormatado;
        switch ((String) acessoInput.getSelectedItem()){
            case "Usuário" -> acessoFormatado = "usuario";
            case "Administrador" -> acessoFormatado = "admin";
            default -> acessoFormatado = null;
        }

        String interesse1Formatado;
        switch ((String) interesse1Input.getSelectedItem()){
            case "IA Responsável" -> interesse1Formatado = "iaresponsavel";
            case "Cibersegurança" -> interesse1Formatado = "ciberseguranca";
            case "Privacidade & Ética" -> interesse1Formatado = "privacidade&etica";
            default -> interesse1Formatado = null;
        }

        String interesse2Formatado;
        switch ((String) interesse2Input.getSelectedItem()){
            case "IA Responsável" -> interesse2Formatado = "iaresponsavel";
            case "Cibersegurança" -> interesse2Formatado = "ciberseguranca";
            case "Privacidade & Ética" -> interesse2Formatado = "privacidade&etica";
            default -> interesse2Formatado = null;
        }

        Usuario novoUser = new Usuario(
                0,
                acessoFormatado,
                nome,
                interesse1Formatado,
                interesse2Formatado,
                true
        );

        // 5. Chamada ao Controller
        // Passamos a senha como String para o Controller. O Controller DEVE fazer o HASH.
        controller.salvarCadastroUsuario(novoUser, new String(senha));
    }
}