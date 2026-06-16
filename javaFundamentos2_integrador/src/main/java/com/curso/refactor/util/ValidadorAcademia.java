package com.curso.refactor.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.curso.refactor.exception.RecursoInvalidoException;
import com.curso.refactor.enums.NivelRecurso;

public final class ValidadorAcademia {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private ValidadorAcademia() {
    }

    public static String validarNombre(String nombre) throws RecursoInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new RecursoInvalidoException("El nombre es obligatorio.");
        }

        String nombreNormalizado = nombre.trim();
        if (nombreNormalizado.length() < 3 || nombreNormalizado.length() > 60) {
            throw new RecursoInvalidoException("El nombre debe tener entre 3 y 60 caracteres.");
        }

        return nombreNormalizado;
    }

    public static double validarPrecio(double precioBase) throws RecursoInvalidoException {
        if (precioBase <= 0) {
            throw new RecursoInvalidoException("El precio debe ser mayor a cero.");
        }

        if (precioBase > 50000) {
            throw new RecursoInvalidoException("El precio no puede ser mayor a $50,000.");
        }

        return precioBase;
    }

    public static NivelRecurso validarNivel(String nivel) throws RecursoInvalidoException {
        if (nivel == null || nivel.trim().isEmpty()) {
            throw new RecursoInvalidoException("El nivel es obligatorio.");
        }

        return NivelRecurso.desdeTexto(nivel);
    }

    public static String validarUrl(String urlDescarga) throws RecursoInvalidoException {
        if (urlDescarga == null || urlDescarga.trim().isEmpty()) {
            throw new RecursoInvalidoException("La URL de descarga es obligatoria.");
        }

        String urlNormalizada = urlDescarga.trim();
        if (!urlNormalizada.startsWith("http://") && !urlNormalizada.startsWith("https://")) {
            throw new RecursoInvalidoException("La URL debe iniciar con http:// o https://.");
        }

        return urlNormalizada;
    }

    public static int validarDuracion(int duracionHoras) throws RecursoInvalidoException {
        if (duracionHoras <= 0) {
            throw new RecursoInvalidoException("La duraciÃ³n debe ser mayor a cero.");
        }

        return duracionHoras;
    }

    public static int validarPaginas(int numeroPaginas) throws RecursoInvalidoException {
        if (numeroPaginas <= 0) {
            throw new RecursoInvalidoException("El nÃºmero de pÃ¡ginas debe ser mayor a cero.");
        }

        return numeroPaginas;
    }

    public static String validarMentor(String mentor) throws RecursoInvalidoException {
        if (mentor == null || mentor.trim().isEmpty()) {
            throw new RecursoInvalidoException("El mentor es obligatorio.");
        }

        return mentor.trim();
    }

    public static LocalDate validarFechaFutura(String fechaTexto) throws RecursoInvalidoException {
        if (fechaTexto == null || fechaTexto.trim().isEmpty()) {
            throw new RecursoInvalidoException("La fecha es obligatoria.");
        }

        try {
            LocalDate fechaProgramada = LocalDate.parse(fechaTexto.trim(), FORMATO_FECHA);
            if (!fechaProgramada.isAfter(LocalDate.now())) {
                throw new RecursoInvalidoException("La fecha debe ser posterior al dÃ­a de hoy.");
            }

            return fechaProgramada;
        } catch (DateTimeParseException e) {
            throw new RecursoInvalidoException("La fecha debe tener formato dd/MM/yyyy.");
        }
    }
}

