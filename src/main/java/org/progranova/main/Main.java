package org.progranova.main;

import org.progranova.controller.AuthController;
import org.progranova.controller.CajeroController;
import org.progranova.model.Cliente;
import org.progranova.model.Cuenta;
import org.progranova.model.Tarjeta;
import org.progranova.service.AuthService;
import org.progranova.service.CuentaService;
import org.progranova.service.TransaccionLogger;
import org.progranova.view.CajeroUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Crear modelos de prueba
        Cliente cliente = new Cliente("123", "Juan Pérez");
        Cuenta cuenta = new Cuenta("0001", 100000.0, 50000.0); // saldo inicial, límite diario
        Tarjeta tarjeta = new Tarjeta("9999-1111", "1234");     // PIN = 1234
        cuenta.asignarTarjeta(tarjeta);
        cliente.agregarCuenta(cuenta);

        // Crear servicios
        AuthService authService = new AuthService();
        CuentaService cuentaService = new CuentaService();
        TransaccionLogger logger = new TransaccionLogger();

        // Crear controladores
        AuthController authController = new AuthController(authService);
        CajeroController cajeroController = new CajeroController(authService, cuentaService, logger);

        // Ejecutar interfaz Swing en hilo de UI
        SwingUtilities.invokeLater(() -> {
            CajeroUI cajeroUI = new CajeroUI(authController, cajeroController, cuenta, tarjeta);
            cajeroUI.setVisible(true);
        });
    }
}