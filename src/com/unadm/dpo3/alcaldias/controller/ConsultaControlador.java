package com.unadm.dpo3.alcaldias.controller;

import com.unadm.dpo3.alcaldias.model.Alcaldia;
import com.unadm.dpo3.alcaldias.model.AlcaldiaDAO;
import com.unadm.dpo3.alcaldias.view.VentanaConsulta;
import com.unadm.dpo3.alcaldias.view.VentanaModificar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Controlador de la opción <b>Consulta</b> (parte "Controlador" del patrón MVC).
 *
 * @author Alejandro Jassan Rea Rivera
 */
// MVC - Controlador de la Consulta: llena la tabla y atiende Modificar/Eliminar.
public class ConsultaControlador {

    private final VentanaConsulta vista; // Asociacion/Agregacion: la vista se recibe por constructor.
    private final AlcaldiaDAO dao; // Depende de la interfaz AlcaldiaDAO (Strategy/inyeccion).

    /**
     * Crea el controlador, conecta la tabla con sus acciones y carga los datos.
     *
     * @param vista ventana de consulta.
     * @param dao   objeto de acceso a datos que se usará.
     */
    public ConsultaControlador(VentanaConsulta vista, AlcaldiaDAO dao) {
        this.vista = vista;
        this.dao = dao;

        vista.getBtnRegresar().addActionListener(evt -> vista.dispose());

        // Los botones de cada fila se atienden con un escucha del ratón.
        // Patron Observer: se escucha el clic en la tabla para Modificar/Eliminar.
        vista.getTabla().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                atenderClicEnTabla(evt);
            }
        });

        cargarRegistros();
    }

    /** Detecta si el clic fue sobre el botón Modificar o Eliminar de alguna fila. */
    private void atenderClicEnTabla(MouseEvent evt) {
        int fila = vista.getTabla().rowAtPoint(evt.getPoint());
        int columna = vista.getTabla().columnAtPoint(evt.getPoint());

        if (fila < 0 || fila >= vista.getTabla().getRowCount()) {
            return; // El clic no fue sobre un registro.
        }
        if (columna == VentanaConsulta.COLUMNA_MODIFICAR) {
            modificar(fila);
        } else if (columna == VentanaConsulta.COLUMNA_ELIMINAR) {
            eliminar(fila);
        }
    }

    /** Lee el archivo de texto y muestra los registros en la tabla. */
    public void cargarRegistros() {
        try {
            List<Alcaldia> catalogo = dao.listar();
            vista.mostrarRegistros(catalogo);

            if (catalogo.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "Todavía no hay registros guardados en el archivo de texto.",
                        "Consulta", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vista,
                    "No se pudo leer el archivo:\n" + e.getMessage(),
                    "Error de lectura", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina el registro indicado: primero pide confirmación, luego lo borra
     * del archivo de texto y actualiza la tabla.
     *
     * @param fila posición del registro en la tabla.
     */
    public void eliminar(int fila) {
        Alcaldia alcaldia = vista.getAlcaldia(fila);

        // Ventana de confirmación con las opciones Confirmar y Cancelar.
        int respuesta = JOptionPane.showOptionDialog(vista,
                "¿Deseas eliminar este registro?\n\n" + alcaldia,
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new Object[]{"Confirmar", "Cancelar"},
                "Cancelar");

        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                dao.eliminar(fila);
                vista.eliminarFila(fila);
                JOptionPane.showMessageDialog(vista,
                        "El registro se eliminó del archivo de texto.",
                        "Registro eliminado", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(vista,
                        "Ocurrió un error al actualizar el archivo:\n" + e.getMessage(),
                        "Error al eliminar", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Si se elige Cancelar (o se cierra la ventana), el registro se conserva.
    }

    /**
     * Abre el cuadro de diálogo para modificar el registro indicado.
     *
     * @param fila posición del registro en la tabla.
     */
    public void modificar(int fila) {
        Alcaldia original = vista.getAlcaldia(fila);
        // Dependencia: el controlador crea el dialogo VentanaModificar.
        VentanaModificar dialogo = new VentanaModificar(vista, original);

        dialogo.getBtnGuardar().addActionListener(evt -> guardarCambios(dialogo, fila));
        dialogo.getBtnCancelar().addActionListener(evt -> dialogo.cerrar());

        dialogo.setVisible(true);
    }

    /** Valida la captura, guarda los cambios en el archivo y refresca la tabla. */
    private void guardarCambios(VentanaModificar dialogo, int fila) {
        String nombre = dialogo.getTxtNombre().getText().trim();

        // Se valida que el campo principal no esté vacío antes de guardar.
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(dialogo,
                    "El campo \"Nombre de la alcaldía\" no puede estar vacío.\n"
                    + "Captura la información antes de guardar los cambios.",
                    "Datos incompletos", JOptionPane.ERROR_MESSAGE);
            dialogo.getTxtNombre().requestFocus();
            return;
        }

        // Si se capturó población, se revisa que sea un número.
        long poblacion = 0;
        String textoPoblacion = dialogo.getTxtPoblacion().getText().trim();
        if (!textoPoblacion.isEmpty()) {
            try {
                poblacion = Long.parseLong(textoPoblacion);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(dialogo,
                        "El campo \"Población\" debe contener sólo números.",
                        "Dato inválido", JOptionPane.ERROR_MESSAGE);
                dialogo.getTxtPoblacion().requestFocus();
                return;
            }
        }

        Alcaldia alcaldia = dialogo.getAlcaldia(poblacion);

        try {
            dao.actualizar(fila, alcaldia);
            vista.actualizarFila(fila, alcaldia);
            JOptionPane.showMessageDialog(dialogo,
                    "La información se actualizó en el archivo de texto:\n\n" + alcaldia,
                    "Registro modificado", JOptionPane.INFORMATION_MESSAGE);
            dialogo.cerrar();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(dialogo,
                    "Ocurrió un error al actualizar el archivo:\n" + e.getMessage(),
                    "Error al modificar", JOptionPane.ERROR_MESSAGE);
        }
    }
}
