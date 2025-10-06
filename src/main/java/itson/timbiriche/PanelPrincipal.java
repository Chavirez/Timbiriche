package itson.timbiriche;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

/**
 * El panel principal del juego, que actúa como el contenedor principal para
 * todos los demás componentes de la interfaz de usuario.
 * <p>
 * Este panel organiza la disposición del tablero de juego, el panel lateral de
 * información del jugador y la etiqueta de turno. Implementa
 * {@link TableroModelo.ModeloListener} para escuchar cambios en el estado del
 * juego y actualizar la vista en consecuencia.

 */
public class PanelPrincipal extends JPanel implements ModeloListener {

    /**
     * El modelo de datos del juego, que contiene toda la lógica y el estado.
     */
    private final TableroModelo modelo;
    /**
     * Etiqueta que muestra el nombre y color del jugador actual o el resultado
     * del juego.
     */
    private final JLabel lblTurno;
    /**
     * El panel lateral que muestra los puntajes y la información de los
     * jugadores.
     */
    private final PanelLateral panelLateral;
    /**
     * El componente que dibuja el tablero de juego (puntos, líneas y cuadros).
     */
    private final TableroVista tableroVista;

    /**
     * Construye el panel principal del juego.
     * <p>
     * Inicializa y organiza los componentes visuales principales (tablero,
     * panel lateral, etiqueta de turno) y se registra como oyente del modelo
     * para recibir actualizaciones del estado del juego.
     *
     * @param modelo El {@link TableroModelo} que representa el estado del
     * juego.
     */
    public PanelPrincipal(TableroModelo modelo) {
        this.modelo = modelo;
        this.modelo.agregarListener(this); // Se suscribe a los cambios del modelo
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(200, 200, 200));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Etiqueta para mostrar el turno
        lblTurno = new JLabel(" ", SwingConstants.LEFT);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 24));
        lblTurno.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        // Creación de los componentes principales
        tableroVista = new TableroVista(modelo);
        panelLateral = new PanelLateral(modelo);

        // Añadir componentes al panel
        add(lblTurno, BorderLayout.NORTH);
        add(tableroVista, BorderLayout.CENTER);
        add(panelLateral, BorderLayout.EAST);

        // Carga el estado inicial de la UI
        modeloCambiado();
    }

    /**
     * Obtiene la instancia de la vista del tablero.
     * <p>
     * Es útil para que clases externas (como el JFrame principal) puedan
     * agregarle controladores, por ejemplo, un listener de mouse.
     *
     * @return El componente {@link TableroVista}.
     */
    public TableroVista getTableroVista() {
        return tableroVista;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Este método es llamado automáticamente por el modelo cada vez que hay un
     * cambio en el estado del juego. Se encarga de actualizar toda la interfaz
     * gráfica para que refleje el nuevo estado.
     */
    @Override
    public void modeloCambiado() {
        if (modelo.isJuegoTerminado()) {
            java.util.List<Jugador> ganadores = modelo.getGanadores();
            String nombres = ganadores.stream().map(Jugador::nombre).collect(Collectors.joining(", "));
            String mensaje = ganadores.size() > 1 ? "¡Empate entre " + nombres + "!" : "¡Ganador: " + nombres + "!";
            lblTurno.setText(mensaje);
            lblTurno.setForeground(Color.BLACK);
        } else {
            Jugador actual = modelo.getJugadorActual();
            lblTurno.setText("Turno: " + actual.nombre());
            lblTurno.setForeground(actual.color());
        }
        panelLateral.actualizarUI(); // Actualiza los puntajes
        tableroVista.repaint();      // Redibuja el tablero
    }
}
