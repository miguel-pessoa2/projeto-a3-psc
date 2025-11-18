package org.example.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostagemDAO {

    ConnectionFactory cf = new ConnectionFactory();

    public List<Postagem> buscarPostagens(int limite){
        List<Postagem> postagens = new ArrayList<>();
        String SQL = "select * from recurso;";

        try(Connection conn = cf.createConnection();
            PreparedStatement ps = conn.prepareStatement(SQL);){

            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String descricao = rs.getString("descricao");
                String categoria = rs.getString("categoria");

                categoria = switch(categoria) {
                    case "iaresponsavel" -> "IA Responsável";
                    case "ciberseguranca" -> "Cibersegurança";
                    case "privacidade&etica" -> "Privacidade & Ética Digital";
                    default -> "Categoria Indefinida";
                };

                String comentarios = rs.getString("comentario");
                String linkRecurso = rs.getString("link_recurso");
                String data = rs.getString("data_postagem");
                int userId = rs.getInt("fk_user_id");

                Postagem p = new Postagem(titulo, autor, descricao, categoria, comentarios, linkRecurso, data, userId);

                postagens.add(p);
            }

        }

        catch(SQLException exc){
            System.out.println("Erro sql: " + exc.getMessage());
        }

        return postagens;
    }

    public void postarRecurso(Postagem post, Usuario user){

        String sql = "insert into recurso (titulo, autor, descricao, categoria, comentario, link_recurso, fk_user_id) values (?, ?, ?, ?, ?, ?, ?);";

        try(Connection conn = cf.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        )
        {
            ps.setString(1, post.getTitulo());
            ps.setString(2, post.getAutor());
            ps.setString(3, post.getDescricao());
            ps.setString(4, post.getCategoria());
            ps.setString(5, post.getComentarios());
            ps.setString(6, post.getLinkRecurso());
            ps.setInt(7, user.getId());

            ps.executeUpdate();

        }
        catch(SQLException exc){
            System.out.println("Erro ao criar postagem: " + exc.getMessage());
        }
    }
}
