package itson.timbiriche;

import javax.swing.JOptionPane;
import java.util.List;
import java.util.stream.Collectors;

/**
 * El "cerebro" del juego. Conecta la Vista (lo que el usuario ve y con lo que
 * interactúa) con el Modelo (las reglas y el estado del juego).
 *
 * - Implementa {@link TableroVista.PlayerInteractionListener}: para "escuchar"
 * cuando el usuario hace clic en el tablero para intentar poner una línea. -
 * Implementa {@link GameActionHandler}: para "ejecutar" las acciones del juego,
 * modificando el estado del Modelo.
 *
 * Este diseño (Modelo-Vista-Controlador) es muy útil. Por ejemplo, si
 * quisiéramos hacer una versión en red, solo tendríamos que cambiar el
 * `actionHandler` para que envíe las acciones a un servidor, sin tocar la Vista
 * ni el Modelo.
 */
public class TableroControlador implements TableroVista.PlayerInteractionListener {

    private final TableroModelo modelo;
    private final TableroVista vista;

    // Este manejador es quien realmente ejecuta la acción (localmente o por red).
    private final GameActionHandler actionHandler;

    /**
     * Constructor para un juego local simple. El controlador se encarga de
     * todo.
     *
     * @param modelo El modelo del juego.
     * @param vista La vista del juego.
     */
    public TableroControlador(TableroModelo modelo, TableroVista vista) {
        // Llama al constructor más completo, pero sin un manejador externo.
        this(modelo, vista, null);
    }

    /**
     * Constructor más avanzado que permite "inyectar" un manejador de acciones.
     * Esto es útil para pruebas o para implementar funcionalidades de red.
     *
     * @param modelo El modelo del juego.
     * @param vista La vista del juego.
     * @param externalActionHandler Un manejador de acciones externo. Si es
     * null, se crea uno local.
     */
public TableroControlador(TableroModelo modelo, TableroVista vista, GameActionHandler externalActionHandler) {
    this.modelo = modelo;
    this.vista = vista;
    
    this.actionHandler = (externalActionHandler != null) ? externalActionHandler : new LocalGameActionHandler(modelo);

    this.vista.setPlayerInteractionListener(this); 
    this.modelo.agregarListener(this::handleModeloCambiado); 

    // ¡Añade esta línea para la sincronización inicial!
    handleModeloCambiado();
}

    // --- Implementación de PlayerInteractionListener (RECIBE acciones de la VISTA) ---
    /**
     * Este método se llama cuando el usuario intenta colocar una línea desde la
     * Vista.
     *
     * @param fila Fila de la línea.
     * @param col Columna de la línea.
     * @param horizontal True si es horizontal.
     */
    @Override
    public void onLineAttempted(int fila, int col, boolean horizontal) {
        // En lugar de modificar el modelo directamente, delega la acción al actionHandler.
        // Esto mantiene el código limpio y preparado para futuras expansiones (como el juego en red).
        actionHandler.placeLine(fila, col, horizontal);
    }

    // --- Manejo de Cambios del Modelo (ESCUCHA al MODELO) ---
    /**
     * Este método se llama automáticamente cuando el Modelo notifica un cambio.
     */
    private void handleModeloCambiado() {
        // Si el juego ha terminado, muestra un mensaje con el resultado.
        if (modelo.isJuegoTerminado()) {
            List<Jugador> ganadores = modelo.getGanadores();
            String nombres = ganadores.stream().map(Jugador::nombre).collect(Collectors.joining(", "));
            String mensaje = ganadores.size() > 1 ? "¡Empate entre " + nombres + "!" : "¡Ganador: " + nombres + "!";
            JOptionPane.showMessageDialog(vista, mensaje, "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
        }
        // Si el manejador es local, se asegura de que sepa a qué jugador le toca ahora.
        if (actionHandler instanceof LocalGameActionHandler) {
            ((LocalGameActionHandler) actionHandler).setControllingPlayerId(modelo.getJugadorActual().id());
        }
    }

    /**
     * Implementación de GameActionHandler para un juego local. Esta clase
     * interna es la que realmente llama a los métodos del TableroModelo.
     * Encapsula la lógica de verificar el turno antes de realizar una jugada.
     */
    private class LocalGameActionHandler implements GameActionHandler {

        private final TableroModelo localModel;
        private int controllingPlayerId; // El ID del jugador que tiene el turno.

        public LocalGameActionHandler(TableroModelo model) {
            this.localModel = model;
        }

        public void setControllingPlayerId(int id) {
            this.controllingPlayerId = id;
        }

        @Override
        public void placeLine(int fila, int col, boolean horizontal) {
            // Antes de colocar la línea, verifica si es realmente el turno del jugador actual.
            // Esta es una capa de seguridad importante.
            if (localModel.getJugadorActual().id() == controllingPlayerId) {
                localModel.agregarLinea(fila, col, horizontal);
            } else {
                // Opcional: Podría mostrar un mensaje de "No es tu turno", pero
                // usualmente se prefiere simplemente ignorar el clic.
            }
        }
    }
}
