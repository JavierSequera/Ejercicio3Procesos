import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        String mensaje = "";
        int puertoServidor = 49201;
        Scanner sc = new Scanner(System.in);
        String nombre;
        try {
            // 1 - Obtención de la dirección del servidor usando el métod getByName de
            // InetAddress
            System.out.println("(Cliente): Estableciendo parámetros de conexión...");
            InetAddress direccion = InetAddress.getLocalHost();

            // 2 - Creación del socket UDP
            System.out.println("(Cliente): Creando el socket...");
            socket = new DatagramSocket();

            while (!mensaje.equals("¡¡Has acertado el número!!")){
                // 3 - Generación del datagrama
                System.out.println("Escriba el número");
                nombre = sc.nextLine();

                byte[] bufferSalida = nombre.getBytes();
                DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccion,
                        puertoServidor);

                // 4 - Envío del datagrama a través de send
                System.out.println("(Cliente) Enviando datagrama...");
                socket.send(paqueteSalida);

                // 5 - Recepción de la respuesta
                System.out.println("(Cliente) Recibiendo respuesta...");
                byte[] bufferEntrada = new byte[64];
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length, direccion,
                        puertoServidor);
                socket.receive(paqueteEntrada);
                mensaje = new String(bufferEntrada).trim();
                System.out.println("Mensaje recibido: " + new String(bufferEntrada).trim());
            }

            // 6 - Cierre del socket
            System.out.println("(Cliente): Cerrando conexión...");
            socket.close();
            System.out.println("(Cliente): Conexión cerrada.");

        } catch (SocketException e) {
            System.err.println("Error al conectar con el servidor.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("No se ha podido enviar o recibir el paquete");
            e.printStackTrace();
        }
    }
}
