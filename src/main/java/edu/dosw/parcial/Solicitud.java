package edu.dosw.parcial;

public class Solicitud {
    private String nombreCompleto;
    private String documento;
    private int edad;
    private String correo;
    private String telefono;
    private String pais;
    private TipoCuenta tipoCuenta;
    private EstadoSolicitud estado;
    private String motivoRechazo;
    private String numeroSolicitud;

    public Solicitud(String nombreCompleto, String documento, int edad, String telefono, 
                     String pais, TipoCuenta tipoCuenta) {
        this.nombreCompleto = nombreCompleto;
        this.documento = documento;
        this.edad = edad;
        this.telefono = telefono;
        this.pais = pais;
        this.tipoCuenta = tipoCuenta;
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    public Solicitud(String nombreCompleto, String documento, int edad, String correo,
                     String telefono, String pais, TipoCuenta tipoCuenta) {
        this(nombreCompleto, documento, edad, telefono, pais, tipoCuenta);
        this.correo = correo;
    }

    // Getters
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getDocumento() {
        return documento;
    }

    public int getEdad() {
        return edad;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPais() {
        return pais;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    // Setters
    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }
}
