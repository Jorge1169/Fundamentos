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
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("Modo de uso: Este producto digital se instala.");
        instalar();
        System.out.println("------------------------");
    }
}