package Controlador;

import Modelo.Interfaces.CuadroListener;
import Modelo.CuadroModelo;
import Vista.CuadroVista;

public class CuadroControlador implements CuadroListener {

    private CuadroModelo modelo;
    private CuadroVista vista;

    public CuadroControlador(CuadroModelo modelo, CuadroVista vista) {
        this.modelo = modelo;
        this.vista = vista;
        
//        vista.setListener(modelo);
        
        modelo.addListener(this);
    }

    @Override
    public void bordeSeleccionado(int numeroCuadro, int borde) {
        
        System.out.println("Controlador: cuadro " + numeroCuadro + " clic en " + borde);
        
    }
}
