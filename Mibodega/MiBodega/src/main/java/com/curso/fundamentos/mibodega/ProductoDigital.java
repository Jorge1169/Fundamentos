/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.fundamentos.mibodega;

/**
 *
 * @author ADMIN
 */
public class ProductoDigital extends Producto {
    // Atributo privado
    private String linkDescarga;
    
    public ProductoDigital(String nombre, double precio, String linkDescarga) {
        super(nombre, precio);
        setLinkDescarga(linkDescarga);  // Usando setter para validar
    }
    
    @Override
    public double calcularPrecioFinal() {
        // Producto digital: precio base + impuesto digital del 10%
        double impuesto = getPrecio() * 0.10;
        return getPrecio() + impuesto;
    }
    
    public String getLinkDescarga() {
        return linkDescarga;
    }
    
    public void setLinkDescarga(String linkDescarga) {
        // Validación: el link no puede estar vacío
        if (linkDescarga == null || linkDescarga.trim().isEmpty()) {
            throw new IllegalArgumentException("El link de descarga no puede estar vacío");
        }
        // Validación: debe ser una URL válida (básica)
        if (!linkDescarga.startsWith("http://") && !linkDescarga.startsWith("https://")) {
            System.out.println("⚠️ Advertencia: El link debería comenzar con http:// o https://");
        }
        this.linkDescarga = linkDescarga;
    }
    
    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("Link de descarga: " + getLinkDescarga());
        System.out.println("Impuesto digital (10%): $" + (getPrecio() * 0.10));
        System.out.println("Precio final: $" + calcularPrecioFinal());
        System.out.println("------------------------");
    }
}