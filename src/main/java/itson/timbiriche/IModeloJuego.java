/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.timbiriche;

import java.util.List;

/**
 *
 * @author joseq
 */
public interface IModeloJuego {
    
    //  Métodos para obtener el estado del juego
    int getTamaño();
    List<Jugador> getJugadores();
    Jugador getJugadorActual();
    int[] getPuntajes();
    int getCuadrado(int f, int c);
    boolean isJuegoTerminado();
    int getLineaHorizontal(int f, int c);
    int getLineaVertical(int f, int c);
    List<Jugador> getGanadores();

    //  Métodos para modificar el estado del juego 
    boolean agregarLinea(int fila, int col, boolean horizontal);

    //  Métodos para el patrón Observer 
    void agregarListener(ModeloListener listener);
    
}
