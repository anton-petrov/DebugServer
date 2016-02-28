package mygps.server.debug;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DebugServer {

    private static int PORT = 11111;

    private static String getTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    public static void main(String[] args) {

        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(PORT);
            byte[] buffer = new byte[Short.MAX_VALUE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("Debug Server started at " + getTimeString() + "\nWaiting for incoming data...");

            while(true) {
                StringBuilder console = new StringBuilder();

                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = 0;
                }

                socket.receive(packet);

                String text = new String(packet.getData(), 0, packet.getLength());

                console.append(String.format("[%s, %s] %s", getTimeString(), packet.getAddress().getHostAddress(), text));
                System.out.println(console.toString());
            }

        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            socket.close();
        }
    }
}
