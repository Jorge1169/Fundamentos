package com.curso.fundamentos.sistemadetienda.model;

import com.curso.fundamentos.sistemadetienda.interfaces.Instalable;

/**
 * Representa un producto digital instalable.
 */
public class Software extends ProductoDigital implements Instalable {

    private String sistemaOperativo;

    public Software(String nombre, double precioBase, String linkDescarga, String sistemaOperativo) {
        super(nombre, precioBase, linkDescarga);
        this.sistemaOperativo = sistemaOperativo;
    }

    /**
     * Mantiene el precio final igual al precio base.
     *
     * @return precio final del software
     */
    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase();
    }

    /**
     * Informa el tipo funcional del registro.
     *
     * @return nombre del tipo
     */
    @Override
    public String getTipo() {
        return "Software";
    }

    /**
     * Ejecuta la accion de instalacion del producto.
     */
    @Override
    public void instalar() {
        System.out.println("Instalando: " + getNombre());
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }
}