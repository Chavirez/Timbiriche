/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;
import Modelo.Interfaces.CuadroListener;

/**
 *
 * @author santi
 */
public class CuadroModelo {
    
    private List<CuadroListener> listeners = new ArrayList<>();

    public void addListener(CuadroListener l) {
        listeners.add(l);
    }

    public void removeListener(CuadroListener l) {
        listeners.remove(l);
    }

    // Notificar un borde seleccionado
    public void notificarBorde(int numero, int borde) {
        for (CuadroListener l : listeners) {
            l.bordeSeleccionado(numero, borde);
        }
    }
}

