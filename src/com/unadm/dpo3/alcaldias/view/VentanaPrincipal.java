package com.unadm.dpo3.alcaldias.view;

import com.unadm.dpo3.alcaldias.controller.CatalogoControlador;
import com.unadm.dpo3.alcaldias.controller.ConsultaControlador;
import com.unadm.dpo3.alcaldias.model.AlcaldiaDAOArchivo;
import com.unadm.dpo3.alcaldias.model.Constantes;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Menú principal de la aplicación.
 *
 * @author Alejandro Jassan Rea Rivera
 */
// MVC - Vista + Herencia: es-un JFrame; crea las vistas y sus controladores (dependencia).
public class VentanaPrincipal extends JFrame {

    private JLabel lblImagen;
    private JLabel lblBienvenida;
    private JButton btnAbrir;
    private JButton btnConsultar;
    private JButton btnSalir;

    /** Crea el menú principal. */
    public VentanaPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle(Constantes.TITULO_VENTANA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(
                getClass().getResource("recursos/icono_alcaldias.png")).getImage());

        // ----- Barra de menú -----
        JMenuBar barraMenu = new JMenuBar();

        JMenu menuCatalogo = new JMenu("Catálogo");
        // Módulos del sistema: solo Alcaldías está habilitado.
        JMenuItem itemClientes = new JMenuItem("Catálogo de Clientes (E - H)");
        itemClientes.setEnabled(false);
        JMenuItem itemProveedores = new JMenuItem("Catálogo de Proveedores (I - L)");
        itemProveedores.setEnabled(false);
        JMenuItem itemProductos = new JMenuItem("Catálogo de Productos (M - P)");
        itemProductos.setEnabled(false);
        JMenuItem itemCiudades = new JMenuItem("Catálogo de Ciudades (Q - T)");
        itemCiudades.setEnabled(false);
        JMenuItem itemAlcaldias = new JMenuItem("Catálogo de Alcaldías (U - Z)");
        itemAlcaldias.addActionListener(evt -> abrirCatalogo());
        JMenuItem itemEstados = new JMenuItem("Catálogo de Estados");
        itemEstados.setEnabled(false);
        JMenuItem itemConsulta = new JMenuItem("Consulta");
        itemConsulta.addActionListener(evt -> abrirConsulta());
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(evt -> salir());
        menuCatalogo.add(itemClientes);
        menuCatalogo.add(itemProveedores);
        menuCatalogo.add(itemProductos);
        menuCatalogo.add(itemCiudades);
        menuCatalogo.add(itemAlcaldias);
        menuCatalogo.add(itemEstados);
        menuCatalogo.addSeparator();
        menuCatalogo.add(itemConsulta);
        menuCatalogo.add(itemSalir);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de...");
        itemAcercaDe.addActionListener(evt -> mostrarAcercaDe());
        menuAyuda.add(itemAcercaDe);

        barraMenu.add(menuCatalogo);
        barraMenu.add(menuAyuda);
        setJMenuBar(barraMenu);

        // ----- Contenido de la ventana -----
        lblImagen = new JLabel(new ImageIcon(
                getClass().getResource("recursos/portada_alcaldias.png")),
                SwingConstants.CENTER);

        lblBienvenida = new JLabel("<html><div style='text-align:center'>"
                + "Bienvenido al sistema de catálogos de la Ferretería La Golondrina.<br>"
                + "Selecciona <b>Catálogo de Alcaldías</b> para registrar información<br>"
                + "o <b>Consulta</b> para ver, modificar y eliminar los registros."
                + "</div></html>", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Tahoma", Font.PLAIN, 13));

        // Dependencia: el botón de Alcaldías es el único módulo habilitado.
        btnAbrir = new JButton("Catálogo de Alcaldías");
        btnAbrir.addActionListener(evt -> abrirCatalogo());

        // Módulos del sistema: solo Alcaldías está disponible (los demás deshabilitados).
        JButton btnClientes = new JButton("Catálogo de Clientes");
        btnClientes.setEnabled(false);
        JButton btnProveedores = new JButton("Catálogo de Proveedores");
        btnProveedores.setEnabled(false);
        JButton btnProductos = new JButton("Catálogo de Productos");
        btnProductos.setEnabled(false);
        JButton btnCiudades = new JButton("Catálogo de Ciudades");
        btnCiudades.setEnabled(false);
        JButton btnEstados = new JButton("Catálogo de Estados");
        btnEstados.setEnabled(false);

        JPanel panelModulos = new JPanel(new GridLayout(6, 1, 6, 6));
        panelModulos.add(btnClientes);
        panelModulos.add(btnProveedores);
        panelModulos.add(btnProductos);
        panelModulos.add(btnCiudades);
        panelModulos.add(btnAbrir);
        panelModulos.add(btnEstados);

        btnConsultar = new JButton("Consulta");
        btnConsultar.addActionListener(evt -> abrirConsulta());

        btnSalir = new JButton("Salir");
        btnSalir.addActionListener(evt -> salir());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnConsultar);
        panelBotones.add(btnSalir);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panelCentro.add(lblBienvenida, BorderLayout.NORTH);
        panelCentro.add(panelModulos, BorderLayout.CENTER);
        panelCentro.add(panelBotones, BorderLayout.SOUTH);

        add(lblImagen, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    /** Abre la ventana del módulo asignado (Catálogo de Alcaldías). */
    public void abrirCatalogo() {
        // Dependencia + Agregacion: crea la vista y le pasa this (duenio).
        CatalogoAlcaldias ventana = new CatalogoAlcaldias(this);
        // Singleton + Agregacion: inyecta el DAO unico en el controlador.
        new CatalogoControlador(ventana, AlcaldiaDAOArchivo.getInstancia());
        setVisible(false);
        ventana.setVisible(true);
    }

    /** Abre la consulta de registros (tabla con las opciones Modificar y Eliminar). */
    public void abrirConsulta() {
        // Dependencia + Agregacion: crea la vista de consulta y le pasa this (duenio).
        VentanaConsulta ventana = new VentanaConsulta(this);
        // Singleton + Agregacion: inyecta el DAO unico en el controlador de consulta.
        new ConsultaControlador(ventana, AlcaldiaDAOArchivo.getInstancia());
        setVisible(false);
        ventana.setVisible(true);
    }

    /** Cierra la aplicación. */
    public void salir() {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Deseas salir de la aplicación?",
                "Salir", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void mostrarAcercaDe() {
        String mensaje = Constantes.NOMBRE_MODULO + "\n\n"
                + "Universidad: " + Constantes.UNIVERSIDAD + "\n"
                + "Unidad didáctica: " + Constantes.UNIDAD_DIDACTICA + "\n"
                + "Actividad: " + Constantes.ACTIVIDAD + "\n"
                + "Estudiante: " + Constantes.NOMBRE_ESTUDIANTE + "\n"
                + "Matrícula: " + Constantes.MATRICULA + "\n"
                + "Grupo: " + Constantes.GRUPO + "\n"
                + "Figura académica: " + Constantes.FIGURA_ACADEMICA;
        JOptionPane.showMessageDialog(this, mensaje, "Acerca de",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
