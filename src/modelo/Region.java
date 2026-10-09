package modelo;

import java.util.HashSet;
import java.util.Set;

public class Region {
    private final Set<Provincia> provincias;

    public Region() {
        this.provincias = new HashSet<>();
    }

    public void agregarProvincia(Provincia p) {
        provincias.add(p);
    }

    public Set<Provincia> getProvincias() {
        return provincias;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Región: ");
        for (Provincia p : provincias) {
            sb.append(p.getNombre()).append(", ");
        }
        return sb.length() > 8 ? sb.substring(0, sb.length() - 2) : sb.toString();
    }
}