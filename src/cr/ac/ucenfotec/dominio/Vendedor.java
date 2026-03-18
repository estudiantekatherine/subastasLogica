package cr.ac.ucenfotec.dominio;

import java.time.LocalDate;

/**
 * Representa a un vendedor registrado en la plataforma de subastas especializadas
 * El vendedor puede crear subastas con sus propios objetos, pero no puede realizar ofertas
 * Posee una puntuación de reputación que refleja su historial en la plataforma
 *
 * @version 1.0
 */
public class Vendedor extends Usuario {

    //Atributos
    /**
     * Puntuación de reputación del vendedor dentro de la plataforma
     * Refleja su historial de transacciones y confiabilidad
     */
    private double puntuacionReputacion;

    /** Dirección física de residencia del vendedor. */
    private String direccionResidencia;

    //Constructores

    /**
     * Constructor por defecto.
     */
    public Vendedor() {
        super();
    }

    /**
     * Constructor que inicializa todos los atributos del vendedor.
     *
     * @param nombreCompleto       Nombre completo del vendedor.
     * @param numeroIdentificacion Número de identificación único.
     * @param fechaNacimiento      Fecha de nacimiento (debe ser mayor de edad).
     * @param contrasena           Contraseña de acceso a la plataforma.
     * @param correoElectronico    Correo electrónico de contacto.
     * @param puntuacionReputacion Puntuación inicial de reputación (normalmente 0.0).
     * @param direccionResidencia  Dirección física de residencia.
     */
    public Vendedor(String nombreCompleto, String numeroIdentificacion,
                    LocalDate fechaNacimiento, String contrasena, String correoElectronico,
                    double puntuacionReputacion, String direccionResidencia) {
        super(nombreCompleto, numeroIdentificacion, fechaNacimiento, contrasena, correoElectronico);
        this.puntuacionReputacion = puntuacionReputacion;
        this.direccionResidencia = direccionResidencia;
    }

    //Getters y Setters

    /**
     * Obtiene la puntuación de reputación del vendedor.
     *
     * @return Puntuación de reputación.
     */
    public double getPuntuacionReputacion() {
        return puntuacionReputacion;
    }

    /**
     * Establece la puntuación de reputación del vendedor.
     *
     * @param puntuacionReputacion Nueva puntuación de reputación.
     */
    public void setPuntuacionReputacion(double puntuacionReputacion) {
        this.puntuacionReputacion = puntuacionReputacion;
    }

    /**
     * Obtiene la dirección de residencia del vendedor.
     *
     * @return Dirección de residencia.
     */
    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    /**
     * Establece la dirección de residencia del vendedor.
     *
     * @param direccionResidencia Nueva dirección de residencia.
     */
    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    //ToString

    /**
     * Retorna una representación textual completa del vendedor,
     * incluyendo su puntuación y dirección adicionales a los datos base del usuario
     *
     * @return Cadena con todos los atributos del vendedor.
     */
    @Override
    public String toString() {
        return "Vendedor{"
             + super.toString()
             + ", puntuacion=" + puntuacionReputacion
             + ", direccion='" + direccionResidencia + '\''
             + "}";
    }
}
