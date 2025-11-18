package org.example.Controller;

import org.example.Model.*;
import org.example.View.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class AdminController {

    private Usuario usuarioLogado;
    private PostagemDAO postagemDAO;
    private UsuarioDAO usuarioDAO;

    private JFrame frame;
    private AdminPanel adminPanel;
    private CriarPostagemPanel criarPostagemPanel;

    private List<Postagem> postagensRecentes;
    private List<Usuario> usuariosList;

    public AdminController(Usuario user){

        usuarioLogado = user;
        postagemDAO = new PostagemDAO();
        usuarioDAO = new UsuarioDAO();
        postagensRecentes = postagemDAO.buscarPostagens(3);
        usuariosList = usuarioDAO.getAllUsuarios();
        adminPanel = new AdminPanel(this);

        frame = new JFrame("Painel de Administrador");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(adminPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private Usuario getUsuarioSelecionado(){
        JTable table = adminPanel.getUsuarioTable();

        // 1. Obtém o índice da linha selecionada na JTable (View)
        int selectedRowInView = table.getSelectedRow();

        if (selectedRowInView >= 0) {
            // 2. Converte o índice da View para o índice do Model,
            // o que é crucial se a tabela for filtrada ou ordenada.
            int selectedRowInModel = table.convertRowIndexToModel(selectedRowInView);

            // 3. Chama o método do TableModel para obter o objeto Usuario completo
            UsuarioTableModel model = adminPanel.getUsuarioTableModel();

            return model.getUsuarioAt(selectedRowInModel);
        }

        return null;

    }

    public void listenerCadastrarUsuarioButton() {
        CadastrarUsuarioPanel cadastrarUsuarioPanel = new CadastrarUsuarioPanel(this);

        JDialog dialog = new JDialog(frame, "Cadastrar Usuário", true);
        dialog.setResizable(false);
        dialog.setContentPane(cadastrarUsuarioPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public void listenerRemoverUsuarioButton() {
        Usuario userParaRemover = getUsuarioSelecionado();

        if(userParaRemover == null){
            JOptionPane.showMessageDialog(frame,
                    "Selecione um usuário na tabela para removê-lo.",
                    "Usuário não selecionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(userParaRemover.isAdministrador()){
            JOptionPane.showMessageDialog(frame,
                    "Não é possivel excluir um administrador.",
                    "Operação não autorizada",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userNome = userParaRemover.getNome();
        int userId = userParaRemover.getId();
        int resposta = JOptionPane.showConfirmDialog(
                frame,
                "Você realmente deseja remover o usuário: " + userNome + " (ID: " + userId + ")?\nEsta ação é irreversível.",
                "Confirmar Remoção de Usuário",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if(resposta == JOptionPane.YES_OPTION){
            try {
                usuarioDAO.removerUsuario(userId);
                JOptionPane.showMessageDialog(
                        frame,
                        "Usuário " + userNome + " removido com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                recarregarTabelaUsuarios();

            }
            catch (SQLException exc) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Houve um erro ao remover o usuário " + userNome + ": " + exc.getMessage(),
                        "Erro ao excluir usuário",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

    }

    public void listenerEditarUsuarioButton() {
        Usuario usuarioSelecionado = getUsuarioSelecionado();

        if(usuarioSelecionado == null){
            JOptionPane.showMessageDialog(frame,
                    "Selecione um usuário na lista para editar.",
                    "Usuário não selecionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(usuarioSelecionado.isAdministrador()){
            JOptionPane.showMessageDialog(frame,
                    "Não é possivel editar dados de um administrador.",
                    "Operação não autorizada",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        EditarUsuarioPanel editarUsuarioPanel = new EditarUsuarioPanel(this, usuarioSelecionado);
        JDialog dialog = new JDialog(frame, "Editar Usuário", true);
        dialog.setContentPane(editarUsuarioPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public void listenerAtivarInativarUsuarioButton() {
        Usuario usuarioSelecionado = getUsuarioSelecionado();

        if(usuarioSelecionado == null){
            JOptionPane.showMessageDialog(frame,
                    "Selecione um usuário na lista para ativar/inativar.",
                    "Usuário não selecionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(usuarioSelecionado.isAdministrador()){
            JOptionPane.showMessageDialog(frame,
                    "Não é possivel ativar/inativar um administrador.",
                    "Operação não autorizada",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userNome = usuarioSelecionado.getNome();
        int userId = usuarioSelecionado.getId();
        boolean userAtivo = usuarioSelecionado.getAtivo();
        String booleanMessage = userAtivo? "inativar" : "ativar";

        int response = JOptionPane.showConfirmDialog(frame,
                "Você realmente deseja " + booleanMessage + " o usuário: " + userNome + " (ID: " + userId + ")?",
                "Ativar/Inativar usuário",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if(response == JOptionPane.YES_OPTION){
            try{
                usuarioDAO.ativarInativarUsuario(userId, !userAtivo);
                JOptionPane.showMessageDialog(frame,
                        "Status do usuário " + userNome + " alterado.",
                        "sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                recarregarTabelaUsuarios();
            }
            catch(SQLException exc){
                JOptionPane.showMessageDialog(frame,
                        "Houve um erro ao ativar/inativar o usuário " + userNome + ": " + exc.getMessage(),
                        "Erro ao ativar/inativar",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void listenerCriarPostagemButton() {
        return;
    }

    public void listenerFazerLogoutButton() {
        int response = JOptionPane.showConfirmDialog(null,
                "Deseja sair do sistema?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if(response == JOptionPane.YES_OPTION){
            usuarioLogado = null;
            usuarioDAO = null;
            postagemDAO = null;
            postagensRecentes = null;
            usuariosList = null;
            adminPanel = null;
            criarPostagemPanel = null;
            frame.dispose();
            new MainController();
        }
    }

    public String salvarEdicaoUsuario(Usuario novoUsuario){
        return usuarioDAO.updateUsuario(novoUsuario);

    }

    public void salvarCadastroUsuario(Usuario user, String senha){
        try{
            usuarioDAO.criarUsuario(user, senha);
            JOptionPane.showMessageDialog(null, "Usuário " + user.getNome() + " cadastrado!");
            recarregarTabelaUsuarios();
        }
        catch(SQLException exc){
            int errorCode = exc.getErrorCode();

            if (errorCode == 1062) {
                JOptionPane.showMessageDialog(frame,
                        "Erro (1062): Este usuário já existe. Por favor, escolha outro nome.",
                        "Erro de Cadastro",
                        JOptionPane.ERROR_MESSAGE);

            } else if (errorCode == 1406) {
                JOptionPane.showMessageDialog(frame,
                        "Erro (1406): Um dos campos excede o limite de caracteres permitido.",
                        "Erro de Dados",
                        JOptionPane.ERROR_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(frame, "Erro no BD. Código: " + errorCode);
            }
        }
    }

    public List<Usuario> getUsuariosList(){
        return usuariosList;
    }

    public void recarregarTabelaUsuarios(){
        try {
            // 1. CHAMA O MODEL/DAO: Busca a lista mais recente e completa de usuários no banco
            List<Usuario> novaListaDeUsuarios = usuarioDAO.getAllUsuarios();

            // 2. NOTIFICA O TABLE MODEL: Passa a nova lista para o TableModel da View
            // (Assumindo que você tem um getter para o TableModel no AdminPanel)
            adminPanel.getUsuarioTableModel().setUsuarios(novaListaDeUsuarios);

            System.out.println("Tabela de usuários recarregada com " + novaListaDeUsuarios.size() + " registros.");

        } catch (Exception e) {
            // Trate a falha na comunicação com o banco de dados
            JOptionPane.showMessageDialog(frame,
                    "Erro ao recarregar a lista de usuários: " + e.getMessage(),
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
