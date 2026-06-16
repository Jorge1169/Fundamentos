package com.curso.refactor;

public enum TipoRecurso {
    VIDEO,
    PDF,
    MENTORIA;

    public static TipoRecurso desdeTexto(String valor) {
        return TipoRecurso.valueOf(valor.trim().toUpperCase());
    }
}
