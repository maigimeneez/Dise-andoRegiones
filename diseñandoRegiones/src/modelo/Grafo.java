package modelo;

import java.util.*;

public class Grafo {
    private final Map<Provincia, List<Arista>> adyacencias;
    private final List<Arista> aristas;

    public Grafo() {
        adyacencias = new HashMap<>();
        aristas = new ArrayList<>();
    }

    public void agregarProvincia(Provincia provincia) {
        if (!adyacencias.containsKey(provincia)) {
            adyacencias.put(provincia, new ArrayList<>());
        }
    }

    public void agregarArista(Provincia p1, Provincia p2, double peso) {
        agregarProvincia(p1);
        agregarProvincia(p2);
        
        Arista arista = new Arista(p1, p2, peso);
        adyacencias.get(p1).add(arista);
        adyacencias.get(p2).add(arista);
        aristas.add(arista);
    }

    public Set<Provincia> getProvincias() {
        return adyacencias.keySet();
    }

    public List<Arista> getAristas() {
        return new ArrayList<>(aristas);
    }

    public List<Arista> getVecinos(Provincia provincia) {
        return adyacencias.getOrDefault(provincia, new ArrayList<>());
    }
}