package com.unadm.dpo3.alcaldias.view;

import com.unadm.dpo3.alcaldias.model.Constantes;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Pantalla de inicio: muestra la imagen alusiva al módulo asignado
 * (Catálogo de Alcaldías) y un botón para pasar al menú principal.
 *
 * @author Alejandro Jassan Rea Rivera
 */
// MVC - Vista + Herencia: es-un JFrame.
public class VentanaInicio extends JFrame {

    private JLabel lblPortada;
    private JLabel lblTitulo;
    private JButton btnContinuar;

    /** Crea la pantalla de inicio. */
    public VentanaInicio() {
        initComponents();
    }

    private void initComponents() {
        setTitle(Constantes.TITULO_VENTANA);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        lblPortada = new JLabel(new ImageIcon(
                getClass().getResource("recursos/portada_alcaldias.png")),
                SwingConstants.CENTER);
        add(lblPortada, BorderLayout.CENTER);

        lblTitulo = new JLabel(Constantes.NOMBRE_MODULO, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));

        btnContinuar = new JButton("Continuar al menú principal");
        btnContinuar.addActionListener((ActionEvent evt) -> {
            btnContinuarActionPerformed(evt);
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(lblTitulo);
        panelInferior.add(btnContinuar);

        add(panelInferior, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void btnContinuarActionPerformed(ActionEvent evt) {
        // Dependencia: la vista de inicio crea el menu principal.
        new VentanaPrincipal().setVisible(true);
        dispose();
    }
}
