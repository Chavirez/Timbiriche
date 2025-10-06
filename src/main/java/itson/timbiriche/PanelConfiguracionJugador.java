package itson.timbiriche;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Un componente de panel (JPanel) que proporciona una interfaz de usuario para
 * editar la configuración de un solo jugador.
 * <p>
 * Este panel permite al usuario establecer un nombre, seleccionar un avatar de una
 * lista (evitando duplicados con otros jugadores) y elegir un color. Está diseñado
 * para ser reutilizable y mostrarse dentro de una ventana de configuración de partida.
 */
public class PanelConfiguracionJugador extends JPanel {

    /** La instancia de configuración del jugador que este panel está editando. */
    private final ConfiguracionJugador config;
    /** Campo de texto para que el usuario ingrese su nombre. */
    private final JTextField txtNombre;
    /** Etiqueta para mostrar una vista previa del avatar seleccionado. */
    private final JLabel lblAvatarPreview;
    /** Panel que muestra el color seleccionado como su color de fondo. */
    private final JPanel panelColorPreview;
    /** La ventana principal (JFrame) que posee este panel, necesaria para los diálogos. */
    private final JFrame ownerFrame;
    /** La lista completa de configuraciones de todos los jugadores en la partida. */
    private final List<ConfiguracionJugador> todasLasConfigs;

    /**
     * Construye un nuevo panel de configuración para un jugador.
     *
     * @param titulo El título que se mostrará en el borde del panel.
     * @param config El objeto {@link ConfiguracionJugador} que este panel modificará.
     * @param owner El {@link JFrame} padre, utilizado para anclar los diálogos modales.
     * @param configs La lista de todas las configuraciones de los jugadores, para
     * validar que no se seleccionen avatares repetidos.
     */
    public PanelConfiguracionJugador(String titulo, ConfiguracionJugador config, JFrame owner, List<ConfiguracionJugador> configs) {
        this.config = config;
        this.ownerFrame = owner;
        this.todasLasConfigs = configs;
        setBorder(BorderFactory.createTitledBorder(titulo));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNombre = new JTextField(config.getNombre(), 15);
        add(txtNombre, gbc);

        // Avatar
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("Avatar:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JPanel panelAvatarSelector = new JPanel(new BorderLayout(5, 0));
        lblAvatarPreview = new JLabel();
        lblAvatarPreview.setPreferredSize(new Dimension(50, 50));
        lblAvatarPreview.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        actualizarAvatarPreview(config.getAvatarPath());
        panelAvatarSelector.add(lblAvatarPreview, BorderLayout.WEST);
        JButton btnSeleccionarAvatar = new JButton("Cambiar Avatar");
        btnSeleccionarAvatar.addActionListener(e -> seleccionarAvatar());
        panelAvatarSelector.add(btnSeleccionarAvatar, BorderLayout.CENTER);
        add(panelAvatarSelector, gbc);

        // Color
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("Color:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JPanel panelColorSelector = new JPanel(new BorderLayout(5, 0));
        panelColorPreview = new JPanel();
        panelColorPreview.setPreferredSize(new Dimension(50, 50));
        panelColorPreview.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        actualizarColorPreview(config.getColor());
        panelColorSelector.add(panelColorPreview, BorderLayout.WEST);
        JButton btnSeleccionarColor = new JButton("Cambiar Color");
        btnSeleccionarColor.addActionListener(e -> seleccionarColor());
        panelColorSelector.add(btnSeleccionarColor, BorderLayout.CENTER);
        add(panelColorSelector, gbc);
    }

    /**
     * Gestiona el proceso de selección de avatar.
     * Recopila los avatares en uso por otros jugadores, muestra el diálogo de
     * selección y actualiza la configuración y la vista previa si se elige uno nuevo.
     */
    private void seleccionarAvatar() {
        Set<String> avataresEnUso = todasLasConfigs.stream()
                .filter(c -> c != this.config) // Excluye la configuración actual
                .map(ConfiguracionJugador::getAvatarPath)
                .collect(Collectors.toSet());
        DialogoSelectorAvatar selector = new DialogoSelectorAvatar(ownerFrame, this.config.getAvatarPath(), avataresEnUso);
        selector.setVisible(true);
        String nuevoAvatarPath = selector.getAvatarSeleccionadoPath();
        if (nuevoAvatarPath != null) {
            this.config.setAvatarPath(nuevoAvatarPath);
            actualizarAvatarPreview(nuevoAvatarPath);
        }
    }

    /**
     * Abre un diálogo JColorChooser para que el usuario seleccione un color y,
     * si se confirma, actualiza la configuración y la vista previa.
     */
    private void seleccionarColor() {
        Color nuevoColor = JColorChooser.showDialog(ownerFrame, "Seleccionar Color", this.config.getColor());
        if (nuevoColor != null) {
            this.config.setColor(nuevoColor);
            actualizarColorPreview(nuevoColor);
        }
    }

    /**
     * Actualiza la etiqueta de vista previa del avatar con una imagen escalada.
     * @param path La ruta del archivo de la imagen del avatar.
     */
    private void actualizarAvatarPreview(String path) {
        lblAvatarPreview.setIcon(Recursos.loadScaledAvatar(path, 50, 50));
    }

    /**
     * Actualiza el color de fondo del panel de vista previa de color.
     * @param color El nuevo color a mostrar.
     */
    private void actualizarColorPreview(Color color) {
        panelColorPreview.setBackground(color);
    }

    /**
     * Recupera el objeto de configuración actualizado desde el panel.
     * <p>
     * Este método primero actualiza el nombre en el objeto de configuración con
     * el valor actual del campo de texto antes de devolverlo.
     *
     * @return El objeto {@link ConfiguracionJugador} con los últimos cambios.
     */
    public ConfiguracionJugador getConfig() {
        config.setNombre(txtNombre.getText().trim());
        return config;
    }
}