package itson.timbiriche;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.ImageIcon;

/**
 * Clase de utilidad para gestionar los recursos del juego, como avatares y colores.
 * <p>
 * Proporciona métodos estáticos para cargar imágenes de avatares, listar colores
 * predefinidos y escanear el classpath en busca de archivos de avatar. Esta clase
 * no está diseñada para ser instanciada.

 */
public class Recursos {

    /**
     * Un arreglo de extensiones de archivo de imagen soportadas para filtrar los avatares.
     */
    private static final String[] EXTENSIONES_IMAGEN = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};

    /**
     * Devuelve una lista predefinida de colores disponibles para los jugadores.
     *
     * @return Una lista inmutable de objetos {@link Color}.
     */
    public static List<Color> getColoresDisponibles() {
        return List.of(
                Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE,
                Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.PINK,
                new Color(100, 50, 0), // Marrón
                new Color(150, 150, 150) // Gris
        );
    }

    /**
     * Escanea la carpeta de recursos '/avatars' para encontrar todas las imágenes de avatares disponibles.
     * <p>
     * Este método es compatible tanto con la ejecución desde un sistema de archivos
     * (como en un IDE) como desde un archivo JAR empaquetado.
     *
     * @return Una lista de cadenas con las rutas de los recursos de los avatares.
     * Devuelve una lista vacía si la carpeta no se encuentra o si ocurre un error.
     */
    public static List<String> getAvataresDisponibles() {
        List<String> avatarPaths = new ArrayList<>();
        try {
            URL url = Recursos.class.getResource("/avatars");
            if (url == null) {
                System.err.println("¡ERROR! No se pudo encontrar la carpeta /avatars en los recursos.");
                return Collections.emptyList();
            }
            URI uri = url.toURI();
            // Lógica para leer dentro de un archivo JAR
            if (uri.getScheme().equals("jar")) {
                Map<String, String> env = new HashMap<>();
                try (FileSystem fs = FileSystems.newFileSystem(uri, env)) {
                    Path path = fs.getPath("/avatars");
                    try (Stream<Path> walk = Files.walk(path, 1)) {
                        avatarPaths = walk
                                .filter(Files::isRegularFile)
                                .map(Path::toString)
                                .filter(Recursos::esFormatoImagenSoportado)
                                .map(p -> p.startsWith("/") ? p : "/" + p) // Asegura que la ruta sea absoluta
                                .collect(Collectors.toList());
                    }
                }
            } else { // Lógica para leer desde el sistema de archivos
                File carpetaAvatares = new File(uri);
                File[] archivos = carpetaAvatares.listFiles();
                if (archivos != null) {
                    for (File archivo : archivos) {
                        if (archivo.isFile() && esFormatoImagenSoportado(archivo.getName())) {
                            avatarPaths.add("/avatars/" + archivo.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar los avatares: " + e.getMessage());
        }
        return avatarPaths;
    }

    /**
     * Convierte la ruta de un recurso de avatar en un nombre legible para el usuario.
     * <p>
     * Ejemplo: "/avatars/mi_avatar-cool.png" se convierte en "Mi avatar cool".
     *
     * @param avatarPath La ruta del recurso del avatar.
     * @return Un nombre formateado y legible, o {@code null} si la entrada es nula o vacía.
     */
    public static String getNombreAmigableAvatar(String avatarPath) {
        if (avatarPath == null || avatarPath.isEmpty()) {
            return null;
        }
        String nombreArchivo = avatarPath.substring(avatarPath.lastIndexOf('/') + 1);
        int dotIndex = nombreArchivo.lastIndexOf('.');
        if (dotIndex > 0) {
            nombreArchivo = nombreArchivo.substring(0, dotIndex);
        }
        nombreArchivo = nombreArchivo.replace("_", " ").replace("-", " ");
        if (nombreArchivo.length() > 0) {
            return Character.toUpperCase(nombreArchivo.charAt(0)) + nombreArchivo.substring(1);
        }
        return nombreArchivo;
    }

    /**
     * Verifica si el nombre de un archivo corresponde a un formato de imagen soportado.
     *
     * @param fileName El nombre del archivo a verificar.
     * @return {@code true} si la extensión del archivo está en la lista de formatos
     * soportados, {@code false} en caso contrario.
     */
    private static boolean esFormatoImagenSoportado(String fileName) {
        String lowerCaseFileName = fileName.toLowerCase();
        for (String ext : EXTENSIONES_IMAGEN) {
            if (lowerCaseFileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Carga una imagen de avatar desde una ruta de recurso y la escala a las dimensiones deseadas.
     *
     * @param resourcePath La ruta del recurso de la imagen (ej. "/avatars/avatar1.png").
     * @param width El ancho deseado para la imagen escalada.
     * @param height La altura deseada para la imagen escalada.
     * @return Un {@link ImageIcon} con la imagen escalada, o {@code null} si la imagen
     * no pudo ser cargada.
     */
    public static ImageIcon loadScaledAvatar(String resourcePath, int width, int height) {
        try {
            URL imgUrl = Recursos.class.getResource(resourcePath);
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar avatar desde " + resourcePath + ": " + e.getMessage());
        }
        return null; // Devuelve null si no se puede cargar la imagen
    }
}