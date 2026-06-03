package com.curso.fundamentos.sistemadetienda.model;

/**
 * Base comun para los productos digitales de la tienda.
 */
public abstract class ProductoDigital extends Producto {

    private String linkDescarga;

    protected ProductoDigital(String nombre, double precioBase, String linkDescarga) {
        super(nombre, precioBase);
        this.linkDescarga = linkDescarga;
    }

    public String getLinkDescarga() {
        return linkDescarga;
    }

    public void setLinkDescarga(String linkDescarga) {
        this.linkDescarga = linkDescarga;
    }
}