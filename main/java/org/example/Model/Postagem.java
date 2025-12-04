package org.example.Model;

public class Postagem {

    private String titulo;
    private String autor;
    private String descricao;
    private String categoria;
    private String comentarios;
    private String linkRecurso;
    private String data;
    private String autorPost;
    private int userId;
    private int postId;

    public Postagem(String titulo, String autor, String descricao,
            String categoria, String comentarios, String linkRecurso,
            String data, String autorPost, int userId, int postId){

        this.titulo = titulo;
        this.autor = autor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.comentarios = comentarios;
        this.linkRecurso = linkRecurso;
        this.data = data;
        this.autorPost = autorPost;
        this.userId = userId;
        this.postId = postId;
    }

    public String getTitulo(){
        return titulo;
    }
    public String getAutor(){
        return autor;
    }
    public String getDescricao(){
        return descricao;
    }
    public String getCategoria(){
        return categoria;
    }
    public String getComentarios() { return comentarios; }
    public String getLinkRecurso() { return linkRecurso; }
    public String getData() { return data; }
    public String getAutorPost() {return autorPost;}
    public int getUserId() { return userId; }
    public int getPostId() { return postId; }
}
