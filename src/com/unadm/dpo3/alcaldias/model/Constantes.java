package com.unadm.dpo3.alcaldias.model;

/**
 * Datos generales del estudiante y del módulo asignado.
 *
 * @author Alejandro Jassan Rea Rivera
 */
// Constantes globales: la Vista y el Controlador dependen de esta clase.
public final class Constantes {

    /** Nombre del módulo asignado segun el apellido paterno (Rea = R = rango Q-T). */
    public static final String NOMBRE_MODULO = "Catálogo de Alcaldías";

    /** Nombre completo del estudiante. */
    public static final String NOMBRE_ESTUDIANTE = "Alejandro Jassan Rea Rivera";

    /** Matrícula del estudiante. */
    public static final String MATRICULA = "ES251104164";

    /** Grupo del estudiante. */
    public static final String GRUPO = "DS-DPO3-2602-B2-001";

    /** Figura académica que imparte la unidad didáctica. */
    public static final String FIGURA_ACADEMICA = "Sandra Pulido Galván";

    /** Universidad. */
    public static final String UNIVERSIDAD = "Universidad Abierta y a Distancia de México (UnADM)";

    /** Unidad didáctica. */
    public static final String UNIDAD_DIDACTICA = "Programación Orientada a Objetos III";

    /** Unidad y actividad de la asignatura. */
    public static final String ACTIVIDAD = "Unidad 1. Archivos - Evidencia de aprendizaje. Programa de archivos";

    /** Etapa del proyecto: la Actividad 2 fue la Etapa 1 y esta entrega es la Etapa 2. */
    public static final String ETAPA = "Etapa 2";

    /**
     * Título de las ventanas: lleva el nombre del módulo asignado, la leyenda
     * "Etapa 2" y el nombre completo del estudiante, como lo pide la actividad.
     */
    public static final String TITULO_VENTANA =
            NOMBRE_MODULO + " - " + ETAPA + " - " + NOMBRE_ESTUDIANTE;

    private Constantes() {
        // Clase de constantes: no se instancia.
    }
}
