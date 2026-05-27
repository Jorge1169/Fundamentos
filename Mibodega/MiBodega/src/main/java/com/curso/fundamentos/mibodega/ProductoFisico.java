/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.fundamentos.mibodega;

/**
 *
 * @author ADMIN
 */
public class ProductoFisico extends Producto {
    private static final double COSTO_ENVIO_POR_KG = 5.0;

    private double peso; // en kg
    
    public ProductoFisico(String nombre, double precio, double peso) {
        super(nombre, precio);
        setPeso(peso);  // Usando setter para validar
    }
    
    @Override
    public double calcularPrecioFinal() {
        return getPrecio() + calcularCostoEnvio();
    }
    
    
    public double getPeso() {
        return peso;
    }
    
    public void setPeso(double peso) {
        // Validación: el peso no puede ser negativo
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }
        this.peso = peso;
    }

    private double calcularCostoEnvio() {
        return getPeso() * COSTO_ENVIO_POR_KG;
    }
    
    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("Peso: " + getPeso() + " kg");
        imprimirMoneda("Costo de envío", calcularCostoEnvio());
        imprimirPrecioFinal();
        imprimirSeparador();
    }
}
