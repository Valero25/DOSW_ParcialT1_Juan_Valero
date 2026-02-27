# Especificación de Requerimientos Funcionales

---

## Registrar Solicitud de Cuenta Digital

### Descripción General

El sistema debe permitir construir y registrar una solicitud de apertura de cuenta digital con los datos del cliente. La solicitud se construye usando el patrón Builder, garantizando que todos los campos obligatorios estén presentes antes de ser enviada al sistema central. Una vez creada, queda almacenada con estado PENDIENTE.

### Actores

Cliente: persona que desea abrir una cuenta digital.

Sistema Central ECI Secure: recibe y almacena la solicitud construida.

### Precondiciones

El cliente debe tener acceso a la aplicación.

El sistema debe estar disponible y operativo.

### Flujo Principal

1. El cliente ingresa sus datos: nombre completo, documento de identidad, edad, teléfono, país y tipo de cuenta.
2. El cliente puede ingresar opcionalmente su correo electrónico.
3. El sistema verifica que todos los campos obligatorios estén completos.
4. El sistema construye el objeto Solicitud mediante el patrón Builder a través de SolicitudBuilder.
5. La solicitud queda registrada en el sistema con estado PENDIENTE.
6. El sistema confirma que la solicitud fue recibida y está lista para ser procesada.

### Flujos Alternos

Si algún campo obligatorio está vacío, el sistema no permite construir la solicitud.

Si el correo no es ingresado, el sistema construye la solicitud sin ese campo usando el constructor correspondiente de la clase Solicitud.

### Postcondiciones

La solicitud queda registrada en el sistema con estado PENDIENTE.

El sistema queda listo para iniciar el proceso de validación sobre esa solicitud.

### Requerimientos Especiales

La construcción de la solicitud debe realizarse mediante el patrón Builder (clase SolicitudBuilder) para garantizar la integridad del objeto antes de procesarse.

El campo correo es opcional y puede omitirse sin afectar el flujo normal.

### Patrón de Diseño Asociado

Patrón: Builder (Creacional).

Clase involucrada: SolicitudBuilder.

Justificación: permite construir el objeto Solicitud con múltiples atributos de forma controlada y legible, manejando el campo opcional correo sin necesidad de constructores sobrecargados en la lógica del cliente.

---

## Validar Solicitud mediante Cadena de Validaciones

### Descripción General

El sistema debe ejecutar una serie de validaciones en orden estricto sobre cada solicitud con estado PENDIENTE. Si alguna validación falla, el proceso se detiene inmediatamente y se retorna el motivo del rechazo. Si todas pasan, la solicitud queda en estado APROBADO con un número de solicitud asignado. El sistema usa el patrón Chain of Responsibility implementado en SistemaGestionSolicitudes.

### Actores

Sistema Central ECI Secure: ejecuta las validaciones de forma automática al recibir una solicitud.

### Precondiciones

La solicitud debe haber sido construida correctamente y estar en estado PENDIENTE.

La cadena de validadores debe estar ensamblada en el orden correcto.

### Flujo Principal

1. El sistema recibe una solicitud con estado PENDIENTE.
2. Se ejecuta la cadena de validaciones en el siguiente orden:
   - ValidadorDocumento: verifica que el documento sea numérico y tenga entre 8 y 10 dígitos.
   - ValidadorEdad: verifica mayoría de edad (mínimo 18 años para LATAM, 21 para Estados Unidos).
   - ValidadorDuplicidad: verifica que no exista otra solicitud aprobada o rechazada con el mismo documento e información.
   - ValidadorTelefono: verifica que el teléfono tenga exactamente 10 dígitos numéricos.
   - ValidadorPais: verifica que el país esté dentro de los permitidos (Colombia, Chile, Perú, Brasil, Estados Unidos).
   - ValidadorTipoCuenta: verifica que Estados Unidos no solicite cuenta corriente.
3. Si todas las validaciones pasan, la solicitud cambia a estado APROBADO y se le asigna un número de solicitud.
4. El sistema imprime el resultado con los datos del titular, tipo de cuenta y país.

### Flujos Alternos

Si cualquier validación falla, el proceso se detiene en ese punto y la solicitud cambia a estado RECHAZADO con el motivo correspondiente.

No se ejecutan las validaciones siguientes una vez que una falla.

### Postcondiciones

La solicitud queda con estado APROBADO o RECHAZADO.

Si fue aprobada, se le asigna un número de solicitud único con formato de 4 dígitos (ej. 0001).

Si fue rechazada, queda registrado el motivo del rechazo.

### Requerimientos Especiales

Debe ser posible agregar nuevas validaciones sin modificar el método procesarSolicitud().

El orden de las validaciones debe respetarse estrictamente.

El tiempo de procesamiento no debe superar 2 segundos para el 95% de las peticiones.

### Patrón de Diseño Asociado

Patrón: Chain of Responsibility (Comportamiento).

Clases involucradas: ValidadorCadena (abstracta), ValidadorDocumento, ValidadorEdad, ValidadorDuplicidad, ValidadorTelefono, ValidadorPais, ValidadorTipoCuenta.

Justificación: cada validación es un eslabón independiente que decide si corta la cadena retornando false o delega al siguiente. Permite extender el sistema con nuevas validaciones agregando una nueva clase y enlazándola en construirCadenaValidadores(), sin tocar el flujo principal.
