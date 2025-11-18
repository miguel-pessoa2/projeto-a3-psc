package org.example.Model;

public class Usuario {

    private int id;
    private String acesso;
    private String nome;
    private String interesse1;
    private String interesse2;
    private boolean ativo;

    public Usuario(int id, String acesso, String nome, String interesse1, String interesse2, boolean ativo){
        this.id = id;
        this.acesso = acesso;
        this.nome = nome;
        this.interesse1 = interesse1;
        this.interesse2 = interesse2;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public String getAcesso() {
        return acesso;
    }

    public String getNome() {
        return nome;
    }

    public String getInteresse1() {
        return interesse1;
    }

    public String getInteresse2() {
        return interesse2;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public boolean isColaborador(){
        return "usuario".equalsIgnoreCase(acesso);
    }
    public boolean isAdministrador(){
        return "admin".equalsIgnoreCase(acesso);
    }
    @Override
    public String toString(){
        return "Nome: " + getNome() + " acesso: " + getAcesso();
    }
}
