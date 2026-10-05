package modelo;

import java.util.*;

public class DiseñadorRegiones {

    public static List<Region> generarRegiones(Grafo grafo, int k) {
        if (k <= 0 || k > grafo.getProvincias().size()) {
            throw new IllegalArgumentException("El número de regiones k debe ser entre 1 y el número total de provincias.");
        }

        Grafo agm = AGM.calcularKruskal(grafo);

        List<Arista> aristasAGM = new ArrayList<>(agm.getAristas());
        aristasAGM.sort(Collections.reverseOrder());

        Grafo bosquecillo = new Grafo();
        for (Provincia p : agm.getProvincias()) {
            bosquecillo.agregarProvincia(p);
        }

        for (int i = k - 1; i < aristasAGM.size(); i++) {
            Arista a = aristasAGM.get(i);
            bosquecillo.agregarArista(a.getProvincia1(), a.getProvincia2(), a.getPeso());
        }

        return obtenerComponentesConexas(bosquecillo);
    }

    private static List<Region> obtenerComponentesConexas(Grafo grafo) {
        List<Region> regiones = new ArrayList<>();
        Set<Provincia> visitados = new HashSet<>();

        for (Provincia provincia : grafo.getProvincias()) {
            if (!visitados.contains(provincia)) {
                Region region = new Region();
                bfs(grafo, provincia, visitados, region);
                regiones.add(region);
            }
        }

        return regiones;
    }

    private static void bfs(Grafo grafo, Provincia origen, Set<Provincia> visitados, Region region) {
        Queue<Provincia> cola = new LinkedList<>();
        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            Provincia actual = cola.poll();
            region.agregarProvincia(actual);

            for (Arista arista : grafo.getVecinos(actual)) {
                Provincia vecino = arista.getProvincia1().equals(actual) ? arista.getProvincia2() : arista.getProvincia1();
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }
    }
}