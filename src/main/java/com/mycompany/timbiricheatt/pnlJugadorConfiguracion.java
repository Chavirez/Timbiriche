/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.timbiricheatt;

public class pnlJugadorConfiguracion extends javax.swing.JPanel {

    GaleriaImagenes g = new GaleriaImagenes();

    public pnlJugadorConfiguracion() {
        initComponents();
        inicializarGaleria();
    }

    private void inicializarGaleria() {
        pnlGaleria.add(g);
        this.repaint();
        this.revalidate();
    }

    public Jugador getJugador() {
        Jugador j = new Jugador();
        j.setNombre(fldNombre.getText());

        switch (jComboBox1.getSelectedIndex()) {
            case 0 -> j.setColor(Color.blue);
            case 1 -> j.setColor(Color.red);
            case 2 -> j.setColor(Color.yellow);
            case 3 -> j.setColor(Color.green);
        }

        j.setAvatar(g.getSelectedImage());
        return j;
    }
    
    // aquí va tu initComponents() generado por NetBeans tal cual
}
