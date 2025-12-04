package org.example.Model;

public class Usuario {

    private final int id;
    private final String acesso;
    private final String nome;
    private final String interesse1;
    private final String interesse2;
    private final boolean ativo;

    public Usuario(int id, String acesso, String nome, String interesse1, String interesse2, boolean ativo){
        this.id = id;
        this.acesso = acesso;
        this.nome = nome;
        this.interesse1 = interesse1;
        this.interesse2 = interesse2;
        this.ativo = ativo;
    }

    public Usuario(CadastroData data){
        this.id = 0;
        this.acesso = data.acesso();
        this.nome = data.nome();
        this.interesse1 = data.interesse1();
        this.interesse2 = data.interesse2();
        this.ativo = true;
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

    public boolean isAtivo() {
        return ativo;
    }
    public boolean isColaborador(){
        return "usuario".equalsIgnoreCase(acesso);
    }
    public boolean isAdministrador(){
        return "admin".equalsIgnoreCase(acesso);
    }

}
