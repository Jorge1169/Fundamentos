# Implementacion de actualizacion - javaFundamentos2_integrador

## Objetivo
Documentar todo lo que se puede mejorar en el proyecto segun los requerimientos del reto de refactorizacion.

## Alcance revisado
- Paquete principal: `com.curso.refactor`
- Clases foco:
  - `AcademiaService`
  - `JavaFundamentos2`
  - `GuiaRefactorizacion`
  - Enums ya existentes: `NivelRecurso`, `TipoRecurso`, `FormatoVideo`, `FormatoDocumento`

## Diagnostico actual (resumen)
- El proyecto funciona y compila.
- Existen validaciones repetidas entre registrar y modificar.
- Se usan varios Strings magicos para tipo, nivel y formato, aunque ya existen enums.
- `buscarPorTipoYNombre` devuelve `null` en lugar de `Optional`.
- Varias operaciones con listas/maps estan implementadas con bucles imperativos que pueden migrar a Streams.
- El `Main` repite muchos bloques `try-catch`.
- Hay metodos largos en `AcademiaService` y `JavaFundamentos2`.
- Hay duplicacion clara en pares de metodos registrar/modificar por cada tipo de recurso.

## Plan de mejora por requerimiento

### 1) Extraer validaciones repetidas
**Situacion actual**
- Se repiten validaciones de nombre/precio/nivel, formato, URL, mentor, rangos numericos.

**Mejora propuesta**
- Crear una clase utilitaria de validacion, por ejemplo `ValidadorAcademia`.
- Mover reglas comunes a metodos reutilizables:
  - `validarNombre(...)`
  - `validarPrecio(...)`
  - `validarNivel(...)`
  - `validarUrl(...)`
  - `validarDuracion(...)`
  - `validarPaginas(...)`
  - `validarMentor(...)`
  - `validarFechaFutura(...)`

**Beneficio**
- Menos duplicacion.
- Mensajes de error consistentes.
- Menor costo de mantenimiento.

### 2) Reemplazar Strings magicos por enums
**Situacion actual**
- Se comparan Strings como "VIDEO", "PDF", "MENTORIA", "BASICO", "MP4", etc.
- Ya existen enums que no se aprovechan completamente.

**Mejora propuesta**
- Cambiar firmas internas de `AcademiaService` para usar enums:
  - `TipoRecurso`
  - `NivelRecurso`
  - `FormatoVideo`
  - `FormatoDocumento`
- Agregar parseo seguro desde texto de consola a enum (con error controlado):
  - `TipoRecurso.fromTexto(String)`
  - `NivelRecurso.fromTexto(String)`
  - etc.

**Beneficio**
- Evita errores por typos.
- Hace el codigo mas seguro y legible.

### 3) Usar Optional en busquedas
**Situacion actual**
- `buscarPorTipoYNombre(String tipo, String nombre)` retorna `null` cuando no encuentra.

**Mejora propuesta**
- Cambiar retorno a `Optional<RecursoAcademico>`.
- Adaptar uso en `JavaFundamentos2.buscarRecurso()` con `ifPresentOrElse`.
- En busquedas internas de indice, usar busqueda declarativa para evitar `null` o estados ambiguos.

**Beneficio**
- Evita `NullPointerException`.
- Expresa claramente el caso "puede no existir".

### 4) Usar Streams para duplicados, busquedas y reportes
**Situacion actual**
- `validarDuplicado`, `buscarPorTipoYNombre`, `mostrarResumenPorNivel` usan bucles manuales.

**Mejora propuesta**
- Duplicados: usar `IntStream` o stream con filtro por indice ignorado.
- Busqueda: `recursos.stream().filter(...).findFirst()`.
- Reporte por nivel: `Collectors.groupingBy(..., counting())`.
- Si se requiere orden de salida, usar `TreeMap` o comparador.

**Beneficio**
- Codigo mas declarativo y corto.
- Menor probabilidad de errores de control de flujo.

### 5) Centralizar try-catch del Main usando lambdas
**Situacion actual**
- Cada accion en `JavaFundamentos2` tiene su propio `try-catch` repetido.

**Mejora propuesta**
- Reutilizar interfaz funcional `OperacionAcademia` para centralizar manejo de excepciones:
  - `ejecutarOperacionSegura(String contexto, OperacionAcademia op)`
- Cada metodo de menu delega su logica a la operacion segura.

**Beneficio**
- Main mas limpio.
- Manejo uniforme de errores.

### 6) Mantener herencia e interfaces
**Situacion actual**
- Existe buena base: `RecursoAcademico` + interfaces (`Descargable`, `Reproducible`, etc.).

**Mejora propuesta**
- No romper la jerarquia actual.
- Refactorizar solo logica de servicio/entrada sin alterar contratos funcionales publicos innecesariamente.

**Beneficio**
- Se conserva polimorfismo y compatibilidad del ejercicio.

### 7) Evitar metodos largos
**Situacion actual**
- Hay metodos extensos en `AcademiaService` y `JavaFundamentos2`.

**Mejora propuesta**
- Dividir por responsabilidad:
  - Captura de datos.
  - Parseo/normalizacion.
  - Validacion.
  - Construccion de entidad.
  - Persistencia en lista.
- Introducir metodos privados pequenos y con nombres expresivos.

**Beneficio**
- Mayor legibilidad.
- Mejor testeabilidad.

### 8) Eliminar duplicacion en registrar y modificar
**Situacion actual**
- `registrarX` y `modificarX` repiten gran parte de la logica para Video/Pdf/Mentoria.

**Mejora propuesta**
- Crear flujo comun para "validar + construir + guardar/reemplazar".
- Opciones de diseno:
  - Opcion A: metodos plantilla privados por tipo de recurso.
  - Opcion B: estrategia con lambdas para construir recurso y politica de guardado.
- Mantener API publica clara para el menu (metodos explicitos) y delegar internamente al flujo comun.

**Beneficio**
- Reduce codigo duplicado.
- Facilita cambios futuros en reglas de negocio.

## Riesgos y consideraciones
- Cambiar firmas de metodos a enums impacta llamadas desde `Main`.
- Si se migra a `Optional`, hay que actualizar todos los consumidores para no romper compilacion.
- Conviene refactorizar en pasos pequenos para mantener build estable.

## Orden recomendado de implementacion
1. Introducir parseo/uso de enums sin cambiar comportamiento.
2. Extraer validaciones reutilizables.
3. Migrar busquedas a `Optional`.
4. Migrar duplicados/reportes a Streams.
5. Centralizar `try-catch` del `Main` con lambdas.
6. Eliminar duplicacion registrar/modificar.
7. Limpieza final de metodos largos y nombres.

## Criterios de aceptacion
- Compila sin errores con Maven.
- Se mantiene la funcionalidad actual del menu.
- No hay Strings magicos para tipo/nivel/formato en logica core.
- Busquedas publicas no retornan `null`, usan `Optional`.
- Duplicados, busquedas y resumen usan Streams.
- Manejo de excepciones centralizado en `Main`.
- Menor duplicacion medible entre registrar/modificar.

## Siguiente paso sugerido
Aplicar los cambios por fases en `AcademiaService` y `JavaFundamentos2`, validando compilacion tras cada fase.
