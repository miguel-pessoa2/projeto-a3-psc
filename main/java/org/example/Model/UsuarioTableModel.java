

package org.example.Model; // Sugestão de pacote

import org.example.Model.Usuario;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UsuarioTableModel extends AbstractTableModel {

    private final List<Usuario> usuarios;
    private final String[] colunas = {"ID", "Nome", "Acesso", "Interesse 1", "Interesse 2", "Status"};

    public UsuarioTableModel(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    // Método para atualizar a tabela (chamado pelo Controller)
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

    // Método essencial: Mapeia o objeto Usuario para a coluna correta
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario = usuarios.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> usuario.getId();
            case 1 -> usuario.getNome();
            case 2 -> usuario.getAcesso();
            case 3 -> usuario.getInteresse1();
            case 4 -> usuario.getInteresse2();
            case 5 -> usuario.getAtivo() ? "Ativo   " : "Inativo";
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) return Integer.class; // ID é inteiro
        return String.class;
    }

    public Usuario getUsuarioAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < usuarios.size()) {
            return usuarios.get(rowIndex);
        }
        return null;
    }
}
