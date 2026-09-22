package co.edu.unilibre.gui;

import co.edu.unilibre.dominio.Parqueadero;
import co.edu.unilibre.dominio.Registro;
import co.edu.unilibre.interaccion.GestorParqueadero;
import javax.swing.*;
import java.awt.*;

public class InterfazParqueadero extends JFrame {
    private GestorParqueadero gestor;
    private Parqueadero parqueadero;

    public InterfazParqueadero() {
        this.parqueadero = new Parqueadero();
        this.gestor = new GestorParqueadero(parqueadero);
        mostrarGUI();
    }

    public void mostrarGUI() {
        setTitle("Gestión de Parqueadero de Motos");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 15, 15));

        JButton btnIngreso = new JButton("Registrar Ingreso (RQ01)");
        JButton btnSalida = new JButton("Dar Salida y Pagar (RQ02)");
        JButton btnReporte = new JButton("Generar Reporte (RQ03)");

        btnIngreso.addActionListener(e -> {
            String placa = JOptionPane.showInputDialog("Ingrese Placa de la moto:");
            String marca = JOptionPane.showInputDialog("Ingrese Marca:");
            String idDueno = JOptionPane.showInputDialog("Identificación Dueño:");
            if (placa != null && !placa.isEmpty()) {
                String msj = gestor.ingresarMoto(placa, marca, idDueno);
                JOptionPane.showMessageDialog(this, msj);
            }
        });

        btnSalida.addActionListener(e -> {
            String placa = JOptionPane.showInputDialog("Placa de la moto a dar salida:");
            if (placa == null || placa.isEmpty()) return;

            Registro reg = gestor.sacarMoto(placa);
            if (reg != null) {
                int minutos = gestor.calcularTiempo(reg);
                double valor = gestor.calcularValor(minutos);

                String[] opciones = {"Efectivo", "Nequi"};
                int seleccion = JOptionPane.showOptionDialog(this,
                        "Tiempo de estadía: " + minutos + " min\nValor total a pagar: $" + valor,
                        "Registro de Pago", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

                if (seleccion >= 0) {
                    String tipoPago = seleccion == 1 ? "Nequi" : "Efectivo";
                    gestor.completarPago(reg, tipoPago, valor);
                    JOptionPane.showMessageDialog(this, "Salida registrada exitosamente con " + tipoPago);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error: Moto no encontrada en el sistema o ya se le dio salida.");
            }
        });

        btnReporte.addActionListener(e -> {
            double total = parqueadero.obtenerMotoEspacio().stream()
                    .filter(r -> r.obtenerEstado().equals("PAGADO"))
                    .mapToDouble(r -> r.obtenerPago().obtenerValor()).sum();

            long cantidad = parqueadero.obtenerMotoEspacio().stream()
                    .filter(r -> r.obtenerEstado().equals("PAGADO")).count();

            JOptionPane.showMessageDialog(this,
                    "--- REPORTE DEL PARQUEADERO ---\n" +
                            "# Motos atendidas: " + cantidad + "\n" +
                            "Valor total ingresado: $" + total);
        });

        add(new JLabel("  Seleccione una operación:", SwingConstants.CENTER));
        add(btnIngreso);
        add(btnSalida);
        add(btnReporte);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}