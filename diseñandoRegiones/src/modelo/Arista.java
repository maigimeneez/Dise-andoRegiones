package modelo;

public class Arista implements Comparable<Arista> {
    private final Provincia provincia1;
    private final Provincia provincia2;
    private final double peso;

    public Arista(Provincia provincia1, Provincia provincia2, double peso) {
        this.provincia1 = provincia1;
        this.provincia2 = provincia2;
        this.peso = peso;
    }

    public Provincia getProvincia1() {
        return provincia1;
    }

    public Provincia getProvincia2() {
        return provincia2;
    }

    public double getPeso() {
        return peso;
    }

    @Override
    public int compareTo(Arista otra) {
        return Double.compare(this.peso, otra.peso);
    }
}