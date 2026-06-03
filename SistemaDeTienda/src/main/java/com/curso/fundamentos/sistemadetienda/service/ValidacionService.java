package com.curso.fundamentos.sistemadetienda.service;

import com.curso.fundamentos.sistemadetienda.exception.DatoInvalidoException;
import com.curso.fundamentos.sistemadetienda.exception.ProductoDuplicadoException;
import com.curso.fundamentos.sistemadetienda.model.ItemTienda;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Centraliza las validaciones de negocio del sistema.
 */
public final class ValidacionService {

    private static final int LONGITUD_MIN_NOMBRE = 3;
    private static final int LONGITUD_MAX_NOMBRE = 50;
    private static final double PRECIO_MAXIMO = 100000.0;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final Map<String, String> FORMATOS_MULTIMEDIA = Map.of(
            "MP4", "MP4",
            "MP3", "MP3",
            "GIF", "GIF",
            "JPG", "JPG");
    private static final Map<String, String> SISTEMAS_OPERATIVOS = Map.of(
            "WINDOWS", "Windows",
            "MAC", "Mac",
            "LINUX", "Linux",
            "MULTIPLATAFORMA", "Multiplataforma");

    private ValidacionService() {
    }

    /**
     * Valida y normaliza el nombre de un elemento.
     *
     * @param nombre valor capturado desde la interfaz
     * @return nombre limpio para persistir en memoria
     * @throws DatoInvalidoException cuando el nombre no cumple las reglas
     */
    public static String validarNombre(String nombre) throws DatoInvalidoException {
        if (nombre == null) {
            throw new DatoInvalidoException("El nombre es obligatorio.");
        }

        String nombreLimpio = nombre.trim();
        if (nombreLimpio.isEmpty()) {
            throw new DatoInvalidoException("El nombre no puede estar vacio ni contener solo espacios.");
        }
        if (nombreLimpio.length() < LONGITUD_MIN_NOMBRE || nombreLimpio.length() > LONGITUD_MAX_NOMBRE) {
            throw new DatoInvalidoException("El nombre debe contener entre 3 y 50 caracteres.");
        }
        return nombreLimpio;
    }

    /**
     * Valida el precio base de un elemento.
     *
     * @param precioBase valor capturado desde la interfaz
     * @return precio valido
     * @throws DatoInvalidoException cuando el precio no cumple las reglas
     */
    public static double validarPrecioBase(double precioBase) throws DatoInvalidoException {
        if (precioBase <= 0) {
            throw new DatoInvalidoException("El precio base debe ser mayor a cero.");
        }
        if (precioBase > PRECIO_MAXIMO) {
            throw new DatoInvalidoException("El precio base no puede exceder 100000.");
        }
        return precioBase;
    }

    /**
     * Valida el peso de un producto fisico.
     *
     * @param peso valor capturado desde la interfaz
     * @return peso valido
     * @throws DatoInvalidoException cuando el peso no cumple las reglas
     */
    public static double validarPeso(double peso) throws DatoInvalidoException {
        if (peso <= 0) {
            throw new DatoInvalidoException("El peso debe ser mayor a cero.");
        }
        return peso;
    }

    /**
     * Valida y normaliza el link de descarga de un producto digital.
     *
     * @param link valor capturado desde la interfaz
     * @return link limpio para persistir en memoria
     * @throws DatoInvalidoException cuando el link no cumple las reglas
     */
    public static String validarLink(String link) throws DatoInvalidoException {
        if (link == null) {
            throw new DatoInvalidoException("El link es obligatorio.");
        }

        String linkLimpio = link.trim();
        if (linkLimpio.isEmpty()) {
            throw new DatoInvalidoException("El link no puede estar vacio ni contener solo espacios.");
        }
        if (!linkLimpio.startsWith("http://") && !linkLimpio.startsWith("https://")) {
            throw new DatoInvalidoException("El link debe iniciar con http:// o https://.");
        }

        try {
            new URI(linkLimpio);
        } catch (URISyntaxException exception) {
            throw new DatoInvalidoException("El link contiene caracteres invalidos.");
        }

        return linkLimpio;
    }

    /**
     * Valida el formato de un producto multimedia.
     *
     * @param formato valor capturado desde la interfaz
     * @return formato canonico
     * @throws DatoInvalidoException cuando el formato no es valido
     */
    public static String validarFormatoMultimedia(String formato) throws DatoInvalidoException {
        String formatoNormalizado = validarTextoObligatorio(formato, "El formato es obligatorio.").toUpperCase(Locale.ROOT);
        String formatoCanonico = FORMATOS_MULTIMEDIA.get(formatoNormalizado);
        if (formatoCanonico == null) {
            throw new DatoInvalidoException("Formato invalido. Use MP4, MP3, GIF o JPG.");
        }
        return formatoCanonico;
    }

    /**
     * Valida el sistema operativo de un producto software.
     *
     * @param sistemaOperativo valor capturado desde la interfaz
     * @return sistema operativo canonico
     * @throws DatoInvalidoException cuando el sistema operativo no es valido
     */
    public static String validarSistemaOperativo(String sistemaOperativo) throws DatoInvalidoException {
        String soNormalizado = validarTextoObligatorio(sistemaOperativo, "El sistema operativo es obligatorio.")
                .toUpperCase(Locale.ROOT);
        String sistemaOperativoCanonico = SISTEMAS_OPERATIVOS.get(soNormalizado);
        if (sistemaOperativoCanonico == null) {
            throw new DatoInvalidoException("Sistema operativo invalido. Use Windows, Mac, Linux o Multiplataforma.");
        }
        return sistemaOperativoCanonico;
    }

    /**
     * Valida la fecha de un servicio y exige que sea futura.
     *
     * @param fechaTexto fecha capturada como texto
     * @return fecha valida en formato LocalDate
     * @throws DatoInvalidoException cuando la fecha no cumple las reglas
     */
    public static LocalDate validarFechaServicio(String fechaTexto) throws DatoInvalidoException {
        String fechaLimpia = validarTextoObligatorio(fechaTexto, "La fecha del servicio es obligatoria.");

        try {
            LocalDate fecha = LocalDate.parse(fechaLimpia, FORMATO_FECHA);
            if (!fecha.isAfter(LocalDate.now())) {
                throw new DatoInvalidoException("La fecha del servicio debe ser posterior a la fecha actual.");
            }
            return fecha;
        } catch (DateTimeParseException exception) {
            throw new DatoInvalidoException("La fecha debe tener formato dd/MM/yyyy.");
        }
    }

    /**
     * Verifica que no exista otro elemento del mismo tipo con el mismo nombre.
     *
     * @param items coleccion actual del sistema
     * @param tipo tipo concreto a revisar
     * @param nombre nombre que se desea registrar o modificar
     * @param actual elemento actual que debe ignorarse en una modificacion
     * @throws ProductoDuplicadoException cuando existe otro registro igual
     */
    public static void validarNombreUnico(List<ItemTienda> items, Class<? extends ItemTienda> tipo, String nombre,
            ItemTienda actual) throws ProductoDuplicadoException {
        String nombreNormalizado = normalizar(nombre);
        for (ItemTienda item : items) {
            if (!tipo.isInstance(item) || item == actual) {
                continue;
            }
            if (normalizar(item.getNombre()).equals(nombreNormalizado)) {
                throw new ProductoDuplicadoException("Ya existe un registro de tipo " + item.getTipo()
                        + " con el nombre indicado.");
            }
        }
    }

    /**
     * Normaliza texto obligatorio para reutilizar reglas comunes.
     *
     * @param valor texto a validar
     * @param mensaje mensaje de error cuando el valor es invalido
     * @return texto limpio
     * @throws DatoInvalidoException cuando el valor es nulo o vacio
     */
    public static String validarTextoObligatorio(String valor, String mensaje) throws DatoInvalidoException {
        if (valor == null) {
            throw new DatoInvalidoException(mensaje);
        }
        String valorLimpio = valor.trim();
        if (valorLimpio.isEmpty()) {
            throw new DatoInvalidoException(mensaje);
        }
        return valorLimpio;
    }

    /**
     * Convierte un texto a una forma comparable estable.
     *
     * @param valor texto de entrada
     * @return texto normalizado para comparaciones
     */
    public static String normalizar(String valor) {
        return valor.trim().toUpperCase(Locale.ROOT);
    }
}