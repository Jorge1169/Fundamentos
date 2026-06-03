package com.curso.fundamentos.sistemadetienda.model;

/**
 * Base comun para los productos de la tienda.
 */
public abstract class Producto extends ItemTienda {

    protected Producto(String nombre, double precioBase) {
        super(nombre, precioBase);
    }
}