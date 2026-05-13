/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.curso.fundamentos.java.fundamentos;

import java.util.Scanner;

/**
 *
 * @author Jorge
 */
public class JavaFundamentos {

// Array para guardar las calificaciones
    static double[] calificaciones = new double[5];

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcion; // Variable para las opciones

        do {
            // Menú
            System.out.println("\n=== MENU ===");
            System.out.println("1. Calcular suma");
            System.out.println("2. Convertir texto a mayusculas");
            System.out.println("3. Capturar calificaciones");
            System.out.println("4. Mostrar promedio");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opcion: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            // Switch para elegir acciones
            switch (opcion) {
                
                //Opciones del menu en el case
                case 1:calcularSuma(scanner);break;//sumar

                case 2:convertirMayusculas(scanner);break;//Mayusculas

                case 3:capturarCalificaciones(scanner);break;//Capturar calificaciones

                case 4:mostrarPromedio();break;//Mostrar promedio

                case 0:salirDelPrograma();break;// Salir

                default:System.out.println("Opcion no valida");// Opcion no valida
            }

        } while (opcion != 0);// cuando es igual a 0 termina el Do While
        System.out.println("Saliste del programa");
    }

    // Método para calcular suma
    static void calcularSuma(Scanner scanner) {

        System.out.print("Ingresa el primer numero: ");
        int num1 = scanner.nextInt();

        System.out.print("Ingresa el segundo numero: ");
        int num2 = scanner.nextInt();

        int suma = num1 + num2;

        System.out.println("La suma es: " + suma);
    }

    // Método para convertir texto a mayúsculas
    static void convertirMayusculas(Scanner scanner) {

        System.out.print("Ingresa un texto: ");
        String texto = scanner.nextLine();

        System.out.println("Texto en mayusculas: " + texto.toUpperCase());
    }

    // Método para capturar calificaciones
    static void capturarCalificaciones(Scanner scanner) {

        for (int i = 0; i < calificaciones.length; i++) {

            System.out.print("Ingresa la calificacion " + (i + 1) + ": ");
            calificaciones[i] = scanner.nextDouble();
        }

        System.out.println("Calificaciones guardadas correctamente");
    }

    // Método para mostrar promedio
    static void mostrarPromedio() {

        double suma = 0;

        for (double calificacion : calificaciones) {
            suma += calificacion;
        }

        double promedio = suma / calificaciones.length;

        System.out.println("El promedio es: " + promedio);
    }
    
    // Método para salir del programa
    static void salirDelPrograma() {
        
        System.out.println("Saliendo del programa...");
    }
}
