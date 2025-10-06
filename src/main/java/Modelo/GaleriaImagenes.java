package Modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GaleriaImagenes extends JPanel { 
    
    private JLabel preview;  // Hacemos preview un atributo por si quieres acceder desde fuera
    private ImageIcon seleccionado;
    
    public GaleriaImagenes() {
        setLayout(new BorderLayout());

        // Lista de imágenes
        ArrayList<ImageIcon> images = new ArrayList<>();
        images.add(new ImageIcon(getClass().getResource("/Icons/20000.png")));
        images.add(new ImageIcon(getClass().getResource("/Icons/20001.png")));
        images.add(new ImageIcon(getClass().getResource("/Icons/20002.png")));
        images.add(new ImageIcon(getClass().getResource("/Icons/20003.png")));
        images.add(new ImageIcon(getClass().getResource("/Icons/20004.png")));
        images.add(new ImageIcon(getClass().getResource("/Icons/20006.png")));
        images.add(new ImageIcon(getClass().getResource("/Icons/20007.png")));
        images.add(new ImageIcon(getClass().getResource("/Icons/20008.png")));
        images.add(new ImageIcon(getClass().getResource("/Icons/20009.png")));
        images.add(new ImageIcon(getClass().getResource("/Icons/2000a.png")));
        images.add(new ImageIcon(getClass().getResource("/Icons/2000b.png")));
        images.add(new ImageIcon(getClass().getResource("/Icons/2000c.png")));

        // Panel para miniaturas
        JPanel panel = new JPanel(new GridLayout(3, 3, 10, 10));
        preview = new JLabel("Selecciona una imagen", JLabel.CENTER);

        // Crear miniaturas
        for (ImageIcon icon : images) {
            Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel thumb = new JLabel(new ImageIcon(scaled));
            thumb.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

            thumb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    preview.setIcon(icon);
                    preview.setText("");
                    seleccionado = icon;
                }
            });

            panel.add(thumb);
        }

        // Agregar a este JPanel
        add(new JScrollPane(panel), BorderLayout.CENTER);
        add(preview, BorderLayout.SOUTH);
    }

    public ImageIcon getSeleccionado() {
        
        if(seleccionado != null)
            return seleccionado;
        else
            return null;
    }

    
    
    // Opcional: método para obtener la imagen seleccionada
    public Icon getSelectedImage() {
        return preview.getIcon();
    }
}
