package itson.timbiriche;

import javax.swing.*;
import java.util.List;

/**
 * El punto de entrada principal para la aplicación del juego Timbiriche.
 * <p>
 * Esta clase contiene el método {@code main}, que es responsable de
 * inicializar y lanzar el juego. Sigue el patrón Modelo-Vista-Controlador (MVC)
 * al mostrar primero una ventana de configuración para establecer los jugadores,
 * y luego construir la ventana principal del juego con los componentes del modelo,
 * la vista y el controlador.
 *
 * @author [Tu Nombre/Equipo]
 * @version 1.0
 */
public class Timbiriche {

    /**
     * El método principal que inicia el juego Timbiriche.
     * <p>
     * El proceso se ejecuta en el Hilo de Despacho de Eventos (EDT) para garantizar
     * la seguridad de los hilos con los componentes de Swing. Implica:
     * <ol>
     * <li>Mostrar un diálogo de configuración modal ({@link VentanaConfiguracion}) para la preparación de los jugadores.</li>
     * <li>Si la configuración se completa, se inicializan los componentes MVC:
     * <ul>
     * <li>{@link TableroModelo} (el Modelo)</li>
     * <li>{@link PanelPrincipal} (la Vista principal)</li>
     * <li>{@link TableroControlador} (el Controlador)</li>
     * </ul>
     * </li>
     * <li>Construir la ventana principal del juego (JFrame), añadir el panel principal y mostrarla.</li>
     * </ol>
     * Si se cancela la configuración, la aplicación finaliza.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en esta aplicación).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Se usa un frame temporal e invisible como propietario del diálogo modal.
            JFrame ownerFrame = new JFrame();
            ownerFrame.setUndecorated(true);
            ownerFrame.setVisible(true);
            ownerFrame.setLocationRelativeTo(null);

            // 1. Mostrar el diálogo de configuración para obtener la lista de jugadores.
            VentanaConfiguracion dialogoConfig = new VentanaConfiguracion(ownerFrame, 2);
            List<Jugador> jugadores = dialogoConfig.mostrarDialogo();

            ownerFrame.dispose(); // Se deshace del frame temporal.

            // Si el usuario cancela la configuración, el programa termina.
            if (jugadores == null || jugadores.isEmpty()) {
                System.exit(0);
                return;
            }

            // 2. Configurar la ventana principal del juego.
            JFrame frameJuego = new JFrame("Timbiriche");
            frameJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // 3. Inicializar los componentes MVC.
            TableroModelo modelo = new TableroModelo(JuegoConfig.TAMANIO_TABLERO, jugadores);
            PanelPrincipal panelPrincipal = new PanelPrincipal(modelo);
            
            // El controlador conecta el modelo con las interacciones de la vista.
            new TableroControlador(modelo, panelPrincipal.getTableroVista());

            // 4. Finalizar y mostrar la ventana del juego.
            frameJuego.add(panelPrincipal);
            frameJuego.pack();
            frameJuego.setMinimumSize(frameJuego.getSize());
            frameJuego.setLocationRelativeTo(null);
            frameJuego.setVisible(true);
        });
    }
}