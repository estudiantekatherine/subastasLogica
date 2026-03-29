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

    //Solo hereda los atributos de usuario
    //Constructores

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

    //Inclusión del metodo equals
    /**
     * Compara dos moderadores por su número de identificación.
     *
     * @param objeto El objeto a comparar.
     * @return true si tienen la misma identificación, false en caso contrario.
     */
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (objeto == null) return false;
        if (!(objeto instanceof Moderador)) return false; //moderador solo igual a moderador
        return super.equals(objeto);
    }

}
