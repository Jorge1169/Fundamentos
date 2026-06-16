package com.curso.refactor;

public enum FormatoDocumento {
    PDF,
    EPUB;

    public static FormatoDocumento desdeTexto(String valor) {
        return FormatoDocumento.valueOf(valor.trim().toUpperCase());
    }
}
