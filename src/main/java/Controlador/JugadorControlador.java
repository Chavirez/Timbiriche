/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Interfaces.JugadorListener;
import Modelo.JugadorModelo;
import Vista.JugadorVista;
import java.util.ArrayList;
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

    public List<JugadorVista> getVistas() {
        return vistas;
    }

    public void setVistas(List<JugadorVista> vistas) {
        this.vistas = vistas;
    }

    public void agregarVista(JugadorVista vista){
    
        if(this.vistas != null){
            vistas.add(vista);        
        }
        
        else{
            vistas = new ArrayList<>();
            vistas.add(vista);
        }
        
    }

    @Override
    public String toString() {
        return "JugadorControlador{" + "modelo=" + modelo + ", vistas=" + vistas + '}';
    }
    
    
}
