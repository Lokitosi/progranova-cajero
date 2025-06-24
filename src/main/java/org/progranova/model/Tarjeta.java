package org.progranova.model;

public class Tarjeta {
    private String numero;
    private String pin;
    private int intentosFallidos;
    private boolean bloqueada;

    public Tarjeta(String numero, String pin) {
        this.numero = numero;
        this.pin = pin;
        this.intentosFallidos = 0;
        this.bloqueada = false;
    }

    public boolean verificarPin(String input) {
        if (bloqueada) return false;

        if (this.pin.equals(input)) {
            intentosFallidos = 0;
            return true;
        } else {
            intentosFallidos++;
            if (intentosFallidos >= 3) {
                bloqueada = true;
            }
            return false;
        }
    }

    public boolean estaBloqueada() {
        return bloqueada;
    }

    // Getters
    public String getNumero() {
        return numero;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }
}
