package org.example.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostagemDAO {

    ConnectionFactory cf = new ConnectionFactory();

    public List<Postagem> buscarPostagens(int limite){
        List<Postagem> postagens = new ArrayList<>();
        String SQL = "select r.recurso_id, r.titulo, r.autor, r.descricao, r.categoria, r.comentario, r.link_recurso, r.data_postagem, r.recurso_id,\n" +
                "u.nome as user_nome, u.user_id as id_user\n" +
                "from\n" +
                "recurso r\n" +
                "join\n" +
                "usuarios u on r.fk_user_id = u.user_id;";

        try(Connection conn = cf.createConnection();
            PreparedStatement ps = conn.prepareStatement(SQL)){

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
                int userId = rs.getInt("id_user");
                int recursoId = rs.getInt("recurso_id");
                String userNome = rs.getString("user_nome");

                Postagem p = new Postagem(titulo, autor, descricao,
                        categoria, comentarios, linkRecurso, data,
                        userNome, userId, recursoId);
                postagens.add(p);
            }
        } catch(SQLException exc){
            System.out.println("Erro sql: " + exc.getMessage());
        }

        return postagens;
    }

    public void postarRecurso(Postagem post, Usuario user) throws SQLException{

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
    }

    public void deletarRecurso(int id){
        final String SQL = "delete from recurso where recurso_id = ?;";

        try(Connection conn = cf.createConnection();
            PreparedStatement ps = conn.prepareStatement(SQL)){
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException exc){
            System.out.println("Erro ao deletar postagem: " + exc.getMessage());
        }
    }
}
