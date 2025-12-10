package Gui;

import javax.swing.*;
import java.awt.*;
import Modulos.Residencia;

public class VentanaPrincipal extends JFrame {

    private Residencia funciones;

    public VentanaPrincipal() {
        funciones = new Residencia();

        setTitle("Sistema de Gestión de Estudiantes - AVL");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Solo dos pestañas
        tabbedPane.addTab("👨‍💼 Administrador", new PanelAdmin(funciones));
        tabbedPane.addTab("👨‍🎓 Estudiante", new PanelEstudiante(funciones));

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
