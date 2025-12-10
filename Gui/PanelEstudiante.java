package Gui;

import javax.swing.*;

import Estructuras.Estudiante;

import java.awt.*;
import java.awt.event.*;
import Modulos.Residencia;

public class PanelEstudiante extends JPanel {
    private JTextField nombreField, pbmField, correoField;
    private JTextArea resultadoArea;
    private Residencia funciones;

    public PanelEstudiante(Residencia funciones) {
        this.funciones=funciones;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initUI();
    }

    private void initUI() {
        // PANEL SUPERIOR - Título
        JLabel titulo = new JLabel("Panel de Estudiante", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(0, 100, 200));
        add(titulo, BorderLayout.NORTH);

        // PANEL CENTRAL - Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Estudiante"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        nombreField = new JTextField(20);
        panelForm.add(nombreField, gbc);

        // PBM
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("PBM (1-100):"), gbc);
        gbc.gridx = 1;
        pbmField = new JTextField(10);
        panelForm.add(pbmField, gbc);

        // Correo
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        correoField = new JTextField(20);
        panelForm.add(correoField, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnInsertar = new JButton("➕ Insertar Estudiante");
        btnInsertar.setBackground(new Color(50, 150, 50));
        btnInsertar.setForeground(Color.WHITE);
        btnInsertar.addActionListener(e -> insertarEstudiante());

        panelBotones.add(btnInsertar);
        panelForm.add(panelBotones, gbc);

        add(panelForm, BorderLayout.CENTER);

        // PANEL INFERIOR - Resultados
        JPanel panelResultado = new JPanel(new BorderLayout());
        panelResultado.setBorder(BorderFactory.createTitledBorder("Resultados"));

        resultadoArea = new JTextArea(10, 40);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(resultadoArea);

        panelResultado.add(scroll, BorderLayout.CENTER);

        // Botón para listar todos
        JButton btnListar = new JButton("📋 Listar Todos los Estudiantes");
        btnListar.addActionListener(e -> listarEstudiantes());
        panelResultado.add(btnListar, BorderLayout.SOUTH);

        add(panelResultado, BorderLayout.SOUTH);
    }

    private void insertarEstudiante() {
        try {
            String nombre = nombreField.getText().trim();
            String correo = correoField.getText().trim();
            int pbm = Integer.parseInt(pbmField.getText().trim());

            if (nombre.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y correo son obligatorios");
                return;
            }

            if (pbm < 1 || pbm > 100) {
                JOptionPane.showMessageDialog(this, "PBM debe estar entre 1 y 100");
                return;
            }

            funciones.registrarEstudiante(nombre,pbm,correo);

            resultadoArea.append("✅ Estudiante insertado: " + nombre + "\n");
            limpiarCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "PBM debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarPbm() {
        try {
            int pbm = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese PBM a buscar:"));

            // Crear temporal para búsqueda
            String encontrado= funciones.listarEstudiantesPorPrioridad();
            if (encontrado != null) {
                resultadoArea.append("🔍 Encontrado: " + encontrado + "\n");
            } else {
                resultadoArea.append("❌ No se encontró estudiante con PBM=" + pbm + "\n");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "PBM inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarEstudiantes() {
        resultadoArea.setText("");
        resultadoArea.append("LISTA DE ESTUDIANTES (Ordenados por PBM)\n");
        resultadoArea.append("===========================================\n");
        resultadoArea.append(funciones.listarEstudiantesPorPrioridad());
    }

    private void limpiarCampos() {
        nombreField.setText("");
        pbmField.setText("");
        correoField.setText("");
    }
}