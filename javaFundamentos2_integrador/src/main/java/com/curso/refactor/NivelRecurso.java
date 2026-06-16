package com.curso.refactor;

public enum NivelRecurso {
    BASICO,
    INTERMEDIO,
    AVANZADO;

    public static NivelRecurso desdeTexto(String valor) {
        return NivelRecurso.valueOf(valor.trim().toUpperCase());
    }
}
