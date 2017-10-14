package enigma;

import java.util.Scanner;

/**
 * @author Sergio Atencia Martínez
 */
public class Main {

    /**
     * Método para mostrar por pantalla el mensaje cifrado.
     *
     * @param msg: mensaje generado por el cifrado.
     */
    static void imprimirMensaje(String msg) {
        System.out.print("\nSu mensaje cifrado: ");
        for (char c : msg.toCharArray()) {
            System.out.print(c);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Maquina maquina;
        Scanner entrada = new Scanner(System.in);
        String mensaje, clavijaI, clavijaR, clavijaC;

        /*Solicitamos por teclado el mensaje que queremos cifrar/descifrar y la 
        configuración de las clavijas de intercambio de los rotores.*/
        System.out.println("Configure la clavija de intercambio para la muesca del rotor I: ");
        clavijaI = entrada.nextLine().toUpperCase();
        System.out.println("Configure la clavija de intercambio para la muesca del rotor II: ");
        clavijaC = entrada.nextLine().toUpperCase();
        System.out.println("Configure la clavija de intercambio para la muesca del rotor III: ");
        clavijaR = entrada.nextLine().toUpperCase();
        System.out.println("Introduzca el mensaje a cifrar/descifrar: ");
        mensaje = entrada.nextLine().toUpperCase();

        /*Creamos un objeto Máquina con los rotores I, II y III con la configu-
        ración anterior y procedemos con el cifrado/descifrado.*/
        maquina = new Maquina(clavijaI, clavijaC, clavijaR);
        imprimirMensaje(maquina.convertir(mensaje));
    }
}
