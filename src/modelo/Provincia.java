package modelo;

import java.util.Objects;


public class Provincia {
	private String nombre;
	
	public Provincia (String nombre) {
		this.nombre = nombre;
	}
	public String getNombre(){
		 return nombre;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			 return true;
	    }

	    if (!(o instanceof Provincia)) {
	    	 return false;
	    }

	    Provincia otra = (Provincia) o;

	    return Objects.equals(nombre, otra.nombre);
	}

	 @Override
	 public int hashCode() {
	    return Objects.hash(nombre);
	 }
	    
	 @Override
	 public String toString() {
		 return this.nombre;
	 }

}
