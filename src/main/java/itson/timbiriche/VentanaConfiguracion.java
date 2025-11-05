package itson.timbiriche;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Un diálogo modal (JDialog) para configurar los detalles de los jugadores antes
 * de iniciar una partida de Timbiriche.
 * <p>
 * Esta ventana permite a los usuarios establecer el nombre, avatar y color para
 * cada jugador. Además, realiza validaciones para asegurar que no haya nombres,
 * avatares o colores duplicados entre los jugadores.
 *
 * @author [Tu Nombre/Equipo]
 * @version 1.0
 */
public class VentanaConfiguracion extends JDialog {

    /** Lista de los componentes UI (JPanel) que gestionan la configuración de cada jugador. */
    private final List<PanelConfiguracionJugador> panelesConfigUI;
    /** Lista de los objetos de datos de configuración que respaldan a los paneles UI. */
    private final List<ConfiguracionJugador> configsMutables;
    /**
     * La lista final de jugadores inmutables. Es {@code null} hasta que la
     * configuración se confirma exitosamente con el botón "Jugar".
     */
    private List<Jugador> jugadoresConfigurados = null;

    /**
     * Construye la ventana de configuración.
     * <p>
     * Crea paneles de configuración para el número especificado de jugadores,
     * asignándoles nombres, avatares y colores predeterminados.
     *
     * @param owner El {@link JFrame} propietario de este diálogo.
     * @param numJugadores El número de jugadores a configurar.
     */
    public VentanaConfiguracion(JFrame owner, int numJugadores) {
        super(owner, "Configurar Partida", true);
        panelesConfigUI = new ArrayList<>();
        configsMutables = new ArrayList<>();

        // Carga los recursos disponibles para asignar valores por defecto
        List<String> avatares = Recursos.getAvataresDisponibles();
        List<Color> colores = Recursos.getColoresDisponibles();

        // Crea configuraciones iniciales para cada jugador
        for (int i = 0; i < numJugadores; i++) {
            configsMutables.add(new ConfiguracionJugador(
                    "Jugador " + (i + 1),
                    avatares.get(i % avatares.size()),
                    colores.get(i % colores.size())
            ));
        }

        setLayout(new BorderLayout(10, 10));
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

        // Crea y añade un panel de UI para cada configuración de jugador
        for (int i = 0; i < configsMutables.size(); i++) {
            PanelConfiguracionJugador panel = new PanelConfiguracionJugador("Jugador " + (i + 1), configsMutables.get(i), owner, configsMutables);
            panelesConfigUI.add(panel);
            panelCentral.add(panel);
        }

        JButton btnJugar = new JButton("Jugar");
        btnJugar.addActionListener(e -> iniciarJuego());
        add(new JScrollPane(panelCentral), BorderLayout.CENTER);
        add(btnJugar, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(owner);
    }

    /**
     * Inicia el proceso de validación y creación de jugadores al pulsar "Jugar".
     * Si la configuración es válida, crea la lista de jugadores y cierra el diálogo.
     * Si no, muestra un mensaje de error.
     */
    private void iniciarJuego() {
        // Obtiene la configuración final desde cada panel de UI
        List<ConfiguracionJugador> configsFinales = panelesConfigUI.stream()
                .map(PanelConfiguracionJugador::getConfig)
                .collect(Collectors.toList());
        try {
            validarConfiguraciones(configsFinales);
            // Si la validación es exitosa, convierte las configuraciones en jugadores inmutables
            jugadoresConfigurados = IntStream.range(0, configsFinales.size())
                    .mapToObj(i -> new Jugador(i + 1, configsFinales.get(i).getNombre(), configsFinales.get(i).getAvatarPath(), configsFinales.get(i).getColor()))
                    .collect(Collectors.toList());
            setVisible(false); // Cierra el diálogo
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Configuración", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Valida una lista de configuraciones de jugadores.
     * Asegura que los nombres no estén vacíos y que no haya nombres, avatares o
     * colores repetidos.
     *
     * @param configs La lista de configuraciones a validar.
     * @throws IllegalArgumentException si se encuentra alguna inconsistencia.
     */
    private void validarConfiguraciones(List<ConfiguracionJugador> configs) {
        Set<String> nombres = new HashSet<>();
        Set<String> avatares = new HashSet<>();
        Set<Color> colores = new HashSet<>();
        for (ConfiguracionJugador c : configs) {
            if (c.getNombre().isBlank()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío.");
            }
            if (!nombres.add(c.getNombre())) {
                throw new IllegalArgumentException("Nombre repetido: " + c.getNombre());
            }
            if (!avatares.add(c.getAvatarPath())) {
                throw new IllegalArgumentException("Un avatar está repetido.");
            }
            if (!colores.add(c.getColor())) {
                throw new IllegalArgumentException("Un color está repetido.");
            }
        }
    }

    /**
     * Muestra el diálogo de configuración y espera a que el usuario lo cierre.
     *
     * @return Una lista de {@link Jugador} con los datos configurados si el usuario
     * presiona "Jugar", o {@code null} si cierra la ventana.
     */
    public List<Jugador> mostrarDialogo() {
        setVisible(true); // Bloquea hasta que el diálogo se cierre
        return jugadoresConfigurados;
    }
}