package io.com.igorgmassi.libraryapi.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroReposta(int status, String mensagem, List<ErroCampo> erros) {

    public static ErroReposta repostaPadrao (String mensagem){
        return new ErroReposta(HttpStatus.BAD_REQUEST.value(),mensagem, List.of());
    }

    public static ErroReposta repostaConflito(String mensagem){
        return new ErroReposta(HttpStatus.CONFLICT.value(),mensagem, List.of());
    }
}
