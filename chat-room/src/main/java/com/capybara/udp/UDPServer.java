package com.capybara.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPServer {
    private static final Logger LOGGER = Logger.getLogger(UDPServer.class.getName());
    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[256];

    public UDPServer(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public void receiveThenSend() {
        while(true) {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);

                String messageFromClient = new String(datagramPacket.getData(),0, datagramPacket.getLength());
                LOGGER.log(Level.INFO,"Message from client: " + messageFromClient);

                if (messageFromClient.equalsIgnoreCase("exit")) {
                    LOGGER.log(Level.INFO,"Client has exited.");
                    break;
                }

                // Prepare response
                InetAddress inetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();

                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An error occurred during communication", e);
                break;
            }
        }
        datagramSocket.close();
    }

    public static void main(String[] args) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(8002);
        UDPServer udpServer = new UDPServer(datagramSocket);
        LOGGER.log(Level.INFO,"Server is up and running...");
        udpServer.receiveThenSend();
    }
}
