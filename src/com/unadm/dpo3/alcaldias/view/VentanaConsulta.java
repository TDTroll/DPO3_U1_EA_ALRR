package com.unadm.dpo3.alcaldias.view;

import com.unadm.dpo3.alcaldias.model.Alcaldia;
import com.unadm.dpo3.alcaldias.model.Constantes;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

/**
 * Ventana de la opción <b>Consulta</b>.
 *
 * @author Alejandro Jassan Rea Rivera
 */
// MVC - Vista + Herencia: es-un JFrame; muestra la tabla de registros.
public class VentanaConsulta extends JFrame {

    /** Columna que contiene el botón Modificar. */
    public static final int COLUMNA_MODIFICAR = 4;

    /** Columna que contiene el botón Eliminar. */
    public static final int COLUMNA_ELIMINAR = 5;

    private final VentanaPrincipal duenio; // Asociacion: referencia al menu principal.

    private JTable tabla;
    private ModeloTablaAlcaldias modelo; // Composicion: la ventana crea y es duena de su TableModel.
    private JButton btnRegresar;

    /**
     * Crea la ventana de consulta.
     *
     * @param duenio menú principal, al que se regresa al cerrar esta ventana.
     */
    public VentanaConsulta(VentanaPrincipal duenio) {
        this.duenio = duenio;
        initComponents();
    }

    private void initComponents() {
        setTitle("Consulta - " + Constantes.TITULO_VENTANA);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(
                getClass().getResource("recursos/icono_alcaldias.png")).getImage());

        JLabel lblTitulo = new JLabel("Consulta de registros - Catálogo de Alcaldías");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(12, 12, 6, 12));

        // ----- Tabla de registros -----
        modelo = new ModeloTablaAlcaldias();
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(28);
        tabla.getTableHeader().setReorderingAllowed(false);

        // Las dos últimas columnas muestran botones en lugar de texto plano.
        configurarColumnaBoton(COLUMNA_MODIFICAR);
        configurarColumnaBoton(COLUMNA_ELIMINAR);

        JScrollPane desplazador = new JScrollPane(tabla);
        desplazador.setBorder(BorderFactory.createEmptyBorder(0, 12, 6, 12));

        // ----- Botón para regresar -----
        btnRegresar = new JButton("Regresar al menú principal");

        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 8, 10, 8));
        panelInferior.add(btnRegresar);

        setLayout(new BorderLayout());
        add(lblTitulo, BorderLayout.NORTH);
        add(desplazador, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // Al cerrar la consulta se vuelve a mostrar el menú principal.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent evt) {
                duenio.setVisible(true);
            }
        });

        setSize(new Dimension(780, 430));
        setLocationRelativeTo(null);
    }

    /** Prepara una columna para que se dibuje como botón. */
    private void configurarColumnaBoton(int indice) {
        TableColumn columna = tabla.getColumnModel().getColumn(indice);
        // Dependencia: usa BotonCeldaRenderer para dibujar el boton en la celda.
        columna.setCellRenderer(new BotonCeldaRenderer());
        columna.setPreferredWidth(100);
        columna.setMaxWidth(120);
    }

    /**
     * Muestra en la tabla las alcaldías recibidas.
     *
     * @param registros alcaldías leídas del archivo de texto.
     */
    public void mostrarRegistros(List<Alcaldia> registros) {
        modelo.setAlcaldias(registros);
    }

    public JTable getTabla() {
        return tabla;
    }

    public JButton getBtnRegresar() {
        return btnRegresar;
    }

    /**
     * @param fila posición del registro.
     * @return la alcaldía que está en esa fila.
     */
    public Alcaldia getAlcaldia(int fila) {
        return modelo.getAlcaldia(fila);
    }

    /**
     * Actualiza una fila de la tabla.
     *
     * @param fila     posición del registro.
     * @param alcaldia datos nuevos.
     */
    public void actualizarFila(int fila, Alcaldia alcaldia) {
        modelo.actualizarFila(fila, alcaldia);
    }

    /**
     * Quita una fila de la tabla.
     *
     * @param fila posición del registro.
     */
    public void eliminarFila(int fila) {
        modelo.eliminarFila(fila);
    }
}
