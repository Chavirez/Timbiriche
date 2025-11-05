/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dominio;

import java.io.Serializable;

/**
 *
 * @author santi
 */
public class Tablero implements Serializable{
    
    public TamanosTablero tamano;
    public Punto[] puntos;

    public Tablero(TamanosTablero tamano) {
        this.tamano = tamano;
    }

    public TamanosTablero getTamano() {
        return tamano;
    }

    public void setTamano(TamanosTablero tamano) {
        this.tamano = tamano;
    }

    public Punto[] getPuntos() {
        return puntos;
    }

    public void setPuntos(Punto[] puntos) {
        this.puntos = puntos;
    }    
    
}
