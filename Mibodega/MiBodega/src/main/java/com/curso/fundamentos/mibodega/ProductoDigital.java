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
    private String tipoProducto;
    
    public ProductoDigital(String nombre, double precio, String linkDescarga, String tipoProducto) {
        super(nombre, precio);
        setLinkDescarga(linkDescarga);  // Usando setter para validar
        setTipoProducto(tipoProducto);  // Usando setter para validar
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

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        if (tipoProducto == null || tipoProducto.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar el tipo de producto digital");
        }

        String tipoNormalizado = tipoProducto.trim();
        if (tipoNormalizado.equalsIgnoreCase("reproducible")) {
            this.tipoProducto = "Reproducible";
        } else if (tipoNormalizado.equalsIgnoreCase("instalable")) {
            this.tipoProducto = "Instalable";
        } else {
            throw new IllegalArgumentException("Tipo inválido. Use Reproducible o Instalable");
        }
    }
    
    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("Tipo: " + getTipoProducto());
        System.out.println("Link de descarga: " + getLinkDescarga());
        System.out.println("Impuesto digital (10%): $" + (getPrecio() * 0.10));
        System.out.println("Precio final: $" + calcularPrecioFinal());
        System.out.println("------------------------");
    }
}