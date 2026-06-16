package com.curso.refactor;

public class CursoPdf extends RecursoDigital implements Certificable {

    private int numeroPaginas;
    private String formatoDocumento;

    public CursoPdf(String nombre, double precioBase, String nivel, int numeroPaginas,
            String urlDescarga, String formatoDocumento) {
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

    public String getFormatoDocumento() {
        return formatoDocumento;
    }

    public void setFormatoDocumento(String formatoDocumento) {
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
    public String obtenerTipo() {
        return "PDF";
    }

    @Override
    public void generarCertificado() {
        System.out.println("Generando certificado del curso PDF: " + getNombre());
    }

    @Override
    public void mostrarDetalle() {
        System.out.println("=== Curso PDF ===");
        super.mostrarDetalle();
        System.out.println("Páginas: " + numeroPaginas);
        System.out.println("Formato: " + formatoDocumento);
    }
}
