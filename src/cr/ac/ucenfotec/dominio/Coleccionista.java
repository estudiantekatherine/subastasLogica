package cr.ac.ucenfotec.dominio;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Representa a un coleccionista registrado en la plataforma de subastas
 * El coleccionista puede crear subastas con objetos de su colección y también puede
 * realizar ofertas en subastas de otros usuarios, pero nunca en las propias
 * Mantiene una colección de objetos y una lista de intereses temáticos
 *
 *
 * @version 1.0
 */
public class Coleccionista extends Usuario {

    //Atributos
    /**
     * Puntuación de reputación del coleccionista dentro de la plataforma
     */
    private double puntuacionReputacion;

    /** Dirección física de residencia del coleccionista */
    private String direccionResidencia;

    /**
     * Lista de categorías o temas de interés del coleccionista
     */
    private ArrayList<String> listaDeIntereses;

    /**
     * Lista de objetos coleccionables que posee el coleccionista
     * Solo objetos de esta lista pueden ser incluidos en sus subastas
     */
    private ArrayList<ObjetoColeccionable> coleccionDeObjetos;

    /** Indica si el coleccionista tiene el rol de moderador de la plataforma*/
    private boolean esModerador;

    //Constructores

    /**
     * Constructor por defecto.
     */
    public Coleccionista() {
        super();
    }

    /**
     * Constructor que inicializa todos los atributos del coleccionista.
     *
     * @param nombreCompleto       Nombre completo del coleccionista.
     * @param numeroIdentificacion Número de identificación único.
     * @param fechaNacimiento      Fecha de nacimiento (debe ser mayor de edad).
     * @param contrasena           Contraseña de acceso a la plataforma.
     * @param correoElectronico    Correo electrónico de contacto.
     * @param puntuacionReputacion Puntuación inicial de reputación (normalmente 0.0).
     * @param direccionResidencia  Dirección física de residencia.
     * @param listaDeIntereses     Lista de intereses temáticos del coleccionista.
     * @param coleccionDeObjetos   Lista inicial de objetos en su colección.
     */
    public Coleccionista(String nombreCompleto, String numeroIdentificacion,
                         LocalDate fechaNacimiento, String contrasena, String correoElectronico,
                         double puntuacionReputacion, String direccionResidencia,
                         ArrayList<String> listaDeIntereses,
                         ArrayList<ObjetoColeccionable> coleccionDeObjetos) {
        super(nombreCompleto, numeroIdentificacion, fechaNacimiento, contrasena, correoElectronico);
        this.puntuacionReputacion = puntuacionReputacion;
        this.direccionResidencia = direccionResidencia;
        this.listaDeIntereses = (listaDeIntereses != null) ? listaDeIntereses : new ArrayList<>();
        this.coleccionDeObjetos = (coleccionDeObjetos != null) ? coleccionDeObjetos : new ArrayList<>();
        this.esModerador = false;
    }

    //Métodos
    /**
     * Agrega un objeto coleccionable a la colección personal del coleccionista
     *
     * @param objetoNuevo Objeto a incorporar en la colección.
     */
    public void agregarObjetoAColeccion(ObjetoColeccionable objetoNuevo) {
        this.coleccionDeObjetos.add(objetoNuevo);
    }

    /**
     * Agrega un nuevo interés temático a la lista del coleccionista
     *
     * @param nuevoInteres Interés temático a agregar (ejemplo: "monedas antiguas").
     */
    public void agregarInteres(String nuevoInteres) {
        this.listaDeIntereses.add(nuevoInteres);
    }

    //Getters y Setters
    /**
     * Obtiene la puntuación de reputación del coleccionista
     *
     * @return Puntuación de reputación.
     */
    public double getPuntuacionReputacion() {
        return puntuacionReputacion;
    }

    /**
     * Establece la puntuación de reputación del coleccionista.
     *
     * @param puntuacionReputacion Nueva puntuación de reputación.
     */
    public void setPuntuacionReputacion(double puntuacionReputacion) {
        this.puntuacionReputacion = puntuacionReputacion;
    }

    /**
     * Obtiene la dirección de residencia del coleccionista.
     *
     * @return Dirección de residencia.
     */
    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    /**
     * Establece la dirección de residencia del coleccionista.
     *
     * @param direccionResidencia Nueva dirección de residencia.
     */
    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    /**
     * Obtiene la lista de intereses temáticos del coleccionista.
     *
     * @return Lista de intereses.
     */
    public ArrayList<String> getListaDeIntereses() {
        return listaDeIntereses;
    }

    /**
     * Establece la lista de intereses temáticos del coleccionista.
     *
     * @param listaDeIntereses Nueva lista de intereses.
     */
    public void setListaDeIntereses(ArrayList<String> listaDeIntereses) {
        this.listaDeIntereses = listaDeIntereses;
    }

    /**
     * Obtiene la colección de objetos del coleccionista.
     *
     * @return Lista de objetos coleccionables.
     */
    public ArrayList<ObjetoColeccionable> getColeccionDeObjetos() {
        return coleccionDeObjetos;
    }

    /**
     * Establece la colección de objetos del coleccionista.
     *
     * @param coleccionDeObjetos Nueva colección de objetos.
     */
    public void setColeccionDeObjetos(ArrayList<ObjetoColeccionable> coleccionDeObjetos) {
        this.coleccionDeObjetos = coleccionDeObjetos;
    }

    /**
     * Indica si el coleccionista tiene el rol de moderador asignado.
     *
     * @return true si es moderador, false en caso contrario.
     */
    public boolean isEsModerador() {
        return esModerador;
    }

    /**
     * Establece si el coleccionista tiene el rol de moderador.
     *
     * @param esModerador true para asignarlo como moderador, false para quitarle el rol.
     */
    public void setEsModerador(boolean esModerador) {
        this.esModerador = esModerador;
    }

    //To String
    /**
     * Retorna una representación textual completa del coleccionista,
     * incluyendo su puntuación, dirección, intereses y cantidad de objetos en su colección
     *
     * @return Cadena con todos los atributos del coleccionista.
     */
    @Override
    public String toString() {
        return "Coleccionista{"
             + super.toString()
             + ", puntuacion=" + puntuacionReputacion
             + ", direccion='" + direccionResidencia + '\''
             + ", intereses=" + listaDeIntereses
             + ", cantidadObjetos=" + coleccionDeObjetos.size()
                + ", esModerador=" + esModerador
             + "}";
    }
//Inclusión de metodo equals
    /**
     * Compara dos coleccionistas por su número de identificación.
     *
     * @param objeto El objeto a comparar.
     * @return true si tienen la misma identificación, false en caso contrario.
     */
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (objeto == null) return false;
        if (!(objeto instanceof Coleccionista)) return false; //coleccionista solo igual a coleccionista
        return super.equals(objeto);

    }
}
