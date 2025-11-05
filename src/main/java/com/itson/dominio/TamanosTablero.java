/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dominio;

import java.io.Serializable;


public enum TamanosTablero implements Serializable{

    PEQUENO(10, 10),
    MEDIANO(20, 20),
    GRANDE(30, 30);

    private final int ancho;
    private final int alto;

    TamanosTablero(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}
