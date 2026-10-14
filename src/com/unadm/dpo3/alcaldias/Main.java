package com.unadm.dpo3.alcaldias;

import com.unadm.dpo3.alcaldias.view.VentanaInicio;
import javax.swing.UIManager;

/**
 * Clase principal del programa.
 *
 * @author Alejandro Jassan Rea Rivera
 */
public class Main {

    public static void main(String[] args) {
        try {
            // Se usa el estilo del sistema operativo (si no se puede, se deja el de Java).
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo cambiar el estilo de la ventana.");
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Dependencia: Main crea y muestra la vista de inicio.
                new VentanaInicio().setVisible(true);
            }
        });
    }
}
