package com.unadm.dpo3.alcaldias.view;

import com.unadm.dpo3.alcaldias.model.Alcaldia;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Ventana para <b>modificar</b> un registro del catálogo.
 *
 * @author Alejandro Jassan Rea Rivera
 */
// MVC - Vista + Herencia: es-un JDialog; edita un registro (dependencia de Alcaldia).
public class VentanaModificar extends JDialog {

    private JTextField txtNombre;
    private JTextField txtAlcalde;
    private JTextField txtPoblacion;
    private JTextField txtTelefono;
    private JButton btnGuardar;
    private JButton btnCancelar;

    /**
     * Crea el cuadro de diálogo con los datos del registro a modificar.
     *
     * @param duenio   ventana desde la que se abre.
     * @param alcaldia registro seleccionado en la tabla.
     */
    // Dependencia: recibe el modelo Alcaldia cuyos datos se van a editar.
    public VentanaModificar(JFrame duenio, Alcaldia alcaldia) {
        super(duenio, "Modificar registro - Catálogo de Alcaldías", true);
        initComponents(alcaldia);
    }

    private void initComponents(Alcaldia alcaldia) {
        JLabel lblTitulo = new JLabel("Edita la información del registro");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(12, 12, 4, 12));

        JLabel lblNombre = new JLabel("Nombre de la alcaldía:");
        JLabel lblAlcalde = new JLabel("Alcalde o alcaldesa:");
        JLabel lblPoblacion = new JLabel("Población (habitantes):");
        JLabel lblTelefono = new JLabel("Teléfono:");

        // Los campos se llenan con los datos actuales del registro.
        txtNombre = new JTextField(alcaldia.getNombre(), 22);
        txtAlcalde = new JTextField(alcaldia.getAlcalde(), 22);
        txtPoblacion = new JTextField(String.valueOf(alcaldia.getPoblacion()), 22);
        txtTelefono = new JTextField(alcaldia.getTelefono(), 22);

        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBorder(BorderFactory.createEmptyBorder(4, 16, 10, 16));
        agregarCampo(panelCampos, lblNombre, txtNombre, 0);
        agregarCampo(panelCampos, lblAlcalde, txtAlcalde, 1);
        agregarCampo(panelCampos, lblPoblacion, txtPoblacion, 2);
        agregarCampo(panelCampos, lblTelefono, txtTelefono, 3);

        btnGuardar = new JButton("Guardar cambios");
        btnCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        setLayout(new BorderLayout());
        add(lblTitulo, BorderLayout.NORTH);
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getOwner());
        setResizable(false);
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

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    /**
     * Arma la alcaldía con los datos capturados en el formulario.
     *
     * @param poblacion población ya validada.
     * @return el registro con la información nueva.
     */
    // Dependencia: la vista arma una nueva Alcaldia con los datos capturados.
    public Alcaldia getAlcaldia(long poblacion) {
        return new Alcaldia(
                txtNombre.getText().trim(),
                txtAlcalde.getText().trim(),
                poblacion,
                txtTelefono.getText().trim());
    }

    /** Cierra el cuadro de diálogo. */
    public void cerrar() {
        dispose();
    }
}
