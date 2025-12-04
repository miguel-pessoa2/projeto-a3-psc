package org.example.Model;

public record PostagemData(
        String titulo,
        String autor,
        String descricao,
        String categoria,
        String comentarios,
        String link,
        String data
) {
}
