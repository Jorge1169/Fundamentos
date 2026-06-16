package com.curso.refactor.model;

import com.curso.refactor.enums.FormatoVideo;
import com.curso.refactor.enums.NivelRecurso;
import com.curso.refactor.enums.TipoRecurso;
import com.curso.refactor.interfaces.Certificable;
import com.curso.refactor.interfaces.Reproducible;

public class CursoVideo extends RecursoDigital implements Reproducible, Certificable {

    private int duracionHoras;
    private FormatoVideo formatoVideo;

    public CursoVideo(String nombre, double precioBase, NivelRecurso nivel, int duracionHoras,
            String urlDescarga, FormatoVideo formatoVideo) {
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

    public FormatoVideo getFormatoVideo() {
        return formatoVideo;
    }

    public void setFormatoVideo(FormatoVideo formatoVideo) {
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
    public TipoRecurso obtenerTipo() {
        return TipoRecurso.VIDEO;
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
        System.out.println("DuraciÃ³n: " + duracionHoras + " horas");
        System.out.println("Formato: " + formatoVideo.name());
    }
}

