/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.timbiricheatt;

import java.util.List;

/**
 *
 * @author santi
 */
public class JugadorControl {
    
    public List<Jugador> jugadores;

    public JugadorControl() {
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
    
    public void añadirJugadores(Jugador jugador){
    
        this.jugadores.add(jugador);
        
    }
    
    public void eliminarJugador(Jugador jugador){
    
        this.jugadores.remove(jugador);
        
    }
    
}
