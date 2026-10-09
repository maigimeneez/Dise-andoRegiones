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
        if (provincia == null) {
            throw new IllegalArgumentException("La provincia no puede ser null.");
        }
        adyacencias.putIfAbsent(provincia, new ArrayList<>());
    }

    public void agregarArista(Provincia p1, Provincia p2, double peso) {
        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("Las provincias no pueden ser null.");
        }
        if (p1.equals(p2)) {
            throw new IllegalArgumentException("No se puede conectar una provincia consigo misma.");
        }
        if (!Double.isFinite(peso) || peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser un número finito positivo.");
        }
        if (existeArista(p1, p2)) {
            throw new IllegalArgumentException("Ya existe una arista entre " + p1 + " y " + p2 + ".");
        }

        agregarProvincia(p1);
        agregarProvincia(p2);

        Arista arista = new Arista(p1, p2, peso);
        adyacencias.get(p1).add(arista);
        adyacencias.get(p2).add(arista);
        aristas.add(arista);
    }

    public boolean existeArista(Provincia p1, Provincia p2) {
        for (Arista a : getVecinos(p1)) {
            if ((a.getProvincia1().equals(p1) && a.getProvincia2().equals(p2))
                    || (a.getProvincia1().equals(p2) && a.getProvincia2().equals(p1))) {
                return true;
            }
        }
        return false;
    }

    public Set<Provincia> getProvincias() {
        return Collections.unmodifiableSet(adyacencias.keySet());
    }

    public List<Arista> getAristas() {
        return Collections.unmodifiableList(aristas);
    }

    public List<Arista> getVecinos(Provincia provincia) {
        List<Arista> vecinos = adyacencias.get(provincia);
        if (vecinos == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(vecinos);
    }

    public boolean esConexo() {
        if (adyacencias.isEmpty()) {
            return true;
        }
        Provincia origen = adyacencias.keySet().iterator().next();
        Set<Provincia> visitados = new HashSet<>();
        Deque<Provincia> pendientes = new ArrayDeque<>();
        pendientes.add(origen);
        visitados.add(origen);

        while (!pendientes.isEmpty()) {
            Provincia actual = pendientes.poll();
            for (Arista a : adyacencias.get(actual)) {
                Provincia vecino = a.getProvincia1().equals(actual) ? a.getProvincia2() : a.getProvincia1();
                if (visitados.add(vecino)) {
                    pendientes.add(vecino);
                }
            }
        }
        return visitados.size() == adyacencias.size();
    }
}