package com.curso.fundamentos.sistemadetienda.model;

/**
 * Representa cualquier elemento registrable dentro del sistema.
 */
public abstract class ItemTienda {

    private String nombre;
    private double precioBase;

    protected ItemTienda(String nombre, double precioBase) {
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    /**
     * Calcula el precio final del elemento segun su tipo.
     *
     * @return precio final calculado
     */
    public abstract double calcularPrecioFinal();

    /**
     * Devuelve el nombre funcional del tipo de elemento.
     *
     * @return nombre del tipo
     */
    public abstract String getTipo();

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
}