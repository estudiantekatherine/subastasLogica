package cr.ac.ucenfotec.dominio;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Representa una subasta dentro de la plataforma de subastas
 *
 * @version 1.0
 */
public class Subasta {

    //Estados válidos del físico de un objeto
    /** Constante que representa una subasta vigente que acepta nuevas ofertas */
    public static final String ESTADO_ACTIVA     = "Activa";

    /** Constante que representa una subasta que ya no acepta más ofertas */
    public static final String ESTADO_VENCIDA    = "Vencida";

    /** Constante que representa una subasta adjudicada formalmente al ganador */
    public static final String ESTADO_ADJUDICADA = "Adjudicada";

    //Atributos
    /** Fecha y hora exacta en que la subasta vence y deja de aceptar nuevas ofertas */
    private LocalDateTime fechaDeVencimiento;

    /** Usuario que creó la subasta. Puede ser un Vendedor o un Coleccionista */
    private Usuario creadorDeLaSubasta;

    /** Precio mínimo en dinero que un coleccionista debe ofrecer para participar */
    private double precioMinimoDeAceptacion;

    /** Lista de objetos coleccionables incluidos en esta subasta. Debe tener al menos uno */
    private ArrayList<ObjetoColeccionable> objetosSubastados;

    /** Estado actual de la subasta */
    private String estadoActual;

    /** Lista de todas las ofertas recibidas durante el período activo de la subasta */
    private ArrayList<Oferta> ofertasRecibidas;

    //Constructores
    /**
     * Constructor por defecto.
     */
    public Subasta() {
    }

    /**
     * Constructor que inicializa todos los atributos de la subasta
     *
     * @param fechaDeVencimiento       Fecha y hora límite de la subasta.
     * @param creadorDeLaSubasta       Vendedor o coleccionista que crea la subasta.
     * @param precioMinimoDeAceptacion Precio mínimo requerido para ofertar.
     * @param objetosSubastados        Lista de objetos incluidos en la subasta.
     */
    public Subasta(LocalDateTime fechaDeVencimiento, Usuario creadorDeLaSubasta,
                   double precioMinimoDeAceptacion,
                   ArrayList<ObjetoColeccionable> objetosSubastados) {
        this.fechaDeVencimiento       = fechaDeVencimiento;
        this.creadorDeLaSubasta       = creadorDeLaSubasta;
        this.precioMinimoDeAceptacion = precioMinimoDeAceptacion;
        this.objetosSubastados        = (objetosSubastados != null) ? objetosSubastados : new ArrayList<>();
        this.estadoActual             = ESTADO_ACTIVA;
        this.ofertasRecibidas         = new ArrayList<>();
    }

    //Métodos
    /**
     * Calcula el tiempo restante hasta el vencimiento de la subasta
     *
     * @return Duración con el tiempo restante, o Duration.ZERO si ya venció.
     */
    public Duration calcularTiempoRestante() {
        LocalDateTime momentoActual = LocalDateTime.now();
        if (momentoActual.isAfter(fechaDeVencimiento)) {
            return Duration.ZERO;
        }
        return Duration.between(momentoActual, fechaDeVencimiento);
    }

    /**
     * Retorna el tiempo restante en formato legible.
     *
     * @return Texto con el tiempo restante, o "Subasta vencida" si ya expiró
     */
    public String getTiempoRestanteFormateado() {
        Duration tiempoRestante = calcularTiempoRestante();
        if (tiempoRestante.isZero()) {
            return "Subasta vencida";
        }
        long dias     = tiempoRestante.toDays();
        long horas    = tiempoRestante.toHours()   % 24;
        long minutos  = tiempoRestante.toMinutes()  % 60;
        long segundos = tiempoRestante.getSeconds() % 60;
        return dias + " día(s), " + horas + " hora(s), "
             + minutos + " minuto(s), " + segundos + " segundo(s)";
    }

    /**
     * Obtiene la puntuación de reputación del creador de la subasta
     *
     * @return Puntuación del creador, o 0.0 si el creador no tiene puntuación.
     */
    public double getPuntuacionDelCreador() {
        if (creadorDeLaSubasta instanceof Vendedor) {
            Vendedor vendedorCreador = (Vendedor) creadorDeLaSubasta;
            return vendedorCreador.getPuntuacionReputacion();
        } else if (creadorDeLaSubasta instanceof Coleccionista) {
            Coleccionista coleccionistaCreador = (Coleccionista) creadorDeLaSubasta;
            return coleccionistaCreador.getPuntuacionReputacion();
        }
        return 0.0;
    }

    /**
     * Agrega una nueva oferta a la lista de ofertas recibidas
     *
     * @param nuevaOferta Oferta a registrar en la subasta
     */
    public void agregarOferta(Oferta nuevaOferta) {
        this.ofertasRecibidas.add(nuevaOferta);
    }

    /**
     * Retorna la oferta con el precio más alto entre todas las recibidas, si encuentra
     * una mayor la reemplaza
     *
     * @return La oferta más alta, o null si no hay ofertas
     */
    public Oferta getOfertaMasAlta() {
        if (ofertasRecibidas.isEmpty()) {
            return null;
        }
        Oferta mejorOferta = ofertasRecibidas.get(0);
        for (Oferta oferta : ofertasRecibidas) {
            if (oferta.getPrecioOfertado() > mejorOferta.getPrecioOfertado()) {
                mejorOferta = oferta;
            }
        }
        return mejorOferta;
    }

    //Getters y Setters

    /** @return Fecha y hora de vencimiento */
    public LocalDateTime getFechaDeVencimiento() {
        return fechaDeVencimiento; }

    /** @param fechaDeVencimiento Nueva fecha y hora de vencimiento */
    public void setFechaDeVencimiento(LocalDateTime fechaDeVencimiento) {
        this.fechaDeVencimiento = fechaDeVencimiento; }

    /** @return Usuario creador (vendedor o coleccionista) */
    public Usuario getCreadorDeLaSubasta() {
        return creadorDeLaSubasta; }

    /** @param creadorDeLaSubasta Nuevo usuario creador */
    public void setCreadorDeLaSubasta(Usuario creadorDeLaSubasta) {
        this.creadorDeLaSubasta = creadorDeLaSubasta; }

    /** @return Precio mínimo de aceptación */
    public double getPrecioMinimoDeAceptacion() {
        return precioMinimoDeAceptacion; }

    /** @param precioMinimoDeAceptacion Nuevo precio mínimo */
    public void setPrecioMinimoDeAceptacion(double precioMinimoDeAceptacion) {
        this.precioMinimoDeAceptacion = precioMinimoDeAceptacion; }

    /** @return Lista de objetos subastados */
    public ArrayList<ObjetoColeccionable> getObjetosSubastados() {
        return objetosSubastados; }

    /** @param objetosSubastados Nueva lista de objetos subastados */
    public void setObjetosSubastados(ArrayList<ObjetoColeccionable> objetosSubastados) {
        this.objetosSubastados = objetosSubastados; }

    /** @return Estado actual: "Activa", "Vencida" o "Adjudicada" */
    public String getEstadoActual() {
        return estadoActual; }

    /**
     * Establece el estado actual.
     *
     * @param estadoActual Nuevo estado de la subasta
     */
    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual; }

    /** @return Lista de ofertas recibidas. */
    public ArrayList<Oferta> getOfertasRecibidas() {
        return ofertasRecibidas; }

    /** @param ofertasRecibidas Nueva lista de ofertas. */
    public void setOfertasRecibidas(ArrayList<Oferta> ofertasRecibidas) {
        this.ofertasRecibidas = ofertasRecibidas; }

    //ToString
    /**
     * Retorna una representación textual completa de la subasta
     *
     * @return Cadena con todos los atributos relevantes de la subasta
     */
    @Override
    public String toString() {
        String nombreCreador = (creadorDeLaSubasta != null)
                ? creadorDeLaSubasta.getNombreCompleto() : "Sin creador";
        return "Subasta{"
             + "creador='"        + nombreCreador                                   + '\''
             + ", puntuacion="     + getPuntuacionDelCreador()
             + ", precioMinimo=¢"  + String.format("%.2f", precioMinimoDeAceptacion)
             + ", vencimiento="    + fechaDeVencimiento
             + ", tiempoRestante=" + getTiempoRestanteFormateado()
             + ", estado='"        + estadoActual                                   + '\''
             + ", objetos="        + objetosSubastados.size()
             + ", ofertas="        + ofertasRecibidas.size()
             + '}';
    }

    //Inclusión del metodo equals
    /**
     * Compara dos subastas por su creador y fecha de vencimiento
     * Dos subastas son iguales si las creó la misma persona con la misma fecha de vencimiento
     *
     * @param objeto El objeto a comparar.
     * @return true si tienen el mismo creador y fecha de vencimiento, false en caso contrario.
     */
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (objeto == null) return false;
        if (!(objeto instanceof Subasta)) return false;
        Subasta otraSubasta = (Subasta) objeto;
        return this.creadorDeLaSubasta.equals(otraSubasta.creadorDeLaSubasta)
                && this.fechaDeVencimiento.equals(otraSubasta.fechaDeVencimiento);
    }
}
