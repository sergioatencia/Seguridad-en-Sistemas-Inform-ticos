package diffiehellman;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 *
 * @author Sergio Atencia Martínez
 */
public class DiffieHellman {

    /**
     * Método que genera una clave secreta particular. Opcionalmente, permite el
     * establecimiento de una clave secreta compartida con un destinatario.
     *
     * @param entrada es el objeto Scanner que permite meter datos por teclado.
     */
    public static void generarclave(Scanner entrada) {
        BigInteger p, n, x, z1, y, z2, r1, r2;

        System.out.println("Alice, introduzca un número primo, un generador y un exponente: ");
        p = entrada.nextBigInteger();       // p es el número primo.
        n = entrada.nextBigInteger();       // n es el generador.
        x = entrada.nextBigInteger();       // x es nuestro exponente.

        if (x.intValue() > n.intValue()) {      // Comprueba que el exponente no es mayor que el generador.
            System.err.println("¡¡¡ERROR!!! Exponente mayor que el generador.");
            System.exit(0);
        }
        z1 = potenciaDV(p, x).mod(n);       // Calcula nuestra clave particular Z1 y la muestra por pantalla.
        System.out.println("Z1 = " + z1 + "\n");
        System.out.print("¿Establecer clave con Bob? (y/n): ");
        if (entrada.next().equals("y")) {
            System.out.print("Bob, introduzca un exponente: ");
            y = entrada.nextBigInteger();   // y es el exponente del destinatario.

            z2 = potenciaDV(p, y).mod(n);   // Calcula la clave particular Z2 del destinatario, pero NO la muestra.
            //Solución normal 1
            r1 = potenciaDV(z2, x).mod(n);  // Calculamos la clave común en ambos lados.
            r2 = potenciaDV(z1, y).mod(n);
            if (r1.compareTo(r2) == 0) {    // Comprobamos que la clave obtenida en cada lado coincide.
                System.out.println("¡¡Fantástico!! Establecida la clave compartida de sesión: " + r1 + ".");
            } else {
                System.out.println("¡¡D'oh!! Imposible establecer la clave.");
            }
        }
    }

    /**
     * Método que realiza la operación x^y como multiplicaciones iterativas,
     * basándose en el algoritmo "Divide y vencerás".
     *
     * @param x es la base de la potencia.
     * @param y es el exponente de la potencia.
     * @return devuelve el resultado de multiplicar x "y" veces.
     *
     * @see http://www.geocities.ws/luisja80/esp/potencia
     */
    public static BigInteger potenciaDV(BigInteger x, BigInteger y) {
        BigInteger aux = BigInteger.ONE;

        while (y.intValue() > 0) {
            if ((y.intValue() & 1) != 0) {
                aux = aux.multiply(x);
            }
            x = x.multiply(x);
            y = y.divide(BigInteger.valueOf(2));
        }
        return aux;
    }

    /**
     * Método que realiza un ataque de fuerza bruta sobre el algoritmo Diffie-
     * Hellman.
     *
     * @param entrada es el objeto Scanner que permite meter datos por teclado.
     */
    private static void fuerzaBruta(Scanner entrada) {
        BigInteger p, n, x, y, z1, z2, z;
        BigInteger aux1, aux2;
        BigInteger r1 = BigInteger.valueOf(0), r2 = BigInteger.ZERO;
        boolean exito = false;          // exito es un flag que indica si el ataque ha tenido éxito o no.

        System.out.println("Atacante, introduzca el número primo, el generador y los valores z1, z2 hackeados: ");
        p = entrada.nextBigInteger();           // p es el número primo.
        n = entrada.nextBigInteger();           // n es el generador.
        z1 = entrada.nextBigInteger();          // z1 y z2 son las claves particulares.
        z2 = entrada.nextBigInteger();

        for (int i = 0; i < n.intValue(); i++) {
            aux1 = BigInteger.ZERO;
            x = BigInteger.valueOf(i);
            aux1 = potenciaDV(p, x).mod(n);
            if (aux1.equals(z1)) {
                //System.out.println("Z1 = " + z1);
                for (int j = 0; j < n.intValue(); j++) {
                    aux2 = BigInteger.ZERO;
                    y = BigInteger.valueOf(j);
                    aux2 = potenciaDV(p, y).mod(n);
                    if (aux2.equals(z2)) {
                        //System.out.println("Z2 = " + z2);
                        System.out.println("¡¡Encontrados x = " + x + ", y = " + y + "!!");

                        r1 = potenciaDV(aux2, x).mod(n);
                        r2 = potenciaDV(aux1, y).mod(n);
                        if (r1.compareTo(r2) == 0) {
                            System.out.println("¡¡Genial!! Clave común descubierta: " + r1 + ".\n");
                        }
                        exito = true;
                        break;
                    }
                }
                break;
            }
        }
        if (exito == false) {
            System.out.println("¡¡Qué mal!! Intento de ataque fallido.\n");
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner entrada = new Scanner(System.in);
        String opcion = null;

        System.out.println("****** DIFFIE-HELLMAN ******");
        System.out.println("\t1) Establecer clave secreta particular.\n\t2) Reventar clave por fuerza bruta.");
        do {
            System.out.print("Seleccione una opción (0 para salir): ");
            opcion = entrada.next();
            System.out.println();
            switch (opcion) {
                case "1":
                    generarclave(entrada);
                    break;
                case "2":
                    fuerzaBruta(entrada);
                    break;
                default:
                    break;
            }
        } while (!opcion.equals("0"));
        System.out.println("\nCerrando programa...");
    }
}
