package cr.ac.ucenfotec.dominio;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Representa la orden de adjudicación generada cuando un coleccionista gana una subasta
 * La orden formaliza la aceptación de la adjudicación e incluye el detalle completo
 * de los objetos adjudicados y el precio total acordado
 *
 * @version 1.0
 */
public class OrdenAdjudicacion {

    //Atributos
    /** Nombre completo del coleccionista que ganó la subasta */
    private String nombreCompletoDelGanador;

    /** Fecha en que se generó formalmente la orden de adjudicación */
    private LocalDate fechaDeLaOrden;

    /** Lista detallada de los objetos coleccionables adjudicados al ganador */
    private ArrayList<ObjetoColeccionable> objetosAdjudicados;

    /**
     * Precio total de la orden, equivalente al precio de la oferta ganadora
     * Es el monto que el coleccionista debe pagar al creador de la subasta
     */
    private double precioTotalDeLaOrden;

    /** Indica si el ganador aceptó formalmente la adjudicación. */
    private boolean aceptada;

    /** Calificación que el ganador le dio al vendedor/creador (escala 1-5). */
    private int calificacionDelGanadorAlVendedor;

    /** Calificación que el vendedor/creador le dio al ganador (escala 1-5). */
    private int calificacionDelVendedorAlGanador;

    /** Indica si la orden fue marcada como entregada por el ganador. */
    private boolean entregada;

    //Constructores
    /**
     * Constructor por defecto.
     */
    public OrdenAdjudicacion() {
    }

    /**
     * Constructor que inicializa todos los atributos de la orden de adjudicación
     *
     * @param nombreCompletoDelGanador Nombre del coleccionista ganador.
     * @param fechaDeLaOrden           Fecha en que se generó la orden.
     * @param objetosAdjudicados       Lista de objetos adjudicados al ganador.
     * @param precioTotalDeLaOrden     Precio total que corresponde pagar.
     */
    public OrdenAdjudicacion(String nombreCompletoDelGanador, LocalDate fechaDeLaOrden,
                              ArrayList<ObjetoColeccionable> objetosAdjudicados,
                              double precioTotalDeLaOrden) {
        this.nombreCompletoDelGanador = nombreCompletoDelGanador;
        this.fechaDeLaOrden = fechaDeLaOrden;
        this.objetosAdjudicados = (objetosAdjudicados != null) ? objetosAdjudicados : new ArrayList<>();
        this.precioTotalDeLaOrden = precioTotalDeLaOrden;
        this.aceptada = false;
        this.calificacionDelGanadorAlVendedor = 0;
        this.calificacionDelVendedorAlGanador = 0;
        this.entregada = false;
    }

    //Getters y Setters
    /**
     * Obtiene el nombre completo del ganador de la subasta
     *
     * @return Nombre completo del ganador.
     */
    public String getNombreCompletoDelGanador() {
        return nombreCompletoDelGanador;
    }

    /**
     * Establece el nombre completo del ganador.
     *
     * @param nombreCompletoDelGanador Nuevo nombre del ganador.
     */
    public void setNombreCompletoDelGanador(String nombreCompletoDelGanador) {
        this.nombreCompletoDelGanador = nombreCompletoDelGanador;
    }

    /**
     * Obtiene la fecha en que se generó la orden.
     *
     * @return Fecha de la orden.
     */
    public LocalDate getFechaDeLaOrden() {
        return fechaDeLaOrden;
    }

    /**
     * Establece la fecha de la orden.
     *
     * @param fechaDeLaOrden Nueva fecha de la orden.
     */
    public void setFechaDeLaOrden(LocalDate fechaDeLaOrden) {
        this.fechaDeLaOrden = fechaDeLaOrden;
    }

    /**
     * Obtiene la lista de objetos adjudicados al ganador.
     *
     * @return Lista de objetos adjudicados.
     */
    public ArrayList<ObjetoColeccionable> getObjetosAdjudicados() {
        return objetosAdjudicados;
    }

    /**
     * Establece la lista de objetos adjudicados.
     *
     * @param objetosAdjudicados Nueva lista de objetos adjudicados.
     */
    public void setObjetosAdjudicados(ArrayList<ObjetoColeccionable> objetosAdjudicados) {
        this.objetosAdjudicados = objetosAdjudicados;
    }

    /**
     * Obtiene el precio total de la orden de adjudicación.
     *
     * @return Precio total de la orden.
     */
    public double getPrecioTotalDeLaOrden() {
        return precioTotalDeLaOrden;
    }

    /**
     * Establece el precio total de la orden.
     *
     * @param precioTotalDeLaOrden Nuevo precio total.
     */
    public void setPrecioTotalDeLaOrden(double precioTotalDeLaOrden) {
        this.precioTotalDeLaOrden = precioTotalDeLaOrden;
    }

    /**
     * Indica si el ganador aceptó la adjudicación.
     *
     * @return true si fue aceptada, false si está pendiente.
     */
    public boolean isAceptada() {
        return aceptada;
    }

    /**
     * Establece si el ganador aceptó la adjudicación.
     *
     * @param aceptada true para marcarla como aceptada.
     */
    public void setAceptada(boolean aceptada) {
        this.aceptada = aceptada;
    }

    /**
     * Obtiene la calificación que el ganador le dio al vendedor.
     *
     * @return Calificación del 1 al 5, o 0 si no ha calificado.
     */
    public int getCalificacionDelGanadorAlVendedor() {
        return calificacionDelGanadorAlVendedor;
    }

    /**
     * Establece la calificación del ganador al vendedor.
     *
     * @param calificacion Calificación del 1 al 5.
     */
    public void setCalificacionDelGanadorAlVendedor(int calificacion) {
        this.calificacionDelGanadorAlVendedor = calificacion;
    }

    /**
     * Obtiene la calificación que el vendedor le dio al ganador.
     *
     * @return Calificación del 1 al 5, o 0 si no ha calificado.
     */
    public int getCalificacionDelVendedorAlGanador() {
        return calificacionDelVendedorAlGanador;
    }

    /**
     * Establece la calificación del vendedor al ganador.
     *
     * @param calificacion Calificación del 1 al 5.
     */
    public void setCalificacionDelVendedorAlGanador(int calificacion) {
        this.calificacionDelVendedorAlGanador = calificacion;
    }

    /**
     * Indica si la orden fue marcada como entregada.
     *
     * @return true si fue entregada, false si está pendiente.
     */
    public boolean isEntregada() {
        return entregada;
    }

    /**
     * Establece si la orden fue entregada al ganador.
     *
     * @param entregada true para marcarla como entregada.
     */
    public void setEntregada(boolean entregada) {
        this.entregada = entregada;
    }

    //To String
    /**
     * Retorna una representación textual completa de la orden de adjudicación,
     * mostrando el ganador, la fecha, la cantidad de objetos y el precio total
     *
     * @return Cadena con todos los atributos de la orden.
     */
    @Override
    public String toString() {
        return "OrdenAdjudicacion{"
             + "ganador='" + nombreCompletoDelGanador + '\''
             + ", fechaOrden=" + fechaDeLaOrden
             + ", cantidadObjetos=" + objetosAdjudicados.size()
             + ", precioTotal=¢" + String.format("%.2f", precioTotalDeLaOrden)
                + ", aceptada=" + aceptada
                + ", entregada=" + entregada
                + ", calificacionGanadorAlVendedor=" + calificacionDelGanadorAlVendedor
                + ", calificacionVendedorAlGanador=" + calificacionDelVendedorAlGanador
             + '}';
    }

    //Inclusión del metodo equals
    /**
     * Compara dos órdenes de adjudicación por el nombre del ganador y la fecha de la orden
     * Dos órdenes son iguales si las ganó la misma persona en la misma fecha
     *
     * @param objeto El objeto a comparar.
     * @return true si tienen el mismo ganador y fecha, false en caso contrario.
     */
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (objeto == null) return false;
        if (!(objeto instanceof OrdenAdjudicacion)) return false;
        OrdenAdjudicacion otraOrden = (OrdenAdjudicacion) objeto;
        return this.nombreCompletoDelGanador.equals(otraOrden.nombreCompletoDelGanador)
                && this.fechaDeLaOrden.equals(otraOrden.fechaDeLaOrden);
    }
}
