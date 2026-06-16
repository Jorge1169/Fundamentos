package com.curso.refactor.model;

import com.curso.refactor.enums.NivelRecurso;
import com.curso.refactor.interfaces.Descargable;

public abstract class RecursoDigital extends RecursoAcademico implements Descargable {

    private String urlDescarga;

    public RecursoDigital(String nombre, double precioBase, NivelRecurso nivel, String urlDescarga) {
        super(nombre, precioBase, nivel);
        this.urlDescarga = urlDescarga;
    }

    public String getUrlDescarga() {
        return urlDescarga;
    }

    public void setUrlDescarga(String urlDescarga) {
        this.urlDescarga = urlDescarga;
    }

    @Override
    public void descargar() {
        System.out.println("Descargando desde: " + urlDescarga);
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase();
    }

    @Override
    public void mostrarDetalle() {
        super.mostrarDetalle();
        System.out.println("URL descarga: " + urlDescarga);
    }
}

