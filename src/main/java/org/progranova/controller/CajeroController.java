package org.progranova.controller;

import org.progranova.model.Cuenta;
import org.progranova.model.Tarjeta;
import org.progranova.model.Transaccion;
import org.progranova.service.AuthService;
import org.progranova.service.CuentaService;
import org.progranova.service.TransaccionLogger;
import javax.swing.*;
import java.awt.Component;
import java.util.Scanner;

public class CajeroController {

    private final AuthService authService;
    private final CuentaService cuentaService;
    private final TransaccionLogger logger;

    private Cuenta cuentaSesion; // Cuenta activa en la sesión

    public CajeroController(AuthService authService, CuentaService cuentaService, TransaccionLogger logger) {
        this.authService = authService;
        this.cuentaService = cuentaService;
        this.logger = logger;
    }

    public void iniciarSesion(Cuenta cuenta, Tarjeta tarjeta) {
        Scanner scanner = new Scanner(System.in);
        cuentaSesion = cuenta;

        System.out.println("Bienvenido al Cajero Automático.");
        System.out.print("Ingrese su PIN: ");
        String pin = scanner.nextLine();

        if (!authService.autenticar(tarjeta, pin)) {
            logger.registrar(Transaccion.Tipo.CONSULTA, 0, cuenta.getNumeroCuenta(), Transaccion.Estado.FALLIDA);
            System.out.println("Autenticación fallida. Terminando sesión.");
            return;
        }

        System.out.println("Autenticación exitosa.");
        mostrarMenu(scanner);
    }

    private void mostrarMenu(Scanner scanner) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\nSeleccione una operación:");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Ingresar dinero");
            System.out.println("4. Salir");

            System.out.print("Opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> consultarSaldo();
                case 2 -> retirar(scanner);
                case 3 -> ingresar(scanner);
                case 4 -> {
                    continuar = false;
                    System.out.println("Gracias por usar el cajero. Tarjeta expulsada.");
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void consultarSaldo() {
        double saldo = cuentaService.consultarSaldo(cuentaSesion);
        System.out.println("Saldo disponible: $" + saldo);
        logger.registrar(Transaccion.Tipo.CONSULTA, 0, cuentaSesion.getNumeroCuenta(), Transaccion.Estado.EXITOSA);
    }

    private void retirar(Scanner scanner) {
        System.out.print("Ingrese el monto a retirar: ");
        double monto = Double.parseDouble(scanner.nextLine());

        try {
            cuentaService.retirar(cuentaSesion, monto);
            System.out.println("Retiro exitoso. Retire su dinero.");
            logger.registrar(Transaccion.Tipo.RETIRO, monto, cuentaSesion.getNumeroCuenta(), Transaccion.Estado.EXITOSA);
        } catch (Exception e) {
            System.out.println("Error al retirar: " + e.getMessage());
            logger.registrar(Transaccion.Tipo.RETIRO, monto, cuentaSesion.getNumeroCuenta(), Transaccion.Estado.FALLIDA);
        }
    }

    private void ingresar(Scanner scanner) {
        System.out.print("Ingrese el monto a depositar: ");
        double monto = Double.parseDouble(scanner.nextLine());

        try {
            cuentaService.ingresar(cuentaSesion, monto);
            System.out.println("Depósito exitoso.");
            logger.registrar(Transaccion.Tipo.INGRESO, monto, cuentaSesion.getNumeroCuenta(), Transaccion.Estado.EXITOSA);
        } catch (Exception e) {
            System.out.println("Error al ingresar: " + e.getMessage());
            logger.registrar(Transaccion.Tipo.INGRESO, monto, cuentaSesion.getNumeroCuenta(), Transaccion.Estado.FALLIDA);
        }
    }

    public void consultarSaldoSwing(Component parent, Cuenta cuenta) {
        double saldo = cuentaService.consultarSaldo(cuenta);
        JOptionPane.showMessageDialog(parent, "Saldo disponible: $" + saldo);
        logger.registrar(
                org.progranova.model.Transaccion.Tipo.CONSULTA,
                0,
                cuenta.getNumeroCuenta(),
                org.progranova.model.Transaccion.Estado.EXITOSA
        );
    }

    public void retirarSwing(Component parent, Cuenta cuenta) {
        String input = JOptionPane.showInputDialog(parent, "Ingrese el monto a retirar:");
        if (input == null) return; // Usuario canceló

        try {
            double monto = Double.parseDouble(input);
            cuentaService.retirar(cuenta, monto);
            JOptionPane.showMessageDialog(parent, "Retiro exitoso. Retire su dinero.");
            logger.registrar(
                    org.progranova.model.Transaccion.Tipo.RETIRO,
                    monto,
                    cuenta.getNumeroCuenta(),
                    org.progranova.model.Transaccion.Estado.EXITOSA
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Error al retirar: " + e.getMessage());
            logger.registrar(
                    org.progranova.model.Transaccion.Tipo.RETIRO,
                    0,
                    cuenta.getNumeroCuenta(),
                    org.progranova.model.Transaccion.Estado.FALLIDA
            );
        }
    }

    public void ingresarSwing(Component parent, Cuenta cuenta) {
        String input = JOptionPane.showInputDialog(parent, "Ingrese el monto a depositar:");
        if (input == null) return;

        try {
            double monto = Double.parseDouble(input);
            cuentaService.ingresar(cuenta, monto);
            JOptionPane.showMessageDialog(parent, "Depósito exitoso.");
            logger.registrar(
                    org.progranova.model.Transaccion.Tipo.INGRESO,
                    monto,
                    cuenta.getNumeroCuenta(),
                    org.progranova.model.Transaccion.Estado.EXITOSA
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Error al ingresar: " + e.getMessage());
            logger.registrar(
                    org.progranova.model.Transaccion.Tipo.INGRESO,
                    0,
                    cuenta.getNumeroCuenta(),
                    org.progranova.model.Transaccion.Estado.FALLIDA
            );
        }
    }

}
