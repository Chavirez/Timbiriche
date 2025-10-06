package itson.timbiriche;

import java.awt.Color;

/**
 * Representa la configuración de un jugador, incluyendo su nombre, avatar y color.
 * Esta clase funciona como un objeto de transferencia de datos (DTO) para encapsular
 * las preferencias y la identificación de un jugador en la partida.
 */
public class ConfiguracionJugador {

    /**
     * El nombre elegido por el jugador.
     */
    private String nombre;
    
    /**
     * La ruta del archivo de la imagen del avatar del jugador.
     */
    private String avatarPath;
    
    /**
     * El color asignado al jugador para marcar líneas y cuadros en el tablero.
     */
    private Color color;

    /**
     * Construye una nueva instancia de configuración del jugador.
     * @param nombre El nombre del jugador.
     * @param avatarPath La ruta al archivo de imagen del avatar.
     * @param color El color asignado al jugador.
     */
    public ConfiguracionJugador(String nombre, String avatarPath, Color color) {
        this.nombre = nombre;
        this.avatarPath = avatarPath;
        this.color = color;
    }

    /**
     * Obtiene el nombre del jugador.
     * @return El nombre actual del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece o actualiza el nombre del jugador.
     * @param nombre El nuevo nombre para el jugador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la ruta del archivo del avatar del jugador.
     * @return Una cadena con la ruta al avatar.
     */
    public String getAvatarPath() {
        return avatarPath;
    }

    /**
     * Establece o actualiza la ruta del archivo del avatar del jugador.
     * @param avatarPath La nueva ruta al archivo del avatar.
     */
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    /**
     * Obtiene el color asignado al jugador.
     * @return El objeto {@link Color} del jugador.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Establece o actualiza el color asignado al jugador.
     * @param color El nuevo {@link Color} para el jugador.
     */
    public void setColor(Color color) {
        this.color = color;
    }
}