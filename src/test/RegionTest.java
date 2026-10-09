package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Provincia;
import modelo.Region;

public class RegionTest {

    private Region region;

    @BeforeEach
    public void setUp() {
        region = new Region();
    }

    @Test
    public void regionNuevaEstaVacia() {
        assertTrue(region.getProvincias().isEmpty());
    }

    @Test
    public void agregarProvinciaLaIncluyeEnLaRegion() {
        Provincia p = new Provincia("Salta");
        region.agregarProvincia(p);

        assertEquals(1, region.getProvincias().size());
        assertTrue(region.getProvincias().contains(p));
    }

    @Test
    public void agregarProvinciaRepetidaNoDuplica() {
        region.agregarProvincia(new Provincia("Salta"));
        region.agregarProvincia(new Provincia("Salta"));

        assertEquals(1, region.getProvincias().size());
    }

    @Test
    public void toStringIncluyeLosNombres() {
        region.agregarProvincia(new Provincia("Salta"));
        region.agregarProvincia(new Provincia("Jujuy"));

        String texto = region.toString();

        assertTrue(texto.contains("Salta"));
        assertTrue(texto.contains("Jujuy"));
    }
}