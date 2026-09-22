package co.edu.unilibre;

import co.edu.unilibre.gui.InterfazParqueadero;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazParqueadero();
        });
    }
}
