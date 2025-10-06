package itson.timbiriche;

import java.awt.Color;
import java.util.Objects;

/**
 * Representa a un jugador en el juego de Timbiriche.

 * Esta es una clase de datos inmutable (récord) que almacena la información
 * esencial de un jugador. La validación en el constructor garantiza la integridad
 * de los datos, asegurando que los campos principales no sean nulos.
 *
 * @param id El identificador numérico único para el jugador.
 * @param nombre El nombre del jugador. No puede ser nulo.
 * @param avatarPath La ruta del archivo para la imagen del avatar del jugador. No puede ser nula.
 * @param color El {@link Color} asignado al jugador para sus líneas y cuadros. No puede ser nulo.
 * @author [Tu Nombre/Equipo]
 * @version 1.0
 */
public record Jugador(int id, String nombre, String avatarPath, Color color) {

    /**
     * Constructor compacto que valida los parámetros del jugador.
     * Este constructor se ejecuta antes de que los campos sean asignados para
     * asegurar que el nombre, la ruta del avatar y el color no sean nulos.
     * @throws NullPointerException si alguno de los campos requeridos es nulo.
     */
    public Jugador   {
        Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        Objects.requireNonNull(avatarPath, "La ruta del avatar no puede ser nula");
        Objects.requireNonNull(color, "El color no puede ser nulo");
    }
}