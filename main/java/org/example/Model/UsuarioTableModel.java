

package org.example.Model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UsuarioTableModel extends AbstractTableModel {

    private final List<Usuario> usuarios;
    private final String[] colunas = {"ID", "Nome", "Acesso", "Interesse 1", "Interesse 2", "Status"};

    public UsuarioTableModel(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setUsuarios(List<Usuario> novosUsuarios) {
        this.usuarios.clear();
        this.usuarios.addAll(novosUsuarios);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario = usuarios.get(rowIndex);

        String acessoFormatado = switch (usuario.getAcesso()){
            case "admin" -> "Administrador";
            default -> "Usuário";
        };

        String interesse1Formatado = switch (usuario.getInteresse1()){
            case "iaresponsavel" -> "IA Responsável";
            case "ciberseguranca" -> "Cibersegurança";
            case "privacidade&etica" -> "Privacidade & Ética";
            default -> "Não definido";
        };

        String interesse2Formatado = switch (usuario.getInteresse2()){
            case "iaresponsavel" -> "IA Responsável";
            case "ciberseguranca" -> "Cibersegurança";
            case "privacidade&etica" -> "Privacidade & Ética";
            default -> "Não definido";
        };

        return switch (columnIndex) {
            case 0 -> usuario.getId();
            case 1 -> usuario.getNome();
            case 2 -> acessoFormatado;
            case 3 -> interesse1Formatado;
            case 4 -> interesse2Formatado;
            case 5 -> usuario.isAtivo() ? "Ativo   " : "Inativo";
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) return Integer.class;
        return String.class;
    }

    public Usuario getUsuarioAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < usuarios.size()) {
            return usuarios.get(rowIndex);
        }
        return null;
    }
}
