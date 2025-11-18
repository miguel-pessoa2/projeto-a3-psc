package org.example.View;

import org.example.Controller.ColaboradorController;
import org.example.Model.Postagem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CriarPostagemPanel extends JPanel {

    // Componentes do Header
    private JPanel headerPanel;
    private JButton voltarBtn;
    private JLabel tituloTelaLabel;

    // Componentes do Formulário (inalterados)
    private JLabel tituloLabel;
    private JLabel autorLabel;
    private JLabel descricaoLabel;
    private JLabel categoriaLabel;
    private JLabel comentariosLabel;
    private JLabel linkLabel;

    private JTextField tituloInput;
    private JTextField autorInput;
    private JTextArea descricaoInput;
    private JComboBox<String> categoriaInput;
    private JTextArea comentariosInput;
    private JTextField linkInput;

    private JButton postarBtn;

    public void limparTela(){
        tituloInput.setText("");
        autorInput.setText("");
        descricaoInput.setText("");
        comentariosInput.setText("");
        categoriaInput.setSelectedIndex(0);
    }

    public CriarPostagemPanel(ColaboradorController controller){
        // 1. Configurações Iniciais do Painel
        setLayout(new GridBagLayout());
        // Ajustamos a borda para dar espaço, mas sem o padding de cima, que será feito pelo header
        setBorder(new EmptyBorder(0, 20, 20, 20));

        // =======================================================
        // 2. CRIAÇÃO DO HEADER (CABEÇALHO)
        // =======================================================
        headerPanel = new JPanel();
        // Usamos BorderLayout para colocar um item à esquerda e outro à direita
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 0, 15, 0)); // Padding interno (cima/baixo)

        // Botões de Ação
        voltarBtn = new JButton("← Voltar");
        voltarBtn.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });

        // Título Central
        tituloTelaLabel = new JLabel("Criação de Novo Recurso", SwingConstants.CENTER);
        tituloTelaLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Painel para Botões de Ação (lado direito)
        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));

        // Painel para Navegação (lado esquerdo)
        JPanel navButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        navButtonsPanel.add(voltarBtn);

        // Adiciona os componentes ao Header
        headerPanel.add(navButtonsPanel, BorderLayout.WEST);
        headerPanel.add(tituloTelaLabel, BorderLayout.CENTER);
        headerPanel.add(actionButtonsPanel, BorderLayout.EAST);

        // =======================================================
        // 3. Inicialização dos Componentes do Formulário (Inalterados)
        // =======================================================
        tituloLabel = new JLabel("Título do Recurso: ");
        autorLabel = new JLabel("Autor do Recurso: ");
        descricaoLabel = new JLabel("Descrição Curta do Recurso: ");
        categoriaLabel = new JLabel("Categoria: ");
        comentariosLabel = new JLabel("Comentários Adicionais: ");
        linkLabel = new JLabel("Link do recurso: ");

        tituloInput = new JTextField(25);
        autorInput = new JTextField(25);
        linkInput = new JTextField(25);

        descricaoInput = new JTextArea(3, 25);
        descricaoInput.setLineWrap(true);
        descricaoInput.setWrapStyleWord(true);
        JScrollPane descricaoScrollPane = new JScrollPane(descricaoInput);

        comentariosInput = new JTextArea(5, 25);
        comentariosInput.setLineWrap(true);
        comentariosInput.setWrapStyleWord(true);
        JScrollPane comentariosScrollPane = new JScrollPane(comentariosInput);

        String[] categorias = {"iaresponsavel", "ciberseguranca", "privacidade&etica"};
        categoriaInput = new JComboBox<>(categorias);

        postarBtn = new JButton("Postar Recurso");
        postarBtn.addActionListener(e -> {
            controller.criarPostagem(new Postagem(
                    tituloInput.getText(),
                    autorInput.getText(),
                    descricaoInput.getText(),
                    (String) categoriaInput.getSelectedItem(),
                    comentariosInput.getText(),
                    linkInput.getText(),
                    "hoje",
                    controller.getUsuarioId()
            ));
        });
        postarBtn.setFont(new Font("Arial", Font.BOLD, 14));
        postarBtn.setBackground(new Color(60, 179, 113));
        postarBtn.setForeground(Color.WHITE);

        // =======================================================
        // 4. Organização do Layout com GridBagLayout
        // =======================================================
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // --- HEADER (Linha 0) ---
        gbc.gridx = 0;
        gbc.gridy = 0; // Posição 0
        gbc.gridwidth = 2; // Ocupa as duas colunas
        gbc.weightx = 1.0; // Se expande horizontalmente
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0); // Remove insets padrão para este item
        add(headerPanel, gbc);

        // Resetamos o gbc para o formulário
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // --- Título (Linha 1 - Antiga Linha 0) ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        add(tituloLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(tituloInput, gbc);
        gbc.weightx = 0.0;

        // --- Autor (Linha 2 - Antiga Linha 1) ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(autorLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(autorInput, gbc);
        gbc.weightx = 0.0;

// --- Categoria (Linha 3 - Antiga Linha 2) ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        add(categoriaLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(categoriaInput, gbc);
        gbc.weightx = 0.0;

        // NOVO CAMPO: Link do Recurso (Linha 4)
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        add(linkLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(linkInput, gbc);
        gbc.weightx = 0.0;

        // Os demais campos (Descrição, Comentários, Botão) agora usam gridy incrementado:

        // --- Descrição Curta (Linha 5 - Antiga Linha 3) ---
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.NORTHWEST; // y = 5
        gbc.weighty = 0.0;
        add(descricaoLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 5; gbc.weightx = 1.0; // y = 5
        gbc.weighty = 0.0;
        add(descricaoScrollPane, gbc);
        gbc.weightx = 0.0;

        // --- Comentários Adicionais (Linha 6 - Antiga Linha 4) ---
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHWEST; // y = 6
        gbc.weighty = 0.0;
        add(comentariosLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 6; gbc.weightx = 1.0; // y = 6
        gbc.weighty = 1.0;
        add(comentariosScrollPane, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0;
        gbc.gridy = 7; // y = 7
        gbc.gridwidth = 2; // Ocupa as duas colunas
        gbc.weightx = 0.0; // Não se expande horizontalmente
        gbc.weighty = 0.0; // Não se expande verticalmente
        gbc.fill = GridBagConstraints.NONE; // Ocupa apenas o espaço necessário
        gbc.anchor = GridBagConstraints.EAST; // Alinha o botão à direita

        // Supondo que você tenha um objeto JButton chamado 'postarButton'
        add(postarBtn, gbc);

        // --- Espaçador de Rodapé (Linha 8 - Antiga Linha 6) ---
        gbc.gridx = 0;
        gbc.gridy = 8; // y = 8
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(Box.createVerticalGlue(), gbc);
    }
}