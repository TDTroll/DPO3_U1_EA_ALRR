package com.unadm.dpo3.alcaldias.model;

import java.io.IOException;
import java.util.List;

/**
 * Contrato del patrón DAO (Data Access Object) para guardar y leer alcaldías.
 *
 * @author Alejandro Jassan Rea Rivera
 */
// Patron DAO: contrato que cumple AlcaldiaDAOArchivo mediante "implements".
public interface AlcaldiaDAO {

    /**
     * Guarda una alcaldía.
     *
     * @param alcaldia alcaldía a registrar.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    void guardar(Alcaldia alcaldia) throws IOException;

    /**
     * Lee todas las alcaldías guardadas.
     *
     * @return lista de alcaldías (vacía si todavía no hay registros).
     * @throws IOException si ocurre un error de lectura.
     */
    List<Alcaldia> listar() throws IOException;

    /**
     * Modifica un registro que ya está guardado.
     *
     * @param indice   posición del registro (0 es el primero de la lista).
     * @param alcaldia datos nuevos que reemplazan al registro.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    void actualizar(int indice, Alcaldia alcaldia) throws IOException;

    /**
     * Elimina un registro que ya está guardado.
     *
     * @param indice posición del registro (0 es el primero de la lista).
     * @throws IOException si ocurre un error de entrada/salida.
     */
    void eliminar(int indice) throws IOException;

    /**
     * Ruta del archivo donde se guardan los datos.
     *
     * @return ruta del archivo de texto.
     */
    String rutaArchivo();
}
