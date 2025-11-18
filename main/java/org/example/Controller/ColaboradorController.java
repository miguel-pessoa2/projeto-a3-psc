package org.example.Controller;

import org.example.Model.Postagem;
import org.example.Model.PostagemDAO;
import org.example.Model.Usuario;
import org.example.Model.Utilities;
import org.example.View.ColaboradorPanel;
import org.example.View.CriarPostagemPanel;

import javax.swing.*;
import java.util.List;

public class ColaboradorController {
    private Usuario usuarioLogado;
    private PostagemDAO postagemDao;

    private JFrame frame;
    private ColaboradorPanel colaboradorPanel;

    private List<Postagem> postagensRecentes;

    public ColaboradorController(Usuario user){
        usuarioLogado = user;
        postagemDao = new PostagemDAO();
        postagensRecentes = postagemDao.buscarPostagens(3);

        frame = new JFrame();
        colaboradorPanel = new ColaboradorPanel(this);
        colaboradorPanel.updatePostagensList(postagensRecentes, this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(colaboradorPanel);
        frame.pack();
        frame.setVisible(true);

    }

    //navega para a tela de criar postagem, usado na tela principal
    public void listenerCriarPostagemButton(){
        CriarPostagemPanel criarPostagemPanel = new CriarPostagemPanel(this);

        JDialog dialog = new JDialog(frame, "Criar Postagem", true);
        dialog.setContentPane(criarPostagemPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    //cria a postagem no banco de dados, usado na tela de criar postagem
    public void criarPostagem(Postagem post){

        String validadeResponse = Utilities.validadePostCreation(post);

        if(validadeResponse.equals("valido")){

            JOptionPane.showMessageDialog(null, "recurso publicado!");
            postagemDao.postarRecurso(post, usuarioLogado);

        }
        else{
            JOptionPane.showMessageDialog(null, validadeResponse);
        }
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

    public int getUsuarioId(){
        return usuarioLogado.getId();
    }
    public String getUsuarioNome(){
        return usuarioLogado.getNome();
    }
}
