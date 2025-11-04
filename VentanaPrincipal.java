import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private JTextField nombreField;
    private JTextField edadField;
    private JComboBox<String> generoCombo;
    private JTextField ocupacionField;
    private JTextArea resultadosArea;
    private List<Usuario<String>> usuarios;
    private List<String> preguntas;
    private JComboBox<String> preguntasCombo;
    private JTextField respuestaField;

    public VentanaPrincipal() {
        usuarios = new ArrayList<>();
        preguntas = Arrays.asList("¿Cómo califica el servicio?", "¿Recomendaría el producto?");
        
        setTitle("Sistema de Encuestas Antropológicas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de entrada de datos
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        inputPanel.add(nombreField);

        inputPanel.add(new JLabel("Edad:"));
        edadField = new JTextField();
        inputPanel.add(edadField);

        inputPanel.add(new JLabel("Género:"));
        generoCombo = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        inputPanel.add(generoCombo);

        inputPanel.add(new JLabel("Ocupación:"));
        ocupacionField = new JTextField();
        inputPanel.add(ocupacionField);

        inputPanel.add(new JLabel("Pregunta:"));
        preguntasCombo = new JComboBox<>(preguntas.toArray(new String[0]));
        inputPanel.add(preguntasCombo);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton agregarButton = new JButton("Agregar Usuario");
        JButton verRespuestasButton = new JButton("Ver Respuestas");
        buttonPanel.add(agregarButton);
        buttonPanel.add(verRespuestasButton);

        // Área de resultados
        resultadosArea = new JTextArea();
        resultadosArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadosArea);

        // Añadir componentes al frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Eventos
        agregarButton.addActionListener(e -> agregarUsuario());
        verRespuestasButton.addActionListener(e -> mostrarRespuestas());

        setLocationRelativeTo(null);
    }

    private void agregarUsuario() {
        try {
            String nombre = nombreField.getText();
            int edad = Integer.parseInt(edadField.getText());
            String genero = (String) generoCombo.getSelectedItem();
            String ocupacion = ocupacionField.getText();

            Usuario<String> usuario = new Usuario<>(nombre, edad);
            usuario.setGenero(genero);
            usuario.setOcupacion(ocupacion);
            usuario.setRespuesta(new ArrayList<>());
            
            usuarios.add(usuario);
            
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente");
            actualizarResultados();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una edad válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        nombreField.setText("");
        edadField.setText("");
        ocupacionField.setText("");
    }

    private void mostrarRespuestas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resumen de usuarios y respuestas:\n\n");
        
        for (Usuario<String> usuario : usuarios) {
            sb.append(usuario.obtenerInformacion()).append("\n");
            sb.append("Ocupación: ").append(usuario.getOcupacion()).append("\n");
            sb.append("Género: ").append(usuario.getGenero()).append("\n");
            sb.append("----------------------------------------\n");
        }
        
        resultadosArea.setText(sb.toString());
    }

    private void actualizarResultados() {
        mostrarRespuestas();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}