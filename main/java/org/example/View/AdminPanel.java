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
import java.util.List;
import java.awt.*;

public class AdminPanel extends JPanel {

    // Componentes do Header
    private JButton criarPostagemButton;
    private JButton sairButton;

    // Componentes da Área de Controle (Usuários)
    private JTable usuarioTable;
    private UsuarioTableModel usuarioTableModel;
    private JButton cadastrarUsuarioButton;
    private JButton removerUsuarioButton;
    private JButton editarUsuarioButton;
    private JButton ativarInativarUsuarioButton;

    // Componentes da Área de Controle (Posts)
    private JButton apagarPostButton;

     private AdminController controller;

    public AdminPanel(AdminController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        // 1. HEADER (NORTE)
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. PAINEL DE CONTEÚDO PRINCIPAL (CENTRO)
        add(createMainContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 10)); // Alinha botões à direita
        headerPanel.setBackground(new Color(230, 230, 230));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY)); // Linha divisória

        // Botão Criar Postagem
        criarPostagemButton = new JButton("Criar Postagem");
        criarPostagemButton.setToolTipText("Acessar a tela de criação de novos recursos.");
        criarPostagemButton.addActionListener(e -> controller.listenerCriarPostagemButton());
        headerPanel.add(criarPostagemButton);

        // Botão Sair
        sairButton = new JButton("Sair");
        sairButton.setToolTipText("Encerrar sessão e retornar à tela de login.");
        sairButton.addActionListener(e -> controller.listenerFazerLogoutButton());
        headerPanel.add(sairButton);

        return headerPanel;
    }

    private JComponent createMainContentPanel() {
        // O MainContentPanel usará um JTabbedPane para separar as funções de Admin
        JTabbedPane mainTabs = new JTabbedPane();
        mainTabs.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. Aba de Controle de Usuários
        mainTabs.addTab("Controle de Usuários", createUsuarioControlPanel(controller.getUsuariosList()));

        // 2. Aba de Controle de Postagens
        mainTabs.addTab("Controle de Postagens", createPostControlPanel());

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

        // --- ESTILIZAÇÃO DA TABELA (AJUSTES PARA DEFINIÇÃO) ---

        // Altura da linha e Fonte
        usuarioTable.setRowHeight(28);
        usuarioTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Cor de seleção
        usuarioTable.setSelectionBackground(new Color(178, 203, 227));
        usuarioTable.setSelectionForeground(Color.BLACK);

        // MUDANÇA CRÍTICA 1: Habilita linhas de grade e as estiliza
        usuarioTable.setShowGrid(true); // RE-HABILITA as linhas de grade
        usuarioTable.setGridColor(new Color(220, 220, 220)); // Define uma cor cinza clara para as linhas

        // Estilização do cabeçalho
        JTableHeader header = usuarioTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(60, 141, 188));
        header.setForeground(Color.WHITE);
        header.setOpaque(true); // Garante que o fundo seja azul

        // Renderer para a coluna "Status" (índice 5)
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
        } catch (ArrayIndexOutOfBoundsException ignored) {} // Captura se a coluna 5 não existir

        JScrollPane scrollPane = new JScrollPane(usuarioTable);

        // MUDANÇA CRÍTICA 2: Adiciona uma borda sutil ao redor da tabela/ScrollPane
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

        panel.add(scrollPane, BorderLayout.CENTER);

        // 3. Painel de Botões (SOUTH) - (Sem alterações na parte dos botões)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        // Botão Cadastrar (Ação Primária)
        cadastrarUsuarioButton = new JButton("Cadastrar Novo");
        cadastrarUsuarioButton.setBackground(new Color(0, 179, 107));
        cadastrarUsuarioButton.setForeground(Color.WHITE);
        cadastrarUsuarioButton.setFocusPainted(false);
        cadastrarUsuarioButton.setFont(new Font("Arial", Font.BOLD, 12));
        cadastrarUsuarioButton.addActionListener(e -> controller.listenerCadastrarUsuarioButton());

        // Botão Remover (Ação de Risco)
        removerUsuarioButton = new JButton("Remover");
        removerUsuarioButton.setBackground(new Color(220, 53, 69));
        removerUsuarioButton.setForeground(Color.WHITE);
        removerUsuarioButton.setFocusPainted(false);
        removerUsuarioButton.setFont(new Font("Arial", Font.BOLD, 12));
        removerUsuarioButton.addActionListener(e -> controller.listenerRemoverUsuarioButton());

        // Botão Editar
        editarUsuarioButton = new JButton("Editar Dados");
        editarUsuarioButton.setBackground(new Color(255, 193, 7));
        editarUsuarioButton.setForeground(Color.BLACK);
        editarUsuarioButton.setFocusPainted(false);
        editarUsuarioButton.setFont(new Font("Arial", Font.BOLD, 12));
        editarUsuarioButton.addActionListener(e -> controller.listenerEditarUsuarioButton());

        // Botão Ativar/Inativar
        ativarInativarUsuarioButton = new JButton("Ativar/Inativar");
        ativarInativarUsuarioButton.setBackground(new Color(108, 117, 125));
        ativarInativarUsuarioButton.setForeground(Color.WHITE);
        ativarInativarUsuarioButton.setFocusPainted(false);
        ativarInativarUsuarioButton.setFont(new Font("Arial", Font.BOLD, 12));
        ativarInativarUsuarioButton.addActionListener(e -> controller.listenerAtivarInativarUsuarioButton());

        buttonPanel.add(cadastrarUsuarioButton);
        buttonPanel.add(removerUsuarioButton);
        buttonPanel.add(editarUsuarioButton);
        buttonPanel.add(ativarInativarUsuarioButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPostControlPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Gerenciamento de Postagens"));

        // Tabela de Postagens (CENTER)
        String[] colunas = {"ID", "Título", "Autor", "Categoria", "Data Postagem"};
        Object[][] dados = { /* Dados serão carregados pelo Controller */ };
        JTable postTable = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(postTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de Botões (SOUTH)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));

        apagarPostButton = new JButton("Apagar Postagem ❌");
        buttonPanel.add(apagarPostButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public JTable getUsuarioTable(){
        return usuarioTable;
    }

    public UsuarioTableModel getUsuarioTableModel(){
        return usuarioTableModel;
    }
}