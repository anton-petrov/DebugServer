package mygps.server.debug;

import java.io.*;
import java.net.*;

public class DebugServer {

    private static int PORT = 11111;

    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(PORT);
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("Debug Server started! Waiting for incoming data...");

            while(true) {
                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = 0;
                }
                socket.receive(packet);
                byte[] data = packet.getData();
                String text = new String(data, 0, data.length);

                System.out.print("[" + packet.getAddress().getHostAddress() + "] : " + text);
            }

        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
