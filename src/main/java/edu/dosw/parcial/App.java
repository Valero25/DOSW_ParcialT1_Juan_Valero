package edu.dosw.parcial;

public class App {
    public static void main(String[] args) {
        System.out.println("=== ECI SECURE REQUEST ENGINE ===");
        System.out.println("Sistema de Gestión de Solicitudes de Cuentas Bancarias\n");

        SistemaGestionSolicitudes sistema = new SistemaGestionSolicitudes();

        // Caso 1: Documento inválido (menos de 8 dígitos)
        Solicitud sol1 = new SolicitudBuilder()
            .nombreCompleto("Ana Rodriguez")
            .documento("123456")
            .edad(25)
            .telefono("3001234567")
            .pais("Colombia")
            .tipoCuenta(TipoCuenta.AHORROS)
            .build();
        sistema.procesarSolicitud(sol1);

        // Caso 2: Documento inválido (contiene letras)
        Solicitud sol2 = new SolicitudBuilder()
            .nombreCompleto("Carlos López")
            .documento("12345ABC")
            .edad(30)
            .correo("ana.rodriguez@email.com")
            .telefono("3009876543")
            .pais("Chile")
            .tipoCuenta(TipoCuenta.CORRIENTE)
            .build();
        sistema.procesarSolicitud(sol2);

        // Caso 3: Menor de edad (Latinoamérica)
        Solicitud sol3 = new SolicitudBuilder()
            .nombreCompleto("Laura Martínez")
            .documento("87654321")
            .edad(17)
            .telefono("3105556789")
            .pais("Colombia")
            .tipoCuenta(TipoCuenta.AHORROS)
            .build();
        sistema.procesarSolicitud(sol3);

        // Caso 4: Menor de edad (Estados Unidos)
        Solicitud sol4 = new SolicitudBuilder()
            .nombreCompleto("John Smith")
            .documento("98765432")
            .edad(20)
            .correo("laura.martinez@email.com")
            .telefono("3201234567")
            .pais("Estados Unidos")
            .tipoCuenta(TipoCuenta.AHORROS)
            .build();
        sistema.procesarSolicitud(sol4);

        // Caso 5: Teléfono inválido (no tiene 10 dígitos)
        Solicitud sol5 = new SolicitudBuilder()
            .nombreCompleto("Pedro Sánchez")
            .documento("45678901")
            .edad(28)
            .telefono("300123456")
            .pais("Perú")
            .tipoCuenta(TipoCuenta.AHORROS)
            .build();
        sistema.procesarSolicitud(sol5);

        // Caso 6: País no permitido
        Solicitud sol6 = new SolicitudBuilder()
            .nombreCompleto("María González")
            .documento("56789012")
            .edad(35)
            .telefono("3107654321")
            .pais("Argentina")
            .tipoCuenta(TipoCuenta.CORRIENTE)
            .build();
        sistema.procesarSolicitud(sol6);

        // Caso 7: Tipo de cuenta no permitido en USA
        Solicitud sol7 = new SolicitudBuilder()
            .nombreCompleto("Robert Johnson")
            .documento("67890123")
            .edad(25)
            .correo("robert.johnson@email.com")
            .telefono("3208765432")
            .pais("Estados Unidos")
            .tipoCuenta(TipoCuenta.CORRIENTE)
            .build();
        sistema.procesarSolicitud(sol7);

        // Caso 8: APROBADO - Primer caso exitoso
        Solicitud sol8 = new SolicitudBuilder()
            .nombreCompleto("Mateo Gómez")
            .documento("78901234")
            .edad(29)
            .correo("mateo.gomez@email.com")
            .telefono("3009876543")
            .pais("Brasil")
            .tipoCuenta(TipoCuenta.AHORROS)
            .build();
        sistema.procesarSolicitud(sol8);

        // Caso 9: APROBADO - Segundo caso exitoso
        Solicitud sol9 = new SolicitudBuilder()
            .nombreCompleto("Sofia Torres")
            .documento("89012345")
            .edad(32)
            .telefono("3106543210")
            .pais("Chile")
            .tipoCuenta(TipoCuenta.CORRIENTE)
            .build();
        sistema.procesarSolicitud(sol9);

        // Caso 10: Duplicidad - Mismo documento que sol8 (aprobado)
        Solicitud sol10 = new SolicitudBuilder()
            .nombreCompleto("Juan Pérez")
            .documento("78901234")
            .edad(40)
            .telefono("3125678901")
            .pais("Colombia")
            .tipoCuenta(TipoCuenta.AHORROS)
            .build();
        sistema.procesarSolicitud(sol10);

        // Caso 11: Primera solicitud que será rechazada por país no permitido
        Solicitud sol11A = new SolicitudBuilder()
            .nombreCompleto("Diego Ramirez")
            .documento("11223344")
            .edad(25)
            .telefono("3001112233")
            .pais("Argentina")
            .tipoCuenta(TipoCuenta.AHORROS)
            .build();
        sistema.procesarSolicitud(sol11A);

        // Caso 11B: Intento duplicado - RECHAZADA previa con misma información
        Solicitud sol11B = new SolicitudBuilder()
            .nombreCompleto("Diego Ramirez")
            .documento("11223344")
            .edad(25)
            .telefono("3001112233")
            .pais("Argentina")
            .tipoCuenta(TipoCuenta.AHORROS)
            .build();
        sistema.procesarSolicitud(sol11B);

        // Caso 12: APROBADO - Caso USA con cuenta de ahorros
        Solicitud sol12 = new SolicitudBuilder()
            .nombreCompleto("Emma Williams")
            .documento("55667788")
            .edad(28)
            .correo("emma.williams@email.com")
            .telefono("3157890123")
            .pais("Estados Unidos")
            .tipoCuenta(TipoCuenta.AHORROS)
            .build();
        sistema.procesarSolicitud(sol12);
    }
}
