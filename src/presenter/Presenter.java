package presenter;

import java.util.ArrayList;
import java.util.List;
import modelo.DiseñadorRegiones;
import modelo.Grafo;
import modelo.Provincia;
import modelo.Region;
import vista.IVentanaPrincipal;
import modelo.Arista;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Presenter {
    private final IVentanaPrincipal vista;
    private Grafo grafo;

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

        try {
            grafo.agregarArista(p1, p2, peso);
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
            return;
        }

        vista.mostrarAristas(grafo.getAristas());
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

    public void cargarGrafoDesdeArchivo(File archivo) {
        // Reiniciamos el grafo para una carga limpia
        this.grafo = new Grafo();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    // Formato: Provincia1, Provincia2, Peso
                    Provincia p1 = new Provincia(partes[0].trim());
                    Provincia p2 = new Provincia(partes[1].trim());
                    double peso = Double.parseDouble(partes[2].trim());

                    // Las agregamos si no existen en el grafo
                    if (!grafo.getProvincias().contains(p1)) grafo.agregarProvincia(p1);
                    if (!grafo.getProvincias().contains(p2)) grafo.agregarProvincia(p2);

                    // Agregamos la arista
                    if (!grafo.existeArista(p1, p2)) {
                        grafo.agregarArista(p1, p2, peso);
                    }
                } else if (partes.length == 1) {
                    // Por si hay una provincia suelta sin conexiones (Formato: Provincia)
                    Provincia p = new Provincia(partes[0].trim());
                    if (!grafo.getProvincias().contains(p)) grafo.agregarProvincia(p);
                }
            }

            // Actualizamos la vista
            vista.mostrarProvincias(new java.util.ArrayList<>(grafo.getProvincias()));
            vista.mostrarAristas(grafo.getAristas());
            vista.mostrarError("¡Archivo cargado con éxito!"); // Usamos el mostrarError como un alert (o podés crear un mostrarMensaje)

        } catch (Exception e) {
            vista.mostrarError("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
    }


    public Grafo getGrafo() {
        return grafo;
    }
}