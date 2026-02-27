package edu.dosw.parcial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SistemaGestionSolicitudes {
    private List<Solicitud> solicitudes;
    private int contadorSolicitudes;
    private ValidadorCadena cadenaValidadores;

    public SistemaGestionSolicitudes() {
        this.solicitudes = new ArrayList<>();
        this.contadorSolicitudes = 0;
        this.cadenaValidadores = construirCadenaValidadores();
    }

    public void procesarSolicitud(Solicitud solicitud) {
        System.out.println("\nProcesando solicitud de " + solicitud.getNombreCompleto());

        if (!cadenaValidadores.validar(solicitud, solicitudes)) {
            rechazarSolicitud(solicitud, cadenaValidadores.getMotivoRechazo());
            return;
        }

        aprobarSolicitud(solicitud);
    }

    private ValidadorCadena construirCadenaValidadores() {
        ValidadorCadena documento = new ValidadorDocumento();
        ValidadorCadena edad = new ValidadorEdad();
        ValidadorCadena duplicidad = new ValidadorDuplicidad();
        ValidadorCadena telefono = new ValidadorTelefono();
        ValidadorCadena pais = new ValidadorPais();
        ValidadorCadena tipoCuenta = new ValidadorTipoCuenta();

        documento
            .setSiguiente(edad)
            .setSiguiente(duplicidad)
            .setSiguiente(telefono)
            .setSiguiente(pais)
            .setSiguiente(tipoCuenta);

        return documento;
    }

    private void rechazarSolicitud(Solicitud solicitud, String motivo) {
        solicitud.setEstado(EstadoSolicitud.RECHAZADO);
        solicitud.setMotivoRechazo(motivo);
        solicitudes.add(solicitud);
        System.out.println("RECHAZADO -> " + motivo);
    }

    private void aprobarSolicitud(Solicitud solicitud) {
        solicitud.setEstado(EstadoSolicitud.APROBADO);
        contadorSolicitudes++;
        solicitud.setNumeroSolicitud(String.format("%04d", contadorSolicitudes));
        solicitudes.add(solicitud);

        System.out.println("APROBADO -> Cuenta creada exitosamente");
        System.out.println("NumeroSolicitud: " + solicitud.getNumeroSolicitud());
        System.out.println("Titular: " + solicitud.getNombreCompleto());
        System.out.println("Tipo: " + solicitud.getTipoCuenta());
        System.out.println("Pais: " + solicitud.getPais());
    }

    public List<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    private abstract static class ValidadorCadena {
        private ValidadorCadena siguiente;
        protected String motivoRechazo;

        public ValidadorCadena setSiguiente(ValidadorCadena siguiente) {
            this.siguiente = siguiente;
            return siguiente;
        }

        public boolean validar(Solicitud solicitud, List<Solicitud> solicitudes) {
            if (!ejecutarValidacion(solicitud, solicitudes)) {
                return false;
            }

            if (siguiente == null) {
                return true;
            }

            boolean resultadoSiguiente = siguiente.validar(solicitud, solicitudes);
            if (!resultadoSiguiente) {
                this.motivoRechazo = siguiente.getMotivoRechazo();
            }
            return resultadoSiguiente;
        }

        public String getMotivoRechazo() {
            return motivoRechazo;
        }

        protected abstract boolean ejecutarValidacion(Solicitud solicitud, List<Solicitud> solicitudes);
    }

    private static class ValidadorDocumento extends ValidadorCadena {
        @Override
        protected boolean ejecutarValidacion(Solicitud solicitud, List<Solicitud> solicitudes) {
            String documento = solicitud.getDocumento();
            if (!documento.matches("\\d+")) {
                motivoRechazo = "El documento debe contener solo números";
                return false;
            }
            if (documento.length() < 8 || documento.length() > 10) {
                motivoRechazo = "El documento debe tener entre 8 y 10 dígitos";
                return false;
            }
            return true;
        }
    }

    private static class ValidadorEdad extends ValidadorCadena {
        @Override
        protected boolean ejecutarValidacion(Solicitud solicitud, List<Solicitud> solicitudes) {
            int edadMinima = solicitud.getPais().equals("Estados Unidos") ? 21 : 18;
            if (solicitud.getEdad() < edadMinima) {
                motivoRechazo = "El solicitante es menor de edad";
                return false;
            }
            return true;
        }
    }

    private static class ValidadorDuplicidad extends ValidadorCadena {
        @Override
        protected boolean ejecutarValidacion(Solicitud solicitud, List<Solicitud> solicitudes) {
            for (Solicitud existente : solicitudes) {
                if (existente.getEstado() == EstadoSolicitud.APROBADO
                    && existente.getDocumento().equals(solicitud.getDocumento())) {
                    motivoRechazo = "Ya existe una solicitud aprobada con este documento";
                    return false;
                }

                if (existente.getEstado() == EstadoSolicitud.RECHAZADO
                    && existente.getDocumento().equals(solicitud.getDocumento())
                    && existente.getNombreCompleto().equals(solicitud.getNombreCompleto())
                    && existente.getEdad() == solicitud.getEdad()
                    && existente.getTelefono().equals(solicitud.getTelefono())) {
                    motivoRechazo = "Existe una solicitud rechazada previa con la misma información";
                    return false;
                }
            }
            return true;
        }
    }

    private static class ValidadorTelefono extends ValidadorCadena {
        @Override
        protected boolean ejecutarValidacion(Solicitud solicitud, List<Solicitud> solicitudes) {
            if (!solicitud.getTelefono().matches("\\d{10}")) {
                motivoRechazo = "El teléfono debe tener exactamente 10 dígitos numéricos";
                return false;
            }
            return true;
        }
    }

    private static class ValidadorPais extends ValidadorCadena {
        private static final List<String> PAISES_PERMITIDOS = Arrays.asList(
            "Colombia", "Chile", "Perú", "Brasil", "Estados Unidos"
        );

        @Override
        protected boolean ejecutarValidacion(Solicitud solicitud, List<Solicitud> solicitudes) {
            if (!PAISES_PERMITIDOS.contains(solicitud.getPais())) {
                motivoRechazo = "El país no está en la lista de países permitidos";
                return false;
            }
            return true;
        }
    }

    private static class ValidadorTipoCuenta extends ValidadorCadena {
        @Override
        protected boolean ejecutarValidacion(Solicitud solicitud, List<Solicitud> solicitudes) {
            if (solicitud.getPais().equals("Estados Unidos")
                && solicitud.getTipoCuenta() == TipoCuenta.CORRIENTE) {
                motivoRechazo = "Estados Unidos no permite cuentas corrientes";
                return false;
            }
            return true;
        }
    }
}
