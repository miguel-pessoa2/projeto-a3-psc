package org.example.Controller;

import org.example.Model.*;
import org.example.View.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Arrays;
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
        criarPostagemPanel = new CriarPostagemPanel();
        setupListeners();

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

        int selectedRowInView = table.getSelectedRow();

        if (selectedRowInView >= 0) {
            int selectedRowInModel = table.convertRowIndexToModel(selectedRowInView);

            UsuarioTableModel model = adminPanel.getUsuarioTableModel();

            return model.getUsuarioAt(selectedRowInModel);
        }

        return null;

    }


    public void recarregarTabelaUsuarios(){
        try {
            List<Usuario> novaListaDeUsuarios = usuarioDAO.getAllUsuarios();

            adminPanel.getUsuarioTableModel().setUsuarios(novaListaDeUsuarios);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                "Erro ao recarregar a lista de usuários: " + e.getMessage(),
                "Erro de Conexão",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupListeners(){
        adminPanel.getCriarPostagemButton().addActionListener(e -> listenerCriarPostagemButton());
        adminPanel.getSairButton().addActionListener(e -> listenerSairButton());
        adminPanel.getCadastrarUsuarioButton().addActionListener(e -> listenerCadastrarUsuarioButton());
        adminPanel.getRemoverUsuarioButton().addActionListener(e -> listenerRemoverUsuarioButton());
        adminPanel.getEditarUsuarioButton().addActionListener(e -> listenerEditarUsuarioButton());
        adminPanel.getAtivarInativarUsuarioButton().addActionListener(e -> listenerAtivarInativarUsuarioButton());
        criarPostagemPanel.getPostarButton().addActionListener(e -> listenerPublicarPostagemButton());
        adminPanel.getAdminPostagensPanel().getFiltroPanel().getFiltrarButton().addActionListener(e -> {
            FiltroData filtroData = adminPanel.getAdminPostagensPanel().getFiltroPanel().getFiltroData();

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

            adminPanel.getAdminPostagensPanel().updatePostagensList(postagensFiltradas);
        });
    }

    public void listenerCadastrarUsuarioButton() {
        CadastrarUsuarioPanel cadastrarUsuarioPanel = new CadastrarUsuarioPanel();

        JDialog dialog = new JDialog(frame, "Cadastrar Usuário", true);
        dialog.setResizable(false);
        dialog.setContentPane(cadastrarUsuarioPanel);

        cadastrarUsuarioPanel.getSalvarButton().addActionListener(e -> {
            CadastroData data = cadastrarUsuarioPanel.getFormData();

            if(data.nome().isEmpty()){
                JOptionPane.showMessageDialog(dialog,
                    "O campo 'Nome' deve ser preenchido.",
                    "Cadastro Inválido",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            if(data.senha().length == 0 || data.senhaConfirm().length == 0){
                JOptionPane.showMessageDialog(dialog,
                    "O campo 'Senha' deve ser preenchido.",
                    "Cadastro Inválido",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            if(!Arrays.equals(data.senha(), data.senhaConfirm())){
                JOptionPane.showMessageDialog(dialog,
                    "As senhas inseridas não conferem.",
                    "Cadastro Inválido",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            try{
                usuarioDAO.criarUsuario(new Usuario(data), new String(data.senha()));
                JOptionPane.showMessageDialog(dialog,
                        "Usuário cadastrado!",
                        "Cadastro Realizado",
                        JOptionPane.INFORMATION_MESSAGE);
                Arrays.fill(data.senha(), ' ');
                Arrays.fill(data.senhaConfirm(), ' ');
                dialog.dispose();
                recarregarTabelaUsuarios();
            }

            catch(SQLException exc){
                JOptionPane.showMessageDialog(dialog,
                    "Houve um erro ao cadastrar o usuário.",
                    "Erro ao Cadastrar",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

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
        int resposta = JOptionPane.showConfirmDialog(frame,
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
                    JOptionPane.INFORMATION_MESSAGE);
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

        EditarUsuarioPanel editarUsuarioPanel = new EditarUsuarioPanel(usuarioSelecionado);
        JDialog dialog = new JDialog(frame, "Editar Usuário", true);
        dialog.setContentPane(editarUsuarioPanel);

        editarUsuarioPanel.getSalvarButton().addActionListener(e -> {
            Usuario userData = editarUsuarioPanel.getUserData();

            if(userData.getNome().isBlank()){
                JOptionPane.showMessageDialog(dialog,
                    "O campo 'Nome' deve ser preenchido.",
                    "Edição Inválida",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            try{
                usuarioDAO.updateUsuario(userData);
                JOptionPane.showMessageDialog(dialog,
                    "Usuário editado!",
                    "Edição Realizada",
                    JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                recarregarTabelaUsuarios();
            }
            catch(SQLException exc){
                JOptionPane.showMessageDialog(dialog,
                    "Houve um erro ao cadastrar o usuário.",
                    "Erro ao Cadastrar",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

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
        boolean userAtivo = usuarioSelecionado.isAtivo();
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
        criarPostagemPanel.limparTela();

        JDialog dialog = new JDialog(frame, "Criar Postagem", true);
        dialog.setContentPane(criarPostagemPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
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
            postagemDAO.postarRecurso(p, usuarioLogado);
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

    public void listenerSairButton() {
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

    public void listenerDeletarPostagemButton(Postagem p){
        int response = JOptionPane.showConfirmDialog(null,
                "Você deseja apagar essa postagem?",
                "Apagar Postagem",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if(response == JOptionPane.YES_OPTION){
                postagemDAO.deletarRecurso(p.getPostId());
                updatePostagensList();
        }
    }

    private void updatePostagensList() {
        List<Postagem> postagensAtualizadas = postagemDAO.buscarPostagens(1);
        adminPanel.getAdminPostagensPanel().updatePostagensList(postagensAtualizadas);
    }

    public List<Postagem> getPostagensRecentes(){
        return postagemDAO.buscarPostagens(1);
    }

    public List<Usuario> getUsuariosList(){
        return usuariosList;
    }

}
