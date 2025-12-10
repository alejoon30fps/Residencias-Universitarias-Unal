package Gui;

import javax.swing.*;

import Estructuras.Estudiante; // Asumo que esta clase es necesaria
import java.awt.*;
import java.awt.event.*;
import Modulos.Residencia; // Clase que contiene la lógica de negocio

public class PanelAdmin extends JPanel {
    private Residencia funciones;
    // Campos existentes
    private JTextField idEliminarField, pbmField, nuevoPbmField;
    
    // Campos y áreas nuevas
    private JTextField numCuposField;
    private JTextField idBuscarField;
    private JTextArea logArea;
    private JTextArea admitidosArea; // Nueva área para la lista de admitidos

    public PanelAdmin(Residencia funciones) {
        this.funciones = funciones;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initUI();
    }

    private void initUI() {
        // PANEL SUPERIOR - Título
        JLabel titulo = new JLabel(" Panel de Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(200, 0, 0));
        add(titulo, BorderLayout.NORTH);
        
        // --- PANEL CENTRAL PRINCIPAL (Operaciones) ---
        // Usaremos un BoxLayout para apilar los paneles de operación
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        // Paneles de operaciones existentes
        panelCentral.add(crearPanelEliminar());
        panelCentral.add(Box.createVerticalStrut(10)); // Espacio
        panelCentral.add(crearPanelModificarPBM());
        panelCentral.add(Box.createVerticalStrut(10)); // Espacio

        // --- Nuevos Paneles ---
        panelCentral.add(crearPanelCupos());
        panelCentral.add(Box.createVerticalStrut(10)); // Espacio
        panelCentral.add(crearPanelBuscar());
        panelCentral.add(Box.createVerticalStrut(10)); // Espacio
        
        JScrollPane scrollCentral = new JScrollPane(panelCentral);
        scrollCentral.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollCentral, BorderLayout.CENTER);


        // PANEL DERECHO - Lista de Admitidos
        JPanel panelAdmitidos = crearPanelAdmitidos();
        add(panelAdmitidos, BorderLayout.EAST);

        // PANEL INFERIOR - Log/Resultados
        JPanel panelLog = crearPanelLog();
        add(panelLog, BorderLayout.SOUTH);
    }
    
    // --- Métodos de Creación de Paneles (refactorizados para claridad) ---
    
    private JPanel crearPanelEliminar() {
        JPanel panelEliminar = new JPanel(new GridBagLayout());
        panelEliminar.setBorder(BorderFactory.createTitledBorder("❌ Eliminar Estudiante"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0;
        panelEliminar.add(new JLabel("ID del estudiante:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        idEliminarField = new JTextField(15);
        panelEliminar.add(idEliminarField, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE;
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        btnEliminar.setBackground(new Color(200, 50, 50));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> eliminarEstudiante());
        panelEliminar.add(btnEliminar, gbc);
        
        return panelEliminar;
    }

    private JPanel crearPanelModificarPBM() {
        JPanel panelModificar = new JPanel(new GridBagLayout());
        panelModificar.setBorder(BorderFactory.createTitledBorder("✏️ Modificar PBM"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0;
        panelModificar.add(new JLabel("ID del estudiante:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        pbmField = new JTextField(15);
        panelModificar.add(pbmField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        panelModificar.add(new JLabel("Nuevo PBM:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        nuevoPbmField = new JTextField(15);
        panelModificar.add(nuevoPbmField, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.gridheight = 2; gbc.fill = GridBagConstraints.NONE;
        JButton btnModificar = new JButton("📝 Modificar");
        btnModificar.setBackground(new Color(50, 100, 200));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.addActionListener(e -> modificarPBM());
        panelModificar.add(btnModificar, gbc);
        
        return panelModificar;
    }

    private JPanel crearPanelCupos() {
        // Nuevo Panel para gestionar Cupos
        JPanel panelCupos = new JPanel(new GridBagLayout());
        panelCupos.setBorder(BorderFactory.createTitledBorder("🏆 Gestión de Cupos"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // 1. Campo para ingresar Num. de Cupos
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0;
        panelCupos.add(new JLabel("Número de Cupos:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        numCuposField = new JTextField(5);
        panelCupos.add(numCuposField, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE;
        JButton btnSetCupos = new JButton("⚙️ Fijar Cupos");
        btnSetCupos.setBackground(new Color(255, 165, 0)); // Naranja
        btnSetCupos.setForeground(Color.WHITE);
        btnSetCupos.addActionListener(e -> ingresarNumCupos());
        panelCupos.add(btnSetCupos, gbc);
        
        // 2. Botón de Asignación Automática
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton btnAsignar = new JButton("✨ Asignar Cupos Automáticamente");
        btnAsignar.setBackground(new Color(60, 179, 113)); // Verde Marino
        btnAsignar.setForeground(Color.WHITE);
        btnAsignar.addActionListener(e -> asignarCupos());
        panelCupos.add(btnAsignar, gbc);
        
        return panelCupos;
    }
    
    private JPanel crearPanelBuscar() {
        // Nuevo Panel para buscar estudiante por ID
        JPanel panelBuscar = new JPanel(new GridBagLayout());
        panelBuscar.setBorder(BorderFactory.createTitledBorder("🔎 Buscar Estudiante"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0;
        panelBuscar.add(new JLabel("ID del estudiante:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        idBuscarField = new JTextField(15);
        panelBuscar.add(idBuscarField, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE;
        JButton btnBuscar = new JButton("Buscar ID");
        btnBuscar.setBackground(new Color(100, 100, 100)); // Gris oscuro
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> buscarEstudiante());
        panelBuscar.add(btnBuscar, gbc);
        
        return panelBuscar;
    }

    private JPanel crearPanelAdmitidos() {
        // Nuevo Panel para mostrar la lista de admitidos
        JPanel panelAdmitidos = new JPanel(new BorderLayout());
        panelAdmitidos.setBorder(BorderFactory.createTitledBorder("📜 Estudiantes Admitidos"));
        panelAdmitidos.setPreferredSize(new Dimension(300, 0)); // Darle un ancho fijo

        admitidosArea = new JTextArea(15, 25);
        admitidosArea.setEditable(false);
        admitidosArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollAdmitidos = new JScrollPane(admitidosArea);
        
        JButton btnMostrarAdmitidos = new JButton("Actualizar Lista");
        btnMostrarAdmitidos.addActionListener(e -> mostrarEstudiantesAdmitidos());

        panelAdmitidos.add(scrollAdmitidos, BorderLayout.CENTER);
        panelAdmitidos.add(btnMostrarAdmitidos, BorderLayout.SOUTH);
        
        return panelAdmitidos;
    }

    private JPanel crearPanelLog() {
        // Panel inferior para el Log
        JPanel panelLog = new JPanel(new BorderLayout());
        panelLog.setBorder(BorderFactory.createTitledBorder("📝 Log de Operaciones"));

        logArea = new JTextArea(10, 50); // Reduje el tamaño a 10 filas
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(logArea);

        JButton btnLimpiarLog = new JButton("Limpiar Log");
        btnLimpiarLog.addActionListener(e -> logArea.setText(""));

        panelLog.add(scroll, BorderLayout.CENTER);
        panelLog.add(btnLimpiarLog, BorderLayout.SOUTH);
        
        return panelLog;
    }


    // --- Lógica de Eventos (Implementación de tus funciones) ---

    // Funciones existentes (sin modificar)
    private void eliminarEstudiante() {
        try {
            long id = Long.parseLong(idEliminarField.getText().trim());

            // **1. LLAMADA A TU LÓGICA:**
            boolean eliminado = funciones.eliminarEstudiante(id);

            if (eliminado) {
                // Se usa el query antes de eliminar para obtener el nombre/datos
                String datosEstudiante = funciones.queryEstudianteId(id);
                logArea.append("✅ Eliminado: ID=" + id + " - " + datosEstudiante + "\n");
            } else {
                logArea.append("❌ No encontrado: ID=" + id + "\n");
                JOptionPane.showMessageDialog(this,
                        "No se encontró estudiante con ID: " + id, "Error", JOptionPane.ERROR_MESSAGE);
            }
            idEliminarField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarPBM() {
        try {
            long id = Long.parseLong(pbmField.getText().trim());
            int nuevoPbm = Integer.parseInt(nuevoPbmField.getText().trim());

            if (nuevoPbm < 1 || nuevoPbm > 100) {
                JOptionPane.showMessageDialog(this, "PBM debe estar entre 1 y 100", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // **2. LLAMADA A TU LÓGICA:**
            boolean cambiado = funciones.changeValue(nuevoPbm, id);

            if (cambiado) {
                logArea.append("✏️ Modificado: ID=" + id +
                        " - " + funciones.queryEstudianteId(id) + // Se consulta el estudiante modificado
                        " - Nuevo PBM=" + nuevoPbm + "\n");
                pbmField.setText("");
                nuevoPbmField.setText("");
            } else {
                logArea.append("❌ No se pudo modificar PBM: ID=" + id + "\n");
                JOptionPane.showMessageDialog(this, "No se encontró el estudiante o ocurrió un error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID y PBM deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // --- Nuevas Funciones ---

    private void ingresarNumCupos() {
        try {
            int numCupos = Integer.parseInt(numCuposField.getText().trim());
            
            if (numCupos <= 0) {
                 JOptionPane.showMessageDialog(this, "El número de cupos debe ser mayor a 0", "Advertencia", JOptionPane.WARNING_MESSAGE);
                 return;
            }

            // **3. LLAMADA A TU LÓGICA DE FIJAR CUPOS:**
            // Aquí debes llamar a tu función que guarda el número de cupos en el backend.
            // Ejemplo: funciones.setNumCupos(numCupos);
            // La respuesta de la función (boolean, String, etc.) dependerá de tu lógica.
            
            // Suponiendo que hay una función `setNumCupos`
            funciones.setCupos(numCupos); // Cambia esto por la función correcta
            
            logArea.append("⚙️ Cupos fijados: Total de cupos = " + numCupos + "\n");
            numCuposField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número de Cupos debe ser un número entero válido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
             logArea.append("❌ Error al fijar cupos: " + ex.getMessage() + "\n");
             JOptionPane.showMessageDialog(this, "Error al fijar cupos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void asignarCupos() {
        // **4. LLAMADA A TU LÓGICA DE ASIGNACIÓN AUTOMÁTICA:**
        // Aquí debes llamar a tu función que ejecuta la asignación automática (por ejemplo, basándose en el PBM).
        // Ejemplo: String resultado = funciones.asignarCuposAutomaticamente();
        
        try {
            funciones.asigCupos(); // Cambia esto por la función correcta
            logArea.append("✨ Proceso de Asignación Automática completado.\n");            
            // Opcional: Actualizar la lista de admitidos automáticamente
            mostrarEstudiantesAdmitidos();
            
        } catch (Exception ex) {
            logArea.append("❌ Error en la Asignación Automática: " + ex.getMessage() + "\n");
            JOptionPane.showMessageDialog(this, "Error en la asignación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarEstudiante() {
        try {
            long id = Long.parseLong(idBuscarField.getText().trim());
            
            // **5. LLAMADA A TU LÓGICA DE BÚSQUEDA:**
            // Esta función ya existe en tu código:
            String resultadoBusqueda = funciones.queryEstudianteId(id); // Asumo que devuelve String de los datos
            
            if (resultadoBusqueda != null && !resultadoBusqueda.trim().isEmpty()) {
                logArea.append("🔎 Encontrado: " + resultadoBusqueda + "\n");
            } else {
                logArea.append("❌ No encontrado: ID=" + id + "\n");
                JOptionPane.showMessageDialog(this, "No se encontró estudiante con ID: " + id, "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            idBuscarField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarEstudiantesAdmitidos() {
        // **6. LLAMADA A TU LÓGICA PARA OBTENER LA LISTA DE ADMITIDOS:**
        // Aquí debes llamar a tu función que devuelve la lista (o String formateado) de estudiantes admitidos.
        // Ejemplo: String lista = funciones.getEstudiantesAdmitidos();
        
        try {
            // Suponiendo que tienes una función `getEstudiantesAdmitidos` que devuelve una cadena formateada
            String listaAdmitidos = funciones.listaEntera(); // Cambia esto por la función correcta
            
            admitidosArea.setText(""); // Limpiar
            admitidosArea.append(listaAdmitidos);
            
            logArea.append("📜 Lista de admitidos actualizada.\n");
            
        } catch (Exception ex) {
            admitidosArea.setText("Error al cargar la lista: " + ex.getMessage());
            logArea.append("❌ Error al cargar admitidos: " + ex.getMessage() + "\n");
            JOptionPane.showMessageDialog(this, "Error al cargar la lista de admitidos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // El método buscarEstudiantePorId original puede ser eliminado si no se usa.
    // private String buscarEstudiantePorId(long id) {
    //     return funciones.queryEstudianteId(id);
    // }
}