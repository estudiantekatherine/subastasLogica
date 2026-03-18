package cr.ac.ucenfotec.logica;

import cr.ac.ucenfotec.dominio.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Controlador principal de la plataforma de subastas
 * Centraliza TODA la lógica de negocio, la persistencia en memoria mediante
 * {@link ArrayList} y la aplicación estricta de las reglas de negocio definidas.
 *
 * <p>La interfaz gráfica ÚNICAMENTE interactúa con el sistema a través de esta clase,
 * pasando datos primitivos y recibiendo resultados como cadenas de texto o listas.
 * La interfaz gráfica nunca instancia objetos del dominio directamente.</p>
 *
 * @version 1.0
 */
public class ControladorPlataforma {

    //Atributos
    /**
     * Lista maestra de todos los usuarios registrados en la plataforma
     * Incluye moderador, vendedores y coleccionistas en una sola estructura
     */
    private ArrayList<Usuario> listaDeUsuarios;

    /**
     * Lista de todas las subastas creadas en la plataforma,
     * tanto activas como vencidas o adjudicadas
     */
    private ArrayList<Subasta> listaDeSubastas;

    /**
     * Lista de todas las órdenes de adjudicación generadas al cierre de subastas
     */
    private ArrayList<OrdenAdjudicacion> listaDeOrdenes;

    //Constructores
    /**
     * Constructor por defecto.
     */
    public ControladorPlataforma() {
        this.listaDeUsuarios  = new ArrayList<>();
        this.listaDeSubastas  = new ArrayList<>();
        this.listaDeOrdenes   = new ArrayList<>();
    }

    //Verificación inicial del sistema

    /**
     * Verifica si existe al menos un moderador registrado en la plataforma
     * Se ejecuta al iniciar la aplicación
     *
     * @return {@code true} si hay un moderador registrado; {@code false} en caso contrario.
     */
    public boolean existeModerador() {
        for (Usuario usuario : listaDeUsuarios) {
            if (usuario instanceof Moderador) {
                return true;
            }
        }
        return false;
    }

    //Registro de usuarios
    /**
     * Registra al moderador único de la plataforma
     * Valida que no exista otro moderador y que sea mayor de edad
     *
     * @param nombreCompleto       Nombre completo del moderador.
     * @param numeroIdentificacion Número de identificación único.
     * @param fechaNacimiento      Fecha de nacimiento del moderador.
     * @param contrasena           Contraseña de acceso.
     * @param correoElectronico    Correo electrónico de contacto.
     * @return Mensaje de éxito o descripción del error encontrado.
     */
    public String registrarModerador(String nombreCompleto, String numeroIdentificacion,
                                     LocalDate fechaNacimiento, String contrasena,
                                     String correoElectronico) {
        // Regla: solo puede existir un moderador
        if (existeModerador()) {
            return "Error: Ya existe un moderador registrado en la plataforma";
        }
        // Crear el moderador para validar su edad antes de registrarlo
        Moderador nuevoModerador = new Moderador(
                nombreCompleto, numeroIdentificacion,
                fechaNacimiento, contrasena, correoElectronico);
        // Regla: el moderador debe ser mayor de edad
        if (!nuevoModerador.esMayorDeEdad()) {
            return "Error: El moderador debe ser mayor de edad (18 años o más).";
        }
        listaDeUsuarios.add(nuevoModerador);
        return "Moderador registrado exitosamente.";
    }

    /**
     * Registra un nuevo vendedor en la plataforma.
     * Valida que el vendedor sea mayor de edad
     *
     * @param nombreCompleto       Nombre completo del vendedor.
     * @param numeroIdentificacion Número de identificación único.
     * @param fechaNacimiento      Fecha de nacimiento del vendedor.
     * @param contrasena           Contraseña de acceso.
     * @param correoElectronico    Correo electrónico de contacto.
     * @param direccionResidencia  Dirección física de residencia.
     * @return Mensaje de éxito o descripción del error encontrado.
     */
    public String registrarVendedor(String nombreCompleto, String numeroIdentificacion,
                                    LocalDate fechaNacimiento, String contrasena,
                                    String correoElectronico, String direccionResidencia) {
        Vendedor nuevoVendedor = new Vendedor(
                nombreCompleto, numeroIdentificacion,
                fechaNacimiento, contrasena, correoElectronico,
                0.0, direccionResidencia);
        // Regla: debe ser mayor de edad
        if (!nuevoVendedor.esMayorDeEdad()) {
            return "Error: El vendedor debe ser mayor de edad (18 años o más).";
        }
        listaDeUsuarios.add(nuevoVendedor);
        return "Vendedor registrado exitosamente.";
    }

    /**
     * Registra un nuevo coleccionista en la plataforma
     * Valida que el coleccionista sea mayor de edad
     *
     * @param nombreCompleto       Nombre completo del coleccionista.
     * @param numeroIdentificacion Número de identificación único.
     * @param fechaNacimiento      Fecha de nacimiento del coleccionista.
     * @param contrasena           Contraseña de acceso.
     * @param correoElectronico    Correo electrónico de contacto.
     * @param direccionResidencia  Dirección física de residencia.
     * @return Mensaje de éxito o descripción del error encontrado.
     */
    public String registrarColeccionista(String nombreCompleto, String numeroIdentificacion,
                                         LocalDate fechaNacimiento, String contrasena,
                                         String correoElectronico, String direccionResidencia) {
        Coleccionista nuevoColeccionista = new Coleccionista(
                nombreCompleto, numeroIdentificacion,
                fechaNacimiento, contrasena, correoElectronico,
                0.0, direccionResidencia,
                new ArrayList<>(), new ArrayList<>());
        // Regla #7: debe ser mayor de edad
        if (!nuevoColeccionista.esMayorDeEdad()) {
            return "Error: El coleccionista debe ser mayor de edad (18 años o más).";
        }
        listaDeUsuarios.add(nuevoColeccionista);
        return "Coleccionista registrado exitosamente.";
    }

    //Lista de usuarios

    /**
     * Retorna el listado de usuarios
     * Cada elemento de la lista contiene el número de orden y el toString del usuario
     *
     * @return Lista de cadenas con la información de cada usuario registrado.
     */
    public ArrayList<String> getListaUsuariosFormateada() {
        ArrayList<String> usuariosFormateados = new ArrayList<>();
        for (int i = 0; i < listaDeUsuarios.size(); i++) {
            usuariosFormateados.add((i + 1) + ". " + listaDeUsuarios.get(i).toString());
        }
        return usuariosFormateados;
    }

    /**
     * Retorna el listado de coleccionistas con sus objetos
     * Útil para que el coleccionista elija qué objetos incluir en una subasta
     *
     * @param identificacionColeccionista Identificación del coleccionista consultado
     * @return Lista de cadenas con los objetos del coleccionista, o mensaje de error
     */
    public ArrayList<String> getObjetosDeColeccionistaFormateados(String identificacionColeccionista) {
        ArrayList<String> objetosFormateados = new ArrayList<>();
        Usuario usuarioEncontrado = buscarUsuarioPorIdentificacion(identificacionColeccionista);
        if (!(usuarioEncontrado instanceof Coleccionista)) {
            objetosFormateados.add("Error: No se encontró un coleccionista con esa identificación.");
            return objetosFormateados;
        }
        Coleccionista coleccionista = (Coleccionista) usuarioEncontrado;
        ArrayList<ObjetoColeccionable> objetos = coleccionista.getColeccionDeObjetos();
        if (objetos.isEmpty()) {
            objetosFormateados.add("El coleccionista no tiene objetos en su colección.");
            return objetosFormateados;
        }
        for (int i = 0; i < objetos.size(); i++) {
            objetosFormateados.add((i + 1) + ". " + objetos.get(i).toString());
        }
        return objetosFormateados;
    }

    /**
     * Retorna la cantidad total de usuarios registrados en la plataforma.
     *
     * @return Número de usuarios registrados.
     */
    public int getCantidadDeUsuarios() {
        return listaDeUsuarios.size();
    }

    //Gestión de objetos coleccionables

    /**
     * Agrega un objeto coleccionable a la colección personal de un coleccionista
     *
     * @param identificacionColeccionista Identificación del coleccionista propietario.
     * @param nombreObjeto                Nombre del objeto coleccionable.
     * @param descripcionDetallada        Descripción detallada del objeto.
     * @param opcionEstadoFisico          Estado físico: 1=Nuevo, 2=Usado, 3=Antiguo sin abrir.
     * @param fechaDeCompra               Fecha en que fue adquirido el objeto.
     * @return Mensaje de éxito o descripción del error encontrado.
     */
    public String agregarObjetoAColeccionista(String identificacionColeccionista,
                                               String nombreObjeto, String descripcionDetallada,
                                               int opcionEstadoFisico, LocalDate fechaDeCompra) {
        Usuario usuarioEncontrado = buscarUsuarioPorIdentificacion(identificacionColeccionista);
        if (!(usuarioEncontrado instanceof Coleccionista)) {
            return "Error: No se encontró un coleccionista con esa identificación.";
        }
        Coleccionista coleccionista = (Coleccionista) usuarioEncontrado;
        String estadoFisico = convertirOpcionAEstadoObjeto(opcionEstadoFisico);
        ObjetoColeccionable nuevoObjeto = new ObjetoColeccionable(
                nombreObjeto, descripcionDetallada, estadoFisico, fechaDeCompra);
        coleccionista.agregarObjetoAColeccion(nuevoObjeto);
        return "Objeto '" + nombreObjeto + "' agregado a la colección exitosamente.";
    }

    /**
     * Convierte una opción numérica en el String de estado correspondiente
     * Usa las constantes ESTADO_* de ObjetoColeccionable
     *
     * @param opcion Número: 1=Nuevo, 2=Usado, 3=Antiguo sin abrir.
     * @return String con el estado correspondiente.
     */
    private String convertirOpcionAEstadoObjeto(int opcion) {
        if (opcion == 2) {
            return ObjetoColeccionable.ESTADO_USADO;
        } else if (opcion == 3) {
            return ObjetoColeccionable.ESTADO_ANTIGUO_SIN_ABRIR;
        } else {
            return ObjetoColeccionable.ESTADO_NUEVO;
        }
    }

    //Creación y enlista de subastas
    /**
     * Crea una nueva subasta en la plataforma
     * Para coleccionistas, valida que los objetos pertenezcan a su colección
     * Para vendedores, crea objetos temporales con los nombres indicados
     *
     *
     * @param identificacionCreador    Identificación del usuario que crea la subasta.
     * @param precioMinimoDeAceptacion Precio mínimo requerido para las ofertas.
     * @param fechaDeVencimiento       Fecha y hora límite de la subasta.
     * @param nombresDeObjetos         Nombres de los objetos a incluir en la subasta.
     * @return Mensaje de éxito o descripción del error encontrado.
     */
    public String crearSubasta(String identificacionCreador, double precioMinimoDeAceptacion,
                                LocalDateTime fechaDeVencimiento,
                                ArrayList<String> nombresDeObjetos) {
        Usuario creador = buscarUsuarioPorIdentificacion(identificacionCreador);
        if (creador == null) {
            return "Error: No se encontró ningún usuario con la identificación ingresada.";
        }
        // Regla: el moderador no puede crear ni participar en subastas
        if (creador instanceof Moderador) {
            return "Error: El moderador no puede participar ni crear subastas.";
        }
        // Regla: la subasta debe tener al menos un objeto
        if (nombresDeObjetos == null || nombresDeObjetos.isEmpty()) {
            return "Error: No se puede crear una subasta sin objetos asociados.";
        }

        ArrayList<ObjetoColeccionable> objetosParaSubastar = new ArrayList<>();

        if (creador instanceof Coleccionista coleccionista) {
            // Regla: el coleccionista solo puede subastar objetos de su propia colección
            for (String nombreObjeto : nombresDeObjetos) {
                ObjetoColeccionable objetoEncontrado = buscarObjetoEnColeccion(
                        coleccionista, nombreObjeto);
                if (objetoEncontrado == null) {
                    return "Error: El objeto '" + nombreObjeto
                         + "' no existe en la colección del coleccionista.";
                }
                objetosParaSubastar.add(objetoEncontrado);
            }
        } else if (creador instanceof Vendedor) {
            // El vendedor crea objetos directamente en la subasta con nombre e información básica
            for (String nombreObjeto : nombresDeObjetos) {
                ObjetoColeccionable objetoDelVendedor = new ObjetoColeccionable(
                        nombreObjeto, "Objeto ofrecido por vendedor",
                        ObjetoColeccionable.ESTADO_NUEVO, LocalDate.now());
                objetosParaSubastar.add(objetoDelVendedor);
            }
        }

        Subasta nuevaSubasta = new Subasta(
                fechaDeVencimiento, creador,
                precioMinimoDeAceptacion, objetosParaSubastar);
        listaDeSubastas.add(nuevaSubasta);
        return "Subasta creada exitosamente con " + objetosParaSubastar.size() + " objeto(s).";
    }

    /**
     * Retorna el listado de subastas
     *
     * @return Lista de cadenas con la información de cada subasta registrada.
     */
    public ArrayList<String> getListaSubastasFormateada() {
        ArrayList<String> subastasFormateadas = new ArrayList<>();
        for (int i = 0; i < listaDeSubastas.size(); i++) {
            subastasFormateadas.add("Subasta #" + (i + 1) + ":\n  "
                    + listaDeSubastas.get(i).toString());
        }
        return subastasFormateadas;
    }

    /**
     * Retorna la cantidad total de subastas registradas en la plataforma
     *
     * @return Número de subastas registradas.
     */
    public int getCantidadDeSubastas() {
        return listaDeSubastas.size();
    }

    //Creación y lidts de usuarios
    /**
     * Registra una nueva oferta sobre una subasta específica, aplicando todas las validaciones
     * Solo los coleccionistas pueden ofertar, y no en sus propias subastas
     *
     *
     * @param identificacionOferente Identificación del coleccionista que realiza la oferta.
     * @param indiceSubasta          Índice de la subasta en la lista
     * @param precioOfertado         Precio propuesto por el coleccionista.
     * @return Mensaje de éxito o descripción del error encontrado.
     */
    public String crearOferta(String identificacionOferente, int indiceSubasta,
                               double precioOfertado) {
        Usuario oferente = buscarUsuarioPorIdentificacion(identificacionOferente);
        if (oferente == null) {
            return "Error: No se encontró ningún usuario con la identificación ingresada.";
        }
        // Regla: el moderador no puede realizar ofertas
        if (oferente instanceof Moderador) {
            return "Error: El moderador no puede realizar ofertas en la plataforma.";
        }
        // Regla: el vendedor no puede realizar ofertas
        if (oferente instanceof Vendedor) {
            return "Error: El vendedor no puede realizar ofertas, solo los coleccionistas.";
        }
        // Solo llega aquí si el oferente es un Coleccionista
        Coleccionista coleccionistaOferente = (Coleccionista) oferente;

        // Valida índice de la subasta (la UI muestra base 1, aquí se convierte a base 0)
        int indiceBase0 = indiceSubasta - 1;
        if (indiceBase0 < 0 || indiceBase0 >= listaDeSubastas.size()) {
            return "Error: El número de subasta ingresado no es válido.";
        }
        Subasta subastaSeleccionada = listaDeSubastas.get(indiceBase0);

        // Verifica que la subasta esté activa antes de permitir la oferta
        if (!subastaSeleccionada.getEstadoActual().equals(Subasta.ESTADO_ACTIVA)) {
            return "Error: La subasta seleccionada no está activa (estado: "
                 + subastaSeleccionada.getEstadoActual() + ").";
        }
        // Regla: el coleccionista no puede ofertar en su propia subasta
        if (subastaSeleccionada.getCreadorDeLaSubasta()
                .getNumeroIdentificacion()
                .equals(coleccionistaOferente.getNumeroIdentificacion())) {
            return "Error: El coleccionista no puede realizar ofertas en sus propias subastas.";
        }
        // Valida el precio mínimo de aceptación
        if (precioOfertado < subastaSeleccionada.getPrecioMinimoDeAceptacion()) {
            return "Error: El precio ofertado (¢" + String.format("%.2f", precioOfertado)
                 + ") es menor al precio mínimo requerido (¢"
                 + String.format("%.2f", subastaSeleccionada.getPrecioMinimoDeAceptacion()) + ").";
        }

        Oferta nuevaOferta = new Oferta(coleccionistaOferente, precioOfertado);
        subastaSeleccionada.agregarOferta(nuevaOferta);
        return "Oferta de ¢" + String.format("%.2f", precioOfertado) + " registrada exitosamente.";
    }

    /**
     * Retorna el listado de todas las ofertas formateado para su visualización en consola.
     * Las ofertas se agrupan por subasta para facilitar su lectura.
     *
     * @return Lista de cadenas con la información de cada oferta registrada.
     */
    public ArrayList<String> getTodasLasOfertasFormateadas() {
        ArrayList<String> ofertasFormateadas = new ArrayList<>();
        for (int i = 0; i < listaDeSubastas.size(); i++) {
            Subasta subasta = listaDeSubastas.get(i);
            ArrayList<Oferta> ofertas = subasta.getOfertasRecibidas();
            if (!ofertas.isEmpty()) {
                ofertasFormateadas.add("--- Subasta #" + (i + 1)
                        + " (Creador: " + subasta.getCreadorDeLaSubasta().getNombreCompleto()
                        + ") ---");
                for (int j = 0; j < ofertas.size(); j++) {
                    ofertasFormateadas.add("  Oferta " + (j + 1) + ": " + ofertas.get(j).toString());
                }
            }
        }
        return ofertasFormateadas;
    }

    /**
     * Retorna la cantidad total de ofertas registradas en todas las subastas
     *
     * @return Número total de ofertas
     */
    public int getCantidadTotalDeOfertas() {
        int totalOfertas = 0;
        for (Subasta subasta : listaDeSubastas) {
            totalOfertas += subasta.getOfertasRecibidas().size();
        }
        return totalOfertas;
    }

    //Métodos privados
    /**
     * Busca un usuario en la lista maestra por su número de identificación
     *
     * @param numeroIdentificacion Identificación a buscar en el sistema.
     * @return El usuario encontrado, o {@code null} si no existe ninguno con esa identificación.
     */
    private Usuario buscarUsuarioPorIdentificacion(String numeroIdentificacion) {
        for (Usuario usuario : listaDeUsuarios) {
            if (usuario.getNumeroIdentificacion().equals(numeroIdentificacion)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Busca un objeto en la colección de un coleccionista por su nombre (ignora mayúsculas/minúsculas).
     *
     * @param coleccionista Coleccionista propietario de los objetos.
     * @param nombreObjeto  Nombre del objeto a buscar en su colección.
     * @return El objeto encontrado, o {@code null} si no existe en la colección.
     */
    private ObjetoColeccionable buscarObjetoEnColeccion(Coleccionista coleccionista,
                                                         String nombreObjeto) {
        for (ObjetoColeccionable objeto : coleccionista.getColeccionDeObjetos()) {
            if (objeto.getNombreObjeto().equalsIgnoreCase(nombreObjeto)) {
                return objeto;
            }
        }
        return null;
    }

    //Getters y Setters

    /**
     * Obtiene la lista completa de usuarios registrados
     *
     * @return Lista de todos los usuarios.
     */
    public ArrayList<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
    }

    /**
     * Establece la lista de usuarios (uso interno).
     *
     * @param listaDeUsuarios Nueva lista de usuarios.
     */
    public void setListaDeUsuarios(ArrayList<Usuario> listaDeUsuarios) {
        this.listaDeUsuarios = listaDeUsuarios;
    }

    /**
     * Obtiene la lista completa de subastas registradas
     *
     * @return Lista de todas las subastas
     */
    public ArrayList<Subasta> getListaDeSubastas() {
        return listaDeSubastas;
    }

    /**
     * Establece la lista de subastas (uso interno)
     *
     * @param listaDeSubastas Nueva lista de subastas.
     */
    public void setListaDeSubastas(ArrayList<Subasta> listaDeSubastas) {
        this.listaDeSubastas = listaDeSubastas;
    }

    /**
     * Obtiene la lista completa de órdenes de adjudicación
     *
     * @return Lista de todas las órdenes
     */
    public ArrayList<OrdenAdjudicacion> getListaDeOrdenes() {
        return listaDeOrdenes;
    }

    /**
     * Establece la lista de órdenes de adjudicación
     *
     * @param listaDeOrdenes Nueva lista de órdenes
     */
    public void setListaDeOrdenes(ArrayList<OrdenAdjudicacion> listaDeOrdenes) {
        this.listaDeOrdenes = listaDeOrdenes;
    }
}
