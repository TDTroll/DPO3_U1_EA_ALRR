package com.unadm.dpo3.alcaldias.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Ventana que muestra los registros leídos del archivo de texto.
 *
 * @author Alejandro Jassan Rea Rivera
 */
// MVC - Vista + Herencia: es-un JDialog.
public class VentanaRegistros extends JDialog {

    private JTextArea areaTexto;
    private JButton btnCerrar;

    /**
     * Crea la ventana de consulta de registros.
     *
     * @param duenio   ventana que la abre.
     * @param contenido texto con los registros leídos del archivo.
     */
    // Dependencia: recibe el duenio y el texto ya leido del archivo.
    public VentanaRegistros(Frame duenio, String contenido) {
        super(duenio, "Registros guardados - Catálogo de Alcaldías", true);

        areaTexto = new JTextArea(20, 62);
        areaTexto.setText(contenido);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        areaTexto.setCaretPosition(0);

        JScrollPane desplazador = new JScrollPane(areaTexto);
        desplazador.setBorder(BorderFactory.createEmptyBorder(10, 10, 6, 10));
        add(desplazador, BorderLayout.CENTER);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(evt -> dispose());

        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);

        setSize(new Dimension(640, 440));
        setLocationRelativeTo(duenio);
    }
}
