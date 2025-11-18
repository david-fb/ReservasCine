package view;

import model.Funcion;
import model.Pelicula;
import model.Sala;
import services.FuncionService;
import services.SalaService;
import services.PeliculasService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FuncionesGUI extends JFrame {

    private final FuncionService funcionesService;
    private final PeliculasService peliculasService;
    private final SalaService salaService;
    private JTable funcionesTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> peliculasComboBox;
    private JComboBox<String> salaComboBox;

    private JTextField fechaField, horaField, precioField;

    private int funcionIdAEditar = -1;
    private JTabbedPane tabbedPane;

    private JButton btnAccion; // El botón de Crear/Guardar Cambios

    public FuncionesGUI(FuncionService fService, PeliculasService pService, SalaService sService) {
        this.funcionesService = fService;
        this.peliculasService = pService;
        this.salaService = sService;

        setTitle("🎬 Gestión de Funciones de Cine (Swing)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        this.tabbedPane = new JTabbedPane();

        this.tabbedPane.addTab("📋 Listado de Funciones", crearPanelListado());
        this.tabbedPane.addTab("➕ Crear / Editar Función", crearPanelGestion());

        add(this.tabbedPane);
        cargarDatosFunciones();

        setVisible(true);
    }

    private JPanel crearPanelListado() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"ID", "FECHA", "HORA", "SALA", "PELÍCULA", "PRECIO"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        funcionesTable = new JTable(tableModel);
        panel.add(new JScrollPane(funcionesTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnEditar = new JButton("✏️ Editar Función Seleccionada");
        btnEditar.addActionListener(e -> iniciarEdicion());

        JButton btnEliminar = new JButton("🗑️ Eliminar Función Seleccionada");
        btnEliminar.addActionListener(e -> eliminarFuncion());

        JButton btnActualizar = new JButton("🔄 Recargar Lista");
        btnActualizar.addActionListener(e -> cargarDatosFunciones());
        JButton btnVolver = new JButton("Menu Principal");
        btnVolver.addActionListener(e -> {
        this.dispose();
        });
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnActualizar);
        
        buttonPanel.add(btnVolver);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelGestion() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        fechaField = new JTextField();
        horaField = new JTextField();
        precioField = new JTextField();

        peliculasComboBox = new JComboBox<>();
        cargarPeliculasComboBox();
        salaComboBox = new JComboBox<>();
        cargarSalasComboBox();
        // Etiquetas y Campos
        formPanel.add(new JLabel("Fecha (dd-mm-aaaa):"));
        formPanel.add(fechaField);

        formPanel.add(new JLabel("Hora (hh:mm):"));
        formPanel.add(horaField);

        formPanel.add(new JLabel("ID Sala:"));
        formPanel.add(salaComboBox);

        formPanel.add(new JLabel("Película:"));
        formPanel.add(peliculasComboBox);

        formPanel.add(new JLabel("Precio:"));
        formPanel.add(precioField);

        btnAccion = new JButton("✅ Crear Nueva Función");
        btnAccion.addActionListener(e -> manejarAccion());

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(btnAccion, BorderLayout.SOUTH);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return panel;
    }

    private void cargarDatosFunciones() {

        tableModel.setRowCount(0);

        ArrayList<Funcion> lista = funcionesService.listarFunciones();

        if (lista.isEmpty()) {
            return;
        }

        for (Funcion f : lista) {

            Pelicula peli = peliculasService.getPeliculaById(f.getFk_pelicula());
            String nombrePelicula = (peli != null) ? peli.getTitulo() : "Desconocida";

            Sala sala = salaService.getSalaById(f.getFk_sala());
            String nombreSala = (sala != null) ? sala.getNombre() : "Desconocida";

            Object[] fila = new Object[]{
                f.getIdFuncion(),
                f.getFecha(),
                f.getHora(),
                nombreSala,
                nombrePelicula,
                String.format("$%.2f", f.getPrecioEntrada())
            };
            tableModel.addRow(fila);
        }
    }

    private void cargarPeliculasComboBox() {
        peliculasComboBox.removeAllItems();
        ArrayList<Pelicula> peliculas = peliculasService.listarpeliculas();
        for (Pelicula p : peliculas) {
            peliculasComboBox.addItem(p.getIdPelicula() + " - " + p.getTitulo());
        }
    }

    private void cargarSalasComboBox() {
        salaComboBox.removeAllItems();
        ArrayList<Sala> salas = salaService.listarSalas();
        for (Sala s : salas) {
            String item = s.getIdSala() + " - " + s.getNombre() + " (" + s.getTipo() + ")";
            salaComboBox.addItem(item);
        }
    }

    private void crearFuncion() {
        try {
            String fecha = fechaField.getText();
            String hora = horaField.getText();
            double precio = Double.parseDouble(precioField.getText());

            String selectedSalaItem = (String) salaComboBox.getSelectedItem();
            if (selectedSalaItem == null || selectedSalaItem.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una sala.", "Error de Creación", JOptionPane.ERROR_MESSAGE);
                return;
            }
 
            int idSala = Integer.parseInt(selectedSalaItem.split(" - ")[0]); // <-- ¡CAMBIO CLAVE!

            String selectedPeliItem = (String) peliculasComboBox.getSelectedItem();
            if (selectedPeliItem == null || selectedPeliItem.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una película.", "Error de Creación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idPelicula = Integer.parseInt(selectedPeliItem.split(" - ")[0]);

            funcionesService.createFuncion(fecha, hora, idSala, idPelicula, precio);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos. Verifique Sala, Precio, Fecha u Hora.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarFuncion() {
        int selectedRow = funcionesTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una función de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idFuncion = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar la función con ID: " + idFuncion + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            funcionesService.deleteFuncion(idFuncion);
            JOptionPane.showMessageDialog(this, "Función eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatosFunciones(); // Refrescar la tabla
        }
    }

    private void iniciarEdicion() {
        int selectedRow = funcionesTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una función de la tabla para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        funcionIdAEditar = (int) tableModel.getValueAt(selectedRow, 0);

        cargarFuncionParaEdicion(funcionIdAEditar);

        tabbedPane.setSelectedIndex(1);
    }

    private void cargarFuncionParaEdicion(int id) {
        Funcion funcion = funcionesService.getFuncionById(id);

        if (funcion != null) {
            fechaField.setText(funcion.getFecha());
            horaField.setText(funcion.getHora());
            precioField.setText(String.valueOf(funcion.getPrecioEntrada()));

            seleccionarItemEnComboBox(salaComboBox, funcion.getFk_sala());

            seleccionarItemEnComboBox(peliculasComboBox, funcion.getFk_pelicula());

            btnAccion.setText("💾 Guardar Cambios (Función ID: " + id + ")");
        } else {
            JOptionPane.showMessageDialog(this, "Error: Función no encontrada.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
            funcionIdAEditar = -1; // Resetear
        }
    }


    private void seleccionarItemEnComboBox(JComboBox<String> comboBox, int id) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            String item = comboBox.getItemAt(i);
            // Extraer el ID (Ej: "5 - Título" -> "5")
            if (item.startsWith(id + " - ")) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }


    private void manejarAccion() {
        if (funcionIdAEditar == -1) {

            crearFuncion();
        } else {

            actualizarFuncion(funcionIdAEditar);
        }


        resetearFormulario();
    }

    private void actualizarFuncion(int idFuncion) {
        try {
            String nuevaFecha = fechaField.getText();
            String nuevaHora = horaField.getText();
            double nuevoPrecio = Double.parseDouble(precioField.getText());


            String selectedSalaItem = (String) salaComboBox.getSelectedItem();
            int nuevaSala = Integer.parseInt(selectedSalaItem.split(" - ")[0]);

            String selectedPeliItem = (String) peliculasComboBox.getSelectedItem();
            int nuevaPelicula = Integer.parseInt(selectedPeliItem.split(" - ")[0]);

            funcionesService.updateFuncion(idFuncion, nuevaFecha, nuevaHora, nuevaSala, nuevaPelicula, nuevoPrecio);

            JOptionPane.showMessageDialog(this, "Función ID " + idFuncion + " actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            cargarDatosFunciones(); // Refrescar tabla
            tabbedPane.setSelectedIndex(0); // Volver a la lista

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos en Sala, Precio, Fecha u Hora. Verifique.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetearFormulario() {
        funcionIdAEditar = -1;
        fechaField.setText("");
        horaField.setText("");
        precioField.setText("");
        btnAccion.setText("✅ Crear Nueva Función");
    }

}
