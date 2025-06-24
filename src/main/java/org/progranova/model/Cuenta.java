package org.progranova.model;

public class Cuenta {
    private String numeroCuenta;
    private double saldo;
    private double limiteDiario;
    private double acumuladoDiario;
    private Tarjeta tarjeta;

    public Cuenta(String numeroCuenta, double saldo, double limiteDiario) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.limiteDiario = limiteDiario;
        this.acumuladoDiario = 0;
    }

    public boolean puedeRetirar(double monto) {
        return monto <= saldo && (acumuladoDiario + monto <= limiteDiario);
    }

    public void retirar(double monto) {
        if (puedeRetirar(monto)) {
            saldo -= monto;
            acumuladoDiario += monto;
        } else {
            throw new IllegalArgumentException("Monto inválido para retiro.");
        }
    }

    public void ingresar(double monto) {
        saldo += monto;
    }

    public void asignarTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    // Getters y Setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimiteDiario() {
        return limiteDiario;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }
}
