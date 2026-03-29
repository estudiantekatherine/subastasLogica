package cr.ac.ucenfotec.dominio;

/**
 * Representa una oferta económica presentada por un coleccionista sobre una subasta activa
 * Cada oferta registra el nombre y puntuación del oferente al momento de realizarla,
 * así como el precio propuesto por los objetos subastados
 *
 *
 * @version 1.0
 */
public class Oferta {

    //Atributos
    /** Nombre completo del coleccionista que realizó la oferta. */
    private String nombreDelOferente;

    /**
     * Puntuación de reputación del oferente en el momento exacto en que realizó la oferta
     */
    private double puntuacionDelOferente;

    /** Precio en dinero que el coleccionista propone por los objetos subastados */
    private double precioOfertado;

    /**
     * Referencia directa al coleccionista que realizó la oferta
     * Permite aplicar validaciones de negocio sobre el oferente
     */
    private Coleccionista coleccionistaOferente;

    //Constructores

    /**
     * Constructor por defecto.
     */
    public Oferta() {
    }

    /**
     * Constructor que inicializa todos los atributos de la oferta
     * El nombre y la puntuación del oferente se toman automáticamente del coleccionista
     *
     * @param coleccionistaOferente Coleccionista que realiza la oferta.
     * @param precioOfertado        Precio propuesto por los objetos subastados.
     */
    public Oferta(Coleccionista coleccionistaOferente, double precioOfertado) {
        this.coleccionistaOferente = coleccionistaOferente;
        this.nombreDelOferente = coleccionistaOferente.getNombreCompleto();
        this.puntuacionDelOferente = coleccionistaOferente.getPuntuacionReputacion();
        this.precioOfertado = precioOfertado;
    }

    //Getters y Setters
    /**
     * Obtiene el nombre del oferente
     *
     * @return Nombre completo del coleccionista que realizó la oferta
     */
    public String getNombreDelOferente() {
        return nombreDelOferente;
    }

    /**
     * Establece el nombre del oferente.
     *
     * @param nombreDelOferente Nuevo nombre del oferente.
     */
    public void setNombreDelOferente(String nombreDelOferente) {
        this.nombreDelOferente = nombreDelOferente;
    }

    /**
     * Obtiene la puntuación de reputación del oferente al momento de realizar la oferta.
     *
     * @return Puntuación del oferente.
     */
    public double getPuntuacionDelOferente() {
        return puntuacionDelOferente;
    }

    /**
     * Establece la puntuación del oferente.
     *
     * @param puntuacionDelOferente Nueva puntuación del oferente.
     */
    public void setPuntuacionDelOferente(double puntuacionDelOferente) {
        this.puntuacionDelOferente = puntuacionDelOferente;
    }

    /**
     * Obtiene el precio ofertado por el coleccionista.
     *
     * @return Precio en dinero de la oferta.
     */
    public double getPrecioOfertado() {
        return precioOfertado;
    }

    /**
     * Establece el precio ofertado.
     *
     * @param precioOfertado Nuevo precio de la oferta.
     */
    public void setPrecioOfertado(double precioOfertado) {
        this.precioOfertado = precioOfertado;
    }

    /**
     * Obtiene la referencia al coleccionista que realizó la oferta.
     *
     * @return Coleccionista oferente.
     */
    public Coleccionista getColeccionistaOferente() {
        return coleccionistaOferente;
    }

    /**
     * Establece la referencia al coleccionista oferente.
     *
     * @param coleccionistaOferente Nuevo coleccionista oferente.
     */
    public void setColeccionistaOferente(Coleccionista coleccionistaOferente) {
        this.coleccionistaOferente = coleccionistaOferente;
    }

    //To String

    /**
     * Retorna una representación textual completa de la oferta,
     * mostrando el oferente, su puntuación y el precio propuesto
     *
     * @return Cadena con todos los atributos de la oferta.
     */
    @Override
    public String toString() {
        return "Oferta{"
             + "oferente='" + nombreDelOferente + '\''
             + ", puntuacion=" + puntuacionDelOferente
             + ", precioOfertado=$" + String.format("%.2f", precioOfertado)
             + '}';
    }

    //Inclusión del metodo equals
    /**
     * Compara dos ofertas por el nombre del oferente y el precio ofertado
     * Dos ofertas son iguales si las realizó la misma persona por el mismo precio
     *
     * @param objeto El objeto a comparar
     * @return true si tienen el mismo oferente y precio, false en caso contrario
     */
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (objeto == null) return false;
        if (!(objeto instanceof Oferta)) return false;
        Oferta otraOferta = (Oferta) objeto;
        return this.nombreDelOferente.equals(otraOferta.nombreDelOferente)
                && this.precioOfertado == otraOferta.precioOfertado;
    }
}
