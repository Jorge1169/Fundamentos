package com.curso.refactor;

public class CursoVideo extends RecursoDigital implements Reproducible, Certificable {

    private int duracionHoras;
    private String formatoVideo;

    public CursoVideo(String nombre, double precioBase, String nivel, int duracionHoras,
            String urlDescarga, String formatoVideo) {
        super(nombre, precioBase, nivel, urlDescarga);
        this.duracionHoras = duracionHoras;
        this.formatoVideo = formatoVideo;
    }

    public int getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(int duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public String getFormatoVideo() {
        return formatoVideo;
    }

    public void setFormatoVideo(String formatoVideo) {
        this.formatoVideo = formatoVideo;
    }

    @Override
    public double calcularPrecioFinal() {
        double precio = getPrecioBase();

        if (duracionHoras > 10) {
            precio = precio + 300;
        }

        return precio;
    }

    @Override
    public String obtenerTipo() {
        return "VIDEO";
    }

    @Override
    public void reproducir() {
        System.out.println("Reproduciendo video: " + getNombre());
    }

    @Override
    public void generarCertificado() {
        System.out.println("Generando certificado del curso en video: " + getNombre());
    }

    @Override
    public void mostrarDetalle() {
        System.out.println("=== Curso en video ===");
        super.mostrarDetalle();
        System.out.println("Duración: " + duracionHoras + " horas");
        System.out.println("Formato: " + formatoVideo);
    }
}
