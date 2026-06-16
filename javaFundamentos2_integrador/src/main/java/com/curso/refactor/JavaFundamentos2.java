package com.curso.refactor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class JavaFundamentos2 {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AcademiaService academiaService = new AcademiaService();

    public static void main(String[] args) {
        cargarDatosIniciales();

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> registrarCursoVideo();
                case 2 -> registrarCursoPdf();
                case 3 -> registrarMentoria();
                case 4 -> modificarCursoVideo();
                case 5 -> modificarCursoPdf();
                case 6 -> modificarMentoria();
                case 7 -> academiaService.mostrarCatalogo();
                case 8 -> buscarRecurso();
                case 9 -> academiaService.mostrarResumenPorNivel();
                case 10 -> academiaService.ejecutarEjemplosModulo5();
                case 11 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 11);

        scanner.close();
    }

    private static void cargarDatosIniciales() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            academiaService.registrarCursoVideo("Java Básico", 1200, "BASICO", 8, "https://academia.com/java_basico.mp4", "MP4");
        } catch (AcademiaException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            academiaService.registrarCursoPdf("Guía POO", 400, "INTERMEDIO", 3, "https://academia.com/poo.pdf", "PDF");
        } catch (AcademiaException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            academiaService.registrarMentoria("Mentoría Spring", 1800, "AVANZADO", LocalDate.now().plusDays(5).format(formatter), "Jorge");
        } catch (AcademiaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== ACADEMIA JAVA FUNDAMENTOS 2 =====");
        System.out.println("1. Registrar curso en video");
        System.out.println("2. Registrar curso PDF");
        System.out.println("3. Registrar mentoría");
        System.out.println("4. Modificar curso en video");
        System.out.println("5. Modificar curso PDF");
        System.out.println("6. Modificar mentoría");
        System.out.println("7. Mostrar catálogo");
        System.out.println("8. Buscar recurso por tipo y nombre");
        System.out.println("9. Mostrar resumen por nivel");
        System.out.println("10. Ejecutar ejemplos Módulo 5");
        System.out.println("11. Salir");
    }

    private static void registrarCursoVideo() {
        System.out.println("\n=== Registrar curso en video ===");
        String nombre = leerTexto("Nombre: ");
        double precio = leerDouble("Precio base: ");
        String nivel = leerTexto("Nivel (BASICO, INTERMEDIO, AVANZADO): ");
        int duracion = leerEntero("Duración en horas: ");
        String url = leerTexto("URL de descarga: ");
        String formato = leerTexto("Formato de video (MP4, WEBM, AVI): ");

        try {
            academiaService.registrarCursoVideo(nombre, precio, nivel, duracion, url, formato);
        } catch (AcademiaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarCursoPdf() {
        System.out.println("\n=== Registrar curso PDF ===");
        String nombre = leerTexto("Nombre: ");
        double precio = leerDouble("Precio base: ");
        String nivel = leerTexto("Nivel (BASICO, INTERMEDIO, AVANZADO): ");
        int paginas = leerEntero("Número de páginas: ");
        String url = leerTexto("URL de descarga: ");
        String formato = leerTexto("Formato documento (PDF, EPUB): ");

        try {
            academiaService.registrarCursoPdf(nombre, precio, nivel, paginas, url, formato);
        } catch (AcademiaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarMentoria() {
        System.out.println("\n=== Registrar mentoría ===");
        String nombre = leerTexto("Nombre: ");
        double precio = leerDouble("Precio base: ");
        String nivel = leerTexto("Nivel (BASICO, INTERMEDIO, AVANZADO): ");
        String fecha = leerTexto("Fecha programada (dd/MM/yyyy): ");
        String mentor = leerTexto("Nombre del mentor: ");

        try {
            academiaService.registrarMentoria(nombre, precio, nivel, fecha, mentor);
        } catch (AcademiaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void modificarCursoVideo() {
        System.out.println("\n=== Modificar curso en video ===");
        String nombreActual = leerTexto("Nombre actual: ");
        String nuevoNombre = leerTexto("Nuevo nombre: ");
        double nuevoPrecio = leerDouble("Nuevo precio base: ");
        String nuevoNivel = leerTexto("Nuevo nivel: ");
        int nuevaDuracion = leerEntero("Nueva duración en horas: ");
        String nuevaUrl = leerTexto("Nueva URL: ");
        String nuevoFormato = leerTexto("Nuevo formato: ");

        try {
            academiaService.modificarCursoVideo(nombreActual, nuevoNombre, nuevoPrecio, nuevoNivel, nuevaDuracion, nuevaUrl, nuevoFormato);
        } catch (AcademiaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void modificarCursoPdf() {
        System.out.println("\n=== Modificar curso PDF ===");
        String nombreActual = leerTexto("Nombre actual: ");
        String nuevoNombre = leerTexto("Nuevo nombre: ");
        double nuevoPrecio = leerDouble("Nuevo precio base: ");
        String nuevoNivel = leerTexto("Nuevo nivel: ");
        int nuevasPaginas = leerEntero("Nuevo número de páginas: ");
        String nuevaUrl = leerTexto("Nueva URL: ");
        String nuevoFormato = leerTexto("Nuevo formato: ");

        try {
            academiaService.modificarCursoPdf(nombreActual, nuevoNombre, nuevoPrecio, nuevoNivel, nuevasPaginas, nuevaUrl, nuevoFormato);
        } catch (AcademiaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void modificarMentoria() {
        System.out.println("\n=== Modificar mentoría ===");
        String nombreActual = leerTexto("Nombre actual: ");
        String nuevoNombre = leerTexto("Nuevo nombre: ");
        double nuevoPrecio = leerDouble("Nuevo precio base: ");
        String nuevoNivel = leerTexto("Nuevo nivel: ");
        String nuevaFecha = leerTexto("Nueva fecha (dd/MM/yyyy): ");
        String nuevoMentor = leerTexto("Nuevo mentor: ");

        try {
            academiaService.modificarMentoria(nombreActual, nuevoNombre, nuevoPrecio, nuevoNivel, nuevaFecha, nuevoMentor);
        } catch (AcademiaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void buscarRecurso() {
        String tipo = leerTexto("Tipo (VIDEO, PDF, MENTORIA): ");
        String nombre = leerTexto("Nombre: ");
        RecursoAcademico recurso = academiaService.buscarPorTipoYNombre(tipo, nombre);

        if (recurso == null) {
            System.out.println("No se encontró el recurso.");
        } else {
            recurso.mostrarDetalle();
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número válido.");
            }
        }
    }
}
