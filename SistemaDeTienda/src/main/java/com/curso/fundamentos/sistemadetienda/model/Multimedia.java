package com.curso.fundamentos.sistemadetienda.model;

import com.curso.fundamentos.sistemadetienda.interfaces.Reproducible;

/**
 * Representa un producto digital reproducible.
 */
public class Multimedia extends ProductoDigital implements Reproducible {

    private String formato;

    public Multimedia(String nombre, double precioBase, String linkDescarga, String formato) {
        super(nombre, precioBase, linkDescarga);
        this.formato = formato;
    }

    /**
     * Mantiene el precio final igual al precio base.
     *
     * @return precio final del producto multimedia
     */
    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase();
    }

    /**
     * Informa el tipo funcional del registro.
     *
     * @return nombre del tipo
     */
    @Override
    public String getTipo() {
        return "Multimedia";
    }

    /**
     * Ejecuta la accion de reproduccion del contenido.
     */
    @Override
    public void reproducir() {
        System.out.println("Reproduciendo: " + getNombre());
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}