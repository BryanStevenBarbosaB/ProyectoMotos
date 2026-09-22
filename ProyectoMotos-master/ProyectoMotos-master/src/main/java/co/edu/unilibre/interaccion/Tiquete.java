package co.edu.unilibre.interaccion;

import co.edu.unilibre.dominio.Registro;

public class Tiquete {
    public String generarIngreso(Registro registro) {
        return mostrar(registro);
    }

    public String mostrar(Registro registro) {
        return "--- TIQUETE DE INGRESO ---\n" +
                "Placa: " + registro.obtenerMoto().obtenerPlaca() + "\n" +
                "Hora Ingreso: " + registro.obtenerHoraIngreso().toString();
    }
}