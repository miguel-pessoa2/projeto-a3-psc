package org.example.View;

import org.example.Model.PostagemData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CriarPostagemPanel extends JPanel {

    private JPanel headerPanel;
    private JLabel tituloTelaLabel;

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

    public CriarPostagemPanel(){
        // 1. Configurações Iniciais do Painel
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(0, 20, 20, 20));

        // 2. Criação do Header
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 0, 15, 0));

        tituloTelaLabel = new JLabel("Criação de Novo Recurso", SwingConstants.CENTER);
        tituloTelaLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        JPanel navButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        headerPanel.add(navButtonsPanel, BorderLayout.WEST);
        headerPanel.add(tituloTelaLabel, BorderLayout.CENTER);
        headerPanel.add(actionButtonsPanel, BorderLayout.EAST);

        // 3. Inicialização dos Componentes do Formulário
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

        String[] categorias = {"IA Responsável", "Cibersegurança", "Privacidade & Ética Digital"};
        categoriaInput = new JComboBox<>(categorias);

        postarBtn = new JButton("Postar Recurso");
        postarBtn.setFont(new Font("Arial", Font.BOLD, 14));
        postarBtn.setBackground(new Color(60, 179, 113));
        postarBtn.setForeground(Color.WHITE);

        // 4. Organização do Layout com GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // --- HEADER  ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(headerPanel, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // --- Título ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        add(tituloLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(tituloInput, gbc);
        gbc.weightx = 0.0;

        // --- Autor ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(autorLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(autorInput, gbc);
        gbc.weightx = 0.0;

        // --- Categoria ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        add(categoriaLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(categoriaInput, gbc);
        gbc.weightx = 0.0;

        // --- Link do Recurso ---
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        add(linkLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        add(linkInput, gbc);
        gbc.weightx = 0.0;

        // --- Descrição Curta ---
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weighty = 0.0;
        add(descricaoLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 5; gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        add(descricaoScrollPane, gbc);
        gbc.weightx = 0.0;

        // --- Comentários Adicionais ---
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weighty = 0.0;
        add(comentariosLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 6; gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(comentariosScrollPane, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;

        add(postarBtn, gbc);

        // --- Espaçador de Rodapé ---
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(Box.createVerticalGlue(), gbc);
    }

    public void limparTela(){
        categoriaInput.setSelectedIndex(0);
        comentariosInput.setText("");
        descricaoInput.setText("");
        autorInput.setText("");
        linkInput.setText("");
        tituloInput.setText("");
    }

    public PostagemData getPostagemData(){
        String categoriaFormatada = switch ((String)categoriaInput.getSelectedItem()){
            case "IA Responsável" -> "iaresponsavel";
            case "Cibersegurança" -> "ciberseguranca";
            case "Privacidade & Ética Digital" -> "privacidade&etica";
            default -> "";
        };

        return new PostagemData(
                tituloInput.getText(),
                autorInput.getText(),
                descricaoInput.getText(),
                categoriaFormatada,
                comentariosInput.getText(),
                linkInput.getText(),
                "hoje"
        );
    }

    public JButton getPostarButton(){
        return postarBtn;
    }
}