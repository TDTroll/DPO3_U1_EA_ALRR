package com.unadm.dpo3.alcaldias.view;

import com.unadm.dpo3.alcaldias.model.Constantes;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Ventana del módulo asignado: formulario para registrar alcaldías.
 *
 * @author Alejandro Jassan Rea Rivera
 */
// MVC - Vista + Herencia: es-un JFrame.
public class CatalogoAlcaldias extends JFrame {

    private final VentanaPrincipal duenio; // Asociacion: referencia al menu principal.

    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JLabel lblAlcalde;
    private JLabel lblPoblacion;
    private JLabel lblTelefono;
    private JTextField txtNombre;
    private JTextField txtAlcalde;
    private JTextField txtPoblacion;
    private JTextField txtTelefono;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnVer;
    private JButton btnRegresar;

    /**
     * Crea el formulario del catálogo.
     *
     * @param duenio menú principal, al que se regresa al cerrar esta ventana.
     */
    public CatalogoAlcaldias(VentanaPrincipal duenio) {
        this.duenio = duenio;
        initComponents();
    }

    private void initComponents() {
        setTitle(Constantes.TITULO_VENTANA);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(
                getClass().getResource("recursos/icono_alcaldias.png")).getImage());

        // Composicion: la ventana crea y es duena de sus componentes.
        lblTitulo = new JLabel("Catálogo de Alcaldías - Registro de datos");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(12, 12, 4, 12));

        lblNombre = new JLabel("Nombre de la alcaldía:");
        lblAlcalde = new JLabel("Alcalde o alcaldesa:");
        lblPoblacion = new JLabel("Población (habitantes):");
        lblTelefono = new JLabel("Teléfono:");

        txtNombre = new JTextField(22);
        txtAlcalde = new JTextField(22);
        txtPoblacion = new JTextField(22);
        txtTelefono = new JTextField(22);

        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBorder(BorderFactory.createEmptyBorder(4, 16, 10, 16));
        agregarCampo(panelCampos, lblNombre, txtNombre, 0);
        agregarCampo(panelCampos, lblAlcalde, txtAlcalde, 1);
        agregarCampo(panelCampos, lblPoblacion, txtPoblacion, 2);
        agregarCampo(panelCampos, lblTelefono, txtTelefono, 3);

        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");
        btnVer = new JButton("Ver registros guardados");
        btnRegresar = new JButton("Regresar al menú principal");

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 8, 10, 8));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVer);
        panelBotones.add(btnRegresar);

        setLayout(new BorderLayout());
        add(lblTitulo, BorderLayout.NORTH);
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Al cerrar el formulario se vuelve a mostrar el menú principal.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent evt) {
                duenio.setVisible(true);
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    /** Coloca una etiqueta y su campo de texto en un renglón del formulario. */
    private void agregarCampo(JPanel panel, JLabel etiqueta, JTextField campo, int fila) {
        GridBagConstraints restricciones = new GridBagConstraints();

        restricciones.gridx = 0;
        restricciones.gridy = fila;
        restricciones.anchor = GridBagConstraints.WEST;
        restricciones.insets = new Insets(6, 6, 6, 8);
        panel.add(etiqueta, restricciones);

        restricciones.gridx = 1;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        restricciones.weightx = 1.0;
        panel.add(campo, restricciones);
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtAlcalde() {
        return txtAlcalde;
    }

    public JTextField getTxtPoblacion() {
        return txtPoblacion;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JButton getBtnVer() {
        return btnVer;
    }

    public JButton getBtnRegresar() {
        return btnRegresar;
    }

    /** Borra el contenido de todos los campos de texto. */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtAlcalde.setText("");
        txtPoblacion.setText("");
        txtTelefono.setText("");
        txtNombre.requestFocus();
    }

    /** Cierra el formulario y regresa al menú principal. */
    public void regresarAlMenu() {
        dispose();
    }
}
