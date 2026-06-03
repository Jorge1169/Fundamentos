package com.curso.fundamentos.sistemadetienda.app;
/**
 * Punto de entrada CLI del sistema de tienda.
 */
import com.curso.fundamentos.sistemadetienda.exception.DatoInvalidoException;
import com.curso.fundamentos.sistemadetienda.exception.ReglaNegocioException;
import com.curso.fundamentos.sistemadetienda.interfaces.Instalable;
import com.curso.fundamentos.sistemadetienda.interfaces.Reproducible;
import com.curso.fundamentos.sistemadetienda.model.ItemTienda;
import com.curso.fundamentos.sistemadetienda.model.Multimedia;
import com.curso.fundamentos.sistemadetienda.model.ProductoDigital;
import com.curso.fundamentos.sistemadetienda.model.ProductoFisico;
import com.curso.fundamentos.sistemadetienda.model.Servicio;
import com.curso.fundamentos.sistemadetienda.model.Software;
import com.curso.fundamentos.sistemadetienda.service.TiendaService;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Punto de entrada CLI del sistema de tienda.
 */
public final class Main {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final TiendaService TIENDA_SERVICE = new TiendaService();

    private Main() {
    }

    public static void main(String[] args) {
        try {
            boolean salir = false;
            while (!salir) {
                mostrarMenuPrincipal();
                int opcion = leerOpcion();
                switch (opcion) {
                    case 1 -> mostrarMenuRegistrar();
                    case 2 -> mostrarRegistros();
                    case 3 -> mostrarMenuModificar();
                    case 4 -> salir = true;
                    default -> mostrarError("Opcion invalida. Seleccione una opcion del 1 al 4.");
                }
            }
        } catch (FinEntradaException exception) {
            System.out.println();
        }
        System.out.println("Sistema finalizado.");
    }

    /**
     * Muestra el menu principal del sistema.
     */
    private static void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("===== SISTEMA DE TIENDA =====");
        System.out.println("1. Registrar");
        System.out.println("2. Mostrar Registros");
        System.out.println("3. Modificar Registro");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    /**
     * Muestra y atiende el menu de registro.
     */
    private static void mostrarMenuRegistrar() {
        boolean regresar = false;
        while (!regresar) {
            System.out.println();
            System.out.println("===== REGISTRAR =====");
            System.out.println("1. Producto Fisico");
            System.out.println("2. Producto Digital");
            System.out.println("3. Servicio");
            System.out.println("4. Regresar");
            System.out.print("Seleccione una opcion: ");

            int opcion = leerOpcion();
            switch (opcion) {
                case 1 -> registrarProductoFisico();
                case 2 -> mostrarMenuProductoDigital();
                case 3 -> registrarServicio();
                case 4 -> regresar = true;
                default -> mostrarError("Opcion invalida. Seleccione una opcion del 1 al 4.");
            }
        }
    }

    /**
     * Muestra y atiende el submenu de productos digitales.
     */
    private static void mostrarMenuProductoDigital() {
        boolean regresar = false;
        while (!regresar) {
            System.out.println();
            System.out.println("===== PRODUCTO DIGITAL =====");
            System.out.println("1. Multimedia");
            System.out.println("2. Software");
            System.out.println("3. Regresar");
            System.out.print("Seleccione una opcion: ");

            int opcion = leerOpcion();
            switch (opcion) {
                case 1 -> registrarMultimedia();
                case 2 -> registrarSoftware();
                case 3 -> regresar = true;
                default -> mostrarError("Opcion invalida. Seleccione una opcion del 1 al 3.");
            }
        }
    }

    /**
     * Muestra y atiende el menu de modificacion.
     */
    private static void mostrarMenuModificar() {
        boolean regresar = false;
        while (!regresar) {
            System.out.println();
            System.out.println("===== MODIFICAR REGISTRO =====");
            System.out.println("1. Producto Fisico");
            System.out.println("2. Multimedia");
            System.out.println("3. Software");
            System.out.println("4. Servicio");
            System.out.println("5. Regresar");
            System.out.print("Seleccione una opcion: ");

            int opcion = leerOpcion();
            switch (opcion) {
                case 1 -> modificarProductoFisico();
                case 2 -> modificarMultimedia();
                case 3 -> modificarSoftware();
                case 4 -> modificarServicio();
                case 5 -> regresar = true;
                default -> mostrarError("Opcion invalida. Seleccione una opcion del 1 al 5.");
            }
        }
    }

    /**
     * Ejecuta el flujo de registro de producto fisico.
     */
    private static void registrarProductoFisico() {
        ejecutarConManejo(() -> {
            TIENDA_SERVICE.registrarProductoFisico(
                    leerTexto("Nombre: "),
                    leerDecimal("Precio base: "),
                    leerDecimal("Peso: "));
            mostrarExito("Producto fisico registrado.");
        });
    }

    /**
     * Ejecuta el flujo de registro de multimedia.
     */
    private static void registrarMultimedia() {
        ejecutarConManejo(() -> {
            TIENDA_SERVICE.registrarMultimedia(
                    leerTexto("Nombre: "),
                    leerDecimal("Precio base: "),
                    leerTexto("Link de descarga: "),
                    leerTexto("Formato (MP4, MP3, GIF, JPG): "));
            mostrarExito("Producto multimedia registrado.");
        });
    }

    /**
     * Ejecuta el flujo de registro de software.
     */
    private static void registrarSoftware() {
        ejecutarConManejo(() -> {
            TIENDA_SERVICE.registrarSoftware(
                    leerTexto("Nombre: "),
                    leerDecimal("Precio base: "),
                    leerTexto("Link de descarga: "),
                    leerTexto("Sistema operativo (Windows, Mac, Linux, Multiplataforma): "));
            mostrarExito("Producto software registrado.");
        });
    }

    /**
     * Ejecuta el flujo de registro de servicio.
     */
    private static void registrarServicio() {
        ejecutarConManejo(() -> {
            TIENDA_SERVICE.registrarServicio(
                    leerTexto("Nombre: "),
                    leerDecimal("Precio base: "),
                    leerTexto("Fecha del servicio (dd/MM/yyyy): "));
            mostrarExito("Servicio registrado.");
        });
    }

    /**
     * Ejecuta el flujo de modificacion de producto fisico.
     */
    private static void modificarProductoFisico() {
        ejecutarConManejo(() -> {
            TIENDA_SERVICE.modificarProductoFisico(
                    leerTexto("Nombre actual: "),
                    leerTexto("Nuevo nombre: "),
                    leerDecimal("Nuevo precio base: "),
                    leerDecimal("Nuevo peso: "));
            mostrarExito("Producto fisico modificado.");
        });
    }

    /**
     * Ejecuta el flujo de modificacion de multimedia.
     */
    private static void modificarMultimedia() {
        ejecutarConManejo(() -> {
            TIENDA_SERVICE.modificarMultimedia(
                    leerTexto("Nombre actual: "),
                    leerTexto("Nuevo nombre: "),
                    leerDecimal("Nuevo precio base: "),
                    leerTexto("Nuevo link de descarga: "),
                    leerTexto("Nuevo formato (MP4, MP3, GIF, JPG): "));
            mostrarExito("Multimedia modificada.");
        });
    }

    /**
     * Ejecuta el flujo de modificacion de software.
     */
    private static void modificarSoftware() {
        ejecutarConManejo(() -> {
            TIENDA_SERVICE.modificarSoftware(
                    leerTexto("Nombre actual: "),
                    leerTexto("Nuevo nombre: "),
                    leerDecimal("Nuevo precio base: "),
                    leerTexto("Nuevo link de descarga: "),
                    leerTexto("Nuevo sistema operativo: "));
            mostrarExito("Software modificado.");
        });
    }

    /**
     * Ejecuta el flujo de modificacion de servicio.
     */
    private static void modificarServicio() {
        ejecutarConManejo(() -> {
            TIENDA_SERVICE.modificarServicio(
                    leerTexto("Nombre actual: "),
                    leerTexto("Nuevo nombre: "),
                    leerDecimal("Nuevo precio base: "),
                    leerTexto("Nueva fecha del servicio (dd/MM/yyyy): "));
            mostrarExito("Servicio modificado.");
        });
    }

    /**
     * Muestra todos los registros almacenados en la coleccion polimorfica.
     */
    private static void mostrarRegistros() {
        List<ItemTienda> items = TIENDA_SERVICE.obtenerItems();
        System.out.println();
        System.out.println("===== REGISTROS =====");
        if (items.isEmpty()) {
            System.out.println("No hay registros cargados.");
            return;
        }

        for (ItemTienda item : items) {
            imprimirRegistro(item);
        }
    }

    /**
     * Imprime un registro con sus datos comunes y especificos.
     *
     * @param item elemento a mostrar
     */
    private static void imprimirRegistro(ItemTienda item) {
        System.out.println("------------------------------");
        System.out.println("Tipo: " + item.getTipo());
        System.out.println("Nombre: " + item.getNombre());
        System.out.println("Precio base: " + formatearMonto(item.getPrecioBase()));
        System.out.println("Precio final: " + formatearMonto(item.calcularPrecioFinal()));

        if (item instanceof ProductoFisico productoFisico) {
            System.out.println("Peso: " + productoFisico.getPeso() + " kg");
        } else if (item instanceof Multimedia multimedia) {
            imprimirDigital(multimedia, multimedia.getFormato(), multimedia instanceof Reproducible);
        } else if (item instanceof Software software) {
            imprimirDigital(software, software.getSistemaOperativo(), software instanceof Instalable);
        } else if (item instanceof Servicio servicio) {
            System.out.println("Fecha del servicio: " + servicio.getFechaServicio().format(FORMATO_FECHA));
        }
    }

    /**
     * Imprime los datos especificos de un producto digital.
     *
     * @param productoDigital producto a mostrar
     * @param detalle formato o sistema operativo segun el tipo
     * @param capacidad indica si implementa la interfaz esperada
     */
    private static void imprimirDigital(ProductoDigital productoDigital, String detalle, boolean capacidad) {
        System.out.println("Link de descarga: " + productoDigital.getLinkDescarga());
        if (productoDigital instanceof Multimedia) {
            System.out.println("Formato: " + detalle);
            System.out.println("Reproducible: " + (capacidad ? "Si" : "No"));
        } else {
            System.out.println("Sistema operativo: " + detalle);
            System.out.println("Instalable: " + (capacidad ? "Si" : "No"));
        }
    }

    /**
     * Ejecuta una accion de menu atrapando errores esperados.
     *
     * @param accion bloque que realiza la operacion solicitada
     */
    private static void ejecutarConManejo(AccionMenu accion) {
        try {
            accion.ejecutar();
        } catch (ReglaNegocioException exception) {
            mostrarError(exception.getMessage());
        }
    }

    /**
     * Lee una opcion numerica simple desde consola.
     *
     * @return opcion ingresada o -1 cuando no es valida
     */
    private static int leerOpcion() {
        if (!SCANNER.hasNextLine()) {
            throw new FinEntradaException();
        }
        try {
            return Integer.parseInt(SCANNER.nextLine().trim());
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    /**
     * Lee un texto desde consola.
     *
     * @param mensaje mensaje a mostrar antes de capturar
     * @return texto capturado
     */
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        if (!SCANNER.hasNextLine()) {
            throw new FinEntradaException();
        }
        return SCANNER.nextLine();
    }

    /**
     * Lee un numero decimal desde consola.
     *
     * @param mensaje mensaje a mostrar antes de capturar
     * @return valor decimal capturado
     * @throws DatoInvalidoException cuando la entrada no es numerica
     */
    private static double leerDecimal(String mensaje) throws DatoInvalidoException {
        String valor = leerTexto(mensaje).replace(',', '.');
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException exception) {
            throw new DatoInvalidoException("Debe ingresar un numero valido.");
        }
    }

    /**
     * Formatea montos monetarios para la salida por consola.
     *
     * @param monto valor numerico a presentar
     * @return cadena con formato monetario simple
     */
    private static String formatearMonto(double monto) {
        return String.format(Locale.US, "$%.2f", monto);
    }

    /**
     * Muestra un mensaje de exito en consola.
     *
     * @param mensaje texto a presentar
     */
    private static void mostrarExito(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Muestra un mensaje de error en consola.
     *
     * @param mensaje texto a presentar
     */
    private static void mostrarError(String mensaje) {
        System.out.println("Error: " + mensaje);
    }

    /**
     * Contrato interno para ejecutar acciones del menu con excepciones de negocio.
     */
    @FunctionalInterface
    private interface AccionMenu {

        /**
         * Ejecuta la accion del menu actual.
         *
         * @throws ReglaNegocioException cuando la accion viola una regla del negocio
         */
        void ejecutar() throws ReglaNegocioException;
    }

    /**
     * Indica que la entrada estandar se agoto y el programa debe finalizar sin error.
     */
    private static final class FinEntradaException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}