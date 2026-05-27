/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.curso.fundamentos.mibodega;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */

public class MiBodega {

    private static final ArrayList<Producto> inventario = new ArrayList<>();
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
        
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Precio base: $");
            double precio = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Link de descarga: ");
            String link = scanner.nextLine();

            System.out.print("Tipo de producto digital (1 = Reproducible, 2 = Instalable): ");
            String opcionTipo = scanner.nextLine();

            String tipoProducto;
            if ("1".equals(opcionTipo.trim())) {
                tipoProducto = "Reproducible";
            } else if ("2".equals(opcionTipo.trim())) {
                tipoProducto = "Instalable";
            } else {
                tipoProducto = opcionTipo;
            }
            
            ProductoDigital producto = new ProductoDigital(nombre, precio, link, tipoProducto);
            inventario.add(producto);
            
            System.out.println("Producto digital registrado exitosamente!");
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
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
        
        // USO DEL ENCAPSULAMIENTO: Accedemos a los datos mediante getters
        for (Producto p : inventario) {
            p.mostrarInfo();  // Internamente usa getters
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
        
        // USO DEL ENCAPSULAMIENTO: Accedemos mediante métodos públicos
        for (Producto p : inventario) {
            double precioFinal = p.calcularPrecioFinal();
            total += precioFinal;
            // Usamos getter para obtener el nombre
            System.out.println(p.getNombre() + " $" + String.format("%.2f", precioFinal));
        }
        
        System.out.println("--------------------------------");
        System.out.println("TOTAL GENERAL: $" + String.format("%.2f", total));
    }
}
