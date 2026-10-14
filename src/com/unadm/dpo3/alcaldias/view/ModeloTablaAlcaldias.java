package com.unadm.dpo3.alcaldias.view;

import com.unadm.dpo3.alcaldias.model.Alcaldia;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de la tabla de la opción Consulta.
 *
 * @author Alejandro Jassan Rea Rivera
 */
// Herencia: es-un AbstractTableModel (patron TableModel); adapta el Modelo para la JTable.
public class ModeloTablaAlcaldias extends AbstractTableModel {

    /** Nombres de las columnas; las dos últimas son las de los botones. */
    private static final String[] COLUMNAS = {
        "Alcaldía",
        "Alcalde o alcaldesa",
        "Población",
        "Teléfono",
        "Modificar",
        "Eliminar"
    };

    // Agregacion: guarda referencias a los objetos Alcaldia del Modelo.
    private List<Alcaldia> catalogo = new ArrayList<Alcaldia>();

    @Override
    public int getRowCount() {
        return catalogo.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNAS.length;
    }

    @Override
    public String getColumnName(int columna) {
        return COLUMNAS[columna];
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Alcaldia alcaldia = catalogo.get(fila);
        switch (columna) {
            case 0:
                return alcaldia.getNombre();
            case 1:
                return alcaldia.getAlcalde();
            case 2:
                return alcaldia.getPoblacion();
            case 3:
                return alcaldia.getTelefono();
            case 4:
                return "Modificar";
            case 5:
                return "Eliminar";
            default:
                return "";
        }
    }

    @Override
    public boolean isCellEditable(int fila, int columna) {
        // Los datos se editan con el botón Modificar, no escribiendo en la celda.
        return false;
    }

    /**
     * Reemplaza los registros mostrados.
     *
     * @param registros alcaldías leídas del archivo de texto.
     */
    public void setAlcaldias(List<Alcaldia> registros) {
        this.catalogo = new ArrayList<Alcaldia>(registros);
        fireTableDataChanged();
    }

    /**
     * @param fila posición del registro.
     * @return la alcaldía que está en esa fila.
     */
    public Alcaldia getAlcaldia(int fila) {
        return catalogo.get(fila);
    }

    /**
     * Actualiza una fila con los datos nuevos.
     *
     * @param fila     posición del registro.
     * @param alcaldia datos nuevos.
     */
    public void actualizarFila(int fila, Alcaldia alcaldia) {
        catalogo.set(fila, alcaldia);
        fireTableRowsUpdated(fila, fila);
    }

    /**
     * Quita una fila de la tabla.
     *
     * @param fila posición del registro.
     */
    public void eliminarFila(int fila) {
        catalogo.remove(fila);
        fireTableRowsDeleted(fila, fila);
    }
}
