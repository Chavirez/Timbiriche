package itson.timbiriche;

import javax.swing.*;
import java.util.List;

public class Timbiriche {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ownerFrame = new JFrame();
            ownerFrame.setUndecorated(true);
            ownerFrame.setVisible(true);
            ownerFrame.setLocationRelativeTo(null);

            VentanaConfiguracion dialogoConfig = new VentanaConfiguracion(ownerFrame, 2);
            List<Jugador> jugadores = dialogoConfig.mostrarDialogo();

            ownerFrame.dispose();

            if (jugadores == null || jugadores.isEmpty()) {
                System.exit(0);
                return;
            }

            JFrame frameJuego = new JFrame("Timbiriche");
            frameJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            IModeloJuego modelo = new TableroModelo(JuegoConfig.TAMANIO_TABLERO, jugadores);
            // IModeloJuego modelo = new ModeloJuegoMock(jugadores);

            PanelPrincipal panelPrincipal = new PanelPrincipal(modelo);
            TableroControlador controlador = new TableroControlador(modelo);

            controlador.vincularVista(panelPrincipal.getTableroVista());

            frameJuego.add(panelPrincipal);
            frameJuego.pack();
            frameJuego.setMinimumSize(frameJuego.getSize());
            frameJuego.setLocationRelativeTo(null);
            frameJuego.setVisible(true);
        });
    }
}