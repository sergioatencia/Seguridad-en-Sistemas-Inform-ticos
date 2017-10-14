package enigma;

/**
 * @author Sergio Atencia Martínez
 */
class Rotor {

    //Tablas de cableado de los rotores I-III
    static String cabro1 = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
    static String cabro2 = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
    static String cabro3 = "BDFHJLCPRTXVZNYEIWGAKMUSQO";

    int posicion, muesca = -1;
    int[] cifrado = new int[26];
    int[] descifrado = new int[26];

    //Contructores
    public Rotor() {
        this.cifrado = null;
        this.muesca = 0;
    }

    public Rotor(String nombre, String clavija) {
        switch (nombre) {
            case "I":
                this.cifrado = crea(cabro1);
                this.muesca = aIndex('Q');
                break;
            case "II":
                this.cifrado = crea(cabro2);
                this.muesca = aIndex('E');
                break;
            default:
                this.cifrado = crea(cabro3);
                this.muesca = aIndex('V');
                break;
        }
        this.posicion = aIndex(clavija.charAt(0));
        this.descifrado = crearDescifrado();
    }

    //Métodos
    /**
     * Método para mover el rotor una posición hacia delante.
     */
    public void avanzar() {
        this.posicion = (this.posicion + 1) % 26;
    }

    public static int aIndex(char c) {
        return c - 'A';
    }

    static char aLetra(int p) {
        return (char) (p + 'A');
    }

    /**
     * Método que comprueba si coinciden la posición de la clavija y la de la
     * muesca.
     *
     * @return
     */
    boolean enMuesca() {
        return this.posicion == this.muesca;
    }

    /**
     * Método que construye el cableado del rotor.
     *
     * @param cableado
     * @return
     */
    public int[] crea(String cableado) {
        char[] s = cableado.toCharArray();
        int[] cif = new int[26];
        for (int i = 0; i < 26; i++) {
            cif[i] = aIndex(s[i]);
        }
        return cif;
    }

    /**
     * Método que devuelve la conversión de un entero (dentro del intervalo [0,
     * 25])a otro, según la permutación.
     *
     * @param p
     * @return
     */
    public int convierteAdelante(int p) {
        return ((cifrado[((p + this.posicion) % 26 + 26) % 26] - this.posicion) % 26 + 26) % 26;
    }

    /**
     * Método que devuelve la regresión de un entero (dentro del intervalo [0,
     * 25])a otro, según la permutación.
     *
     * @param e
     * @return
     */
    public int convierteAtras(int e) {
        return ((descifrado[((e + this.posicion) % 26 + 26) % 26] - this.posicion) % 26 + 26) % 26;
    }

    public int[] crearDescifrado() {
        int[] bcipher = new int[26];
        for (int i = 0; i < 26; i++) {
            bcipher[this.cifrado[i]] = i;
        }
        return bcipher;
    }
}
