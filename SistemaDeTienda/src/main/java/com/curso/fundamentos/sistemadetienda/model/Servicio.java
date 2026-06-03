package com.curso.fundamentos.sistemadetienda.model;

import java.time.LocalDate;

/**
 * Representa un servicio programado para una fecha futura.
 */
public class Servicio extends ItemTienda {

    private static final double CARGO_SERVICIO = 0.10;

    private LocalDate fechaServicio;

    public Servicio(String nombre, double precioBase, LocalDate fechaServicio) {
        super(nombre, precioBase);
        this.fechaServicio = fechaServicio;
    }

    /**
     * Aplica un cargo adicional del diez por ciento.
     *
     * @return precio final del servicio
     */
    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase() + (getPrecioBase() * CARGO_SERVICIO);
    }

    /**
     * Informa el tipo funcional del registro.
     *
     * @return nombre del tipo
     */
    @Override
    public String getTipo() {
        return "Servicio";
    }

    public LocalDate getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(LocalDate fechaServicio) {
        this.fechaServicio = fechaServicio;
    }
}