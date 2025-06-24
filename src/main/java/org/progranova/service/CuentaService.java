package org.progranova.service;

import org.progranova.model.Cuenta;

public class CuentaService {

    /**
     * Verifica si el monto puede ser retirado de la cuenta.
     *
     * @param cuenta Cuenta bancaria a verificar
     * @param monto Monto a retirar
     * @return true si es posible el retiro; false si no hay saldo suficiente o se excede el límite diario
     */
    public boolean puedeRetirar(Cuenta cuenta, double monto) {
        if (cuenta == null) {
            throw new IllegalArgumentException("Cuenta no válida.");
        }
        return cuenta.puedeRetirar(monto);
    }

    /**
     * Ejecuta el retiro del monto desde la cuenta.
     *
     * @param cuenta Cuenta desde la que se retira
     * @param monto Monto a retirar
     */
    public void retirar(Cuenta cuenta, double monto) {
        if (!puedeRetirar(cuenta, monto)) {
            throw new IllegalStateException("No se puede retirar: fondos insuficientes o límite diario alcanzado.");
        }
        cuenta.retirar(monto);
    }

    /**
     * Ejecuta un ingreso (depósito) en la cuenta.
     *
     * @param cuenta Cuenta a la que se deposita
     * @param monto Monto a ingresar
     */
    public void ingresar(Cuenta cuenta, double monto) {
        if (cuenta == null || monto <= 0) {
            throw new IllegalArgumentException("Ingreso no válido.");
        }
        cuenta.ingresar(monto);
    }

    /**
     * Devuelve el saldo disponible en la cuenta.
     *
     * @param cuenta Cuenta a consultar
     * @return Saldo actual
     */
    public double consultarSaldo(Cuenta cuenta) {
        if (cuenta == null) {
            throw new IllegalArgumentException("Cuenta no válida.");
        }
        return cuenta.getSaldo();
    }
}

