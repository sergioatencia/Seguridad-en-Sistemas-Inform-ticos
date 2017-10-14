package enigma;

/**
 * @author Sergio Atencia Martínez
 */
public class Maquina {

    //Atributos
    Rotor iz, dr, centro;
    Reflector reflector;

    //Constructores
    public Maquina(String clavijaI, String clavijaC, String clavijaR) {
        this.iz = new Rotor("I", clavijaI);
        this.centro = new Rotor("II", clavijaC);
        this.dr = new Rotor("III", clavijaR);
        this.reflector = new Reflector();
    }

    //Métodos
    /**
     * Método que codifica/descodifica un mensaje pasado como parámetro.
     *
     * @param msg: mensaje
     * @return
     */
    public String convertir(String msg) {
        char[] msgChars = msg.toCharArray();
        String resultado = "";
        for (char c : msgChars) {
            resultado += convierteCaracter(c);
        }
        return resultado;
    }

    /**
     * Método que devuelve la codificación/descodificación de un caracter pasado
     * como parámetro.
     *
     * @param c
     * @return
     */
    public char convierteCaracter(char c) {
        moverRotores();
        int charIndex = Rotor.aIndex(c);
        int salida;
        salida = dr.convierteAdelante(charIndex);
        salida = centro.convierteAdelante(salida);
        salida = iz.convierteAdelante(salida);
        salida = reflector.convierteAdelante(salida);
        salida = iz.convierteAtras(salida);
        salida = centro.convierteAtras(salida);
        salida = dr.convierteAtras(salida);
        return Rotor.aLetra(salida);
    }

    /**
     * Método que mueve los rotores según el estado de éstos.
     */
    public void moverRotores() {
        boolean mueveIz = false;
        boolean mueveCentro = false;
        boolean mueveDr = true;

        if (iz.enMuesca()) {
        }
        if (centro.enMuesca()) {
            mueveCentro = true;
            mueveIz = true;
        }
        if (dr.enMuesca()) {
            mueveCentro = true;
            mueveDr = true;
        }
        if (mueveIz) {
            iz.avanzar();
        }
        if (mueveDr) {
            dr.avanzar();
        }
        if (mueveCentro) {
            centro.avanzar();
        }
    }
}
