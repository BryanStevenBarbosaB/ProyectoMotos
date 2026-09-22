package co.edu.unilibre.dominio;

public class Pago {
    private Double valor;
    private String tipoPago;

    public Pago(Double valor, String tipoPago) {
        this.valor = valor;
        this.tipoPago = tipoPago;
    }

    public Double obtenerValor() { return valor; }
    public String obtenerTipoPago() { return tipoPago; }
}
