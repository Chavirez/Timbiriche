package itson.timbiriche;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

/**
 * Representa un cuadro de diálogo modal que permite al usuario seleccionar un avatar
 * de una lista de opciones disponibles. El diálogo deshabilita los avatares que ya 
 * están siendo utilizados por otros jugadores para asegurar que cada jugador tenga un avatar único.
 */
public class DialogoSelectorAvatar extends JDialog {
    
    /**
     * Almacena la ruta del archivo del avatar que el usuario ha seleccionado.
     * Su valor es {@code null} si el diálogo se cierra sin confirmar una selección.
     */
    private String avatarSeleccionadoPath = null;

    /**
     * Construye y configura el diálogo de selección de avatar.
     * * @param owner El componente {@link Frame} padre sobre el cual se mostrará este diálogo.
     * @param currentAvatarPath La ruta del avatar actualmente seleccionado por el jugador,
     * para preseleccionarlo en la lista.
     * @param avataresEnUso Un conjunto (Set) de cadenas con las rutas de los avatares
     * que ya han sido elegidos por otros jugadores y que deben ser deshabilitados.
     */
    public DialogoSelectorAvatar(Frame owner, String currentAvatarPath, Set<String> avataresEnUso) {
        super(owner, "Seleccionar Avatar", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelAvatares = new JPanel(new GridLayout(0, 4, 10, 10));
        panelAvatares.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        List<String> avataresDisponibles = Recursos.getAvataresDisponibles();
        ButtonGroup grupoAvatares = new ButtonGroup();

        for (String pathCompleto : avataresDisponibles) {
            ImageIcon icon = Recursos.loadScaledAvatar(pathCompleto, 80, 80);
            
            JToggleButton btnAvatar = new JToggleButton(); 
            btnAvatar.setIcon(icon);
            btnAvatar.setActionCommand(pathCompleto);
            btnAvatar.setToolTipText(Recursos.getNombreAmigableAvatar(pathCompleto));

            // Un avatar está en uso si está en la lista de avatares usados Y no es el avatar actual del jugador.
            boolean estaEnUso = avataresEnUso.contains(pathCompleto) && !pathCompleto.equals(currentAvatarPath);
            btnAvatar.setEnabled(!estaEnUso);

            // Preselecciona el avatar actual del jugador
            if (pathCompleto.equals(currentAvatarPath)) {
                btnAvatar.setSelected(true);
                btnAvatar.setEnabled(true);
            }

            grupoAvatares.add(btnAvatar);
            panelAvatares.add(btnAvatar);
        }

        JScrollPane scrollPane = new JScrollPane(panelAvatares);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(e -> {
            ButtonModel seleccion = grupoAvatares.getSelection();
            if (seleccion != null) {
                avatarSeleccionadoPath = seleccion.getActionCommand();
            }
            dispose(); // Cierra el diálogo
        });
        add(btnConfirmar, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }

    /**
     * Obtiene la ruta del archivo del avatar seleccionado por el usuario.
     * @return Una cadena con la ruta del avatar seleccionado, o {@code null} si no se
     * seleccionó ninguno o se canceló el diálogo.
     */
    public String getAvatarSeleccionadoPath() {
        return avatarSeleccionadoPath;
    }
}