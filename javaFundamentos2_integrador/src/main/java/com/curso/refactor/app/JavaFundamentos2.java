package com.curso.refactor.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

import com.curso.refactor.exception.AcademiaException;
import com.curso.refactor.functional.OperacionAcademia;
import com.curso.refactor.model.RecursoAcademico;
import com.curso.refactor.service.AcademiaService;

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
                /**
                 * Procesa la opción seleccionada por el usuario.
                 */
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

        ejecutarOperacionSegura("Carga inicial video", () ->
                academiaService.registrarCursoVideo("Java Básico", 1200, "BASICO", 8,
                        "https://academia.com/java_basico.mp4", "MP4"));

        ejecutarOperacionSegura("Carga inicial PDF", () ->
                academiaService.registrarCursoPdf("Guía POO", 400, "INTERMEDIO", 3,
                        "https://academia.com/poo.pdf", "PDF"));

        ejecutarOperacionSegura("Carga inicial mentoría", () ->
                academiaService.registrarMentoria("Mentoría Spring", 1800, "AVANZADO",
                        LocalDate.now().plusDays(5).format(formatter), "Jorge"));
    }

    private static void mostrarMenu() {
        /**
         * Muestra el menú principal de la aplicación.
         */
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
     /**
     * Registra un nuevo curso en video.
     */
    private static void registrarCursoVideo() {
        System.out.println("\n=== Registrar curso en video ===");
        String nombre = leerTexto("Nombre: ");
        double precio = leerDouble("Precio base: ");
        String nivel = leerTexto("Nivel (BASICO, INTERMEDIO, AVANZADO): ");
        int duracion = leerEntero("Duración en horas: ");
        String url = leerTexto("URL de descarga: ");
        String formato = leerTexto("Formato de video (MP4, WEBM, AVI): ");

        ejecutarOperacionSegura("Registrar curso en video", () ->
                academiaService.registrarCursoVideo(nombre, precio, nivel, duracion, url, formato));
    }

    /**
     * Registra un nuevo curso PDF.
     */
    private static void registrarCursoPdf() {
        System.out.println("\n=== Registrar curso PDF ===");
        String nombre = leerTexto("Nombre: ");
        double precio = leerDouble("Precio base: ");
        String nivel = leerTexto("Nivel (BASICO, INTERMEDIO, AVANZADO): ");
        int paginas = leerEntero("Número de páginas: ");
        String url = leerTexto("URL de descarga: ");
        String formato = leerTexto("Formato documento (PDF, EPUB): ");

        ejecutarOperacionSegura("Registrar curso PDF", () ->
                academiaService.registrarCursoPdf(nombre, precio, nivel, paginas, url, formato));
    }
        /**
        * Registra una nueva mentoría.
        */
    private static void registrarMentoria() {
        System.out.println("\n=== Registrar mentoría ===");
        String nombre = leerTexto("Nombre: ");
        double precio = leerDouble("Precio base: ");
        String nivel = leerTexto("Nivel (BASICO, INTERMEDIO, AVANZADO): ");
        String fecha = leerTexto("Fecha programada (dd/MM/yyyy): ");
        String mentor = leerTexto("Nombre del mentor: ");

        ejecutarOperacionSegura("Registrar mentoría", () ->
                academiaService.registrarMentoria(nombre, precio, nivel, fecha, mentor));
    }
    /**
     * Modifica un curso en video.
     */
    private static void modificarCursoVideo() {
        System.out.println("\n=== Modificar curso en video ===");
        String nombreActual = leerTexto("Nombre actual: ");
        String nuevoNombre = leerTexto("Nuevo nombre: ");
        double nuevoPrecio = leerDouble("Nuevo precio base: ");
        String nuevoNivel = leerTexto("Nuevo nivel: ");
        int nuevaDuracion = leerEntero("Nueva duración en horas: ");
        String nuevaUrl = leerTexto("Nueva URL: ");
        String nuevoFormato = leerTexto("Nuevo formato: ");

        ejecutarOperacionSegura("Modificar curso en video", () ->
                academiaService.modificarCursoVideo(nombreActual, nuevoNombre, nuevoPrecio, nuevoNivel,
                        nuevaDuracion, nuevaUrl, nuevoFormato));
    }
        /**
        * Modifica un curso PDF.
        */
    private static void modificarCursoPdf() {
        System.out.println("\n=== Modificar curso PDF ===");
        String nombreActual = leerTexto("Nombre actual: ");
        String nuevoNombre = leerTexto("Nuevo nombre: ");
        double nuevoPrecio = leerDouble("Nuevo precio base: ");
        String nuevoNivel = leerTexto("Nuevo nivel: ");
        int nuevasPaginas = leerEntero("Nuevo número de páginas: ");
        String nuevaUrl = leerTexto("Nueva URL: ");
        String nuevoFormato = leerTexto("Nuevo formato: ");

        /**
         * Ejecuta la operación de modificación de curso PDF de manera segura.
         */
        ejecutarOperacionSegura("Modificar curso PDF", () ->
                academiaService.modificarCursoPdf(nombreActual, nuevoNombre, nuevoPrecio, nuevoNivel,
                        nuevasPaginas, nuevaUrl, nuevoFormato));
    }

        /**
        * Modifica una mentoría.
        */
    private static void modificarMentoria() {
        System.out.println("\n=== Modificar mentoría ===");
        String nombreActual = leerTexto("Nombre actual: ");
        String nuevoNombre = leerTexto("Nuevo nombre: ");
        double nuevoPrecio = leerDouble("Nuevo precio base: ");
        String nuevoNivel = leerTexto("Nuevo nivel: ");
        String nuevaFecha = leerTexto("Nueva fecha (dd/MM/yyyy): ");
        String nuevoMentor = leerTexto("Nuevo mentor: ");

        /**
         * Ejecuta la operación de modificación de mentoría de manera segura.
         */
        ejecutarOperacionSegura("Modificar mentoría", () ->
                academiaService.modificarMentoria(nombreActual, nuevoNombre, nuevoPrecio, nuevoNivel,
                        nuevaFecha, nuevoMentor));
    }

    /**
     * Busca un recurso académico por tipo y nombre.
     */
    private static void buscarRecurso() {
        String tipo = leerTexto("Tipo (VIDEO, PDF, MENTORIA): ");
        String nombre = leerTexto("Nombre: ");

        ejecutarOperacionSegura("Buscar recurso", () -> {
            Optional<RecursoAcademico> recurso = academiaService.buscarPorTipoYNombre(tipo, nombre);
            recurso.ifPresentOrElse(
                    RecursoAcademico::mostrarDetalle,
                    () -> System.out.println("No se encontró el recurso."));
        });
    }

    /**
     * Ejecuta una operación segura.
     *
     * @param contexto Descripción del contexto de la operación.
     * @param operacion La operación a ejecutar.
     */
    private static void ejecutarOperacionSegura(String contexto, OperacionAcademia operacion) {
        try {
            operacion.ejecutar();
        } catch (AcademiaException e) {
            System.out.println("Error en " + contexto + ": " + e.getMessage());
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    /**
     * Lee un número entero desde la entrada estándar.
     *
     * @param mensaje Mensaje a mostrar al usuario.
     * @return El número entero ingresado por el usuario.
     */
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

    /**
     * Lee un número decimal desde la entrada estándar.
     *
     * @param mensaje Mensaje a mostrar al usuario.
     * @return El número decimal ingresado por el usuario.
     */ 
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

