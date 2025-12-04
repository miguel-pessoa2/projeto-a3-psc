package org.example.View;

import org.example.Controller.AdminController;
import org.example.Controller.ColaboradorController;
import org.example.Model.Postagem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

public class AdminPostagensPanel extends JPanel{
    private JSplitPane centralSplitPane;
    private FiltroPanel filtroPanel;
    private JPanel postagensPanel;
    private JScrollPane scrollPane;
    private AdminController controller;

    public AdminPostagensPanel(AdminController controller){
        setLayout(new BorderLayout(0, 10));
        this.controller = controller;

        postagensPanel = new JPanel();
        postagensPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        postagensPanel.setLayout(new BoxLayout(postagensPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(postagensPanel);
        scrollPane.setPreferredSize(new Dimension(550, 450));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(40);

        filtroPanel = new FiltroPanel();

        centralSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filtroPanel, scrollPane);
        centralSplitPane.setDividerLocation(250);
        centralSplitPane.setOneTouchExpandable(true);
        centralSplitPane.setResizeWeight(0.0);
        centralSplitPane.setEnabled(false);

        centralSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, evt -> {
            int dividerLocation = (int) evt.getNewValue();
            if (dividerLocation > 350) {
                centralSplitPane.setDividerLocation(350);
            }
        });

        add(centralSplitPane, BorderLayout.CENTER);
    }

    public void updatePostagensList(List<Postagem> postagens){

        postagensPanel.removeAll();

        if (postagens == null || postagens.isEmpty()) {
            JLabel noPostsLabel = new JLabel("Nenhuma postagem encontrada recentemente.");
            noPostsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            postagensPanel.add(noPostsLabel);
            postagensPanel.revalidate();
            postagensPanel.repaint();
            return;
        }

        for(Postagem p : postagens){
            JPanel postPanel = new JPanel();

            postPanel.setLayout(new BorderLayout(5, 5));
            postPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEtchedBorder(),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)
            ));
            postPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel postHeader = new JPanel(new BorderLayout());

            JLabel postUsuario = new JLabel(p.getAutorPost());
            postUsuario.setFont(postUsuario.getFont().deriveFont(Font.ITALIC, 11f));
            postUsuario.setForeground(Color.GRAY);

            JLabel postData = new JLabel(p.getData() != null ? "@" + p.getData() : "Data indisponivel");
            postData.setFont(postData.getFont().deriveFont(Font.ITALIC, 11f));
            postData.setHorizontalAlignment(SwingConstants.RIGHT);

            postHeader.add(postUsuario, BorderLayout.WEST);
            postHeader.add(postData, BorderLayout.EAST);

            JPanel postBody = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(3, 5, 3, 5);
            gbc.weightx = 1.0;

            // --- Título ---
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
            JLabel postTitulo = new JLabel(p.getTitulo());
            postTitulo.setFont(postTitulo.getFont().deriveFont(Font.BOLD, 16f));
            postTitulo.setForeground(new Color(0, 102, 204));
            postBody.add(postTitulo, gbc);

            // --- Autor e Categoria ---
            gbc.gridwidth = 1;
            gbc.gridx = 0; gbc.gridy = 1;
            JLabel postAutor = new JLabel("Autor do Recurso: " + p.getAutor());
            postBody.add(postAutor, gbc);

            gbc.gridx = 1; gbc.gridy = 1;
            JLabel postCategoria = new JLabel("Categoria: " + p.getCategoria());
            postCategoria.setHorizontalAlignment(SwingConstants.RIGHT);
            postBody.add(postCategoria, gbc);

            // --- Descrição ---
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            JTextArea descArea = new JTextArea("Descrição Curta: " + p.getDescricao());
            descArea.setEditable(false);
            descArea.setLineWrap(true);
            descArea.setWrapStyleWord(true);
            descArea.setBackground(postBody.getBackground());
            descArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            postBody.add(descArea, gbc);

            // --- Comentários do Autor ---
            gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
            JTextArea comentariosArea = new JTextArea("Comentários do Autor: " + p.getComentarios());
            comentariosArea.setEditable(false);
            comentariosArea.setLineWrap(true);
            comentariosArea.setWrapStyleWord(true);
            comentariosArea.setBackground(new Color(240, 240, 240));
            comentariosArea.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Comentários Adicionais"),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            postBody.add(comentariosArea, gbc);

            JPanel postFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            JButton excluirButton = new JButton("Excluir Postagem");
            excluirButton.setForeground(Color.RED); // Destaca a ação destrutiva
            excluirButton.setFont(new Font("Arial", Font.BOLD, 12));
            excluirButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                excluirButton.addActionListener(e -> {
                    controller.listenerDeletarPostagemButton(p);
                });

            postFooter.add(excluirButton);

            JButton copiarBtn = new JButton("Copiar Link");
            copiarBtn.addActionListener(e -> {
                try {
                    StringSelection stringSelection = new StringSelection(p.getLinkRecurso());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);

                    JOptionPane.showMessageDialog(null,
                            "Link copiado para a área de transferência!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception exc) {
                    System.err.println("Erro ao acessar a área de transferência: " + exc.getMessage());
                    JOptionPane.showMessageDialog(null,
                            "Não foi possível acessar a área de transferência do sistema.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            copiarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            postFooter.add(copiarBtn);

            postPanel.add(postHeader, BorderLayout.NORTH);
            postPanel.add(postBody, BorderLayout.CENTER);
            postPanel.add(postFooter, BorderLayout.SOUTH);

            postagensPanel.add(postPanel);
            postagensPanel.add(Box.createVerticalStrut(15));
        }

        postagensPanel.add(Box.createVerticalGlue());

        postagensPanel.revalidate();
        postagensPanel.repaint();
    }

    public FiltroPanel getFiltroPanel(){
        return filtroPanel;
    }
}
