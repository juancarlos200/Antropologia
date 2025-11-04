import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipalNueva ventana = new VentanaPrincipalNueva();
            ventana.setVisible(true);
        });
    }
}
