import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;

public class Servidor {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        int numeroAleatorio = (int)(Math.random() * 100)+1;
        try {
            // 1 - Crear DatagramSocket y le indicamos el puerto
            System.out.println("(Servidor) Creando socket...");
            socket = new DatagramSocket(49201);

            while (true) {
                // 2 - Crear array de bytes que actuará de buffer
                byte[] buffer = new byte[64];

                // 3 - Creación de datagrama con la clase DatagramPacket
                DatagramPacket datagramaEntrada = new DatagramPacket(buffer, buffer.length);

                // 4 - Recepción del datagrama mediante el método receive
                System.out.println("(Servidor) Esperando peticiones...");
                socket.receive(datagramaEntrada);

                new GestorProcesos(socket, datagramaEntrada, numeroAleatorio).start();

            }
        } catch (SocketException e) {
            System.out.println("Error al crear el Socket");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error al recibir el paquete");
            e.printStackTrace();
        }
    }
}