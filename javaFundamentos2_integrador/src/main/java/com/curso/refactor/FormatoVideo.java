package com.curso.refactor;

public enum FormatoVideo {
    MP4,
    WEBM,
    AVI;

    public static FormatoVideo desdeTexto(String valor) {
        return FormatoVideo.valueOf(valor.trim().toUpperCase());
    }
}
