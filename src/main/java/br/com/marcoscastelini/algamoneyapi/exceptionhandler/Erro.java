package br.com.marcoscastelini.algamoneyapi.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Erro {
    private final String mensagemUsuario;
    private final String mensagemDesenvolvedor;
}
