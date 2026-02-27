# Parcial_DOSW_T1

**Nombre:** Juan David Valero Abril  
**Grupo:**  1
**Profesor:**  Andres Martin Cantor
## 1 Diagrama de contexto
<img width="1182" height="595" alt="imagen_diagrama_contexto" src="https://github.com/user-attachments/assets/196bfa1e-5c91-475e-b3c0-6b289e948718" />


## 2. Patrones de Diseño

### Patrón 1: Builder
- **Tipo:** Creacional
- **Justificación:** La solicitud se construye con múltiples atributos (nombre, documento, edad, teléfono, país, tipo de cuenta) y uno opcional (correo). El patrón Builder permite construir el objeto paso a paso de forma clara, sin necesidad de constructores sobrecargados, y garantiza que todos los campos obligatorios estén presentes antes de enviar la solicitud al sistema central.

### Patrón 2: Chain of Responsibility
- **Tipo:** Comportamiento
- **Justificación:** El sistema debe ejecutar 6 validaciones en orden y detenerse en la primera que falle. Además, el enunciado exige que se puedan agregar nuevas validaciones sin modificar el flujo principal. Este patrón permite encadenar cada validación de forma independiente, cumpliendo exactamente ese requisito.

---

## 3. Requerimientos del Sistema

### Funcionales

- El sistema debe permitir construir una solicitud de cuenta con los datos del cliente y guardarla en estado PENDIENTE. *(Patrón: Builder)*

— El sistema debe ejecutar las validaciones en orden (documento, edad, duplicidad, teléfono, país, tipo de cuenta) y detenerse si alguna falla, retornando el motivo. *(Patrón: Chain of Responsibility)*

— El sistema debe permitir consultar el estado de una solicitud (PENDIENTE, APROBADO, RECHAZADO) dado su documento.

### No Funcionales

— El 95% de las solicitudes deben procesarse en máximo 2 segundos bajo condiciones normales de operación.

— La interfaz debe usar tipografía Poppins, colores azul y amarillo, y ser responsive en cualquier dispositivo.

---

## 4. Casos de Uso e Historias de Usuario

### Historia de Usuario  (Builder)

**Como** cliente de la empresa financiera,  
**quiero** ingresar mis datos personales y tipo de cuenta  
**para que** el sistema registre mi solicitud y la deje en estado PENDIENTE.

**Criterios de aceptación:**
- Todos los campos obligatorios deben estar completos para poder enviar.
- El correo es opcional.
- La solicitud queda guardada con estado PENDIENTE al crearse.

### Historia de Usuario  (Chain of Responsibility)

**Como** sistema central de ECI Secure,  
**quiero** validar cada solicitud siguiendo un orden de reglas  
**para que** solo las solicitudes válidas sean aprobadas y las inválidas sean rechazadas con su motivo.

**Criterios de aceptación:**
- Las validaciones se ejecutan en este orden: documento → edad → duplicidad → teléfono → país → tipo de cuenta.
- Si una validación falla, el proceso se detiene inmediatamente y retorna el motivo.
- Es posible agregar nuevas validaciones sin modificar el motor principal.
<img width="690" height="469" alt="Diagrama_casos_de_usos" src="https://github.com/user-attachments/assets/f73b1e5e-3976-4d63-9469-614424140430" />

