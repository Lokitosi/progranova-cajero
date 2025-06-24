package org.progranova.model;

import java.time.LocalDateTime;

public class Transaccion {
    public enum Tipo {
        RETIRO, INGRESO, CONSULTA, COMPRA_ENTRADAS
    }

    public enum Estado {
        EXITOSA, FALLIDA
    }

    private String id;
    private Tipo tipo;
    private double monto;
    private LocalDateTime fechaHora;
    private Estado estado;
    private String numeroCuenta;

    public Transaccion(String id, Tipo tipo, double monto, String numeroCuenta, Estado estado) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.numeroCuenta = numeroCuenta;
        this.estado = estado;
        this.fechaHora = LocalDateTime.now();
    }

    // Getters
    public String getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id='" + id + '\'' +
                ", tipo=" + tipo +
                ", monto=" + monto +
                ", fechaHora=" + fechaHora +
                ", estado=" + estado +
                ", cuenta='" + numeroCuenta + '\'' +
                '}';
    }
}
