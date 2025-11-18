

package org.example.Controller;

import org.example.Model.ConnectionFactory;
import org.example.Model.Usuario;
import org.example.Model.UsuarioDAO;
import org.example.View.LoginPanel;

import javax.swing.*;
import java.sql.*;

public class MainController {
    private JFrame frame;
    private LoginPanel loginPanel;
    private ConnectionFactory cf;
    private UsuarioDAO usuarioDAO;

    //controller chamado no ponto de entrada da aplicação
    public MainController(){
        cf = new ConnectionFactory();
        usuarioDAO = new UsuarioDAO();
        frame = new JFrame("Login");
        loginPanel = new LoginPanel(this);
        loginPanel.setLoginListener(e -> fazerLogin());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(loginPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void fazerLogin(){

        String username = loginPanel.getUserInput();
        String password = loginPanel.getPassInput();

        Usuario usuario = usuarioDAO.autenticarUsuario(username, password);

        if(usuario != null){
            getControllerPorUsuario(usuario);
        }
        else{
            System.out.println("Login incorreto.");
        }

    }

    private void getControllerPorUsuario(Usuario user){
        frame.dispose();

        if(user.isColaborador()){
            new ColaboradorController(user);
        }
        else if(user.isAdministrador()){
            new AdminController(user);
        }
        else{
            System.out.println("Erro inesperado ao criar controller.");
        }
    }

}
