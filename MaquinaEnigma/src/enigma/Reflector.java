package enigma;

/**
 * @author Sergio Atencia Martínez
 */
class Reflector extends Rotor {

    //Atributos
    static String cabref = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
    int[] reflexion;

    //Constructores
    public Reflector() {
        this.reflexion = crea(cabref);
    }

    //Métodos
    @Override
    public int convierteAdelante(int p) {
        return ((reflexion[((p) % 26 + 26) % 26]) % 26 + 26) % 26;
    }
}
