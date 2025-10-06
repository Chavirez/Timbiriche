package itson.timbiriche;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import itson.timbiriche.ModeloListener;

/**
 * El corazón del juego. Contiene toda la información sobre el estado actual
 * de la partida y la lógica para modificarlo.
 *
 * - No sabe nada sobre cómo se dibuja el juego (eso es trabajo de la Vista).
 * - No sabe nada sobre clics del mouse (eso es trabajo del Controlador).
 *
 * Simplemente mantiene los datos (líneas, cuadrados, puntajes) y ofrece métodos
 * para que el Controlador los modifique (ej. `agregarLinea`).
 *
 * Utiliza el patrón "Observer" (Oyente/Listener) para notificar a quien esté
 * interesado (como el `PanelPrincipal`) cada vez que su estado cambia.
 */
public class TableroModelo implements IModeloJuego {
    private final int tamaño; // Número de puntos por lado (ej. 5 para un tablero de 4x4)
    // Matrices que guardan el ID del jugador que dibujó cada línea. 0 si no hay línea.
    private final int[][] lineasHorizontales;
    private final int[][] lineasVerticales;
    // Matriz que guarda el ID del jugador que completó cada cuadrado. 0 si no está completo.
    private final int[][] cuadrados;
    private int jugadorActualIdx = 0; // Índice del jugador en la lista de jugadores.
    private final List<Jugador> jugadores;
    private final int[] puntajes; // El índice corresponde al jugador.
    private final int totalCuadrados;
    private int cuadradosCompletados = 0;
    // Lista de "oyentes" que serán notificados de los cambios.
    private final List<ModeloListener> listeners = new ArrayList<>();

    public TableroModelo(int tamaño, List<Jugador> jugadores) {
        this.tamaño = tamaño;
        this.jugadores = Objects.requireNonNull(jugadores);
        this.puntajes = new int[jugadores.size()];
        this.totalCuadrados = (tamaño - 1) * (tamaño - 1);
        
        lineasHorizontales = new int[tamaño][tamaño - 1];
        lineasVerticales = new int[tamaño - 1][tamaño];
        cuadrados = new int[tamaño - 1][tamaño - 1];
    }

    // --- Getters (Métodos para obtener información del modelo) ---
    public int getTamaño() { return tamaño; }
    public List<Jugador> getJugadores() { return jugadores; }
    public Jugador getJugadorActual() { return jugadores.get(jugadorActualIdx); }
    public int[] getPuntajes() { return puntajes.clone(); } // .clone() para evitar modificaciones externas.
    public int getCuadrado(int f, int c) { return cuadrados[f][c]; }
    public boolean isJuegoTerminado() { return cuadradosCompletados == totalCuadrados; }
    public int getLineaHorizontal(int f, int c) { return lineasHorizontales[f][c]; }
    public int getLineaVertical(int f, int c) { return lineasVerticales[f][c]; }

    /**
     * Determina quién o quiénes son los ganadores al final del juego.
     * @return Una lista de jugadores. Puede tener más de uno en caso de empate.
     */
    public List<Jugador> getGanadores() {
        if (!isJuegoTerminado()) return new ArrayList<>();
        int maxPuntaje = -1;
        for (int puntaje : puntajes) if (puntaje > maxPuntaje) maxPuntaje = puntaje;
        List<Jugador> ganadores = new ArrayList<>();
        for (int i = 0; i < jugadores.size(); i++) if (puntajes[i] == maxPuntaje) ganadores.add(jugadores.get(i));
        return ganadores;
    }

    // --- Lógica del Juego ---
    /**
     * Intenta agregar una línea al tablero. Este es el método principal de la lógica del juego.
     * @param fila Fila de la línea.
     * @param col Columna de la línea.
     * @param horizontal True si es horizontal.
     * @return true si la línea se pudo agregar, false si ya existía.
     */
    public boolean agregarLinea(int fila, int col, boolean horizontal) {
        int jugadorId = getJugadorActual().id();
        
        // Verifica si la línea ya está ocupada.
        if (horizontal) {
            if (lineasHorizontales[fila][col] != 0) return false;
            lineasHorizontales[fila][col] = jugadorId;
        } else {
            if (lineasVerticales[fila][col] != 0) return false;
            lineasVerticales[fila][col] = jugadorId;
        }
        //-----------------Maybe Mock-----------------------------------
        // Después de poner la línea, verifica si se completó algún cuadrado.
        boolean completoCuadrado = verificarCuadrados();
        
        // Si NO se completó un cuadrado, pasa el turno al siguiente jugador.
        // Si SÍ se completó, el jugador actual vuelve a tirar.
        if (!completoCuadrado) {
            jugadorActualIdx = (jugadorActualIdx + 1) % jugadores.size();
        }
        
        // Notifica a todos los oyentes que el estado del juego ha cambiado.
        notificarCambios();
        return true;
    }

    
    //---------------------Mover al mock----------------------
    /**
     * Revisa todo el tablero en busca de nuevos cuadrados completados.
     * @return true si se completó al menos un cuadrado en esta jugada.
     */
    private boolean verificarCuadrados() {
        boolean seCompletoUnCuadrado = false;
        for (int i = 0; i < tamaño - 1; i++) {
            for (int j = 0; j < tamaño - 1; j++) {
                // Si un cuadrado no tiene dueño y sus 4 líneas están puestas...
                if (cuadrados[i][j] == 0 &&
                    lineasHorizontales[i][j] != 0 && lineasHorizontales[i + 1][j] != 0 &&
                    lineasVerticales[i][j] != 0 && lineasVerticales[i][j + 1] != 0)
                {
                    // ... se le asigna al jugador actual.
                    cuadrados[i][j] = getJugadorActual().id();
                    puntajes[jugadorActualIdx]++;
                    cuadradosCompletados++;
                    seCompletoUnCuadrado = true;
                }
            }
        }
        return seCompletoUnCuadrado;
    }

    // --- Patrón Observer ---
    public void agregarListener(ModeloListener listener) { listeners.add(listener); }
    private void notificarCambios() { for (ModeloListener l : listeners) l.modeloCambiado(); }
}