package org.example.Controller;

import org.example.Model.*;
import org.example.View.ColaboradorPanel;
import org.example.View.CriarPostagemPanel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ColaboradorController {
    private Usuario usuarioLogado;
    private PostagemDAO postagemDao;

    private JFrame frame;
    private ColaboradorPanel colaboradorPanel;
    private CriarPostagemPanel criarPostagemPanel;

    private List<Postagem> postagensRecentes;

    public ColaboradorController(Usuario user){
        usuarioLogado = user;
        postagemDao = new PostagemDAO();
        postagensRecentes = postagemDao.buscarPostagens(3);

        frame = new JFrame();
        colaboradorPanel = new ColaboradorPanel(this);
        criarPostagemPanel = new CriarPostagemPanel();

        colaboradorPanel.updatePostagensList(postagensRecentes);

        setupListeners();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(colaboradorPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void listenerCriarPostagemButton(){
        criarPostagemPanel.limparTela();

        JDialog dialog = new JDialog(frame, "Criar Postagem", true);
        dialog.setContentPane(criarPostagemPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public void fazerLogout(){
        int response = JOptionPane.showConfirmDialog(null,
                        "Deseja sair do sistema?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);

        if(response == JOptionPane.YES_OPTION){
            usuarioLogado = null;
            postagemDao = null;
            colaboradorPanel = null;
            postagensRecentes = null;
            frame.dispose();
            new MainController();
        }
    }

    public void setupListeners(){
        colaboradorPanel.getFiltroPanel().getFiltrarButton().addActionListener(e -> listenerFiltrarButton());
        criarPostagemPanel.getPostarButton().addActionListener(e -> listenerPublicarPostagemButton());
    }

    public void listenerFiltrarButton(){
        FiltroData filtroData = colaboradorPanel.getFiltroPanel().getFiltroData();

        final List<String> interessesUsuario = List.of(
                usuarioLogado.getInteresse1().toLowerCase().trim(),
                usuarioLogado.getInteresse2().toLowerCase().trim()
        );

        List<Postagem> postagensFiltradas = postagensRecentes.stream()

            .filter(post -> {
                if(filtroData.isMinhasPostagensChecked()){
                    return post.getUserId() == usuarioLogado.getId();
                }
                return true;
            })

            .filter(post -> {
                if(filtroData.isMeusInteressesChecked()){
                    String categoriaPost = switch (post.getCategoria()){
                        case "IA Responsável" -> "iaresponsavel";
                        case "Cibersegurança" -> "ciberseguranca";
                        case "Privacidade & Ética Digital" -> "privacidade&etica";
                        default -> "";
                    };

                    return interessesUsuario.contains(categoriaPost);
                }
                return true;
            })

            .filter(post -> !filtroData.isTituloChecked() ||
                    post.getTitulo().toLowerCase().contains(filtroData.tituloInput().toLowerCase()))

            .filter(post -> !filtroData.isAutorChecked() ||
                    post.getAutor().toLowerCase().contains(filtroData.autorInput().toLowerCase()))

            .filter(post -> !filtroData.isCategoriaChecked() ||
                    post.getCategoria().equalsIgnoreCase(filtroData.categoriaInput()))

            .filter(post -> !filtroData.isCriadorChecked() ||
                    post.getAutorPost().toLowerCase().contains(filtroData.criadorInput().toLowerCase()))

            .toList();

        colaboradorPanel.updatePostagensList(postagensFiltradas);
    }

    public void listenerDeletarPostagemButton(Postagem p){
        int response = JOptionPane.showConfirmDialog(null,
            "Você deseja apagar essa postagem?",
            "Apagar Postagem",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if(response == JOptionPane.YES_OPTION){
            if(p.getUserId() == usuarioLogado.getId()){
                postagemDao.deletarRecurso(p.getPostId());
                updatePostagensList();
            }
        }
    }

    public void listenerPublicarPostagemButton(){
        PostagemData postagemData = criarPostagemPanel.getPostagemData();
        Postagem p = new Postagem(postagemData.titulo(), postagemData.autor(),
                postagemData.descricao(), postagemData.categoria(),
                postagemData.comentarios(), postagemData.link(),
                postagemData.data(), usuarioLogado.getNome(),
                usuarioLogado.getId(), 0
        );

        try{
            postagemDao.postarRecurso(p, usuarioLogado);
            JOptionPane.showMessageDialog(criarPostagemPanel,
                "Postagem Publicada!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            updatePostagensList();

        }
        catch(SQLException exc){
            JOptionPane.showMessageDialog(criarPostagemPanel,
                "Houve um erro ao publicar a postagem.",
                "Erro ao publicar",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updatePostagensList(){
        postagensRecentes = postagemDao.buscarPostagens(1);
        colaboradorPanel.updatePostagensList(postagensRecentes);
    }
    public int getUsuarioId(){
        return usuarioLogado.getId();
    }

}
