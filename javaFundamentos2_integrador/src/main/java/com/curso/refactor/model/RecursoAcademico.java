package com.curso.refactor.model;

import java.text.NumberFormat;

import com.curso.refactor.enums.NivelRecurso;
import com.curso.refactor.enums.TipoRecurso;

public abstract class RecursoAcademico {

    private String nombre;
    private double precioBase;
    private NivelRecurso nivel;

    public RecursoAcademico(String nombre, double precioBase, NivelRecurso nivel) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public NivelRecurso getNivel() {
        return nivel;
    }

    public void setNivel(NivelRecurso nivel) {
        this.nivel = nivel;
    }

    public abstract double calcularPrecioFinal();

    public abstract TipoRecurso obtenerTipo();

    public void mostrarDetalle() {
        NumberFormat moneda = NumberFormat.getCurrencyInstance();
        System.out.println("Nombre: " + nombre);
        System.out.println("Nivel: " + nivel.name());
        System.out.println("Precio base: " + moneda.format(precioBase));
        System.out.println("Precio final: " + moneda.format(calcularPrecioFinal()));
    }
}

