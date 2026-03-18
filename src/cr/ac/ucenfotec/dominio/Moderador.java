package cr.ac.ucenfotec.dominio;

import java.time.LocalDate;

/**
 * Representa al moderador único de la plataforma de subastas especializadas
 * El moderador administra la plataforma pero no puede crear subastas ni realizar ofertas
 * Solo puede existir un moderador registrado simultáneamente en el sistema
 *
 *
 * @version 1.0
 */

//Hereda de Usuario
public class Moderador extends Usuario {

    // ===================== CONSTRUCTORES =====================

    /**
     * Constructor por defecto.
     */
    public Moderador() {
        super();
    }

    /**
     * Constructor que inicializa todos los atributos del moderador
     *
     * @param nombreCompleto       Nombre completo del moderador.
     * @param numeroIdentificacion Número de identificación único.
     * @param fechaNacimiento      Fecha de nacimiento (debe ser mayor de edad).
     * @param contrasena           Contraseña de acceso a la plataforma.
     * @param correoElectronico    Correo electrónico de contacto.
     */
    public Moderador(String nombreCompleto, String numeroIdentificacion,
                     LocalDate fechaNacimiento, String contrasena, String correoElectronico) {
        super(nombreCompleto, numeroIdentificacion, fechaNacimiento, contrasena, correoElectronico);
    }

    //To String
    /**
     * Retorna una representación textual del moderador,
     * identificando su rol dentro de la plataforma
     *
     * @return Cadena con el tipo de usuario y sus datos personales.
     */
    @Override
    public String toString() {
        return "Moderador{" + super.toString() + "}";
    }
}
