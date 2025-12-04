package org.example.Model;

public record CadastroData(
    String nome,
    char[] senha,
    char[]senhaConfirm,
    String acesso,
    String interesse1,
    String interesse2
) {}
