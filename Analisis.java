import java.util.*;
import java.util.stream.Collectors;

public class Analisis {
    private int idAnalisis;
    private String nombreAnalisis;
    private List<Encuesta> encuestaAnalizada;
    private List<String> patronesEncontrados;
    private Map<String, List<String>> codigos;
    private String resultados;
    private Map<String, Integer> frecuenciaCodigos;
    private Map<String, List<String>> categorias;
    private List<String> datosLimpios;
    private Map<String, List<String>> segmentos;

    public Analisis() {
        this.encuestaAnalizada = new ArrayList<>();
        this.patronesEncontrados = new ArrayList<>();
        this.codigos = new HashMap<>();
        this.frecuenciaCodigos = new HashMap<>();
        this.categorias = new HashMap<>();
        this.datosLimpios = new ArrayList<>();
        this.segmentos = new HashMap<>();
    }

    // Métodos principales
    public void limpiarDatos(List<String> datos) {
        datosLimpios = datos.stream()
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());
    }

    public void segmentarTexto(String texto, String identificador) {
        List<String> segmentosTexto = Arrays.asList(texto.split("\\."));
        segmentos.put(identificador, segmentosTexto);
    }

    public void crearCategoria(String categoria, List<String> codigos) {
        categorias.put(categoria, codigos);
    }

    public void asignarCodigo(String codigo, String texto) {
        codigos.computeIfAbsent(codigo, k -> new ArrayList<>()).add(texto);
        frecuenciaCodigos.merge(codigo, 1, Integer::sum);
    }

    public List<String> buscarPatrones(String patron) {
        List<String> encontrados = new ArrayList<>();
        for (String texto : datosLimpios) {
            if (texto.toLowerCase().contains(patron.toLowerCase())) {
                encontrados.add(texto);
            }
        }
        patronesEncontrados = encontrados;
        return encontrados;
    }

    public Map<String, Integer> contarFrecuenciaCodigos() {
        return new HashMap<>(frecuenciaCodigos);
    }

    public Map<String, List<String>> analizarCoocurrencia() {
        Map<String, List<String>> coocurrencias = new HashMap<>();
        for (String codigo1 : codigos.keySet()) {
            List<String> coocurrentes = codigos.keySet().stream()
                .filter(codigo2 -> !codigo1.equals(codigo2))
                .filter(codigo2 -> tienenTextoComun(codigo1, codigo2))
                .collect(Collectors.toList());
            coocurrencias.put(codigo1, coocurrentes);
        }
        return coocurrencias;
    }

    private boolean tienenTextoComun(String codigo1, String codigo2) {
        List<String> textos1 = codigos.get(codigo1);
        List<String> textos2 = codigos.get(codigo2);
        return textos1.stream().anyMatch(textos2::contains);
    }

    public List<String> filtrarPorCategoria(String categoria) {
        return categorias.getOrDefault(categoria, new ArrayList<>());
    }

    public String generarResumen() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Resumen del Análisis: ").append(nombreAnalisis).append("\n\n");
        resumen.append("Total de datos analizados: ").append(datosLimpios.size()).append("\n");
        resumen.append("Categorías encontradas: ").append(categorias.size()).append("\n");
        resumen.append("Códigos asignados: ").append(codigos.size()).append("\n");
        resumen.append("Patrones encontrados: ").append(patronesEncontrados.size()).append("\n");
        return resumen.toString();
    }

    // Getters y Setters
    public int getIdAnalisis() {
        return idAnalisis;
    }

    public void setIdAnalisis(int idAnalisis) {
        this.idAnalisis = idAnalisis;
    }

    public String getNombreAnalisis() {
        return nombreAnalisis;
    }

    public void setNombreAnalisis(String nombreAnalisis) {
        this.nombreAnalisis = nombreAnalisis;
    }

    public List<Encuesta> getEncuestaAnalizada() {
        return encuestaAnalizada;
    }

    public void setEncuestaAnalizada(List<Encuesta> encuestaAnalizada) {
        this.encuestaAnalizada = encuestaAnalizada;
    }

    public Map<String, List<String>> getCodigos() {
        return codigos;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }
}