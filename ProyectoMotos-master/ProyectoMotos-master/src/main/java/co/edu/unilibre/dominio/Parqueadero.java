package co.edu.unilibre.dominio;

import java.util.ArrayList;

public class Parqueadero {
    private int espacio = 23;
    private ArrayList<Registro> motoEspacio;

    public Parqueadero() {
        this.motoEspacio = new ArrayList<>();
    }

    public int obtenerEspacioDisponible() {
        int activos = (int) motoEspacio.stream()
                .filter(r -> r.obtenerEstado().equals("ACTIVO")).count();
        return espacio - activos;
    }

    public ArrayList<Registro> obtenerMotoEspacio() { return motoEspacio; }
    public void agregarRegistro(Registro registro) { motoEspacio.add(registro); }
}