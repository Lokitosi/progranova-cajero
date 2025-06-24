package org.progranova.service;

import org.progranova.model.Tarjeta;

public class AuthService {

    /**
     * Verifica si la tarjeta está habilitada y el PIN es correcto.
     *
     * @param tarjeta La tarjeta insertada
     * @param inputPin El PIN ingresado por el cliente
     * @return true si la autenticación es exitosa; false en caso contrario
     */
    public boolean autenticar(Tarjeta tarjeta, String inputPin) {
        if (tarjeta == null) {
            System.out.println("No se detectó una tarjeta.");
            return false;
        }

        if (tarjeta.estaBloqueada()) {
            System.out.println("La tarjeta está bloqueada.");
            return false;
        }

        boolean exito = tarjeta.verificarPin(inputPin);

        if (!exito) {
            System.out.println("PIN incorrecto. Intentos fallidos: " + tarjeta.getIntentosFallidos());
            if (tarjeta.estaBloqueada()) {
                System.out.println("Se ha bloqueado la tarjeta por exceso de intentos.");
            }
        }

        return exito;
    }
}
