package view;

import main.createFunciones; // Necesario para acceder a las dependencias
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

    // Servicios necesarios para la lógica de negocio
    private final FuncionService funcionesService;
    private final PeliculasService peliculasService;
    private final SalaService salaService;
    // Componentes de la interfaz
    private JTable funcionesTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> peliculasComboBox;
    private JComboBox<String> salaComboBox;

    // CAMPOS DE TEXTO para crear/actualizar
    private JTextField fechaField, horaField, precioField;
    // ... dentro de FuncionesGUI.java

// Variables de estado para la Actualización
    private int funcionIdAEditar = -1; // Almacena el ID de la función seleccionada. -1 indica 'Crear'.
    private JTabbedPane tabbedPane; // Necesario para cambiar entre pestañas programáticamente.

// Componentes
    private JButton btnAccion; // El botón de Crear/Guardar Cambios
// ... (resto de variables como tableModel, etc.)

    public FuncionesGUI(FuncionService fService, PeliculasService pService, SalaService sService) {
        this.funcionesService = fService;
        this.peliculasService = pService;
        this.salaService = sService;

        setTitle("🎬 Gestión de Funciones de Cine (Swing)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrar la ventana
        // Crear las pestañas
        this.tabbedPane = new JTabbedPane(); // <-- ¡SOLUCIÓN! Inicializa la variable de clase.

       // Añadir las dos pestañas principales
        this.tabbedPane.addTab("📋 Listado de Funciones", crearPanelListado());
        this.tabbedPane.addTab("➕ Crear / Editar Función", crearPanelGestion());

        add(this.tabbedPane); // Usar la variable de clase
        // Cargar los datos iniciales al arrancar la aplicación
        cargarDatosFunciones();

        setVisible(true);
    }

    /**
     * Crea el panel con la tabla de listado de funciones.
     */
    private JPanel crearPanelListado() {
        JPanel panel = new JPanel(new BorderLayout());

        // Configuración del JTable
        String[] columnNames = {"ID", "FECHA", "HORA", "SALA", "PELÍCULA", "PRECIO"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita la edición directa en la tabla
            }
        };
        funcionesTable = new JTable(tableModel);
        // Añadir el JTable a un JScrollPane (para las barras de desplazamiento)
        panel.add(new JScrollPane(funcionesTable), BorderLayout.CENTER);
        // Panel de botones (CRUD: Actualizar y Eliminar)

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnEditar = new JButton("✏️ Editar Función Seleccionada"); // <-- NUEVO BOTÓN
        btnEditar.addActionListener(e -> iniciarEdicion()); // <-- NUEVO MÉTODO

        JButton btnEliminar = new JButton("🗑️ Eliminar Función Seleccionada");
        btnEliminar.addActionListener(e -> eliminarFuncion());

        JButton btnActualizar = new JButton("🔄 Recargar Lista");
        btnActualizar.addActionListener(e -> cargarDatosFunciones());

        buttonPanel.add(btnEditar); // <-- Añadir botón de editar
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnActualizar);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel con el formulario para crear/actualizar funciones.
     */
    private JPanel crearPanelGestion() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10)); // 6 filas, 2 columnas, con espacio

        // Inicializar campos de texto
        fechaField = new JTextField();
        horaField = new JTextField();
        precioField = new JTextField();

        // ComboBox de Películas
        peliculasComboBox = new JComboBox<>();
        cargarPeliculasComboBox(); // Llenar el ComboBox al inicializar
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
        formPanel.add(peliculasComboBox); // Usamos el ComboBox en lugar de pedir el ID

        formPanel.add(new JLabel("Precio:"));
        formPanel.add(precioField);

        // Botón de Acción (Crear)
        btnAccion = new JButton("✅ Crear Nueva Función"); // <-- Usamos la variable de clase
        btnAccion.addActionListener(e -> manejarAccion());

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(btnAccion, BorderLayout.SOUTH);

        // Añadir un poco de espacio alrededor del formulario
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return panel;
    }

    /**
     * Carga y refresca los datos en el JTable. (Corresponde a la Opción 1)
     */
    /**
     * Carga y refresca los datos en el JTable.
     */
    private void cargarDatosFunciones() {
        // Limpiar filas existentes
        tableModel.setRowCount(0);

        ArrayList<Funcion> lista = funcionesService.listarFunciones();

        if (lista.isEmpty()) {
            // No cambiamos el mensaje de error, solo la forma de mostrar los datos
            return;
        }

        // Iterar sobre las funciones y añadirlas al modelo de la tabla
        for (Funcion f : lista) {

            // --- 1. Obtener el Nombre de la Película (Lógica existente) ---
            Pelicula peli = peliculasService.getPeliculaById(f.getFk_pelicula());
            String nombrePelicula = (peli != null) ? peli.getTitulo() : "Desconocida";

            // --- 2. Obtener el Nombre de la Sala (¡NUEVA LÓGICA!) ---
            Sala sala = salaService.getSalaById(f.getFk_sala());
            String nombreSala = (sala != null) ? sala.getNombre() : "Desconocida";

            Object[] fila = new Object[]{
                f.getIdFuncion(),
                f.getFecha(),
                f.getHora(),
                // Usamos la variable nombreSala en lugar de f.getFk_sala()
                nombreSala, // <-- ¡CAMBIO CLAVE AQUÍ!
                nombrePelicula,
                String.format("$%.2f", f.getPrecioEntrada())
            };
            tableModel.addRow(fila);
        }
    }

    /**
     * Llena el ComboBox con las películas disponibles.
     */
    private void cargarPeliculasComboBox() {
        peliculasComboBox.removeAllItems();
        ArrayList<Pelicula> peliculas = peliculasService.listarpeliculas();
        for (Pelicula p : peliculas) {
            // Almacenamos el ID de la película junto a su título
            peliculasComboBox.addItem(p.getIdPelicula() + " - " + p.getTitulo());
        }
    }

    private void cargarSalasComboBox() {
        salaComboBox.removeAllItems();
        ArrayList<Sala> salas = salaService.listarSalas();
        for (Sala s : salas) {
            // Formato para mostrar: "ID - Nombre (Capacidad)"
            String item = s.getIdSala() + " - " + s.getNombre() + " (" + s.getTipo() + ")";
            salaComboBox.addItem(item);
        }
    }

    /**
     * Crea una nueva función. (Corresponde a la Opción 2)
     */
    /**
     * Crea una nueva función. (Corresponde a la Opción 2)
     */
    private void crearFuncion() {
        try {
            String fecha = fechaField.getText();
            String hora = horaField.getText();
            double precio = Double.parseDouble(precioField.getText());

            // --- OBTENER ID DE SALA DEL COMBOBOX ---
            String selectedSalaItem = (String) salaComboBox.getSelectedItem();
            if (selectedSalaItem == null || selectedSalaItem.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una sala.", "Error de Creación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Extraer el ID de sala (el primer elemento antes del " - ")
            int idSala = Integer.parseInt(selectedSalaItem.split(" - ")[0]); // <-- ¡CAMBIO CLAVE!

            // --- OBTENER ID DE PELÍCULA DEL COMBOBOX (Se mantiene igual) ---
            String selectedPeliItem = (String) peliculasComboBox.getSelectedItem();
            if (selectedPeliItem == null || selectedPeliItem.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una película.", "Error de Creación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idPelicula = Integer.parseInt(selectedPeliItem.split(" - ")[0]);

            funcionesService.createFuncion(fecha, hora, idSala, idPelicula, precio);

            // ... (Mensaje de éxito y refresco de tabla)
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos. Verifique Sala, Precio, Fecha u Hora.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina la función seleccionada. (Corresponde a la Opción 4)
     */
    private void eliminarFuncion() {
        int selectedRow = funcionesTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una función de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID de la función desde la primera columna de la fila seleccionada
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

        // 1. Obtener el ID de la función de la primera columna
        funcionIdAEditar = (int) tableModel.getValueAt(selectedRow, 0);

        // 2. Cargar los datos de esa función en los campos
        cargarFuncionParaEdicion(funcionIdAEditar);

        // 3. Cambiar a la pestaña de Gestión
        tabbedPane.setSelectedIndex(1);
    }

    private void cargarFuncionParaEdicion(int id) {
        // Asume que FuncionService tiene un método getFuncionById
        Funcion funcion = funcionesService.getFuncionById(id);

        if (funcion != null) {
            fechaField.setText(funcion.getFecha());
            horaField.setText(funcion.getHora());
            precioField.setText(String.valueOf(funcion.getPrecioEntrada()));

            // Seleccionar Sala y Película en los ComboBoxes:
            // El formato almacenado es "ID - Nombre..."
            // 1. Sala
            seleccionarItemEnComboBox(salaComboBox, funcion.getFk_sala());

            // 2. Película
            seleccionarItemEnComboBox(peliculasComboBox, funcion.getFk_pelicula());

            // 3. Cambiar el texto del botón
            btnAccion.setText("💾 Guardar Cambios (Función ID: " + id + ")");
        } else {
            JOptionPane.showMessageDialog(this, "Error: Función no encontrada.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
            funcionIdAEditar = -1; // Resetear
        }
    }

// Método de utilidad para seleccionar un item por su ID
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

    /**
     * Maneja tanto la creación como la actualización de funciones.
     */
    private void manejarAccion() {
        if (funcionIdAEditar == -1) {
            // Modo Creación
            crearFuncion();
        } else {
            // Modo Actualización
            actualizarFuncion(funcionIdAEditar);
        }

        // Después de la acción, volvemos al modo Crear
        resetearFormulario();
    }

    /**
     * Implementación de la lógica de Actualización (Opción 3).
     */
    private void actualizarFuncion(int idFuncion) {
        try {
            String nuevaFecha = fechaField.getText();
            String nuevaHora = horaField.getText();
            double nuevoPrecio = Double.parseDouble(precioField.getText());

            // Obtener IDs de los ComboBoxes (igual que en crearFuncion)
            String selectedSalaItem = (String) salaComboBox.getSelectedItem();
            int nuevaSala = Integer.parseInt(selectedSalaItem.split(" - ")[0]);

            String selectedPeliItem = (String) peliculasComboBox.getSelectedItem();
            int nuevaPelicula = Integer.parseInt(selectedPeliItem.split(" - ")[0]);

            // Llamar al servicio para actualizar
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
        // salaField.setText(""); // Ya no necesario, ComboBox mantiene selección
        precioField.setText("");

        // Resetear el texto del botón
        btnAccion.setText("✅ Crear Nueva Función");
    }

}
