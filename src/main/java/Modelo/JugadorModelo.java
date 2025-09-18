/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Modelo.Interfaces.JugadorListener;
import Objetos.Jugador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author santi
 */
public class JugadorModelo implements JugadorListener{
    
    public List<Jugador> jugadores;

    public JugadorModelo() {
    }
    
    public JugadorModelo(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    @Override
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
    
    @Override
    public void agregarJugador(Jugador jugador){
        
        if(this.jugadores != null){
            jugadores.add(jugador);        
        }
        
        else{
            jugadores = new ArrayList<>();
            jugadores.add(jugador);
        }
        
    }
    
}
