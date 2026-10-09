package vista;

import java.util.List;

import modelo.Arista;
import modelo.Provincia;
import modelo.Region;
import modelo.Arista;


public interface IVentanaPrincipal {
    void mostrarProvincias(List<Provincia> provincias);
    void mostrarRegiones(List<Region> regiones);
    void mostrarError(String mensaje);
    void limpiarCampos();
    void mostrarAristas(List<Arista> aristas);
}