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
    // Atributo privado (solo accesible dentro de esta clase)
    private double peso; // en kg
    
    public ProductoFisico(String nombre, double precio, double peso) {
        super(nombre, precio);
        setPeso(peso);  // Usando setter para validar
    }
    
    @Override
    public double calcularPrecioFinal() {
        // Producto físico: precio + costo de envío ($5 por kg)
        double costoEnvio = getPeso() * 5;
        return getPrecio() + costoEnvio;
    }
    
    // ============ GETTERS Y SETTERS ESPECÍFICOS ============
    
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
    
    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("Peso: " + getPeso() + " kg");
        System.out.println("Costo de envío: $" + (getPeso() * 5));
        System.out.println("Precio final: $" + calcularPrecioFinal());
        System.out.println("------------------------");
    }
}
