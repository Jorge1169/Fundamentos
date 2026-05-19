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
public class Mibodega {

// Usamos ArrayList (array dinámico) para almacenar los productos
    private static ArrayList<Producto> inventario = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        int opcion;
        
        do {
            mostrarMenu();
            opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    registrarProductoFisico();
                    break;
                case 2:
                    registrarProductoDigital();
                    break;
                case 3:
                    registrarServicio();
                    break;
                case 4:
                    listarProductos();
                    break;
                case 5:
                    calcularTotal();
                    break;
                case 6:
                    System.out.println("¡Gracias por usar MiBodega! ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("❌ Opción inválida. Por favor, elija una opción del 1 al 6.");
                    break;
            }
            
            if (opcion != 6) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine(); // Limpiar buffer
                scanner.nextLine(); // Esperar Enter
            }
            
        } while (opcion != 6);
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n=== 🏪 MiBodega - Sistema de Gestión ===");
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
            return -1; // Retorna -1 si no es un número válido
        }
    }
    
    private static void registrarProductoFisico() {
        System.out.println("\n📦 Registrar producto físico");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Precio base: $");
        double precio = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Peso (kg): ");
        double peso = Double.parseDouble(scanner.nextLine());
        
        ProductoFisico producto = new ProductoFisico(nombre, precio, peso);
        inventario.add(producto);
        
        System.out.println("✅ Producto físico registrado exitosamente!");
    }
    
    private static void registrarProductoDigital() {
        System.out.println("\n💻 Registrar producto digital");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Precio base: $");
        double precio = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Link de descarga: ");
        String link = scanner.nextLine();
        
        ProductoDigital producto = new ProductoDigital(nombre, precio, link);
        inventario.add(producto);
        
        System.out.println("✅ Producto digital registrado exitosamente!");
    }
    
    private static void registrarServicio() {
        System.out.println("\n🔧 Registrar servicio");
        
        System.out.print("Nombre del servicio: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Precio base: $");
        double precio = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Fecha de servicio (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();
        
        Servicio servicio = new Servicio(nombre, precio, fecha);
        inventario.add(servicio);
        
        System.out.println("✅ Servicio registrado exitosamente!");
    }
    
    private static void listarProductos() {
        System.out.println("\n📋 LISTADO DE PRODUCTOS Y SERVICIOS");
        System.out.println("==================================");
        
        if (inventario.isEmpty()) {
            System.out.println("⚠️ No hay productos registrados.");
            return;
        }
        
        // Polimorfismo: llamamos al método mostrarInfo() de cada objeto
        for (Producto p : inventario) {
            p.mostrarInfo();
        }
        
        System.out.println("Total de productos registrados: " + inventario.size());
    }
    
    private static void calcularTotal() {
        System.out.println("\n💰 CÁLCULO TOTAL DEL INVENTARIO");
        System.out.println("================================");
        
        if (inventario.isEmpty()) {
            System.out.println("⚠️ No hay productos registrados. Total: $0.00");
            return;
        }
        
        double total = 0;
        
        // Polimorfismo: cada producto calcula su precio final según su tipo
        for (Producto p : inventario) {
            double precioFinal = p.calcularPrecioFinal();
            total += precioFinal;
            System.out.println(p.getNombre() + " → $" + String.format("%.2f", precioFinal));
        }
        
        System.out.println("--------------------------------");
        System.out.println("💰 TOTAL GENERAL: $" + String.format("%.2f", total));
    }
}
