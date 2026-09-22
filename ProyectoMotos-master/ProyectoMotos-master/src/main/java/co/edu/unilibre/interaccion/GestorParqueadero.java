package co.edu.unilibre.interaccion;

import co.edu.unilibre.dominio.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class GestorParqueadero {
    private Parqueadero parqueadero;
    private GestorPagos gestorPagos;

    public GestorParqueadero(Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
        this.gestorPagos = new GestorPagos();
    }

    public String ingresarMoto(String placa, String marca, String idDueno) {
        if (parqueadero.obtenerEspacioDisponible() <= 0) {
            return "Error: Parqueadero lleno. Límite de 23 espacios alcanzado.";
        }
        Moto nuevaMoto = new Moto(placa, marca, idDueno);
        Registro nuevoRegistro = new Registro(nuevaMoto, parqueadero);
        parqueadero.agregarRegistro(nuevoRegistro);

        Tiquete tiquete = new Tiquete();
        return tiquete.generarIngreso(nuevoRegistro);
    }

    public Registro sacarMoto(String placa) {
        Optional<Registro> optRegistro = parqueadero.obtenerMotoEspacio().stream()
                .filter(r -> r.obtenerMoto().obtenerPlaca().equals(placa) && r.obtenerEstado().equals("ACTIVO"))
                .findFirst();

        if (optRegistro.isPresent()) {
            Registro registro = optRegistro.get();
            registro.asignarHoraSalida(LocalTime.now());
            return registro;
        }
        return null;
    }

    public int calcularTiempo(Registro registro) {
        long minutos = ChronoUnit.MINUTES.between(registro.obtenerHoraIngreso(), registro.obtenerHoraSalida());
        return minutos <= 0 ? 1 : (int) minutos; // Se cobra al menos 1 minuto
    }

    public double calcularValor(int minutos) {
        return minutos * 40.0;
    }

    public void completarPago(Registro registro, String tipoPago, double valor) {
        Pago pago = gestorPagos.procesarPago(valor, tipoPago);
        registro.asignarPago(pago);
        registro.asignarEstado("PAGADO");
    }
}
