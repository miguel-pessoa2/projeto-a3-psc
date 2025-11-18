package org.example.View;

import org.example.Controller.ColaboradorController;
import org.example.Model.Postagem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ColaboradorPanel extends JPanel {

    private JPanel headerPanel;
    private JLabel tituloLabel;
    private JButton criarPostagemBtn;
    private JButton sairBtn;

    private JSplitPane centralSplitPane;
    private FiltroPanel filtroPanel;
    private JPanel postagensPanel;
    private JScrollPane scrollPane;

    public ColaboradorPanel(ColaboradorController controller){
        // Configuração inicial do painel principal
        setLayout(new BorderLayout(0, 10));

        // 1. Configuração do Painel do Cabeçalho (NORTH)
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding interno (margem)

        // Título Central
        tituloLabel = new JLabel("Área do Colaborador - Feed", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(tituloLabel, BorderLayout.CENTER);

        // Painel para Botões de Ação (EAST/Direita)
        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        criarPostagemBtn = new JButton("Postar Novo Recurso");

        criarPostagemBtn.setBackground(new Color(60, 179, 113));
        criarPostagemBtn.setForeground(Color.WHITE);
        criarPostagemBtn.setFont(new Font("Arial", Font.BOLD, 12));
        criarPostagemBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        criarPostagemBtn.addActionListener(e -> controller.listenerCriarPostagemButton());

        sairBtn = new JButton("Sair");
        sairBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sairBtn.addActionListener(e -> controller.fazerLogout());

        actionButtonsPanel.add(criarPostagemBtn);
        actionButtonsPanel.add(sairBtn);
        headerPanel.add(actionButtonsPanel, BorderLayout.EAST);

        // 2. Configuração da Área Central (Filtros + Postagens)

        // 2.1 Painel de Postagens
        postagensPanel = new JPanel();
        postagensPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        postagensPanel.setLayout(new BoxLayout(postagensPanel, BoxLayout.Y_AXIS));

        // Adiciona o painel de postagens a um JScrollPane para permitir rolagem
        scrollPane = new JScrollPane(postagensPanel);
        scrollPane.setPreferredSize(new Dimension(550, 400));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // Borda sutil

        //altera a unidade de incremento da barra de rolagem
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(40);

        // 2.2 Painel de Filtros (Novo Painel)
        filtroPanel = new FiltroPanel();

        // 2.3 JSplitPane para dividir os filtros e as postagens
        centralSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filtroPanel, scrollPane);
        centralSplitPane.setDividerLocation(250); // Define a largura inicial do painel de filtros
        centralSplitPane.setOneTouchExpandable(true); // Adiciona um botão para expandir/colapsar
        centralSplitPane.setResizeWeight(0.0); // Garante que apenas o scrollPane se expanda
        centralSplitPane.setEnabled(false);

        centralSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, evt -> {
            int dividerLocation = (int) evt.getNewValue();
            if (dividerLocation > 350) {
                centralSplitPane.setDividerLocation(350);
            }
        });

        // Adicionando componentes ao Panel da interface
        add(headerPanel, BorderLayout.NORTH);
        add(centralSplitPane, BorderLayout.CENTER); // Adicionamos o JSplitPane
    }

    // Este método é chamado pelo controller após a busca de postagens no banco
    public void updatePostagensList(List<Postagem> postagens, ColaboradorController controller){

        // 1. Limpa o painel antes de adicionar novos posts
        postagensPanel.removeAll();

        if (postagens == null || postagens.isEmpty()) {
            JLabel noPostsLabel = new JLabel("Nenhuma postagem encontrada recentemente.");
            noPostsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            postagensPanel.add(noPostsLabel);
            postagensPanel.revalidate();
            postagensPanel.repaint();
            return;
        }

        // 2. Itera sobre a lista de postagens recebida
        for(Postagem p : postagens){
            // Cria o painel individual para cada postagem
            JPanel postPanel = new JPanel();

            // Usamos BorderLayout para dividir o painel em Título/Metadados (NORTH) e Conteúdo/Ações (CENTER/SOUTH)
            postPanel.setLayout(new BorderLayout(5, 5));
            postPanel.setBorder(BorderFactory.createCompoundBorder(
                    // Borda de linha fina e arredondada (EtchedBorder é mais suave que LineBorder)
                    BorderFactory.createEtchedBorder(),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12) // Padding interno
            ));
            // Garante que o painel da postagem se alinhe à esquerda
            postPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // =======================================================
            // A) HEADER DA POSTAGEM (Nome do Autor e Data) - NORTH
            // =======================================================
            JPanel postHeader = new JPanel(new BorderLayout());

            // Novo campo: Nome de Usuário do Autor
            JLabel postUsuario = new JLabel(controller.getUsuarioNome());
            postUsuario.setFont(postUsuario.getFont().deriveFont(Font.ITALIC, 11f));
            postUsuario.setForeground(Color.GRAY);

            // Novo campo: Data de Postagem
            JLabel postData = new JLabel(p.getData() != null ? "@" + p.getData() : "Data indisponivel");
            postData.setFont(postData.getFont().deriveFont(Font.ITALIC, 11f));
            postData.setHorizontalAlignment(SwingConstants.RIGHT);

            postHeader.add(postUsuario, BorderLayout.WEST);
            postHeader.add(postData, BorderLayout.EAST);

            // =======================================================
            // B) CORPO DA POSTAGEM (Conteúdo) - CENTER
            // =======================================================
            JPanel postBody = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(3, 5, 3, 5); // Espaçamento interno
            gbc.weightx = 1.0; // Faz os campos de texto se expandirem horizontalmente

            // --- Título (Linha 0) ---
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; // Ocupa 2 colunas
            JLabel postTitulo = new JLabel(p.getTitulo());
            postTitulo.setFont(postTitulo.getFont().deriveFont(Font.BOLD, 16f));
            postTitulo.setForeground(new Color(0, 102, 204)); // Cor de link/título
            postBody.add(postTitulo, gbc);

            // --- Autor e Categoria (Linha 1) ---
            gbc.gridwidth = 1; // Volta a ocupar 1 coluna
            gbc.gridx = 0; gbc.gridy = 1;
            JLabel postAutor = new JLabel("Autor do Recurso: " + p.getAutor());
            postBody.add(postAutor, gbc);

            gbc.gridx = 1; gbc.gridy = 1;
            JLabel postCategoria = new JLabel("Categoria: " + p.getCategoria());
            postCategoria.setHorizontalAlignment(SwingConstants.RIGHT);
            postBody.add(postCategoria, gbc);

            // --- Descrição (Linha 2) ---
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            // JTextArea para melhor visualização de texto com quebra de linha
            JTextArea descArea = new JTextArea("Descrição Curta: " + p.getDescricao());
            descArea.setEditable(false);
            descArea.setLineWrap(true);
            descArea.setWrapStyleWord(true);
            descArea.setBackground(postBody.getBackground()); // Fundo transparente
            descArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Padding
            postBody.add(descArea, gbc);

            // --- Novo Campo: Comentários do Autor (Linha 3) ---
            gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
            JTextArea comentariosArea = new JTextArea("Comentários do Autor: " + p.getComentarios());
            comentariosArea.setEditable(false);
            comentariosArea.setLineWrap(true);
            comentariosArea.setWrapStyleWord(true);
            comentariosArea.setBackground(new Color(240, 240, 240)); // Fundo levemente cinza para destaque
            comentariosArea.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Comentários Adicionais"),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            postBody.add(comentariosArea, gbc);

            // =======================================================
            // C) RODAPÉ DA POSTAGEM (Botões de Ação) - SOUTH
            // =======================================================
            JPanel postFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            // Novo Botão: Copiar
            JButton copiarBtn = new JButton("Copiar Link");
            copiarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            postFooter.add(copiarBtn);

            // =======================================================
            // D) MONTAGEM DO PAINEL PRINCIPAL DA POSTAGEM
            // =======================================================
            postPanel.add(postHeader, BorderLayout.NORTH);
            postPanel.add(postBody, BorderLayout.CENTER);
            postPanel.add(postFooter, BorderLayout.SOUTH);

            // Adiciona o painel do post e um espaçador ao painel principal
            postagensPanel.add(postPanel);
            // Espaçador maior (15px) para separar as postagens
            postagensPanel.add(Box.createVerticalStrut(15));
        }

        // Adiciona um espaçador final para garantir que o último post não fique grudado no final do scroll
        postagensPanel.add(Box.createVerticalGlue());

        // 3. Força o painel a redesenhar com os novos componentes
        postagensPanel.revalidate();
        postagensPanel.repaint();
    }
}