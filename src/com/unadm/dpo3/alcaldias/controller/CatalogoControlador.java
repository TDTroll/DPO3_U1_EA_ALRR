package com.unadm.dpo3.alcaldias.controller;

import com.unadm.dpo3.alcaldias.model.AlcaldiaDAO;
import com.unadm.dpo3.alcaldias.model.Alcaldia;
import com.unadm.dpo3.alcaldias.view.CatalogoAlcaldias;
import com.unadm.dpo3.alcaldias.view.VentanaRegistros;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Controlador del formulario del Catálogo de Alcaldías (parte "Controlador"
 * del patrón MVC).
 *
 * @author Alejandro Jassan Rea Rivera
 */
// MVC - Controlador: comunica la Vista con el Modelo (DAO).
public class CatalogoControlador {

    private final CatalogoAlcaldias vista; // Asociacion/Agregacion: la vista se recibe, no se crea aqui.
    private final AlcaldiaDAO dao; // Depende de la interfaz (Strategy/inyeccion), no de la clase concreta.

    /**
     * Crea el controlador y conecta los botones de la vista con sus acciones.
     *
     * @param vista formulario del catálogo.
     * @param dao   objeto de acceso a datos que se usará.
     */
    public CatalogoControlador(CatalogoAlcaldias vista, AlcaldiaDAO dao) {
        this.vista = vista;
        this.dao = dao;

        // Patron Observer: la vista notifica el clic y el controlador responde.
        vista.getBtnGuardar().addActionListener(evt -> guardar());
        vista.getBtnLimpiar().addActionListener(evt -> vista.limpiarCampos());
        vista.getBtnVer().addActionListener(evt -> verRegistros());
        vista.getBtnRegresar().addActionListener(evt -> vista.regresarAlMenu());
    }

    /**
     * Acción del botón Guardar: revisa que el campo no esté vacío, guarda la
     * información en el archivo de texto y muestra el mensaje correspondiente.
     */
    public void guardar() {
        String nombre = vista.getTxtNombre().getText().trim();

        // Primero se verifica que el campo principal no esté vacío.
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "El campo \"Nombre de la alcaldía\" no puede estar vacío.\n"
                    + "Captura la información antes de guardar.",
                    "Datos incompletos", JOptionPane.ERROR_MESSAGE);
            vista.getTxtNombre().requestFocus();
            return;
        }

        // Si se capturó población, se revisa que sea un número.
        long poblacion = 0;
        String textoPoblacion = vista.getTxtPoblacion().getText().trim();
        if (!textoPoblacion.isEmpty()) {
            try {
                poblacion = Long.parseLong(textoPoblacion);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista,
                        "El campo \"Población\" debe contener sólo números.",
                        "Dato inválido", JOptionPane.ERROR_MESSAGE);
                vista.getTxtPoblacion().requestFocus();
                return;
            }
        }

        // Dependencia: el controlador crea el modelo Alcaldia.
        Alcaldia alcaldia = new Alcaldia(
                nombre,
                vista.getTxtAlcalde().getText().trim(),
                poblacion,
                vista.getTxtTelefono().getText().trim());

        try {
            dao.guardar(alcaldia);
            JOptionPane.showMessageDialog(vista,
                    "La información se guardó en el archivo de texto:\n\n" + alcaldia,
                    "Registro guardado", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarCampos();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vista,
                    "Ocurrió un error al escribir el archivo:\n" + e.getMessage(),
                    "Error al guardar", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Lee el archivo de texto con el DAO y muestra los registros en otra ventana. */
    public void verRegistros() {
        try {
            List<Alcaldia> catalogo = dao.listar();

            StringBuilder texto = new StringBuilder();
            if (catalogo.isEmpty()) {
                texto.append("Todavía no hay registros guardados.\n\n");
            } else {
                for (int i = 0; i < catalogo.size(); i++) {
                    texto.append(i + 1).append(". ").append(catalogo.get(i)).append("\n");
                }
                texto.append("\n");
            }
            texto.append("Archivo: ").append(dao.rutaArchivo());

            // Dependencia: el controlador usa la vista de registros.
            new VentanaRegistros(vista, texto.toString()).setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vista,
                    "No se pudo leer el archivo:\n" + e.getMessage(),
                    "Error de lectura", JOptionPane.ERROR_MESSAGE);
        }
    }
}
