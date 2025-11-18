package view;

import model.Reserva;
import model.Usuario;
import model.Funcion;
import model.Asiento;
import services.ReservaService;
import services.UsuarioService;
import services.FuncionService;
import services.AsientoService;
import services.PeliculasService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import model.Pelicula;

public class ReservasGUI extends JFrame {

    // Servicios
    private final ReservaService reservaService;
    private final UsuarioService usuarioService;
    private final FuncionService funcionService;
    private final AsientoService asientoService;
    private final PeliculasService peliculasService;

    private JTable reservasTable;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;
    private JButton btnAccion;

    // Campos del Formulario
    private JComboBox<String> usuarioComboBox;
    private JComboBox<String> funcionComboBox;
    private JComboBox<String> asientoComboBox;
    private JComboBox<String> estadoComboBox;
    private JTextField fechaReservaField;

    private int reservaIdAEditar = -1;

    public ReservasGUI(ReservaService rService, UsuarioService uService,
            FuncionService fService, AsientoService aService,
            PeliculasService pService) {
        this.reservaService = rService;
        this.usuarioService = uService;
        this.funcionService = fService;
        this.asientoService = aService;
        this.peliculasService = pService;
        setTitle("🎟️ Gestión de Reservas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("📋 Listado de Reservas", crearPanelListado());
        tabbedPane.addTab("➕ Crear / Editar Reserva", crearPanelGestion());
        add(tabbedPane);

        cargarDatosReservas();
        setVisible(true);
    }

    private JPanel crearPanelListado() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"ID", "USUARIO", "FUNCIÓN", "ASIENTO", "ESTADO", "FECHA RESERVA"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reservasTable = new JTable(tableModel);
        panel.add(new JScrollPane(reservasTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnEditar = new JButton("✏️ Editar Reserva Seleccionada");
        btnEditar.addActionListener(e -> iniciarEdicion());

        JButton btnEliminar = new JButton("🗑️ Eliminar Reserva Seleccionada");
        btnEliminar.addActionListener(e -> eliminarReserva());

        JButton btnRecargar = new JButton("🔄 Recargar Lista");
        btnRecargar.addActionListener(e -> cargarDatosReservas());

        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnRecargar);
        JButton btnVolver = new JButton("Menu Principal");
        btnVolver.addActionListener(e -> {
        this.dispose();
        });
        buttonPanel.add(btnVolver);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelGestion() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        usuarioComboBox = new JComboBox<>();
        funcionComboBox = new JComboBox<>();
        asientoComboBox = new JComboBox<>();
        estadoComboBox = new JComboBox<>(new String[]{"ACTIVA", "CANCELADA", "COMPLETADA"});
        fechaReservaField = new JTextField();

        cargarUsuariosComboBox();
        cargarFuncionesComboBox();
        cargarAsientosComboBox();

        formPanel.add(new JLabel("Usuario:"));
        formPanel.add(usuarioComboBox);

        formPanel.add(new JLabel("Función (Película - Fecha - Hora):"));
        formPanel.add(funcionComboBox);

        formPanel.add(new JLabel("Asiento:"));
        formPanel.add(asientoComboBox);

        formPanel.add(new JLabel("Estado:"));
        formPanel.add(estadoComboBox);

        formPanel.add(new JLabel("Fecha de Reserva (dd-mm-aaaa):"));
        formPanel.add(fechaReservaField);

        btnAccion = new JButton("✅ Crear Nueva Reserva");
        btnAccion.addActionListener(e -> manejarAccion());

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(btnAccion, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return panel;
    }

    private void cargarDatosReservas() {
        tableModel.setRowCount(0);
        ArrayList<Reserva> lista = reservaService.listarReservas();

        for (Reserva r : lista) {
            Usuario usuario = usuarioService.getUserById(r.getFk_Usuario());
            String nombreUsuario = (usuario != null) ? usuario.getUser_name() : "Desconocido";

            Funcion funcion = funcionService.getFuncionById(r.getFk_Funcion());
            String descFuncion = (funcion != null)
                    ? funcion.getFecha() + " - " + funcionService.getFuncionById(funcion.getFk_pelicula())
                    : "Desconocida";

            Asiento asiento = asientoService.getAsiento(r.getFk_Asiento());
            String descAsiento = (asiento != null) ? asiento.getFila() + asiento.getNumero() : "Desconocido";

            Object[] fila = new Object[]{
                r.getIdReserva(),
                nombreUsuario,
                descFuncion,
                descAsiento,
                r.getEstado(),
                r.getFecha_Reserva()
            };
            tableModel.addRow(fila);
        }
    }

    private void manejarAccion() {
        if (reservaIdAEditar == -1) {
            crearReserva();
        } else {
            actualizarReserva(reservaIdAEditar);
        }
        resetearFormulario();
    }

    private int obtenerIdDeComboBox(JComboBox<String> comboBox) {
        String selectedItem = (String) comboBox.getSelectedItem();
        if (selectedItem == null || selectedItem.isEmpty()) {
            return -1;
        }
        try {
            return Integer.parseInt(selectedItem.split(" - ")[0]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void crearReserva() {
        try {
        int fkUsuario = obtenerIdDeComboBox(usuarioComboBox);
        int fkFuncion = obtenerIdDeComboBox(funcionComboBox);
        int fkAsiento = obtenerIdDeComboBox(asientoComboBox);
        String estado = (String) estadoComboBox.getSelectedItem();

        String fechaReserva = fechaReservaField.getText();

        if (fkUsuario == -1 || fkFuncion == -1 || fkAsiento == -1 || estado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Usuario, Función y Asiento.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        reservaService.createReserva(fkUsuario, fkFuncion, fkAsiento, estado, fechaReserva);
        JOptionPane.showMessageDialog(this, "Reserva creada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        cargarDatosReservas();
        tabbedPane.setSelectedIndex(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarReserva(int idReserva) {
        try {
            int fkUsuario = obtenerIdDeComboBox(usuarioComboBox);
            int fkFuncion = obtenerIdDeComboBox(funcionComboBox);
            int fkAsiento = obtenerIdDeComboBox(asientoComboBox);
            String nuevoEstado = (String) estadoComboBox.getSelectedItem();
            String nuevaFechaReserva = fechaReservaField.getText();

            reservaService.updateReserva(idReserva, fkUsuario, fkFuncion, fkAsiento, nuevoEstado, nuevaFechaReserva);

            JOptionPane.showMessageDialog(this, "Reserva ID " + idReserva + " actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatosReservas();
            tabbedPane.setSelectedIndex(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarEdicion() {
        int selectedRow = reservasTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva de la tabla para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        reservaIdAEditar = (int) tableModel.getValueAt(selectedRow, 0);
        cargarReservaParaEdicion(reservaIdAEditar);
        tabbedPane.setSelectedIndex(1);
    }

    private void seleccionarItemEnComboBox(JComboBox<String> comboBox, int id) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            String item = comboBox.getItemAt(i);
            if (item.startsWith(id + " - ")) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    private void cargarReservaParaEdicion(int id) {
        Reserva reserva = reservaService.getReservaById(id);
        if (reserva != null) {
            fechaReservaField.setText(reserva.getFecha_Reserva());
            estadoComboBox.setSelectedItem(reserva.getEstado()); 

            seleccionarItemEnComboBox(usuarioComboBox, reserva.getFk_Usuario());
            seleccionarItemEnComboBox(funcionComboBox, reserva.getFk_Funcion());
            seleccionarItemEnComboBox(asientoComboBox, reserva.getFk_Asiento());

            btnAccion.setText("💾 Guardar Cambios (Reserva ID: " + id + ")");
        } else {
            JOptionPane.showMessageDialog(this, "Error: Reserva no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            reservaIdAEditar = -1;
        }
    }

    private void resetearFormulario() {
        reservaIdAEditar = -1;
        fechaReservaField.setText("");
        btnAccion.setText("✅ Crear Nueva Reserva");
    }

    private void eliminarReserva() {
        int selectedRow = reservasTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        int idEliminar = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar la reserva ID: " + idEliminar + "?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            reservaService.deleteReserva(idEliminar);
            JOptionPane.showMessageDialog(this, "Reserva eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatosReservas();
        }
    }

    private void cargarUsuariosComboBox() {
        usuarioComboBox.removeAllItems();
        ArrayList<Usuario> usuarios = usuarioService.listarUsuarios();
        for (Usuario u : usuarios) {
            usuarioComboBox.addItem(u.getUser_id() + " - " + u.getUser_name());
        }
    }

    private void cargarFuncionesComboBox() {
        funcionComboBox.removeAllItems();

        ArrayList<Funcion> funciones = funcionService.listarFunciones();
        for (Funcion f : funciones) {
            Pelicula peli = peliculasService.getPeliculaById(f.getFk_pelicula());
            String nombrePelicula = (peli != null) ? peli.getTitulo() : "Pelicula Desconocida";

            String item = f.getIdFuncion() + " - " + nombrePelicula
                    + " (" + f.getFecha() + " @ " + f.getHora() + ")";

            funcionComboBox.addItem(item);
        }
    }

    private void cargarAsientosComboBox() {
        asientoComboBox.removeAllItems();

        ArrayList<Asiento> asientos = asientoService.getTodosLosAsientos();

        for (Asiento a : asientos) {
            String item = a.getIdAsiento() + " - " + a.getFila() + a.getNumero();
            asientoComboBox.addItem(item);
        }
    }
}


