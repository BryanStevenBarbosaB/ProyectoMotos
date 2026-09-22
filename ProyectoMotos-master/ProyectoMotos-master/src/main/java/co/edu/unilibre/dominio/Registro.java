package co.edu.unilibre.dominio;

import java.time.LocalTime;

public class Registro {
    private static int contador = 1;
    private int idRegistro;
    private Moto moto;
    private LocalTime horaIngreso;
    private LocalTime horaSalida;
    private Pago pago;
    private String estado;
    private Parqueadero parqueadero;

    public Registro(Moto moto, Parqueadero parqueadero) {
        this.idRegistro = contador++;
        this.moto = moto;
        this.horaIngreso = LocalTime.now();
        this.estado = "ACTIVO";
        this.parqueadero = parqueadero;
    }

    public int obtenerIdRegistro() { return idRegistro; }
    public Moto obtenerMoto() { return moto; }
    public LocalTime obtenerHoraIngreso() { return horaIngreso; }
    public LocalTime obtenerHoraSalida() { return horaSalida; }
    public Pago obtenerPago() { return pago; }
    public String obtenerEstado() { return estado; }

    public void asignarHoraSalida(LocalTime horaSalida) { this.horaSalida = horaSalida; }
    public void asignarPago(Pago pago) { this.pago = pago; }
    public void asignarEstado(String estado) { this.estado = estado; }
}