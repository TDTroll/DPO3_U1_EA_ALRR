package com.unadm.dpo3.alcaldias.view;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Dibuja un botón dentro de una celda de la tabla.
 *
 * @author Alejandro Jassan Rea Rivera
 */
// Herencia + Implementacion: es-un JButton y cumple TableCellRenderer (patron Renderer).
public class BotonCeldaRenderer extends JButton implements TableCellRenderer {

    /** Crea el renderizador de botones. */
    public BotonCeldaRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object valor,
            boolean seleccionada, boolean conFoco, int fila, int columna) {
        setText(valor == null ? "" : valor.toString());
        return this;
    }
}
