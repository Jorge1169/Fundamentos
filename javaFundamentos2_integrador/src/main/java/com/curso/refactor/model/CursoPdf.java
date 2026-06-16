package com.curso.refactor.model;

import com.curso.refactor.enums.FormatoDocumento;
import com.curso.refactor.enums.NivelRecurso;
import com.curso.refactor.enums.TipoRecurso;
import com.curso.refactor.interfaces.Certificable;

public class CursoPdf extends RecursoDigital implements Certificable {

    private int numeroPaginas;
    private FormatoDocumento formatoDocumento;

    public CursoPdf(String nombre, double precioBase, NivelRecurso nivel, int numeroPaginas,
            String urlDescarga, FormatoDocumento formatoDocumento) {
        super(nombre, precioBase, nivel, urlDescarga);
        this.numeroPaginas = numeroPaginas;
        this.formatoDocumento = formatoDocumento;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public FormatoDocumento getFormatoDocumento() {
        return formatoDocumento;
    }

    public void setFormatoDocumento(FormatoDocumento formatoDocumento) {
        this.formatoDocumento = formatoDocumento;
    }

    @Override
    public double calcularPrecioFinal() {
        double precio = getPrecioBase();

        if (numeroPaginas > 200) {
            precio = precio + 100;
        }

        return precio;
    }

    @Override
    public TipoRecurso obtenerTipo() {
        return TipoRecurso.PDF;
    }

    @Override
    public void generarCertificado() {
        System.out.println("Generando certificado del curso PDF: " + getNombre());
    }

    @Override
    public void mostrarDetalle() {
        System.out.println("=== Curso PDF ===");
        super.mostrarDetalle();
        System.out.println("PÃ¡ginas: " + numeroPaginas);
        System.out.println("Formato: " + formatoDocumento.name());
    }
}

