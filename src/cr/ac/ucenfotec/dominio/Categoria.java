package cr.ac.ucenfotec.dominio;

/**
 * Representa una categoría temática dentro de la plataforma de subastas.
 * Las subastas se clasifican por categoría para facilitar la búsqueda
 * y organización de los objetos ofrecidos.
 * Ejemplos: "Monedas antiguas", "Cómics", "Figuras de colección".
 *
 * @version 1.0
 */
public class Categoria {

    // Atributos

    /** Nombre identificativo de la categoría dentro del sistema. */
    private String nombreCategoria;

    /** Descripción detallada del tipo de objetos que agrupa esta categoría. */
    private String descripcionCategoria;

    /** Indica si la categoría está activa y disponible para ser asignada a subastas. */
    private boolean activa;

    // Metodos
    //Constructores

    /**
     * Constructor por defecto.
     * Inicializa la categoría con valores neutros y activa por defecto.
     */
    public Categoria() {
        this.nombreCategoria      = "";
        this.descripcionCategoria = "";
        this.activa               = true;
    }

    /**
     * Constructor que inicializa todos los atributos de la categoría.
     *
     * @param nombreCategoria      Nombre de la categoría.
     * @param descripcionCategoria Descripción del tipo de objetos que agrupa.
     */
    public Categoria(String nombreCategoria, String descripcionCategoria) {
        this.nombreCategoria      = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
        this.activa               = true;
    }

    // Getters y Setters

    /**
     * Obtiene el nombre de la categoría.
     *
     * @return Nombre de la categoría.
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param nombreCategoria Nuevo nombre de la categoría.
     */
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * Obtiene la descripción de la categoría.
     *
     * @return Descripción de la categoría.
     */
    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    /**
     * Establece la descripción de la categoría.
     *
     * @param descripcionCategoria Nueva descripción de la categoría.
     */
    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    /**
     * Indica si la categoría está activa en el sistema.
     *
     * @return true si está activa, false si fue desactivada.
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * Establece si la categoría está activa o no.
     *
     * @param activa true para activarla, false para desactivarla.
     */
    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    // ToString

    /**
     * Retorna una representación textual completa de la categoría.
     *
     * @return Cadena con todos los atributos de la categoría.
     */
    @Override
    public String toString() {
        return "Categoria{"
                + "nombre='"      + nombreCategoria      + '\''
                + ", descripcion='" + descripcionCategoria + '\''
                + ", activa="     + activa
                + '}';
    }

    // Metodo equals

    /**
     * Compara dos categorías por su nombre.
     * Dos categorías son iguales si tienen el mismo nombre sin importar mayúsculas.
     *
     * @param objeto El objeto a comparar.
     * @return true si tienen el mismo nombre, false en caso contrario.
     */
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (objeto == null) return false;
        if (!(objeto instanceof Categoria)) return false;
        Categoria otraCategoria = (Categoria) objeto;
        return this.nombreCategoria.equalsIgnoreCase(otraCategoria.nombreCategoria);
    }
}
