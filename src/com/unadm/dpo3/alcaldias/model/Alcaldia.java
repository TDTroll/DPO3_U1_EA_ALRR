package com.unadm.dpo3.alcaldias.model;

/**
 * Modelo que representa una alcaldía del catálogo.
 *
 * @author Alejandro Jassan Rea Rivera
 */
// MVC - Modelo: clase de datos; la Vista y el Controlador dependen de ella.
public class Alcaldia {

    private String nombre;
    private String alcalde;
    private long poblacion;
    private String telefono;

    /** Constructor vacío. */
    public Alcaldia() {
    }

    /**
     * Crea una alcaldía con todos sus datos.
     *
     * @param nombre    nombre de la alcaldía.
     * @param alcalde   nombre del alcalde o alcaldesa.
     * @param poblacion número de habitantes.
     * @param telefono  teléfono de contacto.
     */
    public Alcaldia(String nombre, String alcalde, long poblacion, String telefono) {
        this.nombre = nombre;
        this.alcalde = alcalde;
        this.poblacion = poblacion;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlcalde() {
        return alcalde;
    }

    public void setAlcalde(String alcalde) {
        this.alcalde = alcalde;
    }

    public long getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(long poblacion) {
        this.poblacion = poblacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Convierte la alcaldía en una línea de texto con el formato
     * {@code nombre|alcalde|poblacion|telefono}.
     *
     * @return la línea que se escribe en el archivo.
     */
    public String aLinea() {
        return nombre + "|" + alcalde + "|" + poblacion + "|" + telefono;
    }

    /**
     * Reconstruye una alcaldía a partir de una línea leída del archivo.
     *
     * @param linea línea del archivo de texto.
     * @return la alcaldía correspondiente.
     */
    // Factory Method: reconstruye una Alcaldia desde una linea de texto.
    public static Alcaldia desdeLinea(String linea) {
        String[] datos = linea.split("\\|", -1);
        Alcaldia alcaldia = new Alcaldia();
        alcaldia.setNombre(datos[0]);
        alcaldia.setAlcalde(datos.length > 1 ? datos[1] : "");
        alcaldia.setPoblacion(datos.length > 2 ? convertirPoblacion(datos[2]) : 0);
        alcaldia.setTelefono(datos.length > 3 ? datos[3] : "");
        return alcaldia;
    }

    private static long convertirPoblacion(String valor) {
        try {
            return Long.parseLong(valor.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Alcaldía: " + nombre
                + " | Alcalde/alcaldesa: " + alcalde
                + " | Población: " + poblacion
                + " | Teléfono: " + telefono;
    }
}
