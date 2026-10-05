package presenter;

import java.util.ArrayList;
import java.util.List;
import modelo.DiseñadorRegiones;
import modelo.Grafo;
import modelo.Provincia;
import modelo.Region;
import vista.IVentanaPrincipal;

public class Presenter {
    private final IVentanaPrincipal vista;
    private final Grafo grafo;

    public Presenter(IVentanaPrincipal vista) {
        this.vista = vista;
        this.grafo = new Grafo();
    }

    public void agregarProvincia(String nombreProvincia) {
        if (nombreProvincia == null || nombreProvincia.trim().isEmpty()) {
            vista.mostrarError("El nombre de la provincia no puede estar vacío.");
            return;
        }

        String nombreLimpio = nombreProvincia.trim();
        Provincia provincia = new Provincia(nombreLimpio);

        if (grafo.getProvincias().contains(provincia)) {
            vista.mostrarError("La provincia '" + nombreLimpio + "' ya existe.");
            return;
        }

        grafo.agregarProvincia(provincia);
        vista.mostrarProvincias(new ArrayList<>(grafo.getProvincias()));
        vista.limpiarCampos();
    }

    public void agregarSimilaridad(Provincia p1, Provincia p2, double peso) {
        if (p1 == null || p2 == null) {
            vista.mostrarError("Debe seleccionar dos provincias.");
            return;
        }

        if (p1.equals(p2)) {
            vista.mostrarError("No se puede agregar una arista entre la misma provincia.");
            return;
        }

        if (peso <= 0) {
            vista.mostrarError("El peso o similaridad debe ser un valor positivo.");
            return;
        }

        grafo.agregarArista(p1, p2, peso);
        vista.limpiarCampos();
    }

    public void calcularRegiones(int k) {
        int totalProvincias = grafo.getProvincias().size();

        if (totalProvincias == 0) {
            vista.mostrarError("Debe cargar al menos una provincia para generar regiones.");
            return;
        }

        if (k <= 0 || k > totalProvincias) {
            vista.mostrarError("El número de regiones (k) debe estar entre 1 y " + totalProvincias + ".");
            return;
        }

        try {
            List<Region> regiones = DiseñadorRegiones.generarRegiones(grafo, k);
            vista.mostrarRegiones(regiones);
        } catch (Exception e) {
            vista.mostrarError("Ocurrió un error al calcular las regiones: " + e.getMessage());
        }
    }

    public Grafo getGrafo() {
        return grafo;
    }
}