package com.curso.fundamentos.mibodega;

// Aqui se implementa la interfaz Reproducible para este tipo digital.
public class ProductoDigitalReproducible extends ProductoDigital implements Reproducible {

    public ProductoDigitalReproducible(String nombre, double precio, String linkDescarga) {
        super(nombre, precio, linkDescarga);
    }

    @Override
    public String getTipoProducto() {
        return "Reproducible";
    }

    @Override
    public void reproducir() {
        System.out.println("Accion disponible: Reproducir " + getNombre());
    }

    @Override
    protected String getModoUso() {
        return "Este producto digital se reproduce.";
    }

    @Override
    protected void mostrarAccionDisponible() {
        reproducir();
    }
}