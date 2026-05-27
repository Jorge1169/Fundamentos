/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.fundamentos.mibodega;

/**
 *
 * @author ADMIN
 */
public abstract class Producto {
    private static final String SEPARADOR = "------------------------";

    private String nombre;
    private double precio;
    
    // Constructor
    public Producto(String nombre, double precio) {
        setNombre(nombre);      // Usando setter para validar
        setPrecio(precio);       // Usando setter para validar
    }
    
    // Método abstracto que los hijos deben implementar
    public abstract double calcularPrecioFinal();
    
    // ============ GETTERS Y SETTERS ============
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        // Validación: el nombre no puede estar vacío
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        // Validación: el precio no puede ser negativo
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.precio = precio;
    }
    
    // Método para mostrar información (Se usara para sobre escrubir)
    public void mostrarInfo() {
        System.out.println("Nombre: " + getNombre());
        imprimirMoneda("Precio base", getPrecio());
    }

    protected void imprimirMoneda(String etiqueta, double valor) {
        System.out.println(etiqueta + ": $" + String.format("%.2f", valor));
    }

    protected void imprimirPrecioFinal() {
        imprimirMoneda("Precio final", calcularPrecioFinal());
    }

    protected void imprimirSeparador() {
        System.out.println(SEPARADOR);
    }

    protected double calcularIncrementoPorcentaje(double porcentaje) {
        return getPrecio() * porcentaje;
    }
}
