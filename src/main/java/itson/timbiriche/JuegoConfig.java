package itson.timbiriche;

/**
 * Contiene constantes de configuración globales para el juego Timbiriche.
 * <p>
 * Esta clase centraliza valores fijos que definen el tamaño y la apariencia del
 * tablero de juego, como las dimensiones, márgenes y grosores de línea. No está
 * diseñada para ser instanciada.
 */
public class JuegoConfig {

    /**
     * Define el tamaño del tablero como un número de cuadros por lado. Por
     * ejemplo, un valor de 5 crea un tablero de 5x5 cuadros.
     */
    public static final int TAMANIO_TABLERO = 10;

    /**
     * Define el grosor en píxeles de las líneas que los jugadores dibujan.
     */
    public static final int ANCHO_LINEA = 4;

    /**
     * El margen en píxeles entre el borde del panel y la cuadrícula del juego.
     */
    public static final int MARGEN = 25;

    /**
     * La distancia en píxeles entre los puntos adyacentes de la cuadrícula.
     */
    public static final int ESPACIO = 50;

    /**
     * El radio en píxeles de los puntos que forman los vértices de la
     * cuadrícula.
     */
    public static final int RADIO_PUNTO = 7;
}
