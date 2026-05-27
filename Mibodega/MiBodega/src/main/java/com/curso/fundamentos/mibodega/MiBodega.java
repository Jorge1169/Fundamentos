/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.curso.fundamentos.mibodega;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
        System.out.println("\n Registrar producto físico");
        
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Precio base: $");
            double precio = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Peso (kg): ");
            double peso = Double.parseDouble(scanner.nextLine());

            String clave = construirClaveProductoFisico(nombre, peso);
            if (!clavesInventario.add(clave)) {
                throw new IllegalArgumentException("Ya existe un producto físico con el mismo nombre y peso");
            }
            
            ProductoFisico producto = new ProductoFisico(nombre, precio, peso);
            inventario.add(producto);
            
            System.out.println(" Producto físico registrado exitosamente!");
            
        } catch (IllegalArgumentException e) {
            System.out.println(" Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Error: Dato inválido. Intente nuevamente.");
        }
    }
    
    private static void registrarProductoDigital() {
        System.out.println("\n Registrar producto digital");
        String linkNormalizado = null;
        boolean rollbackLink = false;
        
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Precio base: $");
            double precio = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Link de descarga: ");
            String link = scanner.nextLine();

            linkNormalizado = link.trim().toLowerCase();

            if (!linksDigitales.add(linkNormalizado)) {
                throw new IllegalArgumentException("Ya existe un producto digital con ese link de descarga");
            }
            rollbackLink = true;

            System.out.print("Tipo de producto digital (1 = Reproducible, 2 = Instalable): ");
            String opcionTipo = scanner.nextLine().trim();

            ProductoDigital producto;
            // Aqui usamos interfaces creando la clase concreta segun el tipo elegido.
            if ("1".equals(opcionTipo)) {
                producto = new ProductoDigitalReproducible(nombre, precio, link);
            } else if ("2".equals(opcionTipo)) {
                producto = new ProductoDigitalInstalable(nombre, precio, link);
            } else {
                throw new IllegalArgumentException("Tipo inválido. Elija 1 o 2.");
            }

            String clave = construirClaveProductoDigital(nombre, producto.getTipoProducto(), link);
            if (!clavesInventario.add(clave)) {
                throw new IllegalArgumentException("Ya existe un producto digital con el mismo nombre, tipo y link");
            }
            
            inventario.add(producto);
            rollbackLink = false;
            
            System.out.println("Producto digital registrado exitosamente!");
            
        } catch (IllegalArgumentException e) {
            if (rollbackLink && linkNormalizado != null) {
                linksDigitales.remove(linkNormalizado);
            }
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            if (rollbackLink && linkNormalizado != null) {
                linksDigitales.remove(linkNormalizado);
            }
            System.out.println("Error: Dato inválido. Intente nuevamente.");
        }
    }
    
    private static void registrarServicio() {
        System.out.println("\n Registrar servicio");
        
        try {
            System.out.print("Nombre del servicio: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Precio base: $");
            double precio = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Fecha de servicio (YYYY-MM-DD): ");
            String fecha = scanner.nextLine();

            String clave = construirClaveServicio(nombre, fecha);
            if (!clavesInventario.add(clave)) {
                throw new IllegalArgumentException("Ya existe un servicio con el mismo nombre y fecha");
            }
            
            Servicio servicio = new Servicio(nombre, precio, fecha);
            inventario.add(servicio);
            
            System.out.println("Servicio registrado exitosamente!");
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Dato inválido. Intente nuevamente.");
        }
    }
    
    private static void listarProductos() {
        System.out.println("\nLISTADO DE PRODUCTOS Y SERVICIOS");
        System.out.println("==================================");
        
        if (inventario.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        
        Map<String, Integer> cantidadPorTipo = new HashMap<>();

        // USO DEL ENCAPSULAMIENTO: Accedemos a los datos mediante getters
        for (Producto p : inventario) {
            p.mostrarInfo();  // Internamente usa getters

            String tipo = p.getClass().getSimpleName();
            cantidadPorTipo.put(tipo, cantidadPorTipo.getOrDefault(tipo, 0) + 1);
        }

        System.out.println("\nResumen por tipo (Map):");
        for (Map.Entry<String, Integer> entry : cantidadPorTipo.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
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
        Map<String, Double> totalPorTipo = new HashMap<>();
        
        // USO DEL ENCAPSULAMIENTO: Accedemos mediante métodos públicos
        for (Producto p : inventario) {
            double precioFinal = p.calcularPrecioFinal();
            total += precioFinal;

            String tipo = p.getClass().getSimpleName();
            totalPorTipo.put(tipo, totalPorTipo.getOrDefault(tipo, 0.0) + precioFinal);

            // Usamos getter para obtener el nombre
            System.out.println(p.getNombre() + " $" + String.format("%.2f", precioFinal));
        }

        System.out.println("\nResumen de totales por tipo (Map):");
        for (Map.Entry<String, Double> entry : totalPorTipo.entrySet()) {
            System.out.println(entry.getKey() + " -> $" + String.format("%.2f", entry.getValue()));
        }
        
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
}
