package com.unadm.dpo3.alcaldias.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del patrón DAO que guarda el catálogo en un archivo de texto
 * plano ({@code alcaldias.txt}).
 *
 * @author Alejandro Jassan Rea Rivera
 */
// Implementacion + DAO: cumple el contrato de AlcaldiaDAO (Singleton).
public class AlcaldiaDAOArchivo implements AlcaldiaDAO {

    private static final String NOMBRE_ARCHIVO = "alcaldias.txt";

    /** Primera línea del encabezado del archivo. */
    private static final String ENCABEZADO_1 = "# Catálogo de Alcaldías - Ferretería La Golondrina";

    /** Segunda línea del encabezado: explica el formato de cada registro. */
    private static final String ENCABEZADO_2 = "# Formato: nombre|alcalde|poblacion|telefono";

    private static AlcaldiaDAOArchivo instancia;

    private final File archivo;

    /** Constructor privado: sólo se puede obtener la instancia con getInstancia(). */
    private AlcaldiaDAOArchivo() {
        // El archivo se guarda en la carpeta desde donde se ejecuta el programa.
        String carpeta = System.getProperty("user.dir");
        this.archivo = new File(carpeta + File.separator + NOMBRE_ARCHIVO);
    }

    /**
     * Devuelve la única instancia de la clase.
     *
     * @return la instancia de AlcaldiaDAOArchivo.
     */
    // Patron Singleton: devuelve la unica instancia de la clase.
    public static AlcaldiaDAOArchivo getInstancia() {
        if (instancia == null) {
            instancia = new AlcaldiaDAOArchivo();
        }
        return instancia;
    }

    @Override
    public void guardar(Alcaldia alcaldia) throws IOException {
        FileWriter salida = null;
        BufferedWriter escritor = null;
        try {
            // El segundo parámetro en true significa "agregar al final del archivo".
            salida = new FileWriter(archivo, true);
            escritor = new BufferedWriter(salida);

            // La primera vez se escribe un encabezado para saber cómo está el archivo.
            if (archivo.length() == 0) {
                escribirEncabezado(escritor);
            }

            escritor.write(alcaldia.aLinea());
            escritor.newLine();
        } finally {
            if (escritor != null) {
                escritor.close();
            }
        }
    }

    @Override
    public List<Alcaldia> listar() throws IOException {
        List<Alcaldia> catalogo = new ArrayList<Alcaldia>();

        if (!archivo.exists()) {
            return catalogo;
        }

        FileReader entrada = null;
        BufferedReader lector = null;
        try {
            entrada = new FileReader(archivo);
            lector = new BufferedReader(entrada);

            String linea = lector.readLine();
            while (linea != null) {
                // Se saltan las líneas vacías y los renglones del encabezado.
                if (!linea.trim().isEmpty() && !linea.startsWith("#")) {
                    catalogo.add(Alcaldia.desdeLinea(linea));
                }
                linea = lector.readLine();
            }
        } finally {
            if (lector != null) {
                lector.close();
            }
        }
        return catalogo;
    }

    @Override
    public void actualizar(int indice, Alcaldia alcaldia) throws IOException {
        List<Alcaldia> catalogo = listar();
        if (indice < 0 || indice >= catalogo.size()) {
            return; // Posición inexistente: no hay nada que actualizar.
        }
        catalogo.set(indice, alcaldia);
        reescribir(catalogo);
    }

    @Override
    public void eliminar(int indice) throws IOException {
        List<Alcaldia> catalogo = listar();
        if (indice < 0 || indice >= catalogo.size()) {
            return; // Posición inexistente: no hay nada que eliminar.
        }
        catalogo.remove(indice);
        reescribir(catalogo);
    }

    /**
     * Vuelve a escribir todo el archivo a partir de la lista recibida.
     *
     * <p>Se usa al modificar o eliminar porque un archivo de texto plano no
     * permite borrar una línea: la forma más sencilla es reemplazar todo el
     * contenido por la lista ya actualizada.</p>
     *
     * @param catalogo registros que deben quedar guardados.
     * @throws IOException si ocurre un error al escribir.
     */
    private void reescribir(List<Alcaldia> catalogo) throws IOException {
        BufferedWriter escritor = null;
        try {
            // El segundo parámetro en false significa "reemplazar el contenido".
            escritor = new BufferedWriter(new FileWriter(archivo, false));
            escribirEncabezado(escritor);
            for (Alcaldia alcaldia : catalogo) {
                escritor.write(alcaldia.aLinea());
                escritor.newLine();
            }
        } finally {
            if (escritor != null) {
                escritor.close();
            }
        }
    }

    /** Escribe las dos líneas de encabezado del archivo. */
    private void escribirEncabezado(BufferedWriter escritor) throws IOException {
        escritor.write(ENCABEZADO_1);
        escritor.newLine();
        escritor.write(ENCABEZADO_2);
        escritor.newLine();
    }

    @Override
    public String rutaArchivo() {
        return archivo.getAbsolutePath();
    }
}
