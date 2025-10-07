package itson.timbiriche;

public class TableroControlador implements TableroVista.PlayerInteractionListener {

    private final IModeloJuego modelo;
    private final GameActionHandler actionHandler;

    
    public TableroControlador(IModeloJuego modelo, GameActionHandler externalActionHandler) {
        this.modelo = modelo;
        this.actionHandler = (externalActionHandler != null) ? externalActionHandler : new LocalGameActionHandler(modelo);
    }

    
    public TableroControlador(IModeloJuego modelo) {
        this(modelo, null);
    }

   
    public void vincularVista(TableroVista vista) {
        vista.setPlayerInteractionListener(this);
    }

    @Override
    public void onLineAttempted(int fila, int col, boolean horizontal) {
        actionHandler.placeLine(fila, col, horizontal);
    }

    
    private class LocalGameActionHandler implements GameActionHandler {

        private final IModeloJuego localModel;

        public LocalGameActionHandler(IModeloJuego model) {
            this.localModel = model;
        }

        @Override
        public void placeLine(int fila, int col, boolean horizontal) {
            // El modelo es la única fuente de verdad sobre el turno actual,
            // por lo que simplemente se le pide que agregue la línea.
            localModel.agregarLinea(fila, col, horizontal);
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
        }
    }
}