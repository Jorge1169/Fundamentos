# Cambios de refactorizacion por archivo

## Objetivo
Documentar de forma especifica:
- Que cambio en cada archivo relevante.
- Que responsabilidad tiene ahora cada clase/interfaz/enum.
- Como interactuan actualmente los componentes del sistema.

## Estructura final del proyecto
Paquete base: `com.curso.refactor`

- `app`: entrada de consola y flujo de menu.
- `service`: logica de negocio y orquestacion.
- `model`: entidades y polimorfismo.
- `enums`: tipos de dominio (sin strings magicos).
- `interfaces`: capacidades de recursos.
- `exception`: errores de negocio.
- `functional`: contrato funcional para ejecutar operaciones.
- `util`: validaciones reutilizables.

## Cambios por archivo

### Capa app
- `src/main/java/com/curso/refactor/app/JavaFundamentos2.java`
  - Cambio principal: se movio a `app`.
  - Refactor aplicado:
    - Centralizacion de manejo de errores con `ejecutarOperacionSegura(String, OperacionAcademia)`.
    - Eliminacion de bloques `try-catch` repetidos por accion del menu.
    - Consumo de busqueda con `Optional` (`ifPresentOrElse`).
  - Interacciones:
    - Llama a `AcademiaService` para todos los casos de uso.
    - Usa `OperacionAcademia` para ejecutar lambdas que pueden lanzar `AcademiaException`.

### Capa service
- `src/main/java/com/curso/refactor/service/AcademiaService.java`
  - Cambio principal: se movio a `service`.
  - Refactor aplicado:
    - Eliminacion de strings magicos en reglas core (usa `TipoRecurso`, `NivelRecurso`, `FormatoVideo`, `FormatoDocumento`).
    - Busqueda ahora devuelve `Optional<RecursoAcademico>`.
    - Uso de Streams/IntStream para:
      - Busquedas.
      - Deteccion de duplicados.
      - Resumen por nivel.
    - Extraccion de construccion y validacion por tipo:
      - `construirCursoVideo(...)`
      - `construirCursoPdf(...)`
      - `construirMentoria(...)`
  - Interacciones:
    - Usa `ValidadorAcademia` para reglas transversales.
    - Instancia entidades `CursoVideo`, `CursoPdf`, `Mentoria`.
    - Usa enums para parseo y comparacion segura.
    - Lanza excepciones de negocio (`RecursoInvalidoException`, `RecursoDuplicadoException`, `RecursoNoEncontradoException`).

### Capa util
- `src/main/java/com/curso/refactor/util/ValidadorAcademia.java`
  - Archivo nuevo.
  - Refactor aplicado:
    - Centraliza validaciones de nombre, precio, nivel, URL, duracion, paginas, mentor y fecha futura.
  - Interacciones:
    - Es utilizado por `AcademiaService` en los flujos de registrar/modificar.

### Capa model (entidades)
- `src/main/java/com/curso/refactor/model/RecursoAcademico.java`
  - Cambios:
    - `nivel` paso de `String` a `NivelRecurso`.
    - `obtenerTipo()` paso de `String` a `TipoRecurso`.
  - Interacciones:
    - Superclase abstracta de todos los recursos.

- `src/main/java/com/curso/refactor/model/RecursoDigital.java`
  - Cambios:
    - Constructor ajustado para recibir `NivelRecurso`.
  - Interacciones:
    - Base para recursos descargables (`CursoVideo`, `CursoPdf`).

- `src/main/java/com/curso/refactor/model/CursoVideo.java`
  - Cambios:
    - `formatoVideo` paso de `String` a `FormatoVideo`.
    - `obtenerTipo()` retorna `TipoRecurso.VIDEO`.
  - Interacciones:
    - Implementa `Reproducible` y `Certificable`.

- `src/main/java/com/curso/refactor/model/CursoPdf.java`
  - Cambios:
    - `formatoDocumento` paso de `String` a `FormatoDocumento`.
    - `obtenerTipo()` retorna `TipoRecurso.PDF`.
  - Interacciones:
    - Implementa `Certificable`.

- `src/main/java/com/curso/refactor/model/Mentoria.java`
  - Cambios:
    - Constructor ajustado para `NivelRecurso`.
    - `obtenerTipo()` retorna `TipoRecurso.MENTORIA`.
  - Interacciones:
    - Implementa `Agendable`.

### Capa enums
- `src/main/java/com/curso/refactor/enums/TipoRecurso.java`
- `src/main/java/com/curso/refactor/enums/NivelRecurso.java`
- `src/main/java/com/curso/refactor/enums/FormatoVideo.java`
- `src/main/java/com/curso/refactor/enums/FormatoDocumento.java`
  - Cambio principal: movidos a paquete raiz `enums` (fuera de `model`).
  - Refactor aplicado:
    - Metodo `desdeTexto(...)` con validacion y excepcion de negocio.
  - Interacciones:
    - Consumidos por `AcademiaService`, `ValidadorAcademia` y modelos.

### Capa interfaces
- `src/main/java/com/curso/refactor/interfaces/Agendable.java`
- `src/main/java/com/curso/refactor/interfaces/Certificable.java`
- `src/main/java/com/curso/refactor/interfaces/Descargable.java`
- `src/main/java/com/curso/refactor/interfaces/Reproducible.java`
  - Cambio principal: movidas a paquete raiz `interfaces` (fuera de `model`).
  - Interacciones:
    - Son implementadas por entidades y usadas en `AcademiaService.mostrarCatalogo()` con `instanceof`.

### Capa exception
- `src/main/java/com/curso/refactor/exception/AcademiaException.java`
- `src/main/java/com/curso/refactor/exception/RecursoDuplicadoException.java`
- `src/main/java/com/curso/refactor/exception/RecursoInvalidoException.java`
- `src/main/java/com/curso/refactor/exception/RecursoNoEncontradoException.java`
  - Cambio principal: movidas a paquete `exception`.
  - Interacciones:
    - Lanzadas en service/util y manejadas de forma centralizada en app.

### Capa functional
- `src/main/java/com/curso/refactor/functional/OperacionAcademia.java`
  - Cambio principal: movida a paquete `functional`.
  - Interacciones:
    - `JavaFundamentos2` la usa para envolver operaciones con excepciones chequeadas.

### Documentacion
- `src/main/java/com/curso/refactor/GuiaRefactorizacion.java`
  - Ajustada para reflejar la guia de refactorizacion y estructura modular.

## Como interactuan ahora los archivos

### Flujo principal
1. `JavaFundamentos2` recibe entrada del usuario.
2. `JavaFundamentos2` ejecuta cada accion mediante `ejecutarOperacionSegura(...)`.
3. `AcademiaService` procesa la operacion de negocio.
4. `AcademiaService` valida datos con `ValidadorAcademia` y parsea enums.
5. `AcademiaService` construye/actualiza entidades de `model`.
6. `AcademiaService` usa Streams para buscar, validar duplicados y resumir.
7. Si hay error de negocio, se lanza una excepcion de `exception` y vuelve al `Main` centralizado.

### Relacion entre capas
- `app` depende de `service`, `functional`, `exception`, `model`.
- `service` depende de `model`, `enums`, `interfaces`, `util`, `exception`.
- `util` depende de `enums` y `exception`.
- `model` depende de `enums` e `interfaces`.
- `enums`, `interfaces` y `exception` no dependen de `service` ni de `app`.

## Beneficio tecnico de la refactorizacion
- Menor acoplamiento por responsabilidades claras por paquete.
- Menos duplicacion en registrar/modificar.
- Menor riesgo de errores por texto libre (uso de enums).
- API de busqueda mas segura con `Optional`.
- Servicio mas declarativo y legible con Streams.
- Manejo de errores uniforme y mantenible desde app.
