package com.curso.refactor;

import java.text.NumberFormat;

public abstract class RecursoAcademico {

    private String nombre;
    private double precioBase;
    private String nivel;

    public RecursoAcademico(String nombre, double precioBase, String nivel) {
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

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public abstract double calcularPrecioFinal();

    public abstract String obtenerTipo();

    public void mostrarDetalle() {
        NumberFormat moneda = NumberFormat.getCurrencyInstance();
        System.out.println("Nombre: " + nombre);
        System.out.println("Nivel: " + nivel);
        System.out.println("Precio base: " + moneda.format(precioBase));
        System.out.println("Precio final: " + moneda.format(calcularPrecioFinal()));
    }
}
