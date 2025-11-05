package itson.timbiriche;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Un componente de panel (JPanel) que representa la vista visual del tablero de Timbiriche.
 * <p>
 * Sus responsabilidades principales son:
 * 1. Dibujar el estado actual del juego (puntos, líneas, cuadros) basándose en los datos del {@link TableroModelo}.
 * 2. Capturar los clics del ratón del usuario, traducirlos a coordenadas del tablero y notificar
 * a un oyente (el controlador) sobre el intento de colocar una línea.
 * <p>
 * Como la 'Vista' en el patrón MVC, este componente no contiene lógica de juego.
 *
 * @author [Tu Nombre/Equipo]
 * @version 1.0
 */
public class TableroVista extends JPanel {

    /** El modelo de datos del juego, utilizado para saber qué dibujar. */
    private final TableroModelo modelo;
    /** Almacena el primer punto que un jugador selecciona al intentar trazar una línea. */
    private Point primerPunto = null;
    /** El oyente (normalmente el controlador) que será notificado de las acciones del usuario. */
    private PlayerInteractionListener interactionListener;

    /**
     * Interfaz funcional que define el contrato para comunicar las interacciones
     * del usuario desde la Vista hacia un Controlador.
     */
    @FunctionalInterface
    public interface PlayerInteractionListener {
        /**
         * Se llama cuando el jugador ha hecho clic en dos puntos adyacentes,
         * indicando su intención de dibujar una línea.
         * @param fila Fila de la línea.
         * @param col Columna de la línea.
         * @param horizontal Verdadero si la línea es horizontal, falso si es vertical.
         */
        void onLineAttempted(int fila, int col, boolean horizontal);
    }

    /**
     * Establece el oyente que recibirá las notificaciones de las acciones del jugador.
     * @param listener El oyente (controlador) a notificar.
     */
    public void setPlayerInteractionListener(PlayerInteractionListener listener) {
        this.interactionListener = listener;
    }

    /**
     * Construye la vista del tablero de juego.
     * @param modelo El modelo de datos del juego que esta vista representará.
     */
    public TableroVista(TableroModelo modelo) {
        this.modelo = modelo;
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (modelo.isJuegoTerminado()) {
                    return; // No procesar clics si el juego ha terminado.
                }
                manejarClick(e.getX(), e.getY());
            }
        });
    }

    /**
     * Procesa un clic del ratón, convirtiendo las coordenadas de píxeles en
     * coordenadas de la cuadrícula y gestionando la selección de dos puntos.
     * @param x La coordenada X del clic del ratón.
     * @param y La coordenada Y del clic del ratón.
     */
    private void manejarClick(int x, int y) {
        int fila = Math.round((float) (y - JuegoConfig.MARGEN) / JuegoConfig.ESPACIO);
        int col = Math.round((float) (x - JuegoConfig.MARGEN) / JuegoConfig.ESPACIO);

        if (fila < 0 || col < 0 || fila >= modelo.getTamaño() || col >= modelo.getTamaño()) {
            return; // Clic fuera de la cuadrícula
        }

        Point nuevoPunto = new Point(fila, col);
        if (primerPunto == null) {
            primerPunto = nuevoPunto;
        } else {
            if (!primerPunto.equals(nuevoPunto) && sonAdyacentes(primerPunto, nuevoPunto)) {
                reportLineAttempt(primerPunto, nuevoPunto);
            }
            primerPunto = null; // Reinicia la selección después del segundo clic
        }
        repaint();
    }

    /**
     * Comprueba si dos puntos en la cuadrícula son adyacentes (no en diagonal).
     * @param p1 El primer punto.
     * @param p2 El segundo punto.
     * @return {@code true} si los puntos son adyacentes, {@code false} en caso contrario.
     */
    private boolean sonAdyacentes(Point p1, Point p2) {
        return (Math.abs(p1.x - p2.x) == 1 && p1.y == p2.y) || (Math.abs(p1.y - p2.y) == 1 && p1.x == p2.x);
    }

    /**
     * Convierte dos puntos adyacentes en una representación de línea canónica
     * y lo notifica al oyente (controlador).
     * @param p1 El primer punto de la línea.
     * @param p2 El segundo punto de la línea.
     */
    private void reportLineAttempt(Point p1, Point p2) {
        if (interactionListener == null) return;
        
        boolean horizontal;
        int fila, col;
        
        // Determina la orientación y coordenadas de la línea
        if (p1.x == p2.x) { // Misma fila, diferentes columnas -> Horizontal
            horizontal = true;
            fila = p1.x;
            col = Math.min(p1.y, p2.y);
        } else { // Misma columna, diferentes filas -> Vertical
            horizontal = false;
            fila = Math.min(p1.x, p2.x);
            col = p1.y;
        }

        interactionListener.onLineAttempted(fila, col, horizontal);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Dibuja todos los componentes del tablero: cuadros, líneas, y puntos.
     * Este método es llamado automáticamente por Swing cuando el panel necesita ser repintado.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(JuegoConfig.ANCHO_LINEA, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int tamaño = modelo.getTamaño();

        // 1. Dibujar Cuadrados completados
        for (int i = 0; i < tamaño - 1; i++) {
            for (int j = 0; j < tamaño - 1; j++) {
                int jugadorId = modelo.getCuadrado(i, j);
                if (jugadorId != 0) {
                    g2.setColor(getColorDeJugador(jugadorId));
                    g2.fillRect(JuegoConfig.MARGEN + j * JuegoConfig.ESPACIO, JuegoConfig.MARGEN + i * JuegoConfig.ESPACIO, JuegoConfig.ESPACIO, JuegoConfig.ESPACIO);
                }
            }
        }

        // 2. Dibujar Líneas horizontales
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño - 1; j++) {
                int jugadorId = modelo.getLineaHorizontal(i, j);
                if (jugadorId != 0) {
                    g2.setColor(getColorDeJugador(jugadorId));
                    g2.drawLine(JuegoConfig.MARGEN + j * JuegoConfig.ESPACIO, JuegoConfig.MARGEN + i * JuegoConfig.ESPACIO, JuegoConfig.MARGEN + (j + 1) * JuegoConfig.ESPACIO, JuegoConfig.MARGEN + i * JuegoConfig.ESPACIO);
                }
            }
        }

        // 3. Dibujar Líneas verticales
        for (int i = 0; i < tamaño - 1; i++) {
            for (int j = 0; j < tamaño; j++) {
                int jugadorId = modelo.getLineaVertical(i, j);
                if (jugadorId != 0) {
                    g2.setColor(getColorDeJugador(jugadorId));
                    g2.drawLine(JuegoConfig.MARGEN + j * JuegoConfig.ESPACIO, JuegoConfig.MARGEN + i * JuegoConfig.ESPACIO, JuegoConfig.MARGEN + j * JuegoConfig.ESPACIO, JuegoConfig.MARGEN + (i + 1) * JuegoConfig.ESPACIO);
                }
            }
        }

        // 4. Dibujar Puntos de la cuadrícula y selección actual
        g2.setColor(Color.DARK_GRAY);
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                g2.fillOval(JuegoConfig.MARGEN + j * JuegoConfig.ESPACIO - JuegoConfig.RADIO_PUNTO, JuegoConfig.MARGEN + i * JuegoConfig.ESPACIO - JuegoConfig.RADIO_PUNTO, JuegoConfig.RADIO_PUNTO * 2, JuegoConfig.RADIO_PUNTO * 2);
            }
        }
        if (primerPunto != null) {
            g2.setColor(Color.GREEN); // Resalta el primer punto seleccionado
            g2.fillOval(JuegoConfig.MARGEN + primerPunto.y * JuegoConfig.ESPACIO - 8, JuegoConfig.MARGEN + primerPunto.x * JuegoConfig.ESPACIO - 8, 16, 16);
        }
    }

    /**
     * Busca el color asociado a un ID de jugador específico.
     * @param jugadorId El ID del jugador.
     * @return El {@link Color} del jugador, o Negro como color por defecto si no se encuentra.
     */
    private Color getColorDeJugador(int jugadorId) {
        for (Jugador jugador : modelo.getJugadores()) {
            if (jugador.id() == jugadorId) {
                return jugador.color();
            }
        }
        return Color.BLACK; // Color de respaldo
    }

    /**
     * {@inheritDoc}
     * <p>
     * Calcula el tamaño preferido para este panel, asegurando que sea lo
     * suficientemente grande para contener todo el tablero con sus márgenes.
     */
    @Override
    public Dimension getPreferredSize() {
        int size = (modelo.getTamaño() - 1) * JuegoConfig.ESPACIO + JuegoConfig.MARGEN * 2;
        return new Dimension(size, size);
    }
}