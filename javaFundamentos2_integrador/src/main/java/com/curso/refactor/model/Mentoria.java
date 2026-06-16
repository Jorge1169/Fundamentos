package com.curso.refactor.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.curso.refactor.enums.NivelRecurso;
import com.curso.refactor.enums.TipoRecurso;
import com.curso.refactor.interfaces.Agendable;

public class Mentoria extends RecursoAcademico implements Agendable {

    private LocalDate fechaProgramada;
    private String mentor;

    public Mentoria(String nombre, double precioBase, NivelRecurso nivel, LocalDate fechaProgramada, String mentor) {
        super(nombre, precioBase, nivel);
        this.fechaProgramada = fechaProgramada;
        this.mentor = mentor;
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase() * 1.16;
    }

    @Override
    public TipoRecurso obtenerTipo() {
        return TipoRecurso.MENTORIA;
    }

    @Override
    public void agendar() {
        System.out.println("Agendando mentoría con " + mentor);
    }

    @Override
    public void mostrarDetalle() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("=== Mentoría ===");
        super.mostrarDetalle();
        System.out.println("Fecha: " + fechaProgramada.format(formatter));
        System.out.println("Mentor: " + mentor);
    }
}

