package cr.ac.ucenfotec.logica;

import cr.ac.ucenfotec.dominio.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
public class Service {

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

    /**
     * Lista de categorías temáticas disponibles en la plataforma.
     * Las subastas se clasifican usando estas categorías.
     */
    private ArrayList<Categoria> listaDeCategorias;

    //Constructores
    /**
     * Constructor por defecto.
     */
    public Service() {
        this.listaDeUsuarios  = new ArrayList<>();
        this.listaDeSubastas  = new ArrayList<>();
        this.listaDeOrdenes   = new ArrayList<>();
        this.listaDeCategorias = new ArrayList<>();
        leerArchivoConfiguracion();
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
        // validar identificación duplicada
        if (existeUsuarioConIdentificacion(numeroIdentificacion)) {
            return "Error: Ya existe un usuario con esa identificación.";
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

        if (existeUsuarioConIdentificacion(numeroIdentificacion)) {
            return "Error: Ya existe un usuario con esa identificación registrado";
        }
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

        if (existeUsuarioConIdentificacion(numeroIdentificacion)) {
            return "Error: Ya existe un usuario con esa identificación registrado.";
        }
        Coleccionista nuevoColeccionista = new Coleccionista(
                nombreCompleto, numeroIdentificacion,
                fechaNacimiento, contrasena, correoElectronico,
                0.0, direccionResidencia,
                new ArrayList<>(), new ArrayList<>());
        // Regla: debe ser mayor de edad
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

       // Validar que no exista un objeto con el mismo nombre en la colección
        for (ObjetoColeccionable objeto : coleccionista.getColeccionDeObjetos()) {
            if (objeto.equals(nuevoObjeto)) {
                return "Error: Ya existe un objeto con ese nombre en la colección.";
            }
        }

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
     * Crea una nueva subasta en la plataforma aplicando todas las reglas de negocio.
     * Asigna automáticamente un coleccionista moderador de forma aleatoria.
     * Para coleccionistas, valida que los objetos pertenezcan a su colección.
     * Para vendedores, crea objetos temporales con los nombres indicados.
     *
     * @param identificacionCreador    Identificación del usuario que crea la subasta.
     * @param precioMinimoDeAceptacion Precio mínimo requerido para las ofertas.
     * @param fechaDeVencimiento       Fecha y hora límite de la subasta.
     * @param nombresDeObjetos         Nombres de los objetos a incluir en la subasta.
     * @param nombreCategoria          Nombre de la categoría de la subasta.
     * @return Mensaje de éxito o descripción del error encontrado.
     */
    public String crearSubasta(String identificacionCreador, double precioMinimoDeAceptacion,
                               LocalDateTime fechaDeVencimiento,
                               ArrayList<String> nombresDeObjetos,
                               String nombreCategoria) {
        Usuario creador = buscarUsuarioPorIdentificacion(identificacionCreador);
        if (creador == null) {
            return "Error: No se encontró ningún usuario con la identificación ingresada.";
        }
        // Regla #3: el moderador no puede crear subastas
        if (creador instanceof Moderador) {
            return "Error: El moderador no puede participar ni crear subastas.";
        }
        // Regla #6: la subasta debe tener al menos un objeto
        if (nombresDeObjetos == null || nombresDeObjetos.isEmpty()) {
            return "Error: No se puede crear una subasta sin objetos asociados.";
        }

        // Buscar la categoría seleccionada
        Categoria categoriaSeleccionada = buscarCategoriaPorNombre(nombreCategoria);
        if (categoriaSeleccionada == null) {
            return "Error: La categoría ingresada no existe en el sistema.";
        }

        ArrayList<ObjetoColeccionable> objetosParaSubastar = new ArrayList<>();

        if (creador instanceof Coleccionista) {
            Coleccionista coleccionista = (Coleccionista) creador;
            // Regla #9: el coleccionista solo puede subastar objetos de su colección
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
            for (String nombreObjeto : nombresDeObjetos) {
                ObjetoColeccionable objetoDelVendedor = new ObjetoColeccionable(
                        nombreObjeto, "Objeto ofrecido por vendedor",
                        ObjetoColeccionable.ESTADO_NUEVO, LocalDate.now());
                objetosParaSubastar.add(objetoDelVendedor);
            }
        }

        Subasta nuevaSubasta = new Subasta(
                fechaDeVencimiento, creador,
                precioMinimoDeAceptacion, objetosParaSubastar,
                categoriaSeleccionada);

        // Validar ANTES de agregar que no exista una subasta igual
        for (Subasta subastaExistente : listaDeSubastas) {
            if (subastaExistente.equals(nuevaSubasta)) {
                return "Error: Ya existe una subasta de este creador con la misma fecha de vencimiento.";
            }
        }

        // Asignar moderador aleatorio de los coleccionistas que son moderadores
        Coleccionista moderadorAleatorio = seleccionarModeradorAleatorio();
        String mensajeCorreo = "";
        if (moderadorAleatorio != null) {
            nuevaSubasta.setModeradorAsignado(moderadorAleatorio);
            // Simular envío de correo al moderador asignado
            mensajeCorreo = simularEnvioCorreoModerador(
                    moderadorAleatorio, creador.getNombreCompleto());
        }

        listaDeSubastas.add(nuevaSubasta);

        if (moderadorAleatorio != null) {
            return "Subasta creada exitosamente con " + objetosParaSubastar.size()
                    + " objeto(s). Moderador asignado: "
                    + moderadorAleatorio.getNombreCompleto()
                    + ". " + mensajeCorreo;
        } else {
            return "Subasta creada exitosamente con " + objetosParaSubastar.size()
                    + " objeto(s). Sin moderador asignado "
                    + "(el moderador debe designar coleccionistas como moderadores).";
        }

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

        // Validar que no exista una oferta igual del mismo coleccionista con el mismo precio
        for (Oferta oferta : subastaSeleccionada.getOfertasRecibidas()) {
            if (oferta.equals(nuevaOferta)) {
                return "Error: Ya existe una oferta de este coleccionista con el mismo precio";
            }
        }

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

    // ===================== GESTIÓN DE CATEGORÍAS =====================

    /**
     * Registra una nueva categoría en la plataforma.
     * Valida que no exista otra categoría con el mismo nombre.
     *
     * @param nombreCategoria      Nombre de la nueva categoría.
     * @param descripcionCategoria Descripción del tipo de objetos que agrupa.
     * @return Mensaje de éxito o error.
     */
    public String agregarCategoria(String nombreCategoria, String descripcionCategoria) {
        if (nombreCategoria == null || nombreCategoria.trim().isEmpty()) {
            return "Error: El nombre de la categoría no puede estar vacío.";
        }
        Categoria nuevaCategoria = new Categoria(nombreCategoria, descripcionCategoria);
        for (Categoria categoriaExistente : listaDeCategorias) {
            if (categoriaExistente.equals(nuevaCategoria)) {
                return "Error: Ya existe una categoría con ese nombre.";
            }
        }
        listaDeCategorias.add(nuevaCategoria);
        return "Categoría '" + nombreCategoria + "' registrada exitosamente.";
    }

    /**
     * Retorna el listado de categorías formateado para su visualización.
     *
     * @return Lista de cadenas con la información de cada categoría.
     */
    public ArrayList<String> getListaCategoriasFormateada() {
        ArrayList<String> categoriasFormateadas = new ArrayList<>();
        for (int i = 0; i < listaDeCategorias.size(); i++) {
            categoriasFormateadas.add((i + 1) + ". " + listaDeCategorias.get(i).toString());
        }
        return categoriasFormateadas;
    }

    /**
     * Retorna los nombres de las categorías activas para mostrar en ComboBox.
     *
     * @return Lista de nombres de categorías activas.
     */
    public ArrayList<String> getNombresCategoriasActivas() {
        ArrayList<String> nombresActivos = new ArrayList<>();
        for (Categoria categoria : listaDeCategorias) {
            if (categoria.isActiva()) {
                nombresActivos.add(categoria.getNombreCategoria());
            }
        }
        return nombresActivos;
    }

// ===================== GESTIÓN DE MODERADORES =====================

    /**
     * Selecciona a un coleccionista como moderador de la plataforma.
     * Solo los coleccionistas pueden ser seleccionados como moderadores.
     *
     * @param identificacionColeccionista Identificación del coleccionista a seleccionar.
     * @return Mensaje de éxito o error.
     */
    public String seleccionarColeccionistaComoModerador(String identificacionColeccionista) {
        Usuario usuarioEncontrado = buscarUsuarioPorIdentificacion(identificacionColeccionista);
        if (usuarioEncontrado == null) {
            return "Error: No se encontró ningún usuario con esa identificación.";
        }
        if (!(usuarioEncontrado instanceof Coleccionista)) {
            return "Error: Solo los coleccionistas pueden ser seleccionados como moderadores.";
        }
        Coleccionista coleccionistaSeleccionado = (Coleccionista) usuarioEncontrado;
        if (coleccionistaSeleccionado.isEsModerador()) {
            return "Error: Este coleccionista ya tiene el rol de moderador.";
        }
        coleccionistaSeleccionado.setEsModerador(true);
        return "El coleccionista " + coleccionistaSeleccionado.getNombreCompleto()
                + " ha sido seleccionado como moderador exitosamente.";
    }

// ===================== GESTIÓN DE SUBASTAS AVANZADA =====================

    /**
     * Cierra una subasta activa marcándola como vencida.
     * Una subasta cerrada ya no acepta nuevas ofertas.
     *
     * @param indiceSubasta Índice base 1 de la subasta a cerrar.
     * @return Mensaje con el resultado del cierre.
     */
    public String cerrarSubasta(int indiceSubasta) {
        int indiceBase0 = indiceSubasta - 1;
        if (indiceBase0 < 0 || indiceBase0 >= listaDeSubastas.size()) {
            return "Error: El número de subasta ingresado no es válido.";
        }
        Subasta subastaSeleccionada = listaDeSubastas.get(indiceBase0);
        if (!subastaSeleccionada.getEstadoActual().equals(Subasta.ESTADO_ACTIVA)) {
            return "Error: Solo se pueden cerrar subastas activas. Estado actual: "
                    + subastaSeleccionada.getEstadoActual();
        }
        subastaSeleccionada.setEstadoActual(Subasta.ESTADO_VENCIDA);
        return "Subasta cerrada exitosamente.";
    }

    /**
     * Retorna las subastas en las que el usuario tiene participación.
     * Si es creador muestra las subastas que creó.
     * Si es coleccionista también muestra las subastas donde realizó ofertas.
     *
     * @param identificacionUsuario Identificación del usuario a consultar.
     * @return Lista de cadenas con las subastas del usuario.
     */
    public ArrayList<String> getSubastasPorUsuario(String identificacionUsuario) {
        ArrayList<String> subastasDelUsuario = new ArrayList<>();
        Usuario usuarioEncontrado = buscarUsuarioPorIdentificacion(identificacionUsuario);
        if (usuarioEncontrado == null) {
            subastasDelUsuario.add("Error: No se encontró el usuario con esa identificación.");
            return subastasDelUsuario;
        }
        int contadorSubastas = 0;
        for (int i = 0; i < listaDeSubastas.size(); i++) {
            Subasta subasta = listaDeSubastas.get(i);
            boolean esCreador = subasta.getCreadorDeLaSubasta()
                    .getNumeroIdentificacion()
                    .equals(identificacionUsuario);

            boolean participoComoOferente = false;
            for (Oferta oferta : subasta.getOfertasRecibidas()) {
                if (oferta.getColeccionistaOferente() != null &&
                        oferta.getColeccionistaOferente()
                                .getNumeroIdentificacion()
                                .equals(identificacionUsuario)) {
                    participoComoOferente = true;
                    break;
                }
            }
            if (esCreador || participoComoOferente) {
                String rolEnSubasta = esCreador ? "Creador" : "Participante";
                subastasDelUsuario.add("Subasta #" + (i + 1)
                        + " [" + rolEnSubasta + "]: "
                        + subasta.toString());
                contadorSubastas++;
            }
        }
        if (contadorSubastas == 0) {
            subastasDelUsuario.add("No tiene subastas registradas en la plataforma.");
        }
        return subastasDelUsuario;
    }

    /**
     * Verifica si un vendedor existe en el sistema por su correo electrónico.
     * Se usa en el flujo especial de creación de subasta para vendedores.
     *
     * @param correoElectronico Correo electrónico del vendedor a buscar.
     * @return El Vendedor encontrado, o null si no existe.
     */
    public Vendedor buscarVendedorPorCorreo(String correoElectronico) {
        for (Usuario usuario : listaDeUsuarios) {
            if (usuario instanceof Vendedor &&
                    usuario.getCorreoElectronico().equals(correoElectronico)) {
                return (Vendedor) usuario;
            }
        }
        return null;
    }

    /**
     * Registra un vendedor rápido con datos mínimos para crear una subasta.
     * Se usa cuando el vendedor no existe y decide crear una cuenta básica.
     *
     * @param nombreCompleto      Nombre completo del vendedor.
     * @param correoElectronico   Correo electrónico del vendedor.
     * @param direccionResidencia Dirección del vendedor.
     * @return Mensaje de éxito o error.
     */
    public String registrarVendedorRapido(String nombreCompleto, String correoElectronico,
                                          String direccionResidencia) {
        for (Usuario usuario : listaDeUsuarios) {
            if (usuario.getCorreoElectronico().equals(correoElectronico)) {
                return "Error: Ya existe un usuario con ese correo electrónico.";
            }
        }
        Vendedor nuevoVendedorRapido = new Vendedor(
                nombreCompleto, correoElectronico,
                LocalDate.of(1990, 1, 1), "temporal123",
                correoElectronico, 0.0, direccionResidencia);
        listaDeUsuarios.add(nuevoVendedorRapido);
        return "Vendedor registrado exitosamente. Bienvenido, " + nombreCompleto + "!";
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
     * Verifica si ya existe un usuario con la misma identificación en el sistema
     *
     * @param numeroIdentificacion Identificación a verificar
     * @return true si ya existe, false si no existe
     */
    private boolean existeUsuarioConIdentificacion(String numeroIdentificacion) {
        for (Usuario usuario : listaDeUsuarios) {
            if (usuario.getNumeroIdentificacion().equals(numeroIdentificacion)) {
                return true;
            }
        }
        return false;
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

    //Inicio de sesión
    /**
     * Valida las credenciales de un usuario en el sistema
     * Busca un usuario con el correo y contraseña
     *
     * @param correoElectronico Correo electrónico ingresado.
     * @param contrasena        Contraseña ingresada.
     * @return Mensaje indicando el resultado del inicio de sesión.
     */
    public String iniciarSesion(String correoElectronico, String contrasena) {
        for (Usuario usuario : listaDeUsuarios) {
            if (usuario.getCorreoElectronico().equals(correoElectronico)
                    && usuario.getContrasena().equals(contrasena)) {
                return "Bienvenido, " + usuario.getNombreCompleto()
                        + ". Sesión iniciada correctamente.";
            }
        }
        return "Error: Correo o contraseña incorrectos.";
    }

    //Autenticación con retorno de usuario

    /**
     * Autentica un usuario y retorna el objeto Usuario si las credenciales son correctas.
     * A diferencia de iniciarSesion(), este método retorna el objeto para gestionar la sesión.
     *
     * @param correoElectronico Correo electrónico ingresado.
     * @param contrasena        Contraseña ingresada.
     * @return El Usuario autenticado, o null si las credenciales son incorrectas.
     */
    public Usuario autenticarUsuario(String correoElectronico, String contrasena) {
        for (Usuario usuario : listaDeUsuarios) {
            if (usuario.getCorreoElectronico().equals(correoElectronico)
                    && usuario.getContrasena().equals(contrasena)
                    && usuario.isActivo()) {
                return usuario;
            }
        }
        return null;
    }

//Modificación de usuarios

    /**
     * Modifica los datos básicos de un usuario registrado en la plataforma.
     * Solo se pueden modificar nombre, correo y dirección (si aplica).
     *
     * @param numeroIdentificacion  Identificación del usuario a modificar.
     * @param nuevoNombreCompleto   Nuevo nombre completo del usuario.
     * @param nuevoCorreo           Nuevo correo electrónico.
     * @param nuevaDireccion        Nueva dirección (solo para vendedor y coleccionista).
     * @return Mensaje de éxito o error.
     */
    public String modificarUsuario(String numeroIdentificacion, String nuevoNombreCompleto,
                                   String nuevoCorreo, String nuevaDireccion) {
        Usuario usuarioEncontrado = buscarUsuarioPorIdentificacion(numeroIdentificacion);
        if (usuarioEncontrado == null) {
            return "Error: No se encontró el usuario con esa identificación.";
        }
        usuarioEncontrado.setNombreCompleto(nuevoNombreCompleto);
        usuarioEncontrado.setCorreoElectronico(nuevoCorreo);
        if (usuarioEncontrado instanceof Vendedor) {
            Vendedor vendedorEncontrado = (Vendedor) usuarioEncontrado;
            vendedorEncontrado.setDireccionResidencia(nuevaDireccion);
        } else if (usuarioEncontrado instanceof Coleccionista) {
            Coleccionista coleccionistaEncontrado = (Coleccionista) usuarioEncontrado;
            coleccionistaEncontrado.setDireccionResidencia(nuevaDireccion);
        }
        return "Usuario modificado exitosamente.";
    }

//Activación e inactivación de usuarios

    /**
     * Cambia el estado activo/inactivo de un usuario.
     * Si estaba activo lo inactiva, y si estaba inactivo lo activa.
     *
     * @param numeroIdentificacion Identificación del usuario a activar o inactivar.
     * @return Mensaje indicando el nuevo estado del usuario.
     */
    public String activarInactivarUsuario(String numeroIdentificacion) {
        Usuario usuarioEncontrado = buscarUsuarioPorIdentificacion(numeroIdentificacion);
        if (usuarioEncontrado == null) {
            return "Error: No se encontró el usuario con esa identificación.";
        }
        if (usuarioEncontrado instanceof Moderador) {
            return "Error: No se puede inactivar al moderador de la plataforma.";
        }
        usuarioEncontrado.setActivo(!usuarioEncontrado.isActivo());
        String nuevoEstado = usuarioEncontrado.isActivo() ? "activado" : "inactivado";
        return "Usuario " + usuarioEncontrado.getNombreCompleto() + " " + nuevoEstado + " exitosamente.";
    }

//Adjudicación de subastas
    /**
     * Adjudica una subasta al coleccionista con la oferta más alta.
     * Cambia el estado de la subasta a ADJUDICADA.
     *
     * @param indiceSubasta Índice base 1 de la subasta a adjudicar.
     * @return Mensaje con el resultado de la adjudicación.
     */
    public String adjudicarSubasta(int indiceSubasta) {
        int indiceBase0 = indiceSubasta - 1;
        if (indiceBase0 < 0 || indiceBase0 >= listaDeSubastas.size()) {
            return "Error: El número de subasta ingresado no es válido.";
        }
        Subasta subastaSeleccionada = listaDeSubastas.get(indiceBase0);
        if (subastaSeleccionada.getOfertasRecibidas().isEmpty()) {
            return "Error: La subasta no tiene ofertas para adjudicar.";
        }
        Oferta ofertaGanadora = subastaSeleccionada.getOfertaMasAlta();
        subastaSeleccionada.setEstadoActual(Subasta.ESTADO_ADJUDICADA);

        // Generar la orden de adjudicación
        OrdenAdjudicacion nuevaOrden = new OrdenAdjudicacion(
                ofertaGanadora.getNombreDelOferente(),
                LocalDate.now(),
                subastaSeleccionada.getObjetosSubastados(),
                ofertaGanadora.getPrecioOfertado());
        listaDeOrdenes.add(nuevaOrden);

        return "Subasta adjudicada a " + ofertaGanadora.getNombreDelOferente()
                + " por ¢" + String.format("%.2f", ofertaGanadora.getPrecioOfertado()) + ".";
    }

    /**
     * Retorna el listado de órdenes de adjudicación formateado para su visualización.
     *
     * @return Lista de cadenas con la información de cada orden.
     */
    public ArrayList<String> getListaOrdenesFormateada() {
        ArrayList<String> ordenesFormateadas = new ArrayList<>();
        for (int i = 0; i < listaDeOrdenes.size(); i++) {
            ordenesFormateadas.add("Orden #" + (i + 1) + ": " + listaDeOrdenes.get(i).toString());
        }
        return ordenesFormateadas;
    }

    /**
     * Lee el archivo de configuración de la aplicación al iniciar el sistema.
     * La información de configuración debe estar en el archivo config.txt
     * ubicado en la raíz del proyecto.
     * Si el archivo no existe o no se puede leer, el sistema continúa
     * con valores por defecto.
     */
    private void leerArchivoConfiguracion() {
        try (BufferedReader lectorArchivo = new BufferedReader(
                new FileReader("config.txt"))) {
            String lineaActual;
            System.out.println("=== Configuración del sistema ===");
            while ((lineaActual = lectorArchivo.readLine()) != null) {
                // Ignorar líneas vacías o comentarios que empiezan con #
                if (!lineaActual.trim().isEmpty() && !lineaActual.startsWith("#")) {
                    System.out.println(lineaActual);
                }
            }
            System.out.println("=================================");
        } catch (IOException errorLectura) {
            System.out.println("Advertencia: No se encontró el archivo config.txt. " +
                    "El sistema continuará con valores por defecto.");
        }
    }

    /**
     * Selecciona aleatoriamente un coleccionista con rol de moderador
     * de la lista de usuarios registrados.
     *
     * @return Un Coleccionista moderador aleatorio, o null si no hay ninguno.
     */
    private Coleccionista seleccionarModeradorAleatorio() {
        ArrayList<Coleccionista> moderadoresDisponibles = new ArrayList<>();
        for (Usuario usuario : listaDeUsuarios) {
            if (usuario instanceof Coleccionista) {
                Coleccionista coleccionistaModerador = (Coleccionista) usuario;
                if (coleccionistaModerador.isEsModerador()) {
                    moderadoresDisponibles.add(coleccionistaModerador);
                }
            }
        }
        if (moderadoresDisponibles.isEmpty()) {
            return null;
        }
        int indiceAleatorio = (int) (Math.random() * moderadoresDisponibles.size());
        return moderadoresDisponibles.get(indiceAleatorio);
    }



    /**
     * Busca una categoría en la lista por su nombre, ignorando mayúsculas.
     *
     * @param nombreCategoria Nombre de la categoría a buscar.
     * @return La categoría encontrada, o null si no existe.
     */
    private Categoria buscarCategoriaPorNombre(String nombreCategoria) {
        for (Categoria categoria : listaDeCategorias) {
            if (categoria.getNombreCategoria().equalsIgnoreCase(nombreCategoria)) {
                return categoria;
            }
        }
        return null;
    }

    /**
     * Retorna el listado de subastas con información detallada para cada una.
     * Incluye estado, cantidad de ofertas y oferta más alta.
     *
     * @return Lista de cadenas con la información detallada de cada subasta.
     */
    public ArrayList<String> getListaSubastasDetallada() {
        ArrayList<String> subastasDetalladas = new ArrayList<>();
        for (int i = 0; i < listaDeSubastas.size(); i++) {
            Subasta subasta = listaDeSubastas.get(i);
            Oferta ofertaMasAlta = subasta.getOfertaMasAlta();
            String textoOfertaMasAlta = ofertaMasAlta != null
                    ? "¢" + String.format("%.2f", ofertaMasAlta.getPrecioOfertado())
                    + " por " + ofertaMasAlta.getNombreDelOferente()
                    : "Sin ofertas";

            String lineaSubasta = "Subasta #" + (i + 1) + "\n"
                    + "  Creador: " + subasta.getCreadorDeLaSubasta().getNombreCompleto()
                    + " | Puntuación: " + subasta.getPuntuacionDelCreador() + "\n"
                    + "  Estado: " + subasta.getEstadoActual()
                    + " | Precio mínimo: ¢" + String.format("%.2f",
                    subasta.getPrecioMinimoDeAceptacion()) + "\n"
                    + "  Tiempo restante: " + subasta.getTiempoRestanteFormateado() + "\n"
                    + "  Ofertas recibidas: " + subasta.getOfertasRecibidas().size()
                    + " | Oferta más alta: " + textoOfertaMasAlta + "\n"
                    + "  Categoría: " + (subasta.getCategoriaSubasta() != null
                    ? subasta.getCategoriaSubasta().getNombreCategoria()
                    : "Sin categoría");

            subastasDetalladas.add(lineaSubasta);
        }
        return subastasDetalladas;
    }

    /**
     * Verifica si un vendedor existe en el sistema por su correo electrónico.
     * Retorna un mensaje amigable indicando si ya tiene historial de ventas.
     *
     * @param correoElectronico Correo del vendedor a verificar.
     * @return Mensaje indicando si existe o no en el sistema.
     */
    public String verificarExistenciaVendedor(String correoElectronico) {
        Vendedor vendedorEncontrado = buscarVendedorPorCorreo(correoElectronico);
        if (vendedorEncontrado != null) {
            return "EXISTE: ¡Hola " + vendedorEncontrado.getNombreCompleto()
                    + "! Vemos que ya has realizado ventas en nuestra plataforma. "
                    + "¿Te gustaría crear una cuenta completa para disfrutar de "
                    + "más beneficios?";
        }
        return "NO_EXISTE";
    }

    /**
     * Permite al coleccionista ganador aceptar la adjudicación de una subasta.
     * Solo el ganador de la subasta puede aceptar la adjudicación.
     * Al aceptar se marca la orden como aceptada formalmente.
     *
     * @param indiceOrden           Índice base 1 de la orden a aceptar.
     * @param identificacionGanador Identificación del coleccionista que acepta.
     * @return Mensaje de éxito o error.
     */
    public String aceptarAdjudicacion(int indiceOrden, String identificacionGanador) {
        int indiceBase0 = indiceOrden - 1;
        if (indiceBase0 < 0 || indiceBase0 >= listaDeOrdenes.size()) {
            return "Error: El número de orden ingresado no es válido.";
        }
        OrdenAdjudicacion ordenSeleccionada = listaDeOrdenes.get(indiceBase0);

        if (ordenSeleccionada.isAceptada()) {
            return "Error: Esta adjudicación ya fue aceptada anteriormente.";
        }

        // Verificar que quien acepta es realmente el ganador
        Usuario usuarioEncontrado = buscarUsuarioPorIdentificacion(identificacionGanador);
        if (usuarioEncontrado == null) {
            return "Error: No se encontró el usuario con esa identificación.";
        }
        if (!ordenSeleccionada.getNombreCompletoDelGanador()
                .equals(usuarioEncontrado.getNombreCompleto())) {
            return "Error: Solo el ganador de la subasta puede aceptar la adjudicación.";
        }

        ordenSeleccionada.setAceptada(true);
        return "¡Adjudicación aceptada exitosamente! "
                + "Se ha generado su orden de compra por ¢"
                + String.format("%.2f", ordenSeleccionada.getPrecioTotalDeLaOrden()) + ".";
    }

    /**
     * Retorna las órdenes de adjudicación pendientes de aceptación.
     *
     * @return Lista de cadenas con las órdenes pendientes.
     */
    public ArrayList<String> getOrdenesPendientesDeAceptacion() {
        ArrayList<String> ordenesPendientes = new ArrayList<>();
        for (int i = 0; i < listaDeOrdenes.size(); i++) {
            OrdenAdjudicacion orden = listaDeOrdenes.get(i);
            if (!orden.isAceptada()) {
                ordenesPendientes.add("Orden #" + (i + 1) + ": "
                        + orden.toString()
                        + " | Estado: PENDIENTE DE ACEPTACIÓN");
            }
        }
        if (ordenesPendientes.isEmpty()) {
            ordenesPendientes.add("No hay órdenes pendientes de aceptación.");
        }
        return ordenesPendientes;
    }

    /**
     * Marca una orden como entregada.
     * Solo el ganador puede marcar la orden como entregada.
     * Al marcar como entregada se habilita la calificación mutua.
     *
     * @param indiceOrden           Índice base 1 de la orden.
     * @param identificacionGanador Identificación del ganador que marca la entrega.
     * @return Mensaje de éxito o error.
     */
    public String marcarOrdenComoEntregada(int indiceOrden,
                                           String identificacionGanador) {
        int indiceBase0 = indiceOrden - 1;
        if (indiceBase0 < 0 || indiceBase0 >= listaDeOrdenes.size()) {
            return "Error: El número de orden ingresado no es válido.";
        }
        OrdenAdjudicacion ordenSeleccionada = listaDeOrdenes.get(indiceBase0);

        if (!ordenSeleccionada.isAceptada()) {
            return "Error: La orden debe ser aceptada antes de marcarla como entregada.";
        }
        if (ordenSeleccionada.isEntregada()) {
            return "Error: Esta orden ya fue marcada como entregada.";
        }

        // Verificar que quien marca la entrega es el ganador
        Usuario usuarioEncontrado = buscarUsuarioPorIdentificacion(identificacionGanador);
        if (usuarioEncontrado == null) {
            return "Error: No se encontró el usuario con esa identificación.";
        }
        if (!ordenSeleccionada.getNombreCompletoDelGanador()
                .equals(usuarioEncontrado.getNombreCompleto())) {
            return "Error: Solo el ganador puede marcar la orden como entregada.";
        }

        ordenSeleccionada.setEntregada(true);
        return "Orden marcada como entregada. ¡Ya podés calificar al vendedor!";
    }

    /**
     * Registra la calificación del ganador al vendedor de una orden entregada.
     * La escala es del 1 al 5 donde 1 es lo más bajo y 5 lo más alto.
     *
     * @param indiceOrden           Índice base 1 de la orden.
     * @param identificacionGanador Identificación del ganador que califica.
     * @param calificacion          Valor del 1 al 5.
     * @return Mensaje de éxito o error.
     */
    public String calificarVendedor(int indiceOrden, String identificacionGanador,
                                    int calificacion) {
        if (calificacion < 1 || calificacion > 5) {
            return "Error: La calificación debe ser entre 1 y 5.";
        }
        int indiceBase0 = indiceOrden - 1;
        if (indiceBase0 < 0 || indiceBase0 >= listaDeOrdenes.size()) {
            return "Error: El número de orden ingresado no es válido.";
        }
        OrdenAdjudicacion ordenSeleccionada = listaDeOrdenes.get(indiceBase0);

        if (!ordenSeleccionada.isEntregada()) {
            return "Error: Solo se puede calificar después de marcar la orden como entregada.";
        }
        if (ordenSeleccionada.getCalificacionDelGanadorAlVendedor() > 0) {
            return "Error: Ya calificaste al vendedor de esta orden.";
        }

        // Verificar que quien califica es el ganador
        Usuario usuarioEncontrado = buscarUsuarioPorIdentificacion(identificacionGanador);
        if (usuarioEncontrado == null) {
            return "Error: No se encontró el usuario con esa identificación.";
        }
        if (!ordenSeleccionada.getNombreCompletoDelGanador()
                .equals(usuarioEncontrado.getNombreCompleto())) {
            return "Error: Solo el ganador puede calificar al vendedor.";
        }

        ordenSeleccionada.setCalificacionDelGanadorAlVendedor(calificacion);

        // Actualizar puntuación del creador de la subasta correspondiente
        actualizarPuntuacionUsuario(ordenSeleccionada, calificacion, false);

        return "Calificación de " + calificacion + "/5 registrada al vendedor exitosamente.";
    }

    /**
     * Registra la calificación del vendedor al ganador de una orden entregada.
     * La escala es del 1 al 5 donde 1 es lo más bajo y 5 lo más alto.
     *
     * @param indiceOrden             Índice base 1 de la orden.
     * @param identificacionVendedor  Identificación del vendedor que califica.
     * @param calificacion            Valor del 1 al 5.
     * @return Mensaje de éxito o error.
     */
    public String calificarGanador(int indiceOrden, String identificacionVendedor,
                                   int calificacion) {
        if (calificacion < 1 || calificacion > 5) {
            return "Error: La calificación debe ser entre 1 y 5.";
        }
        int indiceBase0 = indiceOrden - 1;
        if (indiceBase0 < 0 || indiceBase0 >= listaDeOrdenes.size()) {
            return "Error: El número de orden ingresado no es válido.";
        }
        OrdenAdjudicacion ordenSeleccionada = listaDeOrdenes.get(indiceBase0);

        if (!ordenSeleccionada.isEntregada()) {
            return "Error: Solo se puede calificar después de marcar la orden como entregada.";
        }
        if (ordenSeleccionada.getCalificacionDelVendedorAlGanador() > 0) {
            return "Error: Ya calificaste al ganador de esta orden.";
        }

        ordenSeleccionada.setCalificacionDelVendedorAlGanador(calificacion);

        // Actualizar puntuación del ganador
        actualizarPuntuacionUsuario(ordenSeleccionada, calificacion, true);

        return "Calificación de " + calificacion + "/5 registrada al ganador exitosamente.";
    }

    /**
     * Actualiza la puntuación de reputación del usuario calificado.
     * Promedia la nueva calificación con la puntuación actual.
     *
     * @param orden        Orden de adjudicación relacionada.
     * @param calificacion Nueva calificación recibida.
     * @param esGanador    true si se actualiza al ganador, false si es al vendedor.
     */
    private void actualizarPuntuacionUsuario(OrdenAdjudicacion orden,
                                             int calificacion, boolean esGanador) {
        for (Usuario usuario : listaDeUsuarios) {
            boolean coincide = esGanador
                    ? usuario.getNombreCompleto().equals(orden.getNombreCompletoDelGanador())
                    : false;

            if (!esGanador) {
                // Buscar el creador de la subasta correspondiente a la orden
                for (Subasta subasta : listaDeSubastas) {
                    if (subasta.getObjetosSubastados()
                            .equals(orden.getObjetosAdjudicados())) {
                        coincide = subasta.getCreadorDeLaSubasta()
                                .getNumeroIdentificacion()
                                .equals(usuario.getNumeroIdentificacion());
                        break;
                    }
                }
            }

            if (coincide) {
                if (usuario instanceof Vendedor) {
                    Vendedor vendedor = (Vendedor) usuario;
                    double nuevaPuntuacion = vendedor.getPuntuacionReputacion() == 0
                            ? calificacion
                            : (vendedor.getPuntuacionReputacion() + calificacion) / 2.0;
                    vendedor.setPuntuacionReputacion(nuevaPuntuacion);
                } else if (usuario instanceof Coleccionista) {
                    Coleccionista coleccionista = (Coleccionista) usuario;
                    double nuevaPuntuacion = coleccionista.getPuntuacionReputacion() == 0
                            ? calificacion
                            : (coleccionista.getPuntuacionReputacion() + calificacion) / 2.0;
                    coleccionista.setPuntuacionReputacion(nuevaPuntuacion);
                }
                break;
            }
        }
    }

    /**
     * Retorna las órdenes entregadas disponibles para calificar.
     *
     * @return Lista de cadenas con las órdenes entregadas.
     */
    public ArrayList<String> getOrdenesEntregadasFormateadas() {
        ArrayList<String> ordenesEntregadas = new ArrayList<>();
        for (int i = 0; i < listaDeOrdenes.size(); i++) {
            OrdenAdjudicacion orden = listaDeOrdenes.get(i);
            if (orden.isEntregada()) {
                String calGanador = orden.getCalificacionDelGanadorAlVendedor() > 0
                        ? String.valueOf(orden.getCalificacionDelGanadorAlVendedor())
                        : "Pendiente";
                String calVendedor = orden.getCalificacionDelVendedorAlGanador() > 0
                        ? String.valueOf(orden.getCalificacionDelVendedorAlGanador())
                        : "Pendiente";
                ordenesEntregadas.add("Orden #" + (i + 1)
                        + " | Ganador: " + orden.getNombreCompletoDelGanador()
                        + " | Cal. del ganador al vendedor: " + calGanador
                        + " | Cal. del vendedor al ganador: " + calVendedor);
            }
        }
        if (ordenesEntregadas.isEmpty()) {
            ordenesEntregadas.add("No hay órdenes entregadas para calificar.");
        }
        return ordenesEntregadas;
    }

    /**
     * Simula el envío de un correo electrónico al moderador asignado a una subasta.
     * En un ambiente de producción este método se conectaría a un servidor SMTP
     * usando las credenciales del archivo config.txt para enviar el correo real.
     * Por ahora imprime el mensaje en consola y retorna confirmación visual.
     *
     * @param moderadorAsignado  Coleccionista moderador que recibirá el correo.
     * @param nombreCreador      Nombre del usuario que creó la subasta.
     * @return Mensaje confirmando la simulación del envío.
     */
    private String simularEnvioCorreoModerador(Coleccionista moderadorAsignado,
                                               String nombreCreador) {
        String destinatario  = moderadorAsignado.getCorreoElectronico();
        String nombreModerador = moderadorAsignado.getNombreCompleto();

        // Simular el contenido del correo que se enviaría
        String contenidoCorreo = "\n"
                + "========================================\n"
                + "CORREO SIMULADO — Plataforma de Subastas\n"
                + "========================================\n"
                + "Para: " + destinatario + "\n"
                + "Asunto: Fuiste seleccionado como moderador\n"
                + "----------------------------------------\n"
                + "Estimado/a " + nombreModerador + ",\n\n"
                + "Has sido seleccionado/a aleatoriamente para moderar\n"
                + "la subasta creada por: " + nombreCreador + ".\n\n"
                + "Por favor revisá la plataforma para ver los detalles.\n\n"
                + "Saludos,\n"
                + "Plataforma Digital de Subastas Especializadas\n"
                + "========================================";

        // Imprimir en consola simulando el envío
        System.out.println(contenidoCorreo);

        return "Correo enviado a " + destinatario + ".";
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

    /**
     * Obtiene la lista completa de categorías registradas.
     *
     * @return Lista de todas las categorías.
     */
    public ArrayList<Categoria> getListaDeCategorias() {
        return listaDeCategorias;
    }

    /**
     * Establece la lista de categorías.
     *
     * @param listaDeCategorias Nueva lista de categorías.
     */
    public void setListaDeCategorias(ArrayList<Categoria> listaDeCategorias) {
        this.listaDeCategorias = listaDeCategorias;
    }

}
