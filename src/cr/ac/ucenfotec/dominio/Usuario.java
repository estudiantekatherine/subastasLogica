package cr.ac.ucenfotec.dominio;

import java.time.LocalDate;
import java.time.Period;

/**
 * Clase abstracta que representa a un usuario base de la plataforma de subastas
 * Define los atributos y comportamientos comunes a todos los tipos de usuario:
 * {@link Moderador}, {@link Vendedor} y {@link Coleccionista}.
 *
 * @version 1.0
 */
public abstract class Usuario {

    //Atributos
    /** Nombre completo del usuario */
    private String nombreCompleto;

    /** Número de identificación único del usuario en el sistema  */
    private String numeroIdentificacion;

    /** Fecha de nacimiento del usuario. Se utiliza para calcular su edad actual */
    private LocalDate fechaNacimiento;

    /** Contraseña secreta del usuario para acceder a la plataforma */
    private String contrasena;

    /** Dirección de correo electrónico del usuario, utilizada para comunicaciones */
    private String correoElectronico;

    //Constructores
    /**
     * Constructor por defecto.
     */
    public Usuario() {
    }

    /**
     * Constructor que inicializa todos los atributos del usuario
     *
     * @param nombreCompleto       Nombre completo del usuario.
     * @param numeroIdentificacion Número de identificación único.
     * @param fechaNacimiento      Fecha de nacimiento del usuario.
     * @param contrasena           Contraseña de acceso a la plataforma.
     * @param correoElectronico    Correo electrónico de contacto.
     */
    public Usuario(String nombreCompleto, String numeroIdentificacion,
                   LocalDate fechaNacimiento, String contrasena, String correoElectronico) {
        this.nombreCompleto = nombreCompleto;
        this.numeroIdentificacion = numeroIdentificacion;
        this.fechaNacimiento = fechaNacimiento;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
    }

    //Métodos
    /**
     * Calcula la edad actual del usuario en años completos,
     * tomando como base su fecha de nacimiento y la fecha actual del sistema.
     *
     * @return Edad del usuario expresada en años completos.
     */
    public int calcularEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    /**
     * Verifica si el usuario cumple con el requisito de ser mayor de edad (18 años o más)
     *
     * @return si el usuario tiene 18 años o más retorna true; en caso contrario false
     */
    public boolean esMayorDeEdad() {
        return calcularEdad() >= 18;
    }

    //Getters y Setters
    /**
     * Obtiene el nombre completo del usuario.
     *
     * @return Nombre completo.
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Establece el nombre completo del usuario.
     *
     * @param nombreCompleto Nuevo nombre completo.
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Obtiene el número de identificación del usuario.
     *
     * @return Número de identificación.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Establece el número de identificación del usuario.
     *
     * @param numeroIdentificacion Nuevo número de identificación.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     *
     * @return Fecha de nacimiento.
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del usuario.
     *
     * @param fechaNacimiento Nueva fecha de nacimiento.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return Contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contrasena Nueva contraseña.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return Correo electrónico.
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correoElectronico Nuevo correo electrónico.
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    //To String
    /**
     * Retorna una representación textual con los datos comunes del usuario
     *
     * @return Cadena con los atributos comunes del usuario
     */
    @Override
    public String toString() {
        return "nombre='" + nombreCompleto + '\''
             + ", id='" + numeroIdentificacion + '\''
             + ", edad=" + calcularEdad() + " año(s)"
             + ", correo='" + correoElectronico + '\'';
    }

    //Inclusión del metodo equals
    /**
     * Compara dos usuarios por su número de identificación
     *
     * @param objeto El objeto a comparar.
     * @return true si tienen la misma identificación, false en caso contrario.
     */
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (objeto == null) return false;
        if (!(objeto instanceof Usuario)) return false;
        Usuario otroUsuario = (Usuario) objeto;
        return this.numeroIdentificacion.equals(otroUsuario.numeroIdentificacion);
    }
}
