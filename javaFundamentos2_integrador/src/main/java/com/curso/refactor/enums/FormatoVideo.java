package com.curso.refactor.enums;

import com.curso.refactor.exception.RecursoInvalidoException;

public enum FormatoVideo {
    MP4,
    WEBM,
    AVI;

    public static FormatoVideo desdeTexto(String valor) throws RecursoInvalidoException {
        try {
            return FormatoVideo.valueOf(valor.trim().toUpperCase());
        } catch (Exception e) {
            throw new RecursoInvalidoException("El formato de video solo puede ser MP4, WEBM o AVI.");
        }
    }
}

