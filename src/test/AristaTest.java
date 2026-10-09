package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modelo.Arista;
import modelo.Provincia;

public class AristaTest {

    private final Provincia a = new Provincia("A");
    private final Provincia b = new Provincia("B");

    @Test
    public void getteresDevuelvenLosValoresDelConstructor() {
        Arista arista = new Arista(a, b, 7.5);

        assertEquals(a, arista.getProvincia1());
        assertEquals(b, arista.getProvincia2());
        assertEquals(7.5, arista.getPeso(), 0.0001);
    }

    @Test
    public void compareToOrdenaPorPeso() {
        Arista liviana = new Arista(a, b, 1);
        Arista pesada = new Arista(a, b, 5);

        assertTrue(liviana.compareTo(pesada) < 0);
        assertTrue(pesada.compareTo(liviana) > 0);
    }

    @Test
    public void compareToConMismoPesoEsCero() {
        Arista x = new Arista(a, b, 3);
        Arista y = new Arista(b, a, 3);

        assertEquals(0, x.compareTo(y));
    }
}