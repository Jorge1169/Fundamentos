# Sistema de Tienda

## Implementado

- Estructura por capas solicitada en model, interfaces, service, exception y app.
- Coleccion polimorfica unica con List<ItemTienda> dentro de TiendaService.
- Registro de Producto Fisico, Multimedia, Software y Servicio.
- Modificacion por tipo y nombre para los cuatro tipos requeridos.
- Calculo automatico de precio final segun las reglas del documento.
- Validaciones centralizadas para nombre, precio base, peso, link, formato, sistema operativo y fecha.
- Excepciones personalizadas para datos invalidos, duplicados y reglas de negocio.
- Menus CLI requeridos para registrar, mostrar y modificar registros.
- JavaDoc en los metodos de la implementacion, excluyendo getters, setters y main.

## Reglas cubiertas

- Nombre obligatorio, sin espacios unicamente y con longitud de 3 a 50.
- Precio base mayor a cero y no mayor a 100000.
- Unicidad por tipo y nombre.
- Producto fisico con peso obligatorio y recargo fijo de 150 si el peso supera 20 kg.
- Producto digital con link obligatorio, valido y con esquema http o https.
- Multimedia con formatos permitidos MP4, MP3, GIF y JPG.
- Software con sistemas operativos permitidos Windows, Mac, Linux y Multiplataforma.
- Servicio con fecha futura y formato dd/MM/yyyy.

## Estado actual

- Avance funcional: completo respecto al documento de requerimientos adjunto.
- Verificacion pendiente: compilacion y prueba manual en consola dentro del entorno local.
- Mejora opcional no requerida: agregar pruebas unitarias automatizadas.