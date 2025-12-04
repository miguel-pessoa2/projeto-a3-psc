package org.example.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final ConnectionFactory cf = new ConnectionFactory();

    public Usuario autenticarUsuario(String username, String senha) throws SQLException{

        final String SQL = "select user_id, acesso, nome, interesse1, interesse2, ativo from usuarios where nome = ? and senha = ?;";

        try(Connection conn = cf.createConnection();
            PreparedStatement ps = conn.prepareStatement(SQL)){

            ps.setString(1, username);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Usuario(
                    rs.getInt("user_id"),
                    rs.getString("acesso"),
                    rs.getString("nome"),
                    rs.getString("interesse1"),
                    rs.getString("interesse2"),
                    rs.getBoolean("ativo")
                );
            }
            return null;
        }
    }

    public List<Usuario> getAllUsuarios(){

        List<Usuario> usuarioList = new ArrayList<>();
        final String SQL = "select user_id, acesso, nome, interesse1, interesse2, ativo from usuarios;";

        try(Connection conn = cf.createConnection();
            PreparedStatement ps = conn.prepareStatement(SQL)){

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Usuario user = new Usuario(
                    rs.getInt("user_id"),
                    rs.getString("acesso"),
                    rs.getString("nome"),
                    rs.getString("interesse1"),
                    rs.getString("interesse2"),
                    rs.getBoolean("ativo")
                );

                usuarioList.add(user);
            }

            return usuarioList;
        }
        catch(SQLException exc){
            return new ArrayList<>();
        }
    }

    public void removerUsuario(int userId) throws SQLException{
        final String SQL = "delete from usuarios where user_id = ?;";

        try(Connection conn = cf.createConnection();
            PreparedStatement ps = conn.prepareStatement(SQL)){

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    public void updateUsuario(Usuario user) throws SQLException{
        final String SQL = "update usuarios set acesso = ?, nome = ?, interesse1 = ?, interesse2 = ? where user_id = ?;";

        try(Connection conn = cf.createConnection();
            PreparedStatement ps = conn.prepareStatement(SQL)){

            ps.setString(1, user.getAcesso());
            ps.setString(2, user.getNome());
            ps.setString(3, user.getInteresse1());
            ps.setString(4, user.getInteresse2());
            ps.setInt(5, user.getId());
            ps.executeUpdate();

        }
    }

    public void ativarInativarUsuario(int userId, boolean ativo) throws SQLException{
        final String SQL = "update usuarios set ativo = ? where user_id = ?;";

        try(Connection conn = cf.createConnection();
            PreparedStatement ps = conn.prepareStatement(SQL))
        {
            ps.setBoolean(1, ativo);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public void criarUsuario(Usuario user, String senha) throws SQLException{
        final String SQL = "insert into usuarios (acesso, nome, senha, interesse1, interesse2, ativo) values (?, ?, ?, ?, ?, ?);";

        try(Connection conn = cf.createConnection();
            PreparedStatement ps = conn.prepareStatement(SQL))
        {
            ps.setString(1, user.getAcesso());
            ps.setString(2, user.getNome());
            ps.setString(3, senha);
            ps.setString(4, user.getInteresse1());
            ps.setString(5, user.getInteresse2());
            ps.setBoolean(6, true);

            ps.executeUpdate();
        }
    }

}
