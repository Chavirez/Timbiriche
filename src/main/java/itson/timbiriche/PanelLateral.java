package itson.timbiriche;

import javax.swing.*;
import java.awt.*;

/**
 * Un componente de panel (JPanel) que actúa como la barra lateral en la ventana principal del juego.
 * <p>
 * Este panel se encarga de mostrar la información de cada jugador, incluyendo su
 * avatar, nombre y puntaje actual. También contiene un botón para salir de la aplicación.
 * El panel se actualiza a través del método {@link #actualizarUI()} para reflejar
 * los cambios en el estado del juego.
 *
 * @author [Tu Nombre/Equipo]
 * @version 1.0
 */
public class PanelLateral extends JPanel {
    /** El modelo de datos del tablero, que contiene el estado del juego. */
    private final TableroModelo modelo;
    /** Un arreglo de etiquetas (JLabel) para mostrar los puntajes de los jugadores. */
    private final JLabel[] labelsPuntajes;

    /**
     * Construye el panel lateral.
     * <p>
     * Inicializa la interfaz de usuario creando un subpanel para cada jugador
     * y añadiendo un botón de salida en la parte inferior.
     *
     * @param modelo El modelo de datos del juego ({@link TableroModelo}) del cual
     * se obtiene la información de los jugadores y puntajes.
     */
    public PanelLateral(TableroModelo modelo) {
        this.modelo = modelo;
        this.labelsPuntajes = new JLabel[modelo.getJugadores().size()];
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crea un panel para cada jugador
        for (int i = 0; i < modelo.getJugadores().size(); i++) {
            add(crearPanelJugador(modelo.getJugadores().get(i), i));
            add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre jugadores
        }

        add(Box.createVerticalGlue()); // Empuja el botón de salir hacia abajo
        JButton btnSalir = new JButton("Salir");
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.addActionListener(e -> System.exit(0));
        add(btnSalir);
    }

    /**
     * Crea un panel individual que muestra la información de un jugador.
     *
     * @param jugador El objeto {@link Jugador} del cual se mostrará la información.
     * @param index El índice del jugador, usado para asociar su puntaje con la etiqueta correcta.
     * @return Un {@link JPanel} configurado con el avatar, nombre y puntaje del jugador.
     */
    private JPanel crearPanelJugador(Jugador jugador, int index) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setMaximumSize(new Dimension(240, 60));
        panel.setBackground(new Color(230, 230, 230));

        // Avatar del jugador
        JLabel lblAvatar = new JLabel(Recursos.loadScaledAvatar(jugador.avatarPath(), 50, 50));
        lblAvatar.setPreferredSize(new Dimension(50, 50));

        // Nombre del jugador
        JLabel lblNombre = new JLabel(jugador.nombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));

        // Puntaje del jugador (inicializado en 0)
        labelsPuntajes[index] = new JLabel("0");
        labelsPuntajes[index].setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(lblAvatar, BorderLayout.WEST);
        panel.add(lblNombre, BorderLayout.CENTER);
        panel.add(labelsPuntajes[index], BorderLayout.EAST);
        return panel;
    }

    /**
     * Actualiza la interfaz de usuario, específicamente los puntajes de los jugadores.
     * Este método debe ser llamado cada vez que el estado del juego cambie (ej. un jugador anota un punto).
     */
    public void actualizarUI() {
        int[] puntajes = modelo.getPuntajes();
        for (int i = 0; i < puntajes.length; i++) {
            labelsPuntajes[i].setText(String.valueOf(puntajes[i]));
        }
        repaint();
    }
}