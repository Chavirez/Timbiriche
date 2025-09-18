/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Interfaces.JugadorListener;
import Modelo.JugadorModelo;
import Vista.JugadorVista;
import java.util.List;

/**
 *
 * @author santi
 */
public class JugadorControlador {
    
    JugadorModelo modelo;
    List<JugadorVista> vistas;

    public JugadorControlador() {
    }

    public JugadorControlador(JugadorModelo modelo, List<JugadorVista> vistas) {
        this.modelo = modelo;
        this.vistas = vistas;
    }

    public JugadorModelo getModelo() {
        return modelo;
    }

    public void setModelo(JugadorModelo modelo) {
        this.modelo = modelo;
    }

    
    
}
