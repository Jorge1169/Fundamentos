package com.curso.refactor;

public class GuiaRefactorizacion {

    public static void mostrarInstrucciones() {
        System.out.println("Objetivo: refactorizar el proyecto aplicando mÃ³dulos 1 al 5.");
        System.out.println("Estructura por paquetes: app, service, model, model.enums, model.interfaces, exception, functional y util.");
        System.out.println("1. Extraer validaciones repetidas.");
        System.out.println("2. Usar enums en lugar de Strings para nivel, tipo y formato.");
        System.out.println("3. Usar Optional en bÃºsquedas.");
        System.out.println("4. Usar streams para bÃºsqueda, duplicados y reportes.");
        System.out.println("5. Centralizar try-catch del Main con OperacionAcademia.");
        System.out.println("6. Mantener herencia e interfaces.");
        System.out.println("7. Evitar mÃ©todos largos.");
        System.out.println("8. Crear clases o records para datos validados si ayuda.");
    }
}

