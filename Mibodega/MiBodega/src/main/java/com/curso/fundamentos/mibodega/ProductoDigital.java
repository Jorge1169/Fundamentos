/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.fundamentos.mibodega;

/**
 *
 * @author ADMIN
 */
public abstract class ProductoDigital extends Producto {
    private static final double IMPUESTO_DIGITAL = 0.10;

    private String linkDescarga;
    
    public ProductoDigital(String nombre, double precio, String linkDescarga) {
        super(nombre, precio);
        setLinkDescarga(linkDescarga);  // Usando setter para validar
    }
    
    @Override
    public double calcularPrecioFinal() {
        return getPrecio() + calcularImpuestoDigital();
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

    // Este metodo lo implementa cada clase hija segun su tipo digital.
    public abstract String getTipoProducto();

    protected abstract String getModoUso();

    protected abstract void mostrarAccionDisponible();

    private double calcularImpuestoDigital() {
        return calcularIncrementoPorcentaje(IMPUESTO_DIGITAL);
    }
    
    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("Tipo: " + getTipoProducto());
        System.out.println("Link de descarga: " + getLinkDescarga());
        imprimirMoneda("Impuesto digital (10%)", calcularImpuestoDigital());
        imprimirPrecioFinal();
        System.out.println("Modo de uso: " + getModoUso());
        mostrarAccionDisponible();
        imprimirSeparador();
    }
}