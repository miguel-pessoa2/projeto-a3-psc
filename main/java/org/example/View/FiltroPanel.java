package org.example.View;

import org.example.Model.FiltroData;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FiltroPanel extends JPanel {

    private JTextField tituloFilterField;
    private JTextField autorFilterField;
    private JTextField criadorFilterField;
    private JComboBox<String> categoriaFilterBox;

    private JCheckBox tituloCheckBox;
    private JCheckBox autorCheckBox;
    private JCheckBox categoriaCheckBox;
    private JCheckBox criadorCheckBox;
    private JCheckBox minhasPostagensCheckBox;
    private JCheckBox meusInteressesCheckBox;

    private JButton filterButton;

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

        setPreferredSize(new Dimension(300, 0));
        setMinimumSize(new Dimension(150, 0));
        setMaximumSize(new Dimension(400, 0));

        JPanel filterFieldsPanel = new JPanel(new GridBagLayout());
        filterFieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initComponents();
        addFilterComponents(filterFieldsPanel);

        add(filterFieldsPanel, BorderLayout.NORTH);
        add(Box.createVerticalGlue(), BorderLayout.CENTER);
    }

    private void initComponents() {
        tituloFilterField = new JTextField(10);
        autorFilterField = new JTextField(10);
        criadorFilterField = new JTextField(10);

        String[] categorias = {"IA Responsável", "Cibersegurança", "Privacidade & Ética Digital"};
        categoriaFilterBox = new JComboBox<>(categorias);

        tituloCheckBox = new JCheckBox("Filtrar por Título");
        autorCheckBox = new JCheckBox("Filtrar por Autor");
        categoriaCheckBox = new JCheckBox("Filtrar por Categoria");
        criadorCheckBox = new JCheckBox("Filtrar por Usuário");
        minhasPostagensCheckBox = new JCheckBox("Somente Minhas Postagens");
        meusInteressesCheckBox = new JCheckBox("Filtrar por Meus Interesses");

        tituloFilterField.setEnabled(false);
        autorFilterField.setEnabled(false);
        categoriaFilterBox.setEnabled(false);
        criadorCheckBox.setEnabled(false);

        tituloCheckBox.addActionListener(e -> tituloFilterField.setEnabled(tituloCheckBox.isSelected()));
        autorCheckBox.addActionListener(e -> autorFilterField.setEnabled(autorCheckBox.isSelected()));
        categoriaCheckBox.addActionListener(e -> categoriaFilterBox.setEnabled(categoriaCheckBox.isSelected()));
        criadorCheckBox.addActionListener(e -> criadorFilterField.setEnabled(criadorCheckBox.isSelected()));

        minhasPostagensCheckBox.addActionListener(e -> {
            if (minhasPostagensCheckBox.isSelected()) {
                criadorCheckBox.setSelected(false);
                criadorCheckBox.setEnabled(false);
                criadorFilterField.setEnabled(false);

            } else {
                criadorCheckBox.setEnabled(true);
            }
        });

        meusInteressesCheckBox.addActionListener(e -> {
            if(meusInteressesCheckBox.isSelected()){
                categoriaCheckBox.setEnabled(false);
                categoriaCheckBox.setSelected(false);
                categoriaFilterBox.setEnabled(false);

            } else {
                categoriaCheckBox.setEnabled(true);
            }
        });
    }

    private void addFilterComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.gridx = 0;
        gbc.weightx = 0.0;

        // --- 1. Título ---
        gbc.gridy = 0;
        panel.add(tituloCheckBox, gbc);
        gbc.gridy = 1;
        gbc.weightx = 1.0;
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

        // --- 4. Filtro por Criador ---
        gbc.gridy = 6;
        panel.add(criadorCheckBox, gbc);
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        panel.add(criadorFilterField, gbc);
        gbc.weightx = 0.0;

        // --- 5. Somente Minhas Postagens ---
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(minhasPostagensCheckBox, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // --- 6. Meus interesses ---
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(meusInteressesCheckBox, gbc);

        // --- 7. Botão de Filtrar ---
        filterButton = new JButton("Aplicar Filtros");
        filterButton.setBackground(new Color(60, 141, 188));
        filterButton.setForeground(Color.WHITE);
        filterButton.setFocusPainted(false);
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.gridx = 0; gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(filterButton, gbc);

        gbc.weighty = 1.0;
        gbc.gridy = 11;
        panel.add(Box.createVerticalGlue(), gbc);
    }

    public FiltroData getFiltroData(){
        return new FiltroData(
                tituloFilterField.getText(),
                autorFilterField.getText(),
                (String)categoriaFilterBox.getSelectedItem(),
                criadorFilterField.getText(),
                tituloCheckBox.isSelected(),
                autorCheckBox.isSelected(),
                categoriaCheckBox.isSelected(),
                criadorCheckBox.isSelected(),
                minhasPostagensCheckBox.isSelected(),
                meusInteressesCheckBox.isSelected()
        );
    }
    public JButton getFiltrarButton(){
        return filterButton;
    }
}