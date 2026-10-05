package vista;

import java.util.List;
import modelo.Provincia;
import modelo.Region;

public interface IVentanaPrincipal {
    void mostrarProvincias(List<Provincia> provincias);
    void mostrarRegiones(List<Region> regiones);
    void mostrarError(String mensaje);
    void limpiarCampos();
}