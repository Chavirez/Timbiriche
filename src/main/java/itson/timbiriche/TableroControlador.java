package itson.timbiriche;

import javax.swing.JOptionPane;
import java.util.List;
import java.util.stream.Collectors;

public class TableroControlador implements TableroVista.PlayerInteractionListener {

    private final IModeloJuego modelo;
    private final TableroVista vista;
    private final GameActionHandler actionHandler;

    public TableroControlador(IModeloJuego modelo, TableroVista vista) {
        this(modelo, vista, null);
    }

    public TableroControlador(IModeloJuego modelo, TableroVista vista, GameActionHandler externalActionHandler) {
        this.modelo = modelo;
        this.vista = vista;
        this.actionHandler = (externalActionHandler != null) ? externalActionHandler : new LocalGameActionHandler(modelo);
        this.vista.setPlayerInteractionListener(this);
        this.modelo.agregarListener(this::handleModeloCambiado);
        handleModeloCambiado();
    }

    @Override
    public void onLineAttempted(int fila, int col, boolean horizontal) {
        actionHandler.placeLine(fila, col, horizontal);
    }

    private void handleModeloCambiado() {
        if (modelo.isJuegoTerminado()) {
            List<Jugador> ganadores = modelo.getGanadores();
            String nombres = ganadores.stream().map(Jugador::nombre).collect(Collectors.joining(", "));
            String mensaje = ganadores.size() > 1 ? "¡Empate entre " + nombres + "!" : "¡Ganador: " + nombres + "!";
            JOptionPane.showMessageDialog(vista, mensaje, "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
        }
        if (actionHandler instanceof LocalGameActionHandler) {
            ((LocalGameActionHandler) actionHandler).setControllingPlayerId(modelo.getJugadorActual().id());
        }
    }

    private class LocalGameActionHandler implements GameActionHandler {

        private final IModeloJuego localModel;
        private int controllingPlayerId;

        public LocalGameActionHandler(IModeloJuego model) {
            this.localModel = model;
        }

        public void setControllingPlayerId(int id) {
            this.controllingPlayerId = id;
        }

        @Override
        public void placeLine(int fila, int col, boolean horizontal) {
            if (localModel.getJugadorActual().id() == controllingPlayerId) {
                localModel.agregarLinea(fila, col, horizontal);
            }
        }
    }
}