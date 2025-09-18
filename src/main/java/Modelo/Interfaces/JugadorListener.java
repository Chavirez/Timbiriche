/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo.Interfaces;

import Objetos.Jugador;
import java.awt.Color;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author santi
 */
public interface JugadorListener {
    
    public void agregarJugador(Jugador jugador);
    
    public List<Jugador> getJugadores();
    
}
