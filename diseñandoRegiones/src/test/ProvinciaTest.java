package test;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import modelo.Provincia;


public class ProvinciaTest {

	@Test
	public void testObtenerNombre() {
		Provincia p1 = new Provincia("Chubut");
		assertEquals("Chubut", p1.getNombre(), "El metodo getNombre debe devolver el nombre exacto con el que se creo la provincia");
	}
		
	
	
	
    @Test
    public void testIgualdadProvinciasMismoNombre() {
        Provincia p1 = new Provincia("Mendoza");
        Provincia p2 = new Provincia("Mendoza");
        
        assertTrue(p1.equals(p2), "Dos provincias con el mismo nombre deben ser consideradas iguales");
        assertEquals(p1.hashCode(), p2.hashCode(), "El hashCode debe ser identico para provincias iguales");
    }

    @Test
    public void testDesigualdadProvinciasDistintoNombre() {
        Provincia p1 = new Provincia("Formosa");
        Provincia p2 = new Provincia("Jujuy");
        
        assertFalse(p1.equals(p2), "Provincias con nombres distintos no deben ser consideradas iguales");
    }
    
    @Test
    public void testIgualdadConNuloUObjeto() {
        Provincia p1 = new Provincia("Cordoba");
        
        assertFalse(p1.equals(null), "Una provincia no puede ser igual a null");
        assertFalse(p1.equals("Cordoba"), "Una provincia no debe ser igual a un objeto de otro tipo");
    }
}
