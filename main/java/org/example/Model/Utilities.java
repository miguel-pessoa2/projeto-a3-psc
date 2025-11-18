package org.example.Model;

import org.example.View.CriarPostagemPanel;

import javax.swing.*;

public class Utilities {

    public static String validadePostCreation(Postagem post){

        if(post.getTitulo().isEmpty() || post.getTitulo().isBlank()){return "Insira um título para o recurso.";}
        else if(post.getAutor().isEmpty() || post.getAutor().isBlank()){return "Insira um autor para o recurso.";}
        else if(post.getDescricao().isEmpty() || post.getDescricao().isBlank()){return "Insira uma descrição para o recurso.";}
        else if(post.getLinkRecurso().isEmpty() || post.getLinkRecurso().isBlank()){return "Insira um link para o recurso.";}

        return "valido";
    }

}
