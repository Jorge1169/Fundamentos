package com.curso.fundamentos.sistemadetienda.model;

/**
 * Representa un producto fisico con peso asociado.
 */
public class ProductoFisico extends Producto {

    private static final double RECARGO_PESO = 150.0;

    private double peso;

    public ProductoFisico(String nombre, double precioBase, double peso) {
        super(nombre, precioBase);
        this.peso = peso;
    }

    /**
     * Aplica un recargo fijo cuando el peso supera 20 kg.
     *
     * @return precio final del producto fisico
     */
    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase() + (peso > 20 ? RECARGO_PESO : 0);
    }

    /**
     * Informa el tipo funcional del registro.
     *
     * @return nombre del tipo
     */
    @Override
    public String getTipo() {
        return "Producto Fisico";
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}