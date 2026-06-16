package com.curso.refactor.enums;

import com.curso.refactor.exception.RecursoInvalidoException;

public enum TipoRecurso {
    VIDEO,
    PDF,
    MENTORIA;

    public static TipoRecurso desdeTexto(String valor) throws RecursoInvalidoException {
        try {
            return TipoRecurso.valueOf(valor.trim().toUpperCase());
        } catch (Exception e) {
            throw new RecursoInvalidoException("El tipo solo puede ser VIDEO, PDF o MENTORÍA.");
        }
    }
}

