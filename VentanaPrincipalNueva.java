import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VentanaPrincipalNueva extends JFrame {
    private JTabbedPane tabbedPane;
    private JTextArea resultadosArea;
    private List<Usuario<String>> usuarios;
    private Analisis analisisActual;
    
    // Componentes para la pestaña de Análisis
    private JTextArea datosTextArea;
    private JTextField categoriaField;
    private JTextField codigoField;
    private JTextArea codigosTextArea;
    private JComboBox<String> categoriasCombo;
    private JTextField patronField;
    
    // Componentes para la pestaña de Encuestas
    private JTextField nombreField;
    private JTextField edadField;
    private JComboBox<String> generoCombo;
    private JTextField ocupacionField;
    private JTextArea respuestasArea;

    public VentanaPrincipalNueva() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        usuarios = new ArrayList<>();
        analisisActual = new Analisis();
        
        setTitle("Sistema de Análisis Antropológico");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear el TabbedPane principal
        tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);

        // Agregar las pestañas principales
        tabbedPane.addTab("Encuestas", crearPanelEncuestas());
        tabbedPane.addTab("Análisis", crearPanelAnalisis());
        tabbedPane.addTab("Reportes", crearPanelReportes());

        setLocationRelativeTo(null);
    }

    private JPanel crearPanelEncuestas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Encuestado"));

        nombreField = new JTextField(20);
        edadField = new JTextField(20);
        generoCombo = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        ocupacionField = new JTextField(20);

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(nombreField);
        formPanel.add(new JLabel("Edad:"));
        formPanel.add(edadField);
        formPanel.add(new JLabel("Género:"));
        formPanel.add(generoCombo);
        formPanel.add(new JLabel("Ocupación:"));
        formPanel.add(ocupacionField);

        // Panel de respuestas
        JPanel respuestasPanel = new JPanel(new BorderLayout(5, 5));
        respuestasPanel.setBorder(BorderFactory.createTitledBorder("Respuestas"));
        
        respuestasArea = new JTextArea(10, 40);
        respuestasArea.setLineWrap(true);
        respuestasArea.setWrapStyleWord(true);
        JScrollPane scrollRespuestas = new JScrollPane(respuestasArea);
        respuestasPanel.add(scrollRespuestas, BorderLayout.CENTER);

        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton guardarButton = new JButton("Guardar Encuesta");
        JButton verButton = new JButton("Ver Encuestas");
        
        guardarButton.addActionListener(e -> guardarEncuesta());
        verButton.addActionListener(e -> mostrarEncuestas());
        
        botonesPanel.add(guardarButton);
        botonesPanel.add(verButton);

        // Organización final del panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(respuestasPanel, BorderLayout.CENTER);
        topPanel.add(botonesPanel, BorderLayout.SOUTH);

        panel.add(topPanel, BorderLayout.NORTH);
        
        // Área de resultados
        resultadosArea = new JTextArea(15, 40);
        resultadosArea.setEditable(false);
        JScrollPane scrollResultados = new JScrollPane(resultadosArea);
        scrollResultados.setBorder(BorderFactory.createTitledBorder("Resultados"));
        
        panel.add(scrollResultados, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelAnalisis() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior para controles
        JPanel controlPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Herramientas de Análisis"));

        // Campos de entrada
        categoriaField = new JTextField(20);
        codigoField = new JTextField(20);
        patronField = new JTextField(20);
        categoriasCombo = new JComboBox<>();

        // Área de texto principal
        datosTextArea = new JTextArea(20, 40);
        datosTextArea.setLineWrap(true);
        datosTextArea.setWrapStyleWord(true);
        JScrollPane scrollDatos = new JScrollPane(datosTextArea);
        scrollDatos.setBorder(BorderFactory.createTitledBorder("Datos para Análisis"));

        // Botones de análisis
        controlPanel.add(new JLabel("Categoría:"));
        controlPanel.add(categoriaField);
        controlPanel.add(new JLabel("Código:"));
        controlPanel.add(codigoField);
        controlPanel.add(new JLabel("Patrón de búsqueda:"));
        controlPanel.add(patronField);

        JPanel botonesAnalisis = new JPanel(new GridLayout(0, 2, 5, 5));
        botonesAnalisis.setBorder(BorderFactory.createTitledBorder("Acciones"));

        JButton importarButton = new JButton("Importar Datos");
        JButton limpiarButton = new JButton("Limpiar Datos");
        JButton segmentarButton = new JButton("Segmentar Texto");
        JButton crearCategoriaButton = new JButton("Crear Categoría");
        JButton asignarCodigoButton = new JButton("Asignar Código");
        JButton buscarPatronesButton = new JButton("Buscar Patrones");
        JButton analizarCoocurrenciaButton = new JButton("Analizar Coocurrencia");

        botonesAnalisis.add(importarButton);
        botonesAnalisis.add(limpiarButton);
        botonesAnalisis.add(segmentarButton);
        botonesAnalisis.add(crearCategoriaButton);
        botonesAnalisis.add(asignarCodigoButton);
        botonesAnalisis.add(buscarPatronesButton);
        botonesAnalisis.add(analizarCoocurrenciaButton);

        // Eventos de los botones
        importarButton.addActionListener(e -> importarDatos());
        limpiarButton.addActionListener(e -> limpiarDatos());
        segmentarButton.addActionListener(e -> segmentarTexto());
        crearCategoriaButton.addActionListener(e -> crearCategoria());
        asignarCodigoButton.addActionListener(e -> asignarCodigo());
        buscarPatronesButton.addActionListener(e -> buscarPatrones());
        analizarCoocurrenciaButton.addActionListener(e -> analizarCoocurrencia());

        // Panel para resultados del análisis
        codigosTextArea = new JTextArea(8, 40);
        codigosTextArea.setLineWrap(true);
        codigosTextArea.setWrapStyleWord(true);
        JScrollPane scrollCodigos = new JScrollPane(codigosTextArea);
        scrollCodigos.setBorder(BorderFactory.createTitledBorder("Resultados del Análisis"));

        // Organización final del panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(controlPanel, BorderLayout.NORTH);
        topPanel.add(botonesAnalisis, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollDatos, BorderLayout.CENTER);
        panel.add(scrollCodigos, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de controles de reportes
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Opciones de Reporte"));

        JButton generarReporteButton = new JButton("Generar Reporte");
        JButton exportarButton = new JButton("Exportar Resultados");
        JButton guardarProyectoButton = new JButton("Guardar Proyecto");
        JButton filtrarButton = new JButton("Filtrar por Categoría");

        controlPanel.add(generarReporteButton);
        controlPanel.add(exportarButton);
        controlPanel.add(guardarProyectoButton);
        controlPanel.add(filtrarButton);

        // Área de visualización de reportes
        JTextArea reporteArea = new JTextArea(25, 50);
        reporteArea.setEditable(false);
        reporteArea.setLineWrap(true);
        reporteArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reporteArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Reporte"));

        // Eventos
        generarReporteButton.addActionListener(e -> generarReporte(reporteArea));
        exportarButton.addActionListener(e -> exportarResultados(reporteArea.getText()));
        guardarProyectoButton.addActionListener(e -> guardarProyecto());
        filtrarButton.addActionListener(e -> filtrarPorCategoria(reporteArea));

        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void guardarEncuesta() {
        try {
            String nombre = nombreField.getText();
            int edad = Integer.parseInt(edadField.getText());
            String genero = (String) generoCombo.getSelectedItem();
            String ocupacion = ocupacionField.getText();
            String respuesta = respuestasArea.getText();

            Usuario<String> usuario = new Usuario<>(nombre, edad);
            usuario.setGenero(genero);
            usuario.setOcupacion(ocupacion);
            usuario.setRespuesta(Arrays.asList(respuesta));
            
            usuarios.add(usuario);
            
            limpiarCamposEncuesta();
            JOptionPane.showMessageDialog(this, "Encuesta guardada exitosamente");
            mostrarEncuestas();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una edad válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCamposEncuesta() {
        nombreField.setText("");
        edadField.setText("");
        ocupacionField.setText("");
        respuestasArea.setText("");
    }

    private void mostrarEncuestas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Encuestas Registradas:\n\n");
        
        for (Usuario<String> usuario : usuarios) {
            sb.append("Nombre: ").append(usuario.getNombre()).append("\n");
            sb.append("Edad: ").append(usuario.getEdad()).append("\n");
            sb.append("Género: ").append(usuario.getGenero()).append("\n");
            sb.append("Ocupación: ").append(usuario.getOcupacion()).append("\n");
            sb.append("Respuestas: ").append(usuario.getRespuesta()).append("\n");
            sb.append("----------------------------------------\n");
        }
        
        resultadosArea.setText(sb.toString());
    }

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
        if (!categoria.isEmpty()) {
            List<String> codigos = Arrays.asList(codigosTextArea.getText().split("\n"));
            analisisActual.crearCategoria(categoria, codigos);
            categoriasCombo.addItem(categoria);
            categoriaField.setText("");
            codigosTextArea.setText("");
            JOptionPane.showMessageDialog(this, "Categoría creada correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un nombre de categoría");
        }
    }

    private void asignarCodigo() {
        String codigo = codigoField.getText();
        String texto = datosTextArea.getSelectedText();
        if (texto != null && !texto.isEmpty() && !codigo.isEmpty()) {
            analisisActual.asignarCodigo(codigo, texto);
            codigosTextArea.append("Código '" + codigo + "' asignado a: " + texto + "\n");
            JOptionPane.showMessageDialog(this, "Código asignado correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione texto y especifique un código");
        }
    }

    private void buscarPatrones() {
        String patron = patronField.getText();
        if (!patron.isEmpty()) {
            List<String> encontrados = analisisActual.buscarPatrones(patron);
            mostrarResultadosBusqueda(encontrados);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un patrón de búsqueda");
        }
    }

    private void analizarCoocurrencia() {
        Map<String, List<String>> coocurrencias = analisisActual.analizarCoocurrencia();
        mostrarCoocurrencias(coocurrencias);
    }

    private void mostrarResultadosBusqueda(List<String> resultados) {
        StringBuilder sb = new StringBuilder();
        sb.append("Resultados de la búsqueda:\n\n");
        for (String resultado : resultados) {
            sb.append("- ").append(resultado).append("\n");
        }
        codigosTextArea.setText(sb.toString());
    }

    private void mostrarCoocurrencias(Map<String, List<String>> coocurrencias) {
        StringBuilder sb = new StringBuilder();
        sb.append("Análisis de coocurrencia:\n\n");
        coocurrencias.forEach((codigo, coocurrentes) -> {
            sb.append(codigo).append(":\n");
            coocurrentes.forEach(c -> sb.append("  - ").append(c).append("\n"));
            sb.append("\n");
        });
        codigosTextArea.setText(sb.toString());
    }

    private void generarReporte(JTextArea reporteArea) {
        StringBuilder sb = new StringBuilder();
        sb.append(analisisActual.generarResumen()).append("\n\n");
        sb.append("Usuarios encuestados: ").append(usuarios.size()).append("\n\n");
        
        // Añadir estadísticas de códigos
        Map<String, Integer> frecuencias = analisisActual.contarFrecuenciaCodigos();
        sb.append("Frecuencia de códigos:\n");
        frecuencias.forEach((codigo, frecuencia) -> 
            sb.append(codigo).append(": ").append(frecuencia).append(" ocurrencias\n"));
        
        reporteArea.setText(sb.toString());
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

    private void filtrarPorCategoria(JTextArea reporteArea) {
        String categoria = (String) JOptionPane.showInputDialog(
            this,
            "Seleccione una categoría:",
            "Filtrar por Categoría",
            JOptionPane.QUESTION_MESSAGE,
            null,
            ((List<Usuario<String>>) categoriasCombo.getModel()).toArray(),
            categoriasCombo.getSelectedItem()
        );
        
        if (categoria != null) {
            List<String> codigosFiltrados = analisisActual.filtrarPorCategoria(categoria);
            StringBuilder sb = new StringBuilder();
            sb.append("Códigos en la categoría '").append(categoria).append("':\n\n");
            codigosFiltrados.forEach(codigo -> sb.append("- ").append(codigo).append("\n"));
            reporteArea.setText(sb.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipalNueva().setVisible(true);
        });
    }
}