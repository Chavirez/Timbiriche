/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.timbiriche;

import java.awt.Point;

/**
 *
 * @author NaderCroft
 */
public class ModeloJuego {

    public ModeloJuego() {
    }

    /**
     * Comprueba si dos puntos en la cuadrícula son adyacentes (no en diagonal).
     * @param p1 El primer punto.
     * @param p2 El segundo punto.
     * @return {@code true} si los puntos son adyacentes, {@code false} en caso contrario.
     */
    private boolean sonAdyacentes(Point p1, Point p2) {
        return (Math.abs(p1.x - p2.x) == 1 && p1.y == p2.y) || (Math.abs(p1.y - p2.y) == 1 && p1.x == p2.x);
    }
    
    
}
