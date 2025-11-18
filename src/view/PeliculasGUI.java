package view;

import model.Pelicula;
import services.PeliculasService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PeliculasGUI extends JFrame {

    private final PeliculasService peliculasService;

    private JTable peliculasTable;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;
    private JButton btnAccion;

    private JTextField tituloField, generoField, duracionField, clasificacionField;

    private int peliculaIdAEditar = -1; // -1 indica modo Creación

    public PeliculasGUI(PeliculasService pService) {
        this.peliculasService = pService;

        setTitle("🎥 Gestión de Películas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cerrar solo esta ventana
        setSize(700, 500);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("📋 Listado de Películas", crearPanelListado());
        tabbedPane.addTab("➕ Crear / Editar Película", crearPanelGestion());
        add(tabbedPane);

        cargarDatosPeliculas();
        setVisible(true);
    }

    private JPanel crearPanelListado() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"ID", "TÍTULO", "GÉNERO", "DURACIÓN", "CLASIFICACIÓN"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        peliculasTable = new JTable(tableModel);
        panel.add(new JScrollPane(peliculasTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout());

        JButton btnEditar = new JButton("✏️ Editar Película Seleccionada");
        btnEditar.addActionListener(e -> iniciarEdicion());

        JButton btnEliminar = new JButton("🗑️ Eliminar Película Seleccionada");
        btnEliminar.addActionListener(e -> eliminarPelicula());

        JButton btnRecargar = new JButton("🔄 Recargar Lista");
        btnRecargar.addActionListener(e -> cargarDatosPeliculas());
        JButton btnVolver = new JButton("Menu Principal");
        btnVolver.addActionListener(e -> {
            this.dispose();
        });
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnRecargar);
        buttonPanel.add(btnVolver);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelGestion() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        // campos de texto
        tituloField = new JTextField();
        generoField = new JTextField();
        duracionField = new JTextField();
        clasificacionField = new JTextField();

        // Etiquetas y Campos
        formPanel.add(new JLabel("Título:"));
        formPanel.add(tituloField);

        formPanel.add(new JLabel("Género:"));
        formPanel.add(generoField);

        formPanel.add(new JLabel("Duración:"));
        formPanel.add(duracionField);

        formPanel.add(new JLabel("Clasificación:"));
        formPanel.add(clasificacionField);

        btnAccion = new JButton("✅ Crear Nueva Película");
        btnAccion.addActionListener(e -> manejarAccion());

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(btnAccion, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return panel;
    }

    private void cargarDatosPeliculas() {
        tableModel.setRowCount(0);

        ArrayList<Pelicula> lista = peliculasService.listarpeliculas();

        for (Pelicula p : lista) {
            Object[] fila = new Object[]{
                p.getIdPelicula(),
                p.getTitulo(),
                p.getGenero(),
                p.getDuracion(),
                p.getClasificacion()
            };
            tableModel.addRow(fila);
        }
    }

    private void manejarAccion() {
        if (peliculaIdAEditar == -1) {
            crearPelicula();
        } else {
            actualizarPelicula(peliculaIdAEditar);
        }
        resetearFormulario();
        cargarDatosPeliculas();
        tabbedPane.setSelectedIndex(0);
    }

    private void crearPelicula() {
        try {
            String titulo = tituloField.getText();
            String genero = generoField.getText();
            String duracion = duracionField.getText();
            String clasificacion = clasificacionField.getText();

            peliculasService.createPelicula(titulo, genero, duracion, clasificacion);

            JOptionPane.showMessageDialog(this, "Película creada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear película: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarPelicula(int idPelicula) {
        try {
            String nuevoTitulo = tituloField.getText();
            String nuevoGenero = generoField.getText();
            String nuevaDuracion = duracionField.getText();
            String nuevaClasificacion = clasificacionField.getText();

            peliculasService.updatePelicula(idPelicula, nuevoTitulo, nuevoGenero, nuevaDuracion, nuevaClasificacion);

            JOptionPane.showMessageDialog(this, "Película ID " + idPelicula + " actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar película: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarEdicion() {
        int selectedRow = peliculasTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una película de la tabla para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        peliculaIdAEditar = (int) tableModel.getValueAt(selectedRow, 0);

        cargarPeliculaParaEdicion(peliculaIdAEditar);
        tabbedPane.setSelectedIndex(1); // Cambiar a la pestaña de gestión
    }

    private void cargarPeliculaParaEdicion(int id) {
        Pelicula pelicula = peliculasService.getPeliculaById(id);

        if (pelicula != null) {
            tituloField.setText(pelicula.getTitulo());
            generoField.setText(pelicula.getGenero());
            duracionField.setText(pelicula.getDuracion());
            clasificacionField.setText(pelicula.getClasificacion());

            btnAccion.setText("💾 Guardar Cambios (Película ID: " + id + ")");
        } else {
            JOptionPane.showMessageDialog(this, "Error: Película no encontrada.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
            peliculaIdAEditar = -1;
        }
    }

    private void resetearFormulario() {
        peliculaIdAEditar = -1;
        tituloField.setText("");
        generoField.setText("");
        duracionField.setText("");
        clasificacionField.setText("");

        btnAccion.setText("✅ Crear Nueva Película");
    }

    private void eliminarPelicula() {
        int selectedRow = peliculasTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una película para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idEliminar = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar la película ID: " + idEliminar + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            peliculasService.deletePelicula(idEliminar);
            JOptionPane.showMessageDialog(this, "Película eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatosPeliculas(); // Refrescar la tabla
        }
    }
}
