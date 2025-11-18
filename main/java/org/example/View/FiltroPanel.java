package org.example.View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FiltroPanel extends JPanel {

    // Componentes de filtro
    private JTextField tituloFilterField;
    private JTextField autorFilterField;
    private JComboBox<String> categoriaFilterBox;
    private JTextField dataFilterField;

    // Componentes de seleção (CheckBox)
    private JCheckBox tituloCheckBox;
    private JCheckBox autorCheckBox;
    private JCheckBox categoriaCheckBox;
    private JCheckBox dataCheckBox;

    public FiltroPanel() {
        setLayout(new BorderLayout());

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Filtros de Postagem 🔎",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.DARK_GRAY
        ));

        setPreferredSize(new Dimension(300, 0)); // Aumentado ligeiramente para acomodar o CheckBox
        setMinimumSize(new Dimension(150, 0));
        setMaximumSize(new Dimension(400, 0));

        // Painel interno que usará GridBagLayout para organizar os campos e CheckBoxes
        JPanel filterFieldsPanel = new JPanel(new GridBagLayout());
        filterFieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initComponents();
        addFilterComponents(filterFieldsPanel);

        add(filterFieldsPanel, BorderLayout.NORTH);
        add(Box.createVerticalGlue(), BorderLayout.CENTER);
    }

    private void initComponents() {
        // Inicializa os campos de entrada (Fields)
        tituloFilterField = new JTextField(10);
        autorFilterField = new JTextField(10);
        String[] categorias = {"Todos", "IA Responsável", "Cibersegurança", "Privacidade & Ética"};
        categoriaFilterBox = new JComboBox<>(categorias);
        dataFilterField = new JTextField("DD/MM/AAAA", 10);
        dataFilterField.setForeground(Color.GRAY);

        // Inicializa os CheckBoxes, todos desmarcados por padrão
        tituloCheckBox = new JCheckBox("Filtrar por Título");
        autorCheckBox = new JCheckBox("Filtrar por Autor");
        categoriaCheckBox = new JCheckBox("Filtrar por Categoria");
        dataCheckBox = new JCheckBox("Filtrar por Data");

        // Inicialmente desabilita os campos de entrada
        tituloFilterField.setEnabled(false);
        autorFilterField.setEnabled(false);
        categoriaFilterBox.setEnabled(false);
        dataFilterField.setEnabled(false);

        // Adiciona Listeners para habilitar/desabilitar os campos
        tituloCheckBox.addActionListener(e -> tituloFilterField.setEnabled(tituloCheckBox.isSelected()));
        autorCheckBox.addActionListener(e -> autorFilterField.setEnabled(autorCheckBox.isSelected()));
        categoriaCheckBox.addActionListener(e -> categoriaFilterBox.setEnabled(categoriaCheckBox.isSelected()));
        dataCheckBox.addActionListener(e -> dataFilterField.setEnabled(dataCheckBox.isSelected()));

        // Listener de Foco para o placeholder da Data
        dataFilterField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (dataFilterField.getText().equals("DD/MM/AAAA")) {
                    dataFilterField.setText("");
                    dataFilterField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (dataFilterField.getText().isEmpty()) {
                    dataFilterField.setForeground(Color.GRAY);
                    dataFilterField.setText("DD/MM/AAAA");
                }
            }
        });
    }

    private void addFilterComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.gridx = 0; // Coluna 0: CheckBox
        gbc.weightx = 0.0; // Não expande

        // --- 1. Título ---
        gbc.gridy = 0;
        panel.add(tituloCheckBox, gbc);
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Expande o campo de texto
        panel.add(tituloFilterField, gbc);
        gbc.weightx = 0.0;

        // --- 2. Autor ---
        gbc.gridy = 2;
        panel.add(autorCheckBox, gbc);
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        panel.add(autorFilterField, gbc);
        gbc.weightx = 0.0;

        // --- 3. Categoria ---
        gbc.gridy = 4;
        panel.add(categoriaCheckBox, gbc);
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        panel.add(categoriaFilterBox, gbc);
        gbc.weightx = 0.0;

        // --- 4. Data ---
        gbc.gridy = 6;
        panel.add(dataCheckBox, gbc);
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        panel.add(dataFilterField, gbc);
        gbc.weightx = 0.0;

        // --- 5. Botão de Filtrar ---
        JButton filterButton = new JButton("Aplicar Filtros");
        filterButton.setBackground(new Color(60, 141, 188));
        filterButton.setForeground(Color.WHITE);
        filterButton.setFocusPainted(false);
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.gridy = 8;
        panel.add(filterButton, gbc);

        // Espaçador para empurrar tudo para cima
        gbc.weighty = 1.0;
        gbc.gridy = 9;
        panel.add(Box.createVerticalGlue(), gbc);
    }
}