package co.edu.unilibre.interaccion;

import co.edu.unilibre.dominio.Pago;

public class GestorPagos {
    public Pago procesarPago(double valor, String tipoPago) {
        return new Pago(valor, tipoPago);
    }
}
