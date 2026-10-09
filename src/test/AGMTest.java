package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.AGM;
import modelo.Arista;
import modelo.Grafo;
import modelo.Provincia;

public class AGMTest {

    private Provincia a, b, c, d;

    @BeforeEach
    public void setUp() {
        a = new Provincia("A");
        b = new Provincia("B");
        c = new Provincia("C");
        d = new Provincia("D");
    }

    private double pesoTotal(Grafo g) {
        double suma = 0;
        for (Arista ar : g.getAristas()) {
            suma += ar.getPeso();
        }
        return suma;
    }

    @Test
    public void grafoVacioDaAgmVacio() {
        Grafo agm = AGM.calcularKruskal(new Grafo());
        assertTrue(agm.getProvincias().isEmpty());
        assertTrue(agm.getAristas().isEmpty());
    }

    @Test
    public void unaSolaProvinciaNoTieneAristas() {
        Grafo g = new Grafo();
        g.agregarProvincia(a);
        Grafo agm = AGM.calcularKruskal(g);
        assertEquals(1, agm.getProvincias().size());
        assertEquals(0, agm.getAristas().size());
    }

    @Test
    public void triangulo_descartaLaAristaMasPesada() {
        Grafo g = new Grafo();
        g.agregarArista(a, b, 1);
        g.agregarArista(b, c, 2);
        g.agregarArista(a, c, 3);

        Grafo agm = AGM.calcularKruskal(g);

        assertEquals(2, agm.getAristas().size());
        assertEquals(3.0, pesoTotal(agm), 0.0001);
        assertFalse(agm.existeArista(a, c));
    }

    @Test
    public void agmTieneNMenosUnaAristas() {
        Grafo g = new Grafo();
        g.agregarArista(a, b, 4);
        g.agregarArista(b, c, 1);
        g.agregarArista(c, d, 2);
        g.agregarArista(d, a, 3);
        g.agregarArista(a, c, 5);

        Grafo agm = AGM.calcularKruskal(g);

        assertEquals(4, agm.getProvincias().size());
        assertEquals(3, agm.getAristas().size());
        assertTrue(agm.esConexo());
        assertEquals(6.0, pesoTotal(agm), 0.0001); // 1 + 2 + 3
    }

    @Test
    public void pesosRepetidosIgualDaArbolDePesoMinimo() {
        Grafo g = new Grafo();
        g.agregarArista(a, b, 1);
        g.agregarArista(b, c, 1);
        g.agregarArista(a, c, 1);

        Grafo agm = AGM.calcularKruskal(g);

        assertEquals(2, agm.getAristas().size());
        assertEquals(2.0, pesoTotal(agm), 0.0001);
    }

    @Test
    public void grafoDesconexoDaBosque() {
        Grafo g = new Grafo();
        g.agregarArista(a, b, 1);
        g.agregarArista(c, d, 2);

        Grafo agm = AGM.calcularKruskal(g);

        assertEquals(2, agm.getAristas().size());
        assertFalse(agm.esConexo());
    }

    @Test
    public void noModificaElGrafoOriginal() {
        Grafo g = new Grafo();
        g.agregarArista(a, b, 1);
        g.agregarArista(b, c, 2);
        g.agregarArista(a, c, 3);

        AGM.calcularKruskal(g);

        assertEquals(3, g.getAristas().size());
    }
}