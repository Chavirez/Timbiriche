/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.timbiriche;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joseq
 */
public class ModeloJuegoMock implements IModeloJuego {

    private final List<Jugador> jugadores;
    private int jugadorActualIdx = 0;
    private final int[] puntajes;
    private final List<ModeloListener> listeners = new ArrayList<>();

    public ModeloJuegoMock(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.puntajes = new int[jugadores.size()];
    }

    @Override
    public boolean agregarLinea(int fila, int col, boolean horizontal) {
        System.out.println("MOCK: " + getJugadorActual().nombre() + " colocó una línea.");
        puntajes[jugadorActualIdx]++; // El jugador siempre anota un punto.
        jugadorActualIdx = (jugadorActualIdx + 1) % jugadores.size(); // Siempre pasa el turno.
        notificarCambios();
        return true;
    }

    //  Métodos de estado (devuelven datos fijos o simples) 
    @Override
    public int getTamaño() {
        return JuegoConfig.TAMANIO_TABLERO;
    }

    @Override
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    @Override
    public Jugador getJugadorActual() {
        return jugadores.get(jugadorActualIdx);
    }

    @Override
    public int[] getPuntajes() {
        return puntajes.clone();
    }

    @Override
    public int getCuadrado(int f, int c) {
        return 0;
    } // Nunca hay cuadrados

    @Override
    public boolean isJuegoTerminado() {
        return false;
    } // El juego mock nunca termina

    @Override
    public int getLineaHorizontal(int f, int c) {
        return 0;
    } // Nunca hay líneas

    @Override
    public int getLineaVertical(int f, int c) {
        return 0;
    } // Nunca hay líneas

    @Override
    public List<Jugador> getGanadores() {
        return new ArrayList<>();
    }

    //  Implementación del patrón Observer 
    @Override
    public void agregarListener(ModeloListener listener) {
        listeners.add(listener);
    }

    private void notificarCambios() {
        for (ModeloListener l : listeners) {
            l.modeloCambiado();
        }

    }
}
