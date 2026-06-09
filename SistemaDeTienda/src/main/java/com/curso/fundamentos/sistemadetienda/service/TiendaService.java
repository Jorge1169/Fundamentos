package com.curso.fundamentos.sistemadetienda.service;

import com.curso.fundamentos.sistemadetienda.exception.DatoInvalidoException;
import com.curso.fundamentos.sistemadetienda.exception.ReglaNegocioException;
import com.curso.fundamentos.sistemadetienda.model.ItemTienda;
import com.curso.fundamentos.sistemadetienda.model.Multimedia;
import com.curso.fundamentos.sistemadetienda.model.ProductoFisico;
import com.curso.fundamentos.sistemadetienda.model.Servicio;
import com.curso.fundamentos.sistemadetienda.model.Software;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona el ciclo de vida de los elementos de la tienda.
 */
public class TiendaService {

    private final List<ItemTienda> items = new ArrayList<>();
    
    private record DatosBase(String nombre, double precioBase) {
    }

    /**
     * Registra un producto fisico aplicando las reglas del dominio.
     *
     * @param nombre nombre del producto
     * @param precioBase precio base del producto
     * @param peso peso del producto en kg
     * @throws ReglaNegocioException cuando algun dato es invalido
     */
    public void registrarProductoFisico(String nombre, double precioBase, double peso) throws ReglaNegocioException {
        DatosBase datosBase = validarDatosBase(nombre, precioBase);
        double pesoValidado = ValidacionService.validarPeso(peso);
        ValidacionService.validarNombreUnico(items, ProductoFisico.class, datosBase.nombre(), null);
        items.add(new ProductoFisico(datosBase.nombre(), datosBase.precioBase(), pesoValidado));
    }

    /**
     * Registra un producto multimedia aplicando las reglas del dominio.
     *
     * @param nombre nombre del producto
     * @param precioBase precio base del producto
     * @param linkDescarga link de descarga del producto
     * @param formato formato multimedia permitido
     * @throws ReglaNegocioException cuando algun dato es invalido
     */
    public void registrarMultimedia(String nombre, double precioBase, String linkDescarga, String formato)
            throws ReglaNegocioException {
        DatosBase datosBase = validarDatosBase(nombre, precioBase);
        String linkValidado = ValidacionService.validarLink(linkDescarga);
        String formatoValidado = ValidacionService.validarFormatoMultimedia(formato);
        ValidacionService.validarNombreUnico(items, Multimedia.class, datosBase.nombre(), null);
        items.add(new Multimedia(datosBase.nombre(), datosBase.precioBase(), linkValidado, formatoValidado));
    }

    /**
     * Registra un producto software aplicando las reglas del dominio.
     *
     * @param nombre nombre del producto
     * @param precioBase precio base del producto
     * @param linkDescarga link de descarga del producto
     * @param sistemaOperativo sistema operativo compatible
     * @throws ReglaNegocioException cuando algun dato es invalido
     */
    public void registrarSoftware(String nombre, double precioBase, String linkDescarga, String sistemaOperativo)
            throws ReglaNegocioException {
        DatosBase datosBase = validarDatosBase(nombre, precioBase);
        String linkValidado = ValidacionService.validarLink(linkDescarga);
        String sistemaOperativoValidado = ValidacionService.validarSistemaOperativo(sistemaOperativo);
        ValidacionService.validarNombreUnico(items, Software.class, datosBase.nombre(), null);
        items.add(new Software(datosBase.nombre(), datosBase.precioBase(), linkValidado, sistemaOperativoValidado));
    }

    /**
     * Registra un servicio aplicando las reglas del dominio.
     *
     * @param nombre nombre del servicio
     * @param precioBase precio base del servicio
     * @param fechaServicio fecha futura del servicio
     * @throws ReglaNegocioException cuando algun dato es invalido
     */
    public void registrarServicio(String nombre, double precioBase, String fechaServicio) throws ReglaNegocioException {
        DatosBase datosBase = validarDatosBase(nombre, precioBase);
        LocalDate fechaValidada = ValidacionService.validarFechaServicio(fechaServicio);
        ValidacionService.validarNombreUnico(items, Servicio.class, datosBase.nombre(), null);
        items.add(new Servicio(datosBase.nombre(), datosBase.precioBase(), fechaValidada));
    }

    /**
     * Devuelve una vista de solo lectura con todos los registros.
     *
     * @return lista inmodificable de elementos registrados
     */
    public List<ItemTienda> obtenerItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Modifica un producto fisico identificado por tipo y nombre.
     *
     * @param nombreActual nombre del registro actual
     * @param nuevoNombre nuevo nombre del producto
     * @param precioBase nuevo precio base
     * @param peso nuevo peso
     * @throws ReglaNegocioException cuando el elemento no existe o algun dato es invalido
     */
    public void modificarProductoFisico(String nombreActual, String nuevoNombre, double precioBase, double peso)
            throws ReglaNegocioException {
        ProductoFisico producto = buscarPorTipoYNombre(ProductoFisico.class, nombreActual);
        DatosBase datosBase = validarDatosBase(nuevoNombre, precioBase);
        double pesoValidado = ValidacionService.validarPeso(peso);
        ValidacionService.validarNombreUnico(items, ProductoFisico.class, datosBase.nombre(), producto);
        producto.setNombre(datosBase.nombre());
        producto.setPrecioBase(datosBase.precioBase());
        producto.setPeso(pesoValidado);
    }

    /**
     * Modifica un producto multimedia identificado por tipo y nombre.
     *
     * @param nombreActual nombre del registro actual
     * @param nuevoNombre nuevo nombre del producto
     * @param precioBase nuevo precio base
     * @param linkDescarga nuevo link de descarga
     * @param formato nuevo formato multimedia
     * @throws ReglaNegocioException cuando el elemento no existe o algun dato es invalido
     */
    public void modificarMultimedia(String nombreActual, String nuevoNombre, double precioBase, String linkDescarga,
            String formato) throws ReglaNegocioException {
        Multimedia multimedia = buscarPorTipoYNombre(Multimedia.class, nombreActual);
        DatosBase datosBase = validarDatosBase(nuevoNombre, precioBase);
        String linkValidado = ValidacionService.validarLink(linkDescarga);
        String formatoValidado = ValidacionService.validarFormatoMultimedia(formato);
        ValidacionService.validarNombreUnico(items, Multimedia.class, datosBase.nombre(), multimedia);
        multimedia.setNombre(datosBase.nombre());
        multimedia.setPrecioBase(datosBase.precioBase());
        multimedia.setLinkDescarga(linkValidado);
        multimedia.setFormato(formatoValidado);
    }

    /**
     * Modifica un producto software identificado por tipo y nombre.
     *
     * @param nombreActual nombre del registro actual
     * @param nuevoNombre nuevo nombre del producto
     * @param precioBase nuevo precio base
     * @param linkDescarga nuevo link de descarga
     * @param sistemaOperativo nuevo sistema operativo compatible
     * @throws ReglaNegocioException cuando el elemento no existe o algun dato es invalido
     */
    public void modificarSoftware(String nombreActual, String nuevoNombre, double precioBase, String linkDescarga,
            String sistemaOperativo) throws ReglaNegocioException {
        Software software = buscarPorTipoYNombre(Software.class, nombreActual);
        DatosBase datosBase = validarDatosBase(nuevoNombre, precioBase);
        String linkValidado = ValidacionService.validarLink(linkDescarga);
        String sistemaOperativoValidado = ValidacionService.validarSistemaOperativo(sistemaOperativo);
        ValidacionService.validarNombreUnico(items, Software.class, datosBase.nombre(), software);
        software.setNombre(datosBase.nombre());
        software.setPrecioBase(datosBase.precioBase());
        software.setLinkDescarga(linkValidado);
        software.setSistemaOperativo(sistemaOperativoValidado);
    }

    /**
     * Modifica un servicio identificado por tipo y nombre.
     *
     * @param nombreActual nombre del registro actual
     * @param nuevoNombre nuevo nombre del servicio
     * @param precioBase nuevo precio base
     * @param fechaServicio nueva fecha del servicio
     * @throws ReglaNegocioException cuando el elemento no existe o algun dato es invalido
     */
    public void modificarServicio(String nombreActual, String nuevoNombre, double precioBase, String fechaServicio)
            throws ReglaNegocioException {
        Servicio servicio = buscarPorTipoYNombre(Servicio.class, nombreActual);
        DatosBase datosBase = validarDatosBase(nuevoNombre, precioBase);
        LocalDate fechaValidada = ValidacionService.validarFechaServicio(fechaServicio);
        ValidacionService.validarNombreUnico(items, Servicio.class, datosBase.nombre(), servicio);
        servicio.setNombre(datosBase.nombre());
        servicio.setPrecioBase(datosBase.precioBase());
        servicio.setFechaServicio(fechaValidada);
    }

    /**
     * Valida los datos compartidos por todos los elementos de la tienda.
     *
     * @param nombre nombre capturado desde la interfaz
     * @param precioBase precio base capturado desde la interfaz
     * @return datos base validados y listos para usar
     * @throws DatoInvalidoException cuando alguno de los valores es invalido
     */
    private DatosBase validarDatosBase(String nombre, double precioBase) throws DatoInvalidoException {
        return new DatosBase(
                ValidacionService.validarNombre(nombre),
                ValidacionService.validarPrecioBase(precioBase));
    }

    /**
     * Busca un elemento por su tipo concreto y nombre.
     *
     * @param tipo clase concreta del elemento
     * @param nombre nombre a localizar
     * @param <T> tipo concreto del elemento
     * @return elemento encontrado y tipado
     * @throws DatoInvalidoException cuando no existe un registro que coincida
     */
    private <T extends ItemTienda> T buscarPorTipoYNombre(Class<T> tipo, String nombre) throws DatoInvalidoException {
        String nombreValidado = ValidacionService.validarNombre(nombre);
        String nombreNormalizado = ValidacionService.normalizar(nombreValidado);
        for (ItemTienda item : items) {
            if (tipo.isInstance(item) && ValidacionService.normalizar(item.getNombre()).equals(nombreNormalizado)) {
                return tipo.cast(item);
            }
        }
        throw new DatoInvalidoException("No existe un registro de tipo " + nombreTipo(tipo)
                + " con el nombre indicado.");
    }

    /**
     * Convierte una clase concreta al nombre visible del tipo.
     *
     * @param tipo clase concreta del elemento
     * @return nombre legible del tipo
     */
    private String nombreTipo(Class<? extends ItemTienda> tipo) {
        if (tipo == ProductoFisico.class) {
            return "Producto Fisico";
        }
        if (tipo == Multimedia.class) {
            return "Multimedia";
        }
        if (tipo == Software.class) {
            return "Software";
        }
        return "Servicio";
    }
}