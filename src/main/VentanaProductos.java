/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import model.Producto;
import services.ProductoService;
import java.util.List;

//VentanaProductos
public class VentanaProductos extends JFrame {
    
    private ProductoService ps;
    private JSplitPane splitPane;
    private JPanel panelIzquierdo, panelDerecho;
    private JButton btnRegistrarComida, btnRegistrarBebida, btnRegistrarCombo, btnMostrarProductos, btnComprar, btnEliminar;

    public VentanaProductos() {
        ps = new ProductoService();
        setTitle("Gestión de Comidas");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel izquierdo con fondo y título
        panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BorderLayout());
        panelIzquierdo.setBackground(new Color(245, 245, 245));
        
        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        panelIzquierdo.add(lblImagen, BorderLayout.CENTER); 

        ImageIcon icon = new ImageIcon("src/assets/comidas.jpg");
        Image imgOriginal = icon.getImage();
        
        panelIzquierdo.addComponentListener(new java.awt.event.ComponentAdapter() {
        @Override
        public void componentResized(java.awt.event.ComponentEvent evt) {
            int ancho = panelIzquierdo.getWidth();
            int alto = panelIzquierdo.getHeight() - 50; // restar espacio para el título si lo hay
            Image imgEscalada = imgOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(imgEscalada));
            }
         });
       
        
        lblImagen.setHorizontalAlignment(JLabel.CENTER);

        JLabel lblTitulo = new JLabel("Registro de Productos", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(50, 50, 50));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        panelIzquierdo.add(lblImagen, BorderLayout.CENTER);
        panelIzquierdo.add(lblTitulo, BorderLayout.SOUTH);

        // Panel derecho con botones estilizados
        panelDerecho = new JPanel();
        panelDerecho.setLayout(new GridLayout(6, 1, 15, 15));
        panelDerecho.setBackground(new Color(230, 230, 250));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        


        btnRegistrarComida = crearBoton("Registrar Comida", "src/assets/comida_cris.png");
        btnRegistrarBebida = crearBoton("Registrar Bebida", "src/assets/refrescos.png");
        btnRegistrarCombo = crearBoton("Registrar Combo", "src/assets/combos.png");
        btnMostrarProductos = crearBoton("Mostrar Productos", "src/assets/listado_com.png");
        btnComprar = crearBoton("Comprar Productos", "src/assets/comprar_com.png");
        btnEliminar = crearBoton("Eliminar Productos", "src/assets/basura.png");

        panelDerecho.add(btnRegistrarComida);
        panelDerecho.add(btnRegistrarBebida);
        panelDerecho.add(btnRegistrarCombo);
        panelDerecho.add(btnMostrarProductos);
        panelDerecho.add(btnComprar);
        panelDerecho.add(btnEliminar);

        // SplitPane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(320);
        splitPane.setDividerSize(10); 
        splitPane.setEnabled(false);   //TRUE se vuelve activar
        add(splitPane);

        // Acción para mostrar JTable
        btnRegistrarComida.addActionListener(e -> registrarComida());
        btnRegistrarBebida.addActionListener(e -> registrarBebida());
        btnRegistrarCombo.addActionListener(e -> registrarCombo());
        btnMostrarProductos.addActionListener(e -> mostrarProductos());
        btnComprar.addActionListener(e -> comprarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
    }

    private JButton crearBoton(String texto, String iconoEmoji) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(new Color(100, 149, 237));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);

        // Cargar la imagen como icono
        ImageIcon icon = new ImageIcon(iconoEmoji);           
        Image img = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH); 
        boton.setIcon(new ImageIcon(img));    
        return boton;
    }
    
    private void mostrarProductos() {
        // Columnas de la tabla
        String[] columnas = {"ID", "Tipo", "Nombre", "Precio", "Stock"};
        List<Producto> lista = ps.getProductos();

        // Crear matriz para JTable
        Object[][] datos = new Object[lista.size()][5];
        for (int i = 0; i < lista.size(); i++) {
            Producto p = lista.get(i);
            datos[i][0] = p.getIdProducto();
            datos[i][1] = p.getTipo();
            datos[i][2] = p.getNombre();
            datos[i][3] = p.getPrecio();
            datos[i][4] = p.getStock();
        }

        // Crear tabla
        JTable tabla = new JTable(datos, columnas);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        JScrollPane scroll = new JScrollPane(tabla);

        // PANEL
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(scroll, BorderLayout.CENTER);

        // VOLVER AL MENÚ
        JButton btnVolver = new JButton("Volver al menú principal");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 16));
        btnVolver.setBackground(new Color(70, 130, 180));
        btnVolver.setForeground(Color.white);
        btnVolver.setFocusPainted(false);

        btnVolver.addActionListener(e -> {
            // Regresar al panel derecho original
            splitPane.setRightComponent(panelDerecho);
            splitPane.setDividerLocation(300);
        });

        panelTabla.add(btnVolver, BorderLayout.SOUTH);

        // Reemplazar panel derecho
        splitPane.setRightComponent(panelTabla);
        splitPane.setDividerLocation(300);
        splitPane.setDividerSize(10); 
        splitPane.setEnabled(false);   
    }




    private void registrarComida() {
        
        
        JTextField nombre = new JTextField();
        JTextField precio = new JTextField();
        JTextField stock = new JTextField();
        JTextField categoria = new JTextField();

        Object[] message = {
            "Nombre:", nombre,
            "Precio:", precio,
            "Stock:", stock,
            "Categoría:", categoria
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Comida", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String n = nombre.getText();
                double p = Double.parseDouble(precio.getText());
                int s = Integer.parseInt(stock.getText());
                String c = categoria.getText();

                ps.registrarComida(n, p, s, c);
                JOptionPane.showMessageDialog(this, "Comida registrada correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
    
    private void registrarBebida() {
        JTextField nombre = new JTextField();
        JTextField precio = new JTextField();
        JTextField stock = new JTextField();

        Object[] message = {
            "Nombre:", nombre,
            "Precio:", precio,
            "Stock:", stock
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Bebida", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String n = nombre.getText();
                double p = Double.parseDouble(precio.getText());
                int s = Integer.parseInt(stock.getText());

                ps.registrarBebida(n, p, s);
                JOptionPane.showMessageDialog(this, "Bebida registrada correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
    
    private void registrarCombo() {
        JTextField nombre = new JTextField();
        JTextField precio = new JTextField();
        JTextField stock = new JTextField();

        Object[] message = {
            "Nombre:", nombre,
            "Precio:", precio,
            "Stock:", stock
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Combo", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String n = nombre.getText();
                double p = Double.parseDouble(precio.getText());
                int s = Integer.parseInt(stock.getText());

                ps.registrarCombo(n, p, s);
                JOptionPane.showMessageDialog(this, "Combo registrado correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
    
    private void comprarProducto() {
        JTextField id = new JTextField();
        JTextField cantidad = new JTextField();

        Object[] message = {
            "ID del producto:", id,
            "Cantidad:", cantidad
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Comprar Producto", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int i = Integer.parseInt(id.getText());
                int c = Integer.parseInt(cantidad.getText());
                if (ps.comprarProducto(i, c)) {
                    JOptionPane.showMessageDialog(this, "Compra realizada");
                } else {
                    JOptionPane.showMessageDialog(this, "Stock insuficiente o ID no existe");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
    
    private void eliminarProducto() {
        String id = JOptionPane.showInputDialog(this, "ID del producto a eliminar:");
        if (id != null) {
            try {
                int i = Integer.parseInt(id);
                if (ps.eliminarProducto(i)) {
                    JOptionPane.showMessageDialog(this, "Producto eliminado");
                } else {
                    JOptionPane.showMessageDialog(this, "ID no encontrado");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaProductos().setVisible(true));
    }
}
