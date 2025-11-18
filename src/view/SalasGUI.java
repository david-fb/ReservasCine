package view;

import model.Sala;
import services.SalaService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SalasGUI extends JFrame {

    private final SalaService salaService;

    // Componentes de la Interfaz
    private JTable salasTable;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;
    // Crear o Guardar
    private JButton btnAccion;

    private JTextField nombreField, capacidadField, tipoField;

    private int salaIdAEditar = -1; // -1 indica ya que empieza desde 0 en la BD

    public SalasGUI(SalaService sService) {
        this.salaService = sService;

        setTitle("🛋️ Gestión de Salas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("📋 Listado de Salas", crearPanelListado());
        tabbedPane.addTab("➕ Crear / Editar Sala", crearPanelGestion());
        add(tabbedPane);

        cargarDatosSalas();
        setVisible(true);
    }

    private JPanel crearPanelListado() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"ID", "NOMBRE", "CAPACIDAD", "TIPO"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        salasTable = new JTable(tableModel);
        panel.add(new JScrollPane(salasTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout());

        JButton btnEditar = new JButton("✏️ Editar Sala Seleccionada");
        btnEditar.addActionListener(e -> iniciarEdicion());

        JButton btnEliminar = new JButton("🗑️ Eliminar Sala Seleccionada");
        btnEliminar.addActionListener(e -> eliminarSala());

        JButton btnRecargar = new JButton("🔄 Recargar Lista");
        btnRecargar.addActionListener(e -> cargarDatosSalas());
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
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        nombreField = new JTextField();
        capacidadField = new JTextField();
        tipoField = new JTextField();

        formPanel.add(new JLabel("Nombre de la Sala:"));
        formPanel.add(nombreField);

        formPanel.add(new JLabel("Capacidad:"));
        formPanel.add(capacidadField);

        formPanel.add(new JLabel("Tipo (2D, 3D, VIP):"));
        formPanel.add(tipoField);

        btnAccion = new JButton("✅ Crear Nueva Sala");
        btnAccion.addActionListener(e -> manejarAccion());

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(btnAccion, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return panel;
    }

    private void cargarDatosSalas() {
        tableModel.setRowCount(0);

        ArrayList<Sala> lista = salaService.listarSalas();

        for (Sala s : lista) {
            Object[] fila = new Object[]{
                s.getIdSala(),
                s.getNombre(),
                s.getCapacidad(),
                s.getTipo()
            };
            tableModel.addRow(fila);
        }
    }

    private void manejarAccion() {
        if (salaIdAEditar == -1) {
            crearSala();
        } else {
            actualizarSala(salaIdAEditar);
        }
        resetearFormulario();
        cargarDatosSalas();
        tabbedPane.setSelectedIndex(0);
    }

    private void crearSala() {
        try {
            String nombre = nombreField.getText();
            int capacidad = Integer.parseInt(capacidadField.getText());
            String tipo = tipoField.getText();

            salaService.registrarSala(nombre, capacidad, tipo);

            JOptionPane.showMessageDialog(this, "Sala creada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear sala: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarSala(int idSala) {
        try {
            String nuevoNombre = nombreField.getText();
            int nuevaCapacidad = Integer.parseInt(capacidadField.getText());
            String nuevoTipo = tipoField.getText();

            salaService.actualizarSala(idSala, nuevoNombre, nuevaCapacidad, nuevoTipo);

            JOptionPane.showMessageDialog(this, "Sala ID " + idSala + " actualizada correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser un número entero valido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar sala: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarEdicion() {
        int selectedRow = salasTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una sala de la tabla para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        salaIdAEditar = (int) tableModel.getValueAt(selectedRow, 0);

        cargarSalaParaEdicion(salaIdAEditar);
        tabbedPane.setSelectedIndex(1);
    }

    private void cargarSalaParaEdicion(int id) {
        Sala sala = salaService.getSalaById(id);

        if (sala != null) {
            nombreField.setText(sala.getNombre());
            capacidadField.setText(String.valueOf(sala.getCapacidad()));
            tipoField.setText(sala.getTipo());

            btnAccion.setText("💾 Guardar Cambios (Sala ID: " + id + ")");
        } else {
            JOptionPane.showMessageDialog(this, "Error: Sala no encontrada.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
            salaIdAEditar = -1;
        }
    }

    private void resetearFormulario() {
        salaIdAEditar = -1;
        nombreField.setText("");
        capacidadField.setText("");
        tipoField.setText("");

        btnAccion.setText("✅ Crear Nueva Sala");
    }

    private void eliminarSala() {
        int selectedRow = salasTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una sala para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idEliminar = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar la sala ID: " + idEliminar + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            salaService.deleteSala(idEliminar);
            JOptionPane.showMessageDialog(this, "Sala eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatosSalas();
        }
    }
}
