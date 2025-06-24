package org.progranova.controller;

import org.progranova.model.Cuenta;
import org.progranova.model.Tarjeta;
import org.progranova.service.AuthService;

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Intenta autenticar una tarjeta usando un PIN.
     *
     * @param tarjeta Tarjeta insertada por el usuario
     * @param pin PIN ingresado
     * @return true si se autentica correctamente, false si falla
     */
    public boolean autenticar(Tarjeta tarjeta, String pin) {
        return authService.autenticar(tarjeta, pin);
    }

    /**
     * Verifica si una tarjeta está bloqueada.
     */
    public boolean estaBloqueada(Tarjeta tarjeta) {
        return tarjeta.estaBloqueada();
    }
}
