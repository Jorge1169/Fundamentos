package com.curso.refactor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AcademiaService {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final List<RecursoAcademico> recursos = new ArrayList<>();

    public void registrarCursoVideo(String nombre, double precioBase, String nivel, int duracionHoras,
            String urlDescarga, String formatoVideo) throws AcademiaException {
        validarNombrePrecioNivel(nombre, precioBase, nivel);

        if (duracionHoras <= 0) {
            throw new RecursoInvalidoException("La duración debe ser mayor a cero.");
        }

        validarUrl(urlDescarga);

        String formatoNormalizado = formatoVideo.trim().toUpperCase();
        if (!formatoNormalizado.equals("MP4") && !formatoNormalizado.equals("WEBM") && !formatoNormalizado.equals("AVI")) {
            throw new RecursoInvalidoException("El formato de video solo puede ser MP4, WEBM o AVI.");
        }

        validarDuplicado("VIDEO", nombre, -1);

        CursoVideo curso = new CursoVideo(nombre.trim(), precioBase, nivel.trim().toUpperCase(), duracionHoras, urlDescarga.trim(), formatoNormalizado);
        validarPrecioFinal(curso);
        recursos.add(curso);
        System.out.println("Curso en video registrado correctamente.");
    }

    public void registrarCursoPdf(String nombre, double precioBase, String nivel, int numeroPaginas,
            String urlDescarga, String formatoDocumento) throws AcademiaException {
        validarNombrePrecioNivel(nombre, precioBase, nivel);

        if (numeroPaginas <= 0) {
            throw new RecursoInvalidoException("El número de páginas debe ser mayor a cero.");
        }

        validarUrl(urlDescarga);

        String formatoNormalizado = formatoDocumento.trim().toUpperCase();
        if (!formatoNormalizado.equals("PDF") && !formatoNormalizado.equals("EPUB")) {
            throw new RecursoInvalidoException("El formato de documento solo puede ser PDF o EPUB.");
        }

        validarDuplicado("PDF", nombre, -1);

        CursoPdf curso = new CursoPdf(nombre.trim(), precioBase, nivel.trim().toUpperCase(), numeroPaginas, urlDescarga.trim(), formatoNormalizado);
        validarPrecioFinal(curso);
        recursos.add(curso);
        System.out.println("Curso PDF registrado correctamente.");
    }

    public void registrarMentoria(String nombre, double precioBase, String nivel, String fechaTexto, String mentor)
            throws AcademiaException {
        validarNombrePrecioNivel(nombre, precioBase, nivel);

        LocalDate fechaProgramada = validarFechaFutura(fechaTexto);

        if (mentor == null || mentor.trim().isEmpty()) {
            throw new RecursoInvalidoException("El mentor es obligatorio.");
        }

        validarDuplicado("MENTORIA", nombre, -1);

        Mentoria mentoria = new Mentoria(nombre.trim(), precioBase, nivel.trim().toUpperCase(), fechaProgramada, mentor.trim());
        validarPrecioFinal(mentoria);
        recursos.add(mentoria);
        System.out.println("Mentoría registrada correctamente.");
    }

    public void modificarCursoVideo(String nombreActual, String nuevoNombre, double nuevoPrecio,
            String nuevoNivel, int nuevaDuracion, String nuevaUrl, String nuevoFormato) throws AcademiaException {
        int indice = buscarIndice("VIDEO", nombreActual);
        validarNombrePrecioNivel(nuevoNombre, nuevoPrecio, nuevoNivel);

        if (nuevaDuracion <= 0) {
            throw new RecursoInvalidoException("La duración debe ser mayor a cero.");
        }

        validarUrl(nuevaUrl);

        String formatoNormalizado = nuevoFormato.trim().toUpperCase();
        if (!formatoNormalizado.equals("MP4") && !formatoNormalizado.equals("WEBM") && !formatoNormalizado.equals("AVI")) {
            throw new RecursoInvalidoException("El formato de video solo puede ser MP4, WEBM o AVI.");
        }

        validarDuplicado("VIDEO", nuevoNombre, indice);
        CursoVideo curso = new CursoVideo(nuevoNombre.trim(), nuevoPrecio, nuevoNivel.trim().toUpperCase(), nuevaDuracion, nuevaUrl.trim(), formatoNormalizado);
        validarPrecioFinal(curso);
        recursos.set(indice, curso);
        System.out.println("Curso en video modificado correctamente.");
    }

    public void modificarCursoPdf(String nombreActual, String nuevoNombre, double nuevoPrecio,
            String nuevoNivel, int nuevasPaginas, String nuevaUrl, String nuevoFormato) throws AcademiaException {
        int indice = buscarIndice("PDF", nombreActual);
        validarNombrePrecioNivel(nuevoNombre, nuevoPrecio, nuevoNivel);

        if (nuevasPaginas <= 0) {
            throw new RecursoInvalidoException("El número de páginas debe ser mayor a cero.");
        }

        validarUrl(nuevaUrl);

        String formatoNormalizado = nuevoFormato.trim().toUpperCase();
        if (!formatoNormalizado.equals("PDF") && !formatoNormalizado.equals("EPUB")) {
            throw new RecursoInvalidoException("El formato de documento solo puede ser PDF o EPUB.");
        }

        validarDuplicado("PDF", nuevoNombre, indice);
        CursoPdf curso = new CursoPdf(nuevoNombre.trim(), nuevoPrecio, nuevoNivel.trim().toUpperCase(), nuevasPaginas, nuevaUrl.trim(), formatoNormalizado);
        validarPrecioFinal(curso);
        recursos.set(indice, curso);
        System.out.println("Curso PDF modificado correctamente.");
    }

    public void modificarMentoria(String nombreActual, String nuevoNombre, double nuevoPrecio,
            String nuevoNivel, String nuevaFechaTexto, String nuevoMentor) throws AcademiaException {
        int indice = buscarIndice("MENTORIA", nombreActual);
        validarNombrePrecioNivel(nuevoNombre, nuevoPrecio, nuevoNivel);

        LocalDate fechaProgramada = validarFechaFutura(nuevaFechaTexto);

        if (nuevoMentor == null || nuevoMentor.trim().isEmpty()) {
            throw new RecursoInvalidoException("El mentor es obligatorio.");
        }

        validarDuplicado("MENTORIA", nuevoNombre, indice);
        Mentoria mentoria = new Mentoria(nuevoNombre.trim(), nuevoPrecio, nuevoNivel.trim().toUpperCase(), fechaProgramada, nuevoMentor.trim());
        validarPrecioFinal(mentoria);
        recursos.set(indice, mentoria);
        System.out.println("Mentoría modificada correctamente.");
    }

    public void mostrarCatalogo() {
        if (recursos.isEmpty()) {
            System.out.println("No hay recursos registrados.");
            return;
        }

        System.out.println("\n===== CATÁLOGO =====");

        for (RecursoAcademico recurso : recursos) {
            recurso.mostrarDetalle();

            if (recurso instanceof Descargable descargable) {
                descargable.descargar();
            }

            if (recurso instanceof Reproducible reproducible) {
                reproducible.reproducir();
            }

            if (recurso instanceof Agendable agendable) {
                agendable.agendar();
            }

            if (recurso instanceof Certificable certificable) {
                certificable.generarCertificado();
            }

            System.out.println("------------------------");
        }
    }

    public RecursoAcademico buscarPorTipoYNombre(String tipo, String nombre) {
        for (RecursoAcademico recurso : recursos) {
            if (recurso.obtenerTipo().equalsIgnoreCase(tipo)
                    && recurso.getNombre().equalsIgnoreCase(nombre)) {
                return recurso;
            }
        }

        return null;
    }

    public void mostrarResumenPorNivel() {
        Map<String, Integer> contadorPorNivel = new HashMap<>();
        Set<String> nivelesUsados = new HashSet<>();

        for (RecursoAcademico recurso : recursos) {
            nivelesUsados.add(recurso.getNivel());

            if (!contadorPorNivel.containsKey(recurso.getNivel())) {
                contadorPorNivel.put(recurso.getNivel(), 1);
            } else {
                contadorPorNivel.put(recurso.getNivel(), contadorPorNivel.get(recurso.getNivel()) + 1);
            }
        }

        System.out.println("===== RESUMEN POR NIVEL =====");

        for (String nivel : nivelesUsados) {
            System.out.println(nivel + ": " + contadorPorNivel.get(nivel));
        }
    }

    public void ejecutarEjemplosModulo5() {
        System.out.println("============ Predicate ============");
        Predicate<RecursoAcademico> recursoCaro = recurso -> recurso.getPrecioBase() > 1000;
        recursos.forEach(recurso -> System.out.println(recurso.getNombre() + " caro: " + recursoCaro.test(recurso)));

        System.out.println("============ Function ============");
        Function<RecursoAcademico, String> obtenerNombre = recurso -> recurso.getNombre();
        recursos.forEach(recurso -> System.out.println("Nombre: " + obtenerNombre.apply(recurso)));

        System.out.println("============ Consumer ============");
        Consumer<RecursoAcademico> mostrar = recurso -> recurso.mostrarDetalle();
        recursos.forEach(mostrar);

        System.out.println("============ Supplier ============");
        Supplier<LocalDate> fechaActual = () -> LocalDate.now();
        System.out.println("Fecha generada: " + fechaActual.get());
    }

    private void validarNombrePrecioNivel(String nombre, double precioBase, String nivel) throws RecursoInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new RecursoInvalidoException("El nombre es obligatorio.");
        }

        if (nombre.trim().length() < 3 || nombre.trim().length() > 60) {
            throw new RecursoInvalidoException("El nombre debe tener entre 3 y 60 caracteres.");
        }

        if (precioBase <= 0) {
            throw new RecursoInvalidoException("El precio debe ser mayor a cero.");
        }

        if (precioBase > 50000) {
            throw new RecursoInvalidoException("El precio no puede ser mayor a $50,000.");
        }

        if (nivel == null || nivel.trim().isEmpty()) {
            throw new RecursoInvalidoException("El nivel es obligatorio.");
        }

        String nivelNormalizado = nivel.trim().toUpperCase();

        if (!nivelNormalizado.equals("BASICO")
                && !nivelNormalizado.equals("INTERMEDIO")
                && !nivelNormalizado.equals("AVANZADO")) {
            throw new RecursoInvalidoException("El nivel solo puede ser BASICO, INTERMEDIO o AVANZADO.");
        }
    }

    private void validarUrl(String urlDescarga) throws RecursoInvalidoException {
        if (urlDescarga == null || urlDescarga.trim().isEmpty()) {
            throw new RecursoInvalidoException("La URL de descarga es obligatoria.");
        }

        if (!urlDescarga.trim().startsWith("http://") && !urlDescarga.trim().startsWith("https://")) {
            throw new RecursoInvalidoException("La URL debe iniciar con http:// o https://.");
        }
    }

    private LocalDate validarFechaFutura(String fechaTexto) throws RecursoInvalidoException {
        if (fechaTexto == null || fechaTexto.trim().isEmpty()) {
            throw new RecursoInvalidoException("La fecha es obligatoria.");
        }

        LocalDate fechaProgramada;

        try {
            fechaProgramada = LocalDate.parse(fechaTexto.trim(), FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new RecursoInvalidoException("La fecha debe tener formato dd/MM/yyyy.");
        }

        if (!fechaProgramada.isAfter(LocalDate.now())) {
            throw new RecursoInvalidoException("La fecha debe ser posterior al día de hoy.");
        }

        return fechaProgramada;
    }

    private void validarDuplicado(String tipo, String nombre, int indiceIgnorado) throws RecursoDuplicadoException {
        for (int i = 0; i < recursos.size(); i++) {
            if (i == indiceIgnorado) {
                continue;
            }

            RecursoAcademico recurso = recursos.get(i);

            if (recurso.obtenerTipo().equalsIgnoreCase(tipo)
                    && recurso.getNombre().trim().equalsIgnoreCase(nombre.trim())) {
                throw new RecursoDuplicadoException("Ya existe un recurso de tipo " + tipo + " con el nombre: " + nombre.trim());
            }
        }
    }

    private void validarPrecioFinal(RecursoAcademico recurso) throws RecursoInvalidoException {
        if (recurso.calcularPrecioFinal() < recurso.getPrecioBase()) {
            throw new RecursoInvalidoException("El precio final no puede ser menor al precio base.");
        }
    }

    private int buscarIndice(String tipo, String nombre) throws RecursoNoEncontradoException {
        for (int i = 0; i < recursos.size(); i++) {
            RecursoAcademico recurso = recursos.get(i);

            if (recurso.obtenerTipo().equalsIgnoreCase(tipo)
                    && recurso.getNombre().trim().equalsIgnoreCase(nombre.trim())) {
                return i;
            }
        }

        throw new RecursoNoEncontradoException("No se encontró un recurso de tipo " + tipo + " con el nombre: " + nombre);
    }
}
