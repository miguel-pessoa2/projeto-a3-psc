package org.example.View;

import org.example.Controller.AdminController;
import org.example.Model.UsuarioTableModel;
import org.example.Model.Usuario;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.util.List;
import java.awt.*;

public class AdminPanel extends JPanel {

    private JButton criarPostagemButton;
    private JButton sairButton;

    private JTable usuarioTable;
    private UsuarioTableModel usuarioTableModel;
    private JButton cadastrarUsuarioButton;
    private JButton removerUsuarioButton;
    private JButton editarUsuarioButton;
    private JButton ativarInativarUsuarioButton;

    private final AdminPostagensPanel adminPostagensPanel;
    private final AdminController controller;

    public AdminPanel(AdminController controller) {
        this.controller = controller;
        adminPostagensPanel = new AdminPostagensPanel(controller);
        adminPostagensPanel.updatePostagensList(controller.getPostagensRecentes());
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        headerPanel.setBackground(new Color(230, 230, 230));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        criarPostagemButton = new JButton("Criar Postagem");
        criarPostagemButton.setToolTipText("Acessar a tela de criação de novos recursos.");
        headerPanel.add(criarPostagemButton);

        sairButton = new JButton("Sair");
        sairButton.setToolTipText("Encerrar sessão e retornar à tela de login.");
        headerPanel.add(sairButton);

        return headerPanel;
    }

    private JComponent createMainContentPanel() {
        JTabbedPane mainTabs = new JTabbedPane();
        mainTabs.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainTabs.addTab("Controle de Usuários", createUsuarioControlPanel(controller.getUsuariosList()));
        mainTabs.addTab("Controle de Postagens", adminPostagensPanel);

        return mainTabs;
    }

    private JPanel createUsuarioControlPanel(List<Usuario> usuariosIniciais) {

        // 1. Configuração do Painel Principal
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Border etchedBorder = BorderFactory.createEtchedBorder();
        panel.setBorder(BorderFactory.createTitledBorder(
                etchedBorder,
                "Gerenciamento de Usuários",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                new Color(50, 50, 50)
        ));

        // 2. Tabela e Modelo
        this.usuarioTableModel = new UsuarioTableModel(usuariosIniciais);
        usuarioTable = new JTable(usuarioTableModel);
        usuarioTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableColumnModel columnModel = usuarioTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(60);
        columnModel.getColumn(0).setMaxWidth(60);

        usuarioTable.setRowHeight(28);
        usuarioTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        usuarioTable.setSelectionBackground(new Color(178, 203, 227));
        usuarioTable.setSelectionForeground(Color.BLACK);

        usuarioTable.setShowGrid(true); //
        usuarioTable.setGridColor(new Color(220, 220, 220));

        JTableHeader header = usuarioTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(60, 141, 188));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);

        // Renderer para a coluna "Status"
        try {
            usuarioTable.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    String status = (String) value;

                    if ("Ativo".equals(status)) {
                        c.setForeground(new Color(34, 139, 34)); // Verde
                    } else if ("Inativo".equals(status)) {
                        c.setForeground(Color.RED);
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                    return c;
                }
            });
        } catch (ArrayIndexOutOfBoundsException ignored) {}

        JScrollPane scrollPane = new JScrollPane(usuarioTable);

        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

        panel.add(scrollPane, BorderLayout.CENTER);

        // 3. Painel de Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        cadastrarUsuarioButton = new JButton("Cadastrar Novo");
        cadastrarUsuarioButton.setBackground(new Color(0, 179, 107));
        cadastrarUsuarioButton.setForeground(Color.WHITE);
        cadastrarUsuarioButton.setFocusPainted(false);
        cadastrarUsuarioButton.setFont(new Font("Arial", Font.BOLD, 12));

        removerUsuarioButton = new JButton("Remover");
        removerUsuarioButton.setBackground(new Color(220, 53, 69));
        removerUsuarioButton.setForeground(Color.WHITE);
        removerUsuarioButton.setFocusPainted(false);
        removerUsuarioButton.setFont(new Font("Arial", Font.BOLD, 12));

        editarUsuarioButton = new JButton("Editar Dados");
        editarUsuarioButton.setBackground(new Color(255, 193, 7));
        editarUsuarioButton.setForeground(Color.BLACK);
        editarUsuarioButton.setFocusPainted(false);
        editarUsuarioButton.setFont(new Font("Arial", Font.BOLD, 12));

        ativarInativarUsuarioButton = new JButton("Ativar/Inativar");
        ativarInativarUsuarioButton.setBackground(new Color(108, 117, 125));
        ativarInativarUsuarioButton.setForeground(Color.WHITE);
        ativarInativarUsuarioButton.setFocusPainted(false);
        ativarInativarUsuarioButton.setFont(new Font("Arial", Font.BOLD, 12));

        buttonPanel.add(cadastrarUsuarioButton);
        buttonPanel.add(removerUsuarioButton);
        buttonPanel.add(editarUsuarioButton);
        buttonPanel.add(ativarInativarUsuarioButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    //métodos getter
    public JButton getAtivarInativarUsuarioButton() {
        return ativarInativarUsuarioButton;
    }
    public JButton getEditarUsuarioButton() {
        return editarUsuarioButton;
    }
    public JButton getRemoverUsuarioButton() {
        return removerUsuarioButton;
    }
    public JButton getCadastrarUsuarioButton() {
        return cadastrarUsuarioButton;
    }
    public JButton getSairButton() {
        return sairButton;
    }
    public JButton getCriarPostagemButton() {
        return criarPostagemButton;
    }
    public JTable getUsuarioTable() {
        return usuarioTable;
    }
    public UsuarioTableModel getUsuarioTableModel() {
        return usuarioTableModel;
    }
    public AdminPostagensPanel getAdminPostagensPanel() {
        return adminPostagensPanel;
    }


}