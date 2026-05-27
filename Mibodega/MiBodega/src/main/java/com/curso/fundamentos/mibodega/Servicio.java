/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.fundamentos.mibodega;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author ADMIN
 */
public class Servicio extends Producto {
    private static final double IVA = 0.16;

    private String fechaServicio;
    
    public Servicio(String nombre, double precio, String fechaServicio) {
        super(nombre, precio);
        setFechaServicio(fechaServicio);  // Usando setter para validar
    }
    
    @Override
    public double calcularPrecioFinal() {
        return getPrecio() + calcularIva();
    }
    
    
    public String getFechaServicio() {
        return fechaServicio;
    }
    
    public void setFechaServicio(String fechaServicio) {
        // Validación: la fecha no puede estar vacía
        if (fechaServicio == null || fechaServicio.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de servicio no puede estar vacía");
        }
        
        // Validación: formato de fecha correcto
        try {
            LocalDate.parse(fechaServicio, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use YYYY-MM-DD");
        }
        
        this.fechaServicio = fechaServicio;
    }

    private double calcularIva() {
        return calcularIncrementoPorcentaje(IVA);
    }
    
    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("Fecha de servicio: " + getFechaServicio());
        imprimirMoneda("IVA (16%)", calcularIva());
        imprimirPrecioFinal();
        imprimirSeparador();
    }
}
