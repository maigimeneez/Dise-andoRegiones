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
	
	
	
	
	
}
