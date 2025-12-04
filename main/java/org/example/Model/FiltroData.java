package org.example.Model;

public record FiltroData(
        String tituloInput,
        String autorInput,
        String categoriaInput,
        String criadorInput,
        boolean isTituloChecked,
        boolean isAutorChecked,
        boolean isCategoriaChecked,
        boolean isCriadorChecked,
        boolean isMinhasPostagensChecked,
        boolean isMeusInteressesChecked
) {}
