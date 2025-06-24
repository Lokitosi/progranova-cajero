package org.progranova.view;

import org.progranova.controller.AuthController;
import org.progranova.controller.CajeroController;
import org.progranova.model.Cuenta;
import org.progranova.model.Tarjeta;

import javax.swing.*;
import java.awt.*;

public class CajeroUI extends JFrame {

    private final AuthController authController;
    private final CajeroController cajeroController;

    private final Cuenta cuenta;
    private final Tarjeta tarjeta;

    public CajeroUI(AuthController authController, CajeroController cajeroController, Cuenta cuenta, Tarjeta tarjeta) {
        this.authController = authController;
        this.cajeroController = cajeroController;
        this.cuenta = cuenta;
        this.tarjeta = tarjeta;

        setTitle("Cajero Automático");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mostrarPantallaLogin();
    }

    private void mostrarPantallaLogin() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        JLabel label = new JLabel("Ingrese su PIN:");
        JPasswordField pinField = new JPasswordField();
        JButton ingresarButton = new JButton("Ingresar");

        panel.add(label);
        panel.add(pinField);
        panel.add(ingresarButton);

        setContentPane(panel);
        revalidate();

        ingresarButton.addActionListener(e -> {
            String pin = new String(pinField.getPassword());

            if (tarjeta.estaBloqueada()) {
                JOptionPane.showMessageDialog(this, "Tarjeta bloqueada. Contacte al banco.");
                System.exit(0);
            }

            if (authController.autenticar(tarjeta, pin)) {
                mostrarMenuOperaciones();
            } else {
                JOptionPane.showMessageDialog(this, "PIN incorrecto. Intentos: " + tarjeta.getIntentosFallidos());
                if (tarjeta.estaBloqueada()) {
                    JOptionPane.showMessageDialog(this, "Tarjeta bloqueada por exceder intentos.");
                    System.exit(0);
                }
            }
        });
    }

    private void mostrarMenuOperaciones() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JButton consultarBtn = new JButton("Consultar saldo");
        JButton retirarBtn = new JButton("Retirar dinero");
        JButton ingresarBtn = new JButton("Ingresar dinero");
        JButton salirBtn = new JButton("Salir");

        panel.add(consultarBtn);
        panel.add(retirarBtn);
        panel.add(ingresarBtn);
        panel.add(salirBtn);

        setContentPane(panel);
        revalidate();

        consultarBtn.addActionListener(e -> cajeroController.consultarSaldoSwing(this, cuenta));
        retirarBtn.addActionListener(e -> cajeroController.retirarSwing(this, cuenta));
        ingresarBtn.addActionListener(e -> cajeroController.ingresarSwing(this, cuenta));
        salirBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Gracias por usar el cajero.");
            System.exit(0);
        });
    }
}
