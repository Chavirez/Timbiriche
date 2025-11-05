package itson.timbiriche;

/**
 * Interfaz que define las acciones que un jugador puede realizar en el juego.
 * Esto permite que el controlador dirija estas acciones a un modelo local o las
 * envíe por la red.
 */
public interface GameActionHandler {

    /**
     * Intenta colocar una línea en el tablero.
     *
     * @param fila La fila de la línea.
     * @param col La columna de la línea.
     * @param horizontal True si es una línea horizontal, false si es vertical.
     */
    void placeLine(int fila, int col, boolean horizontal);

    // Aquí se podrían añadir otras acciones del juego, como iniciar una nueva partida, etc.
    // void startGame();
    // void sendChatMessage(String message);
}