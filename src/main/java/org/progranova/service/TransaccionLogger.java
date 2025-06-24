package org.progranova.service;

import org.progranova.model.Transaccion;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransaccionLogger {

    private List<Transaccion> historial;

    public TransaccionLogger() {
        this.historial = new ArrayList<>();
    }

    /**
     * Registra una nueva transacción.
     *
     * @param tipo Tipo de transacción (RETIRO, INGRESO, etc.)
     * @param monto Monto involucrado
     * @param numeroCuenta Cuenta asociada
     * @param estado Estado de la transacción (EXITOSA o FALLIDA)
     */
    public void registrar(Transaccion.Tipo tipo, double monto, String numeroCuenta, Transaccion.Estado estado) {
        String id = UUID.randomUUID().toString();
        Transaccion transaccion = new Transaccion(id, tipo, monto, numeroCuenta, estado);
        historial.add(transaccion);
        System.out.println("📄 Transacción registrada: " + transaccion);
    }

    /**
     * Devuelve todas las transacciones registradas.
     */
    public List<Transaccion> obtenerHistorial() {
        return historial;
    }

    /**
     * Devuelve todas las transacciones de una cuenta específica.
     */
    public List<Transaccion> obtenerHistorialPorCuenta(String numeroCuenta) {
        return historial.stream()
                .filter(t -> t.getNumeroCuenta().equals(numeroCuenta))
                .collect(Collectors.toList());
    }
}
