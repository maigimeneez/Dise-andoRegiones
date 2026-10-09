package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import modelo.Grafo;
import modelo.Provincia;

import org.junit.jupiter.api.BeforeEach;




public class GrafoTest {

	private Grafo grafo;

    @BeforeEach
    public void setUp() {
        grafo = new Grafo();
    }


    @Test
    public void agregarProvinciaAumentaElTamano() {
        Provincia p1 = new Provincia("Salta");
        grafo.agregarProvincia(p1);

        assertEquals(1, grafo.getProvincias().size(), "El grafo debe tener exactamente uuna provincia");
        assertTrue(grafo.getProvincias().contains(p1), "El grafo debe contener laprovincia Salta");
    }

    @Test
    public void agregarProvinciaDuplicadaNoSuma() {
        Provincia p1 = new Provincia("Jujuy");
        Provincia p2 = new Provincia("Jujuy");

        grafo.agregarProvincia(p1);
        grafo.agregarProvincia(p2);

        assertEquals(1, grafo.getProvincias().size(), "El grafo no debe duplicar provincias iguales");
    }

    @Test
    public void agregarAristaConectaLasProvincias() {
        Provincia p1 = new Provincia("San Juan");
        Provincia p2 = new Provincia("San Luis");

        grafo.agregarArista(p1, p2, 12);

        assertEquals(2, grafo.getProvincias().size(), "Se deberiam agregar las dos provincias al conectarse la arista");
        assertEquals(1, grafo.getAristas().size(), "Deberia haber una sola arista en total");

        assertEquals(1, grafo.getVecinos(p1).size(), "San Juan debe tener un solo vecino");
        assertEquals(1, grafo.getVecinos(p2).size(), "San Luis debe tener un solo vecino");
    }

    @Test
    public void agregarAristaDuplicadaLanzaExcepcion() {
        Provincia p1 = new Provincia("A");
        Provincia p2 = new Provincia("B");
        grafo.agregarArista(p1, p2, 1);

        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista(p2, p1, 5));
    }

    @Test
    public void agregarAutoAristaLanzaExcepcion() {
        Provincia p1 = new Provincia("A");
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista(p1, new Provincia("A"), 1));
    }

    @Test
    public void pesoInvalidoLanzaExcepcion() {
        Provincia p1 = new Provincia("A");
        Provincia p2 = new Provincia("B");
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista(p1, p2, 0));
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista(p1, p2, -3));
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista(p1, p2, Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista(p1, p2, Double.POSITIVE_INFINITY));
    }

    @Test
    public void vecinosDeProvinciaInexistenteEsVacio() {
        assertTrue(grafo.getVecinos(new Provincia("Nada")).isEmpty());
    }

    @Test
    public void coleccionesDevueltasNoSonModificables() {
        grafo.agregarProvincia(new Provincia("A"));
        assertThrows(UnsupportedOperationException.class, () -> grafo.getProvincias().clear());
    }

    @Test
    public void esConexo() {
        Provincia a = new Provincia("A");
        Provincia b = new Provincia("B");
        Provincia c = new Provincia("C");
        grafo.agregarArista(a, b, 1);
        assertTrue(grafo.esConexo());

        grafo.agregarProvincia(c);
        assertFalse(grafo.esConexo());

        grafo.agregarArista(b, c, 2);
        assertTrue(grafo.esConexo());
    }

}
