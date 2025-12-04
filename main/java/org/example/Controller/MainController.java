

package org.example.Controller;

import org.example.Model.Usuario;
import org.example.Model.UsuarioDAO;
import org.example.View.LoginPanel;

import javax.swing.*;
import java.sql.*;

public class MainController {
    private JFrame frame;
    private LoginPanel loginPanel;
    private UsuarioDAO usuarioDAO;

    public MainController(){
        usuarioDAO = new UsuarioDAO();
        frame = new JFrame("Login");
        loginPanel = new LoginPanel();
        loginPanel.getLoginButton().addActionListener(e -> listenerLoginButton());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(loginPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void listenerLoginButton(){

        String username = loginPanel.getUsuarioInput().trim();
        String password = loginPanel.getSenhaInput();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                "Preencha todos os campos.",
                "Login Inválido",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario user = usuarioDAO.autenticarUsuario(username, password);

            if (user == null) {
                JOptionPane.showMessageDialog(frame,
                    "Usuário ou senha incorretos.",
                    "Login Inválido",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (user.isAtivo()){
                getControllerPorUsuario(user);
            } else {
                JOptionPane.showMessageDialog(frame,
                    "A sua conta (" + user.getNome() + ") está inativa. \nProcure um administrador para reativar.",
                    "Conta Inativa",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
        catch(SQLException exc){
            JOptionPane.showMessageDialog(frame,
                "Erro de conexão com o banco de dados. Tente novamente mais tarde.",
                "Erro Crítico",
                JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(frame,
                "Houve um erro inesperadoao fazer login.",
                "Ocorreu um Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
