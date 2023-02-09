import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class GestorProcesos extends Thread {
    DatagramSocket socket;
    DatagramPacket datagramaEntrada;

    int numAleatorio;

    public GestorProcesos(DatagramSocket socket, DatagramPacket datagramaEntrada, int numAleatorio) {
        super();
        this.socket = socket;
        this.datagramaEntrada = datagramaEntrada;
        this.numAleatorio = numAleatorio;
    }

    @Override
    public void run() {
        realizarProceso();
    }

    public void realizarProceso() {
        String mensaje = "";
        // Recepción de mensaje del cliente.
        int mensajeRecibido = Integer.parseInt(new String(datagramaEntrada.getData()).trim());
        System.out.println("(Servidor): Mensaje recibido: " + mensajeRecibido);
        //Comprobación número.

        if(mensajeRecibido == numAleatorio){
            mensaje = "¡¡Has acertado el número!!";
        } else if (mensajeRecibido > numAleatorio) {
            mensaje = "Te has pasado";
        } else if (mensajeRecibido < numAleatorio) {
            mensaje = "Te has quedado corto";
        }

        // 5 - Generación y envío de la respuesta
        System.out.println("(Servidor): Enviando datagrama...");
        byte[] mensajeEnviado = mensaje.getBytes();
        DatagramPacket packetSalida = new DatagramPacket(mensajeEnviado, mensajeEnviado.length,
                datagramaEntrada.getAddress(), datagramaEntrada.getPort());
        try {
            socket.send(packetSalida);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
