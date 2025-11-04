import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import javax.swing.filechooser.FileNameExtensionFilter;

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
    private Analisis analisisActual;
    private JTabbedPane tabbedPane;
    private JTextArea datosTextArea;
    private JTextField categoriaField;
    private JTextField codigoField;
    private JTextArea codigosTextArea;
    private JComboBox<String> categoriasCombo;
    private JTextField patronField;

    public VentanaPrincipal() {
        usuarios = new ArrayList<>();
        preguntas = Arrays.asList("¿Cómo califica el servicio?", "¿Recomendaría el producto?");
        analisisActual = new Analisis();
        
        setTitle("Sistema de Análisis Antropológico");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear el TabbedPane principal
        tabbedPane = new JTabbedPane();
        add(tabbedPane);

        // Agregar las pestañas
        tabbedPane.addTab("Encuestas", crearPanelEncuestas());
        tabbedPane.addTab("Análisis", crearPanelAnalisis());
        tabbedPane.addTab("Reportes", crearPanelReportes());

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

    private JPanel crearPanelEncuestas() {
        JPanel panel = new JPanel(new BorderLayout());
        
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

        // Eventos
        agregarButton.addActionListener(e -> agregarUsuario());
        verRespuestasButton.addActionListener(e -> mostrarRespuestas());

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelAnalisis() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Área de texto para datos
        datosTextArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(datosTextArea);
        
        // Controles para categorías y códigos
        categoriaField = new JTextField(20);
        codigoField = new JTextField(20);
        codigosTextArea = new JTextArea(5, 40);
        categoriasCombo = new JComboBox<>();
        patronField = new JTextField(20);

        // Botones de análisis
        JButton importarButton = new JButton("Importar Datos");
        JButton limpiarButton = new JButton("Limpiar Datos");
        JButton segmentarButton = new JButton("Segmentar Texto");
        JButton crearCategoriaButton = new JButton("Crear Categoría");
        JButton asignarCodigoButton = new JButton("Asignar Código");
        JButton buscarPatronesButton = new JButton("Buscar Patrones");
        JButton analizarCoocurrenciaButton = new JButton("Analizar Coocurrencia");

        // Agregar componentes al panel de control
        controlPanel.add(new JLabel("Categoría:"));
        controlPanel.add(categoriaField);
        controlPanel.add(new JLabel("Código:"));
        controlPanel.add(codigoField);
        controlPanel.add(importarButton);
        controlPanel.add(limpiarButton);
        controlPanel.add(segmentarButton);
        controlPanel.add(crearCategoriaButton);
        controlPanel.add(asignarCodigoButton);
        controlPanel.add(buscarPatronesButton);
        controlPanel.add(analizarCoocurrenciaButton);

        // Eventos
        importarButton.addActionListener(e -> importarDatos());
        limpiarButton.addActionListener(e -> limpiarDatos());
        segmentarButton.addActionListener(e -> segmentarTexto());
        crearCategoriaButton.addActionListener(e -> crearCategoria());
        asignarCodigoButton.addActionListener(e -> asignarCodigo());
        buscarPatronesButton.addActionListener(e -> buscarPatrones());
        analizarCoocurrenciaButton.addActionListener(e -> analizarCoocurrencia());

        // Organizar el panel
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());

        JButton generarReporteButton = new JButton("Generar Reporte");
        JButton exportarButton = new JButton("Exportar Resultados");
        JButton guardarProyectoButton = new JButton("Guardar Proyecto");

        controlPanel.add(generarReporteButton);
        controlPanel.add(exportarButton);
        controlPanel.add(guardarProyectoButton);

        JTextArea reporteArea = new JTextArea(20, 40);
        reporteArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reporteArea);

        generarReporteButton.addActionListener(e -> generarReporte(reporteArea));
        exportarButton.addActionListener(e -> exportarResultados(reporteArea.getText()));
        guardarProyectoButton.addActionListener(e -> guardarProyecto());

        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Métodos de funcionalidad
    private void importarDatos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                List<String> datos = Files.readAllLines(fileChooser.getSelectedFile().toPath());
                datosTextArea.setText(String.join("\n", datos));
                analisisActual.limpiarDatos(datos);
                JOptionPane.showMessageDialog(this, "Datos importados correctamente");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al importar datos: " + ex.getMessage());
            }
        }
    }

    private void limpiarDatos() {
        String texto = datosTextArea.getText();
        List<String> lineas = Arrays.asList(texto.split("\n"));
        analisisActual.limpiarDatos(lineas);
        datosTextArea.setText(String.join("\n", lineas));
        JOptionPane.showMessageDialog(this, "Datos limpiados correctamente");
    }

    private void segmentarTexto() {
        String texto = datosTextArea.getText();
        String identificador = JOptionPane.showInputDialog("Ingrese un identificador para el segmento:");
        if (identificador != null && !identificador.isEmpty()) {
            analisisActual.segmentarTexto(texto, identificador);
            JOptionPane.showMessageDialog(this, "Texto segmentado correctamente");
        }
    }

    private void crearCategoria() {
        String categoria = categoriaField.getText();
        String codigosTexto = codigosTextArea.getText();
        List<String> codigos = Arrays.asList(codigosTexto.split("\n"));
        analisisActual.crearCategoria(categoria, codigos);
        categoriasCombo.addItem(categoria);
        JOptionPane.showMessageDialog(this, "Categoría creada correctamente");
    }

    private void asignarCodigo() {
        String codigo = codigoField.getText();
        String texto = datosTextArea.getSelectedText();
        if (texto != null && !texto.isEmpty()) {
            analisisActual.asignarCodigo(codigo, texto);
            JOptionPane.showMessageDialog(this, "Código asignado correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione el texto a codificar");
        }
    }

    private void buscarPatrones() {
        String patron = patronField.getText();
        List<String> encontrados = analisisActual.buscarPatrones(patron);
        mostrarResultadosBusqueda(encontrados);
    }

    private void analizarCoocurrencia() {
        Map<String, List<String>> coocurrencias = analisisActual.analizarCoocurrencia();
        mostrarCoocurrencias(coocurrencias);
    }

    private void mostrarResultadosBusqueda(List<String> resultados) {
        StringBuilder sb = new StringBuilder();
        sb.append("Resultados encontrados:\n\n");
        for (String resultado : resultados) {
            sb.append("- ").append(resultado).append("\n");
        }
        resultadosArea.setText(sb.toString());
    }

    private void mostrarCoocurrencias(Map<String, List<String>> coocurrencias) {
        StringBuilder sb = new StringBuilder();
        sb.append("Análisis de coocurrencia:\n\n");
        coocurrencias.forEach((codigo, coocurrentes) -> {
            sb.append(codigo).append(":\n");
            coocurrentes.forEach(c -> sb.append("  - ").append(c).append("\n"));
            sb.append("\n");
        });
        resultadosArea.setText(sb.toString());
    }

    private void generarReporte(JTextArea reporteArea) {
        String resumen = analisisActual.generarResumen();
        reporteArea.setText(resumen);
    }

    private void exportarResultados(String contenido) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }
                Files.write(file.toPath(), contenido.getBytes());
                JOptionPane.showMessageDialog(this, "Resultados exportados correctamente");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al exportar resultados: " + ex.getMessage());
            }
        }
    }

    private void guardarProyecto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de proyecto", "proj"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileChooser.getSelectedFile()))) {
                oos.writeObject(analisisActual);
                JOptionPane.showMessageDialog(this, "Proyecto guardado correctamente");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el proyecto: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}