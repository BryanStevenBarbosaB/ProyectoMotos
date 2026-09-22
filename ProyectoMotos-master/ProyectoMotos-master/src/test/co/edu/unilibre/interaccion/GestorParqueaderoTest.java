package co.edu.unilibre.interaccion;

import co.edu.unilibre.dominio.Parqueadero;
import co.edu.unilibre.dominio.Registro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestorParqueaderoTest {
    private Parqueadero parqueadero;
    private GestorParqueadero gestor;

    @BeforeEach
    public void prepararEntorno() {
        parqueadero = new Parqueadero();
        gestor = new GestorParqueadero(parqueadero);
    }

    // ==========================================
    // PRUEBAS DE ÉXITO
    // ==========================================

    @Test
    public void pruebaIngresoExitoso() {
        String mensaje = gestor.ingresarMoto("ABC-123", "Yamaha", "1001");

        assertEquals(22, parqueadero.obtenerEspacioDisponible());
        assertTrue(mensaje.contains("ABC-123"));
    }

    @Test
    public void pruebaSalidaYRegistroDePagoExitoso() {
        gestor.ingresarMoto("XYZ-789", "Suzuki", "1002");

        Registro registro = gestor.sacarMoto("XYZ-789");
        assertNotNull(registro);

        double valor = gestor.calcularValor(10); // 10 min * 40 pesos
        gestor.completarPago(registro, "Nequi", valor);

        assertEquals("PAGADO", registro.obtenerEstado());
        assertEquals("Nequi", registro.obtenerPago().obtenerTipoPago());
        assertEquals(400.0, registro.obtenerPago().obtenerValor());

        assertEquals(23, parqueadero.obtenerEspacioDisponible());
    }

    // ==========================================
    // PRUEBAS DE FRACASO (ERRORES Y LÍMITES)
    // ==========================================

    @Test
    public void pruebaFracasoPorParqueaderoLleno() {
        for (int i = 0; i < 23; i++) {
            gestor.ingresarMoto("MOT-" + i, "Honda", "Dueño" + i);
        }

        assertEquals(0, parqueadero.obtenerEspacioDisponible());

        String mensajeError = gestor.ingresarMoto("SOB-999", "KTM", "Invasor");

        assertEquals("Error: Parqueadero lleno. Límite de 23 espacios alcanzado.", mensajeError);
        assertEquals(0, parqueadero.obtenerEspacioDisponible());
    }

    @Test
    public void pruebaFracasoSalidaPlacaInexistente() {
        Registro registro = gestor.sacarMoto("FANTASMA");
        assertNull(registro);
    }

    @Test
    public void pruebaFracasoDobleSalida() {
        gestor.ingresarMoto("DUO-111", "AKT", "1003");
        Registro primerIntento = gestor.sacarMoto("DUO-111");
        gestor.completarPago(primerIntento, "Efectivo", 40.0);

        Registro segundoIntento = gestor.sacarMoto("DUO-111");

        assertNull(segundoIntento, "No se puede dar salida a una moto que ya no está ACTIVA");
    }
}
