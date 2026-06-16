package com.curso.refactor.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.curso.refactor.exception.AcademiaException;
import com.curso.refactor.exception.RecursoDuplicadoException;
import com.curso.refactor.exception.RecursoInvalidoException;
import com.curso.refactor.exception.RecursoNoEncontradoException;
import com.curso.refactor.model.CursoPdf;
import com.curso.refactor.model.CursoVideo;
import com.curso.refactor.model.Mentoria;
import com.curso.refactor.model.RecursoAcademico;
import com.curso.refactor.enums.FormatoDocumento;
import com.curso.refactor.enums.FormatoVideo;
import com.curso.refactor.enums.NivelRecurso;
import com.curso.refactor.enums.TipoRecurso;
import com.curso.refactor.interfaces.Agendable;
import com.curso.refactor.interfaces.Certificable;
import com.curso.refactor.interfaces.Descargable;
import com.curso.refactor.interfaces.Reproducible;
import com.curso.refactor.util.ValidadorAcademia;

public class AcademiaService {

    private final List<RecursoAcademico> recursos = new ArrayList<>();

    public void registrarCursoVideo(String nombre, double precioBase, String nivel, int duracionHoras,
            String urlDescarga, String formatoVideo) throws AcademiaException {
        CursoVideo curso = construirCursoVideo(nombre, precioBase, nivel, duracionHoras, urlDescarga, formatoVideo);
        validarDuplicado(TipoRecurso.VIDEO, curso.getNombre(), -1);
        validarPrecioFinal(curso);
        recursos.add(curso);
        System.out.println("Curso en video registrado correctamente.");
    }

    public void registrarCursoPdf(String nombre, double precioBase, String nivel, int numeroPaginas,
            String urlDescarga, String formatoDocumento) throws AcademiaException {
        CursoPdf curso = construirCursoPdf(nombre, precioBase, nivel, numeroPaginas, urlDescarga, formatoDocumento);
        validarDuplicado(TipoRecurso.PDF, curso.getNombre(), -1);
        validarPrecioFinal(curso);
        recursos.add(curso);
        System.out.println("Curso PDF registrado correctamente.");
    }

    public void registrarMentoria(String nombre, double precioBase, String nivel, String fechaTexto, String mentor)
            throws AcademiaException {
        Mentoria mentoria = construirMentoria(nombre, precioBase, nivel, fechaTexto, mentor);
        validarDuplicado(TipoRecurso.MENTORIA, mentoria.getNombre(), -1);
        validarPrecioFinal(mentoria);
        recursos.add(mentoria);
        System.out.println("MentorÃ­a registrada correctamente.");
    }

    public void modificarCursoVideo(String nombreActual, String nuevoNombre, double nuevoPrecio,
            String nuevoNivel, int nuevaDuracion, String nuevaUrl, String nuevoFormato) throws AcademiaException {
        int indice = buscarIndice(TipoRecurso.VIDEO, nombreActual);
        CursoVideo curso = construirCursoVideo(nuevoNombre, nuevoPrecio, nuevoNivel, nuevaDuracion, nuevaUrl, nuevoFormato);
        validarDuplicado(TipoRecurso.VIDEO, curso.getNombre(), indice);
        validarPrecioFinal(curso);
        recursos.set(indice, curso);
        System.out.println("Curso en video modificado correctamente.");
    }

    public void modificarCursoPdf(String nombreActual, String nuevoNombre, double nuevoPrecio,
            String nuevoNivel, int nuevasPaginas, String nuevaUrl, String nuevoFormato) throws AcademiaException {
        int indice = buscarIndice(TipoRecurso.PDF, nombreActual);
        CursoPdf curso = construirCursoPdf(nuevoNombre, nuevoPrecio, nuevoNivel, nuevasPaginas, nuevaUrl, nuevoFormato);
        validarDuplicado(TipoRecurso.PDF, curso.getNombre(), indice);
        validarPrecioFinal(curso);
        recursos.set(indice, curso);
        System.out.println("Curso PDF modificado correctamente.");
    }

    public void modificarMentoria(String nombreActual, String nuevoNombre, double nuevoPrecio,
            String nuevoNivel, String nuevaFechaTexto, String nuevoMentor) throws AcademiaException {
        int indice = buscarIndice(TipoRecurso.MENTORIA, nombreActual);
        Mentoria mentoria = construirMentoria(nuevoNombre, nuevoPrecio, nuevoNivel, nuevaFechaTexto, nuevoMentor);
        validarDuplicado(TipoRecurso.MENTORIA, mentoria.getNombre(), indice);
        validarPrecioFinal(mentoria);
        recursos.set(indice, mentoria);
        System.out.println("MentorÃ­a modificada correctamente.");
    }

    public void mostrarCatalogo() {
        if (recursos.isEmpty()) {
            System.out.println("No hay recursos registrados.");
            return;
        }

        System.out.println("\n===== CATÃLOGO =====");

        recursos.forEach(recurso -> {
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
        });
    }

    public Optional<RecursoAcademico> buscarPorTipoYNombre(String tipo, String nombre) throws RecursoInvalidoException {
        TipoRecurso tipoRecurso = TipoRecurso.desdeTexto(tipo);
        String nombreNormalizado = ValidadorAcademia.validarNombre(nombre);

        return recursos.stream()
                .filter(recurso -> recurso.obtenerTipo() == tipoRecurso
                        && recurso.getNombre().equalsIgnoreCase(nombreNormalizado))
                .findFirst();
    }

    public void mostrarResumenPorNivel() {
        Map<NivelRecurso, Long> contadorPorNivel = recursos.stream()
                .collect(Collectors.groupingBy(RecursoAcademico::getNivel, Collectors.counting()));

        System.out.println("===== RESUMEN POR NIVEL =====");
        contadorPorNivel.forEach((nivel, total) -> System.out.println(nivel.name() + ": " + total));
    }

    public void ejecutarEjemplosModulo5() {
        System.out.println("============ Predicate ============");
        Predicate<RecursoAcademico> recursoCaro = recurso -> recurso.getPrecioBase() > 1000;
        recursos.forEach(recurso -> System.out.println(recurso.getNombre() + " caro: " + recursoCaro.test(recurso)));

        System.out.println("============ Function ============");
        Function<RecursoAcademico, String> obtenerNombre = RecursoAcademico::getNombre;
        recursos.forEach(recurso -> System.out.println("Nombre: " + obtenerNombre.apply(recurso)));

        System.out.println("============ Consumer ============");
        Consumer<RecursoAcademico> mostrar = RecursoAcademico::mostrarDetalle;
        recursos.forEach(mostrar);

        System.out.println("============ Supplier ============");
        Supplier<LocalDate> fechaActual = LocalDate::now;
        System.out.println("Fecha generada: " + fechaActual.get());
    }

    private CursoVideo construirCursoVideo(String nombre, double precioBase, String nivel, int duracionHoras,
            String urlDescarga, String formatoVideo) throws RecursoInvalidoException {
        String nombreValidado = ValidadorAcademia.validarNombre(nombre);
        double precioValidado = ValidadorAcademia.validarPrecio(precioBase);
        NivelRecurso nivelValidado = ValidadorAcademia.validarNivel(nivel);
        int duracionValidada = ValidadorAcademia.validarDuracion(duracionHoras);
        String urlValidada = ValidadorAcademia.validarUrl(urlDescarga);
        FormatoVideo formatoValidado = FormatoVideo.desdeTexto(formatoVideo);

        return new CursoVideo(nombreValidado, precioValidado, nivelValidado, duracionValidada, urlValidada, formatoValidado);
    }

    private CursoPdf construirCursoPdf(String nombre, double precioBase, String nivel, int numeroPaginas,
            String urlDescarga, String formatoDocumento) throws RecursoInvalidoException {
        String nombreValidado = ValidadorAcademia.validarNombre(nombre);
        double precioValidado = ValidadorAcademia.validarPrecio(precioBase);
        NivelRecurso nivelValidado = ValidadorAcademia.validarNivel(nivel);
        int paginasValidadas = ValidadorAcademia.validarPaginas(numeroPaginas);
        String urlValidada = ValidadorAcademia.validarUrl(urlDescarga);
        FormatoDocumento formatoValidado = FormatoDocumento.desdeTexto(formatoDocumento);

        return new CursoPdf(nombreValidado, precioValidado, nivelValidado, paginasValidadas, urlValidada, formatoValidado);
    }

    private Mentoria construirMentoria(String nombre, double precioBase, String nivel, String fechaTexto, String mentor)
            throws RecursoInvalidoException {
        String nombreValidado = ValidadorAcademia.validarNombre(nombre);
        double precioValidado = ValidadorAcademia.validarPrecio(precioBase);
        NivelRecurso nivelValidado = ValidadorAcademia.validarNivel(nivel);
        LocalDate fechaValida = ValidadorAcademia.validarFechaFutura(fechaTexto);
        String mentorValidado = ValidadorAcademia.validarMentor(mentor);

        return new Mentoria(nombreValidado, precioValidado, nivelValidado, fechaValida, mentorValidado);
    }

    private void validarDuplicado(TipoRecurso tipo, String nombre, int indiceIgnorado) throws RecursoDuplicadoException {
        boolean duplicado = IntStream.range(0, recursos.size())
                .filter(indice -> indice != indiceIgnorado)
                .mapToObj(recursos::get)
                .anyMatch(recurso -> recurso.obtenerTipo() == tipo
                        && recurso.getNombre().equalsIgnoreCase(nombre));

        if (duplicado) {
            throw new RecursoDuplicadoException("Ya existe un recurso de tipo " + tipo.name() + " con el nombre: " + nombre);
        }
    }

    private void validarPrecioFinal(RecursoAcademico recurso) throws RecursoInvalidoException {
        if (recurso.calcularPrecioFinal() < recurso.getPrecioBase()) {
            throw new RecursoInvalidoException("El precio final no puede ser menor al precio base.");
        }
    }

    private int buscarIndice(TipoRecurso tipo, String nombre) throws RecursoNoEncontradoException, RecursoInvalidoException {
        String nombreNormalizado = ValidadorAcademia.validarNombre(nombre);

        return IntStream.range(0, recursos.size())
                .filter(i -> recursos.get(i).obtenerTipo() == tipo
                        && recursos.get(i).getNombre().equalsIgnoreCase(nombreNormalizado))
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontrÃ³ un recurso de tipo " + tipo.name() + " con el nombre: " + nombreNormalizado));
    }
}

