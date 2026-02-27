package edu.dosw.parcial;

public class SolicitudBuilder {
    private String nombreCompleto;
    private String documento;
    private int edad;
    private String correo;
    private String telefono;
    private String pais;
    private TipoCuenta tipoCuenta;

    public SolicitudBuilder nombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
        return this;
    }

    public SolicitudBuilder documento(String documento) {
        this.documento = documento;
        return this;
    }

    public SolicitudBuilder edad(int edad) {
        this.edad = edad;
        return this;
    }

    public SolicitudBuilder correo(String correo) {
        this.correo = correo;
        return this;
    }

    public SolicitudBuilder telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public SolicitudBuilder pais(String pais) {
        this.pais = pais;
        return this;
    }

    public SolicitudBuilder tipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
        return this;
    }

    public Solicitud build() {
        if (correo == null || correo.isBlank()) {
            return new Solicitud(nombreCompleto, documento, edad, telefono, pais, tipoCuenta);
        }
        return new Solicitud(nombreCompleto, documento, edad, correo, telefono, pais, tipoCuenta);
    }
}