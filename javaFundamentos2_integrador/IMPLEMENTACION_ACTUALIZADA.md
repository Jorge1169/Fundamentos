# Implementacion aplicada y cambios por archivo

## Resumen
Se aplico la actualizacion del proyecto con estos objetivos cumplidos:
- Reorganizacion por paquetes (carpetas) para separar responsabilidades.
- Eliminacion de strings magicos en logica core con uso de enums.
- Uso de Optional en busquedas.
- Uso de Streams en duplicados, busquedas y reportes.
- Centralizacion de `try-catch` en el Main usando `OperacionAcademia`.
- Extraccion de validaciones repetidas a una utilidad compartida.

## Nueva organizacion de paquetes
Bajo `src/main/java/com/curso/refactor`:
- `app`: punto de entrada y flujo de menu.
- `service`: casos de uso de negocio.
- `model`: entidades del dominio.
- `enums`: enums de tipo/nivel/formato.
- `interfaces`: capacidades del dominio.
- `exception`: jerarquia de excepciones.
- `functional`: interfaces funcionales.
- `util`: utilidades de validacion.

## Cambios aplicados por archivo

### App
- [src/main/java/com/curso/refactor/app/JavaFundamentos2.java](src/main/java/com/curso/refactor/app/JavaFundamentos2.java)
  - Se movio a paquete `app`.
  - Se centralizo el manejo de excepciones con `ejecutarOperacionSegura(String, OperacionAcademia)`.
  - Se reemplazo el patron repetido de `try-catch` por lambdas.
  - Se adapto la busqueda a `Optional` con `ifPresentOrElse`.

### Service
- [src/main/java/com/curso/refactor/service/AcademiaService.java](src/main/java/com/curso/refactor/service/AcademiaService.java)
  - Se movio a paquete `service`.
  - Se reemplazo comparacion por Strings magicos con enums (`TipoRecurso`, `NivelRecurso`, `FormatoVideo`, `FormatoDocumento`).
  - `buscarPorTipoYNombre(...)` ahora retorna `Optional<RecursoAcademico>`.
  - Se migro logica de duplicados y busquedas a Streams/IntStream.
  - Se migro el resumen por nivel a `Collectors.groupingBy(..., counting())`.
  - Se eliminaron validaciones duplicadas mediante metodos constructores reutilizables por tipo.

### Util
- [src/main/java/com/curso/refactor/util/ValidadorAcademia.java](src/main/java/com/curso/refactor/util/ValidadorAcademia.java)
  - Nuevo archivo.
  - Centraliza validaciones de nombre, precio, nivel, URL, duracion, paginas, mentor y fecha futura.
  - Unifica mensajes de error y reduce repeticion en `AcademiaService`.

### Model
- [src/main/java/com/curso/refactor/model/RecursoAcademico.java](src/main/java/com/curso/refactor/model/RecursoAcademico.java)
  - Se movio a paquete `model`.
  - `nivel` paso de `String` a `NivelRecurso`.
  - `obtenerTipo()` paso de `String` a `TipoRecurso`.

- [src/main/java/com/curso/refactor/model/RecursoDigital.java](src/main/java/com/curso/refactor/model/RecursoDigital.java)
  - Se movio a paquete `model`.
  - Ajuste de constructor para recibir `NivelRecurso`.

- [src/main/java/com/curso/refactor/model/CursoVideo.java](src/main/java/com/curso/refactor/model/CursoVideo.java)
  - Se movio a paquete `model`.
  - `formatoVideo` paso de `String` a `FormatoVideo`.
  - `obtenerTipo()` retorna `TipoRecurso.VIDEO`.

- [src/main/java/com/curso/refactor/model/CursoPdf.java](src/main/java/com/curso/refactor/model/CursoPdf.java)
  - Se movio a paquete `model`.
  - `formatoDocumento` paso de `String` a `FormatoDocumento`.
  - `obtenerTipo()` retorna `TipoRecurso.PDF`.

- [src/main/java/com/curso/refactor/model/Mentoria.java](src/main/java/com/curso/refactor/model/Mentoria.java)
  - Se movio a paquete `model`.
  - Ajuste de constructor para `NivelRecurso`.
  - `obtenerTipo()` retorna `TipoRecurso.MENTORIA`.

### Enums
- [src/main/java/com/curso/refactor/enums/TipoRecurso.java](src/main/java/com/curso/refactor/enums/TipoRecurso.java)
- [src/main/java/com/curso/refactor/enums/NivelRecurso.java](src/main/java/com/curso/refactor/enums/NivelRecurso.java)
- [src/main/java/com/curso/refactor/enums/FormatoVideo.java](src/main/java/com/curso/refactor/enums/FormatoVideo.java)
- [src/main/java/com/curso/refactor/enums/FormatoDocumento.java](src/main/java/com/curso/refactor/enums/FormatoDocumento.java)
  - Se movieron a paquete `enums`.
  - Se mantuvo `desdeTexto(...)` con manejo de error y mensaje de negocio (`RecursoInvalidoException`).

### Interfaces
- [src/main/java/com/curso/refactor/interfaces/Agendable.java](src/main/java/com/curso/refactor/interfaces/Agendable.java)
- [src/main/java/com/curso/refactor/interfaces/Certificable.java](src/main/java/com/curso/refactor/interfaces/Certificable.java)
- [src/main/java/com/curso/refactor/interfaces/Descargable.java](src/main/java/com/curso/refactor/interfaces/Descargable.java)
- [src/main/java/com/curso/refactor/interfaces/Reproducible.java](src/main/java/com/curso/refactor/interfaces/Reproducible.java)
  - Se movieron a paquete `interfaces` sin cambiar comportamiento.

### Excepciones
- [src/main/java/com/curso/refactor/exception/AcademiaException.java](src/main/java/com/curso/refactor/exception/AcademiaException.java)
- [src/main/java/com/curso/refactor/exception/RecursoDuplicadoException.java](src/main/java/com/curso/refactor/exception/RecursoDuplicadoException.java)
- [src/main/java/com/curso/refactor/exception/RecursoInvalidoException.java](src/main/java/com/curso/refactor/exception/RecursoInvalidoException.java)
- [src/main/java/com/curso/refactor/exception/RecursoNoEncontradoException.java](src/main/java/com/curso/refactor/exception/RecursoNoEncontradoException.java)
  - Se movieron a paquete `exception`.

### Functional
- [src/main/java/com/curso/refactor/functional/OperacionAcademia.java](src/main/java/com/curso/refactor/functional/OperacionAcademia.java)
  - Se movio a paquete `functional`.
  - Se ajusto import de `AcademiaException`.

### Guia
- [src/main/java/com/curso/refactor/GuiaRefactorizacion.java](src/main/java/com/curso/refactor/GuiaRefactorizacion.java)
  - Se actualizo mensaje para reflejar la estructura por paquetes aplicada.

## Archivos reubicados
Se reubicaron los archivos que estaban en `com/curso/refactor` a sus nuevas carpetas (`app`, `service`, `model`, `enums`, `interfaces`, `exception`, `functional`).

## Validacion tecnica
Se valido compilacion local con:

```powershell
javac -d target\classes (Get-ChildItem -Path src\main\java -Recurse -Filter *.java).FullName
```

Resultado: compilacion exitosa sin errores.

## Estado frente a requerimientos
1. Extraer validaciones repetidas: cumplido con `ValidadorAcademia`.
2. Reemplazar strings magicos por enums: cumplido en logica core.
3. Usar Optional en busquedas: cumplido en servicio y consumo desde app.
4. Usar Streams para duplicados/busquedas/reportes: cumplido.
5. Centralizar try-catch del Main usando lambdas: cumplido.
6. Mantener herencia e interfaces: cumplido.
7. Evitar metodos largos: mejorado con metodos privados por tipo y utilidades.
8. Eliminar codigo duplicado en registrar/modificar: mejorado con flujo comun de construccion/validacion.
