/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Objetos.Jugador;
import java.awt.Color;

public class pnlJugadorConfiguracion extends javax.swing.JPanel {

    GaleriaImagenes g = new GaleriaImagenes();

    public pnlJugadorConfiguracion() {
        initComponents();
        this.setSize(1000, 1000);
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

        j.setAvatar(g.getSeleccionado());
        return j;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        lblNombre = new javax.swing.JLabel();
        fldNombre = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        lblColor = new javax.swing.JLabel();
        lblColor1 = new javax.swing.JLabel();
        pnlGaleria = new javax.swing.JPanel();

        lblNombre.setText("Nombre");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Azul", "Rojo", "Amarillo", "Verde" }));

        lblColor.setText("Color");

        lblColor1.setText("Avatar");

        pnlGaleria.setLayout(new java.awt.GridLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblColor1)
                    .addComponent(lblColor)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre)
                    .addComponent(fldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlGaleria, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblColor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblColor1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlGaleria, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
    }
    // </editor-fold>

    // Variables declaration - do not modify
    private javax.swing.JTextField fldNombre;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblColor1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JPanel pnlGaleria;
    // End of variables declaration

}
