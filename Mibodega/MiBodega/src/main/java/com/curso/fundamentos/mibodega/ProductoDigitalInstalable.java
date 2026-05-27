package com.curso.fundamentos.mibodega;

// Aqui se implementa la interfaz Instalable para este tipo digital.
public class ProductoDigitalInstalable extends ProductoDigital implements Instalable {

    public ProductoDigitalInstalable(String nombre, double precio, String linkDescarga) {
        super(nombre, precio, linkDescarga);
    }

    @Override
    public String getTipoProducto() {
        return "Instalable";
    }

    @Override
    public void instalar() {
        System.out.println("Accion disponible: Instalar " + getNombre());
    }

    @Override
    protected String getModoUso() {
        return "Este producto digital se instala.";
    }

    @Override
    protected void mostrarAccionDisponible() {
        instalar();
    }
}