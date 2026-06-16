package com.curso.refactor.enums;

import com.curso.refactor.exception.RecursoInvalidoException;

public enum NivelRecurso {
    BASICO,
    INTERMEDIO,
    AVANZADO;

    public static NivelRecurso desdeTexto(String valor) throws RecursoInvalidoException {
        try {
            return NivelRecurso.valueOf(valor.trim().toUpperCase());
        } catch (Exception e) {
            throw new RecursoInvalidoException("El nivel solo puede ser BÁSICO, INTERMEDIO o AVANZADO.");
        }
    }
}

