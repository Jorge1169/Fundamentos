package com.curso.refactor.functional;

import com.curso.refactor.exception.AcademiaException;

@FunctionalInterface
public interface OperacionAcademia {
    void ejecutar() throws AcademiaException;
}

