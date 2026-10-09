package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.DiseñadorRegiones;
import modelo.Grafo;
import modelo.Provincia;
import modelo.Region;

public class DiseñadorRegionesTest {

    private Provincia a, b, c, d;
    private Grafo cadena; // A -1- B -5- C -1- D

    @BeforeEach
    public void setUp() {
        a = new Provincia("A");
        b = new Provincia("B");
        c = new Provincia("C");
        d = new Provincia("D");

        cadena = new Grafo();
        cadena.agregarArista(a, b, 1);
        cadena.agregarArista(b, c, 5);
        cadena.agregarArista(c, d, 1);
    }

    private Region regionDe(List<Region> regiones, Provincia p) {
        for (Region r : regiones) {
            if (r.getProvincias().contains(p)) {
                return r;
            }
        }
        return null;
    }

    @Test
    public void kIgualUnoDevuelveUnaRegionConTodo() {
        List<Region> regiones = DiseñadorRegiones.generarRegiones(cadena, 1);

        assertEquals(1, regiones.size());
        assertEquals(4, regiones.get(0).getProvincias().size());
    }

    @Test
    public void kIgualNDevuelveUnaRegionPorProvincia() {
        List<Region> regiones = DiseñadorRegiones.generarRegiones(cadena, 4);

        assertEquals(4, regiones.size());
        for (Region r : regiones) {
            assertEquals(1, r.getProvincias().size());
        }
    }

    @Test
    public void kIntermedioCortaLaAristaMasPesada() {
        List<Region> regiones = DiseñadorRegiones.generarRegiones(cadena, 2);

        assertEquals(2, regiones.size());
        assertSame(regionDe(regiones, a), regionDe(regiones, b));
        assertSame(regionDe(regiones, c), regionDe(regiones, d));
        assertNotSame(regionDe(regiones, a), regionDe(regiones, c));
    }

    @Test
    public void todasLasProvinciasQuedanAsignadasExactamenteUnaVez() {
        List<Region> regiones = DiseñadorRegiones.generarRegiones(cadena, 3);

        int total = 0;
        for (Region r : regiones) {
            total += r.getProvincias().size();
        }
        assertEquals(4, total);
    }

    @Test
    public void usaElAgmYNoTodasLasAristas() {
        // Triángulo: la arista A-C (9) no entra en el AGM, así que con k=2 se corta B-C (2)
        Grafo g = new Grafo();
        g.agregarArista(a, b, 1);
        g.agregarArista(b, c, 2);
        g.agregarArista(a, c, 9);

        List<Region> regiones = DiseñadorRegiones.generarRegiones(g, 2);

        assertEquals(2, regiones.size());
        assertSame(regionDe(regiones, a), regionDe(regiones, b));
        assertNotSame(regionDe(regiones, b), regionDe(regiones, c));
    }

    @Test
    public void kCeroLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> DiseñadorRegiones.generarRegiones(cadena, 0));
    }

    @Test
    public void kMayorQueProvinciasLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> DiseñadorRegiones.generarRegiones(cadena, 5));
    }

    @Test
    public void grafoDesconexoLanzaExcepcion() {
        Grafo g = new Grafo();
        g.agregarArista(a, b, 1);
        g.agregarArista(c, d, 2);

        assertThrows(IllegalArgumentException.class, () -> DiseñadorRegiones.generarRegiones(g, 2));
    }
}