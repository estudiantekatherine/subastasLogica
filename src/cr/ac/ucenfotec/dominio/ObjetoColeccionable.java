package cr.ac.ucenfotec.dominio;

import java.time.LocalDate;
import java.time.Period;

/**
 * Representa un objeto de valor ofrecido dentro de la plataforma de subastas especializadas.
 * Cada objeto pertenece a un coleccionista o puede ser ofrecido directamente por un vendedor.
 * @version 1.0
 */
public class ObjetoColeccionable {

    /** Estados fisicos válidos del objeto coleccionable*/

    /** Constante que representa un objeto completamente nuevo, sin uso previo. */
    public static final String ESTADO_NUEVO = "Nuevo";

    /** Constante que representa un objeto que ya fue utilizado por su propietario. */
    public static final String ESTADO_USADO             = "Usado";

    /** Constante que representa un objeto antiguo que jamás fue abierto desde su fabricación. */
    public static final String ESTADO_ANTIGUO_SIN_ABRIR = "Antiguo sin abrir";

    //Atributos

    /** Nombre identificativo del objeto coleccionable dentro del sistema. */
    private String nombreObjeto;

    /** Descripción detallada del objeto, incluyendo características, materiales y relevancia. */
    private String descripcionDetallada;

    /**
     * Estado físico actual del objeto.
     * Los valores válidos están definidos como constantes en esta misma clase:
     * ESTADO_NUEVO, ESTADO_USADO, ESTADO_ANTIGUO_SIN_ABRIR.
     */
    private String estadoFisico;

    /** Fecha en que el objeto fue adquirido originalmente por su propietario. */
    private LocalDate fechaDeCompra;

    //Constructores
    /**
     * Constructor por defecto.
     */
    public ObjetoColeccionable() {
    }

    /**
     * Constructor que inicializa todos los atributos del objeto coleccionable.
     *
     * @param nombreObjeto         Nombre del objeto coleccionable.
     * @param descripcionDetallada Descripción completa del objeto.
     * @param estadoFisico         Estado físico: usar las constantes ESTADO_NUEVO, ESTADO_USADO o ESTADO_ANTIGUO_SIN_ABRIR.
     * @param fechaDeCompra        Fecha en que fue adquirido originalmente.
     */
    public ObjetoColeccionable(String nombreObjeto, String descripcionDetallada,
                                String estadoFisico, LocalDate fechaDeCompra) {
        this.nombreObjeto = nombreObjeto;
        this.descripcionDetallada = descripcionDetallada;
        this.estadoFisico = estadoFisico;
        this.fechaDeCompra = fechaDeCompra;
    }

    //Métodos
    /**
     * Calcula la antigüedad del objeto desde su fecha de compra hasta el día de hoy
     *
     * @return Un {@link Period} que representa la antigüedad del objeto
     */
    public Period calcularAntiguedad() {
        return Period.between(fechaDeCompra, LocalDate.now());
    }

    /**
     * Retorna la antigüedad del objeto en un formato legible
     * Ejemplo: "3 año(s), 2 mes(es), 15 día(s)"
     *
     * @return Cadena de texto con la antigüedad desglosada en años, meses y días
     */
    public String getAntiguedadFormateada() {
        Period antiguedad = calcularAntiguedad();
        return antiguedad.getYears() + " año(s), "
             + antiguedad.getMonths() + " mes(es), "
             + antiguedad.getDays() + " día(s)";
    }

    // Getters y Setters
    /**
     * Obtiene el nombre del objeto coleccionable
     *
     * @return Nombre del objeto
     */
    public String getNombreObjeto() {
        return nombreObjeto;
    }

    /**
     * Establece el nombre del objeto coleccionable
     *
     * @param nombreObjeto Nuevo nombre del objeto
     */
    public void setNombreObjeto(String nombreObjeto) {
        this.nombreObjeto = nombreObjeto;
    }

    /**
     * Obtiene la descripción detallada del objeto
     *
     * @return Descripción del objeto
     */
    public String getDescripcionDetallada() {
        return descripcionDetallada;
    }

    /**
     * Establece la descripción detallada del objeto
     *
     * @param descripcionDetallada Nueva descripción del objeto
     */
    public void setDescripcionDetallada(String descripcionDetallada) {
        this.descripcionDetallada = descripcionDetallada;
    }

    /**
     * Obtiene el estado físico actual del objeto
     *
     * @return Estado físico del objeto (usa las constantes ESTADO_* de esta clase)
     */
    public String getEstadoFisico() {
        return estadoFisico;
    }

    /**
     * Establece el estado físico del objeto.
     * Se recomienda usar las constantes definidas: ESTADO_NUEVO, ESTADO_USADO, ESTADO_ANTIGUO_SIN_ABRIR
     *
     * @param estadoFisico Nuevo estado físico del objeto
     */
    public void setEstadoFisico(String estadoFisico) {
        this.estadoFisico = estadoFisico;
    }

    /**
     * Obtiene la fecha de compra del objeto
     *
     * @return Fecha de compra
     */
    public LocalDate getFechaDeCompra() {
        return fechaDeCompra;
    }

    /**
     * Establece la fecha de compra del objeto
     *
     * @param fechaDeCompra Nueva fecha de compra
     */
    public void setFechaDeCompra(LocalDate fechaDeCompra) {
        this.fechaDeCompra = fechaDeCompra;
    }

    //ToString
    /**
     * Retorna una representación textual completa del objeto coleccionable,
     * incluyendo su antigüedad calculada dinámicamente.
     *
     * @return Cadena con todos los atributos del objeto coleccionable.
     */
    @Override
    public String toString() {
        return "ObjetoColeccionable{"
             + "nombre='" + nombreObjeto + '\''
             + ", descripcion='" + descripcionDetallada + '\''
             + ", estado=" + estadoFisico
             + ", fechaCompra=" + fechaDeCompra
             + ", antiguedad=" + getAntiguedadFormateada()
             + '}';
    }

    //Inclusión del metodo equals
    /**
     * Compara dos objetos coleccionables por su nombre
     * Dos objetos coleccionables son iguales si tienen el mismo nombre
     *
     * @param objeto El objeto a comparar.
     * @return true si tienen el mismo nombre, false en caso contrario.
     */
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (objeto == null) return false;
        if (!(objeto instanceof ObjetoColeccionable)) return false;
        ObjetoColeccionable otroObjeto = (ObjetoColeccionable) objeto;
        return this.nombreObjeto.equalsIgnoreCase(otroObjeto.nombreObjeto);
    }
}
