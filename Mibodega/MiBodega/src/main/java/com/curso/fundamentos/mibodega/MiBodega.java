/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.curso.fundamentos.mibodega;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author ADMIN
 */

public class MiBodega {

    private static final ArrayList<Producto> inventario = new ArrayList<>();
    // Set: guarda una clave unica por producto para evitar registros duplicados.
    private static final Set<String> clavesInventario = new HashSet<>();
    // Set especifico para no repetir enlaces de productos digitales.
    private static final Set<String> linksDigitales = new HashSet<>();
    private static final Scanner scanner = new Scanner(System.in);

    @FunctionalInterface
    private interface AccionRegistro {
        void ejecutar();
    }
    
    public static void main(String[] args) {
        int opcion;
        
        do {
            mostrarMenu();
            opcion = leerOpcion();
            
            switch (opcion) {
                case 1 -> registrarProductoFisico();
                case 2 -> registrarProductoDigital();
                case 3 -> registrarServicio();
                case 4 -> listarProductos();
                case 5 -> calcularTotal();
                case 6 -> System.out.println("¡Gracias por usar MiBodega! ¡Hasta pronto!");
                default -> System.out.println("Opción inválida. Por favor, elija una opción del 1 al 6.");
            }
            
            if (opcion != 6) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
            
        } while (opcion != 6);
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n===  MiBodega - Sistema de Gestión ===");
        System.out.println("1. Registrar producto físico");
        System.out.println("2. Registrar producto digital");
        System.out.println("3. Registrar servicio");
        System.out.println("4. Listar productos");
        System.out.println("5. Calcular total");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void registrarProductoFisico() {
        ejecutarRegistro("Registrar producto físico", () -> {
            String nombre = leerTexto("Nombre: ");
            double precio = leerDouble("Precio base: $");
            double peso = leerDouble("Peso (kg): ");

            String clave = construirClaveProductoFisico(nombre, peso);
            validarClaveUnica(clave, "Ya existe un producto físico con el mismo nombre y peso");

            ProductoFisico producto = new ProductoFisico(nombre, precio, peso);
            inventario.add(producto);
            clavesInventario.add(clave);

            System.out.println("Producto físico registrado exitosamente!");
        });
    }
    
    private static void registrarProductoDigital() {
        ejecutarRegistro("Registrar producto digital", () -> {
            String nombre = leerTexto("Nombre: ");
            double precio = leerDouble("Precio base: $");
            String link = leerTexto("Link de descarga: ");
            String opcionTipo = leerTexto("Tipo de producto digital (1 = Reproducible, 2 = Instalable): ").trim();

            // Aqui usamos interfaces creando la clase concreta segun el tipo elegido.
            ProductoDigital producto = crearProductoDigital(nombre, precio, link, opcionTipo);

            String linkNormalizado = normalizar(link);
            validarLinkDigitalUnico(linkNormalizado);

            String clave = construirClaveProductoDigital(nombre, producto.getTipoProducto(), link);
            validarClaveUnica(clave, "Ya existe un producto digital con el mismo nombre, tipo y link");

            inventario.add(producto);
            clavesInventario.add(clave);
            linksDigitales.add(linkNormalizado);

            System.out.println("Producto digital registrado exitosamente!");
        });
    }
    
    private static void registrarServicio() {
        ejecutarRegistro("Registrar servicio", () -> {
            String nombre = leerTexto("Nombre del servicio: ");
            double precio = leerDouble("Precio base: $");
            String fecha = leerTexto("Fecha de servicio (YYYY-MM-DD): ");

            String clave = construirClaveServicio(nombre, fecha);
            validarClaveUnica(clave, "Ya existe un servicio con el mismo nombre y fecha");

            Servicio servicio = new Servicio(nombre, precio, fecha);
            inventario.add(servicio);
            clavesInventario.add(clave);

            System.out.println("Servicio registrado exitosamente!");
        });
    }
    
    private static void listarProductos() {
        System.out.println("\nLISTADO DE PRODUCTOS Y SERVICIOS");
        System.out.println("==================================");
        
        if (inventario.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        
        Map<String, Integer> cantidadPorTipo = new LinkedHashMap<>();

        // USO DEL ENCAPSULAMIENTO: Accedemos a los datos mediante getters
        for (Producto p : inventario) {
            p.mostrarInfo();  // Internamente usa getters

            incrementarCantidad(cantidadPorTipo, p.getClass().getSimpleName());
        }

        imprimirResumenCantidad(cantidadPorTipo);
        
        System.out.println("Total de productos registrados: " + inventario.size());
    }
    
    private static void calcularTotal() {
        System.out.println("\nCÁLCULO TOTAL DEL INVENTARIO");
        System.out.println("================================");
        
        if (inventario.isEmpty()) {
            System.out.println("No hay productos registrados. Total: $0.00");
            return;
        }
        
        double total = 0;
        Map<String, Double> totalPorTipo = new LinkedHashMap<>();
        
        // USO DEL ENCAPSULAMIENTO: Accedemos mediante métodos públicos
        for (Producto p : inventario) {
            double precioFinal = p.calcularPrecioFinal();
            total += precioFinal;

            acumularTotal(totalPorTipo, p.getClass().getSimpleName(), precioFinal);

            // Usamos getter para obtener el nombre
            System.out.println(p.getNombre() + " $" + String.format("%.2f", precioFinal));
        }

        imprimirResumenTotales(totalPorTipo);
        
        System.out.println("--------------------------------");
        System.out.println("TOTAL GENERAL: $" + String.format("%.2f", total));
    }

    private static String construirClaveProductoFisico(String nombre, double peso) {
        return "FISICO|" + normalizar(nombre) + "|" + String.format("%.3f", peso);
    }

    private static String construirClaveProductoDigital(String nombre, String tipo, String link) {
        return "DIGITAL|" + normalizar(nombre) + "|" + normalizar(tipo) + "|" + normalizar(link);
    }

    private static String construirClaveServicio(String nombre, String fecha) {
        return "SERVICIO|" + normalizar(nombre) + "|" + normalizar(fecha);
    }

    private static String normalizar(String valor) {
        return valor == null ? "" : valor.trim().toLowerCase();
    }

    private static String leerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static double leerDouble(String prompt) {
        String valor = leerTexto(prompt);
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Dato numérico inválido");
        }
    }

    private static void ejecutarRegistro(String titulo, AccionRegistro accion) {
        System.out.println("\n " + titulo);
        try {
            accion.ejecutar();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Dato inválido. Intente nuevamente.");
        }
    }

    private static ProductoDigital crearProductoDigital(String nombre, double precio, String link, String opcionTipo) {
        if ("1".equals(opcionTipo)) {
            return new ProductoDigitalReproducible(nombre, precio, link);
        }
        if ("2".equals(opcionTipo)) {
            return new ProductoDigitalInstalable(nombre, precio, link);
        }
        throw new IllegalArgumentException("Tipo inválido. Elija 1 o 2.");
    }

    private static void validarClaveUnica(String clave, String mensajeError) {
        if (clavesInventario.contains(clave)) {
            throw new IllegalArgumentException(mensajeError);
        }
    }

    private static void validarLinkDigitalUnico(String linkNormalizado) {
        if (linksDigitales.contains(linkNormalizado)) {
            throw new IllegalArgumentException("Ya existe un producto digital con ese link de descarga");
        }
    }

    private static void incrementarCantidad(Map<String, Integer> cantidades, String tipo) {
        cantidades.put(tipo, cantidades.getOrDefault(tipo, 0) + 1);
    }

    private static void acumularTotal(Map<String, Double> totales, String tipo, double precioFinal) {
        totales.put(tipo, totales.getOrDefault(tipo, 0.0) + precioFinal);
    }

    private static void imprimirResumenCantidad(Map<String, Integer> cantidadPorTipo) {
        System.out.println("\nResumen por tipo (Map):");
        for (Map.Entry<String, Integer> entry : cantidadPorTipo.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void imprimirResumenTotales(Map<String, Double> totalPorTipo) {
        System.out.println("\nResumen de totales por tipo (Map):");
        for (Map.Entry<String, Double> entry : totalPorTipo.entrySet()) {
            System.out.println(entry.getKey() + " -> $" + String.format("%.2f", entry.getValue()));
        }
    }
}
