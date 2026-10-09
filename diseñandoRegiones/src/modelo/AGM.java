package modelo;

import java.util.*;

public class AGM {

    public static Grafo calcularKruskal(Grafo grafo) {
        Grafo agm = new Grafo();
        for (Provincia p : grafo.getProvincias()) {
            agm.agregarProvincia(p);
        }

        List<Arista> aristasOrdenadas = new ArrayList<>(grafo.getAristas());
        Collections.sort(aristasOrdenadas);

        UnionFind uf = new UnionFind(grafo.getProvincias());

        for (Arista arista : aristasOrdenadas) {
            Provincia p1 = arista.getProvincia1();
            Provincia p2 = arista.getProvincia2();

            if (uf.find(p1) != uf.find(p2)) {
                uf.union(p1, p2);
                agm.agregarArista(p1, p2, arista.getPeso());
            }
        }

        return agm;
    }

    // Estructura Auxiliar Union-Find para Kruskal
    private static class UnionFind {
        private final Map<Provincia, Provincia> padre = new HashMap<>();

        public UnionFind(Set<Provincia> provincias) {
            for (Provincia p : provincias) {
                padre.put(p, p);
            }
        }

        public Provincia find(Provincia p) {
            if (padre.get(p).equals(p)) {
                return p;
            }
            Provincia raiz = find(padre.get(p));
            padre.put(p, raiz); // Compresión de caminos
            return raiz;
        }

        public void union(Provincia p1, Provincia p2) {
            Provincia raiz1 = find(p1);
            Provincia raiz2 = find(p2);
            if (!raiz1.equals(raiz2)) {
                padre.put(raiz1, raiz2);
            }
        }
    }
}