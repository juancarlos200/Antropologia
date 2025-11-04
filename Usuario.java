import java.util.List;

public class Usuario<Respuesta> implements IPersona {
    private int idUsuario;                     
    private String nombre;                     
    private int edadUsuario;                   
    private String genero;                     
    private String ocupacion;                  
    private List<Respuesta> respuesta;        

    public Usuario() {
    }

    public Usuario(String nombre, int edad) {
        this.nombre = nombre;
        this.edadUsuario = edad;
    }

    @Override
    public String obtenerInformacion() {
        return "Usuario: " + nombre + " (" + edadUsuario + " años)";
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public int getEdad() {
        return edadUsuario;
    }

    @Override
    public void setEdad(int edad) {
        this.edadUsuario = edad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public List<Respuesta> getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(List<Respuesta> respuesta) {
        this.respuesta = respuesta;
    }
}
