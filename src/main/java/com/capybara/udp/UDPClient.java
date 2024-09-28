package com.capybara.udp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPClient {
    private static final Logger LOGGER = Logger.getLogger(UDPClient.class.getName());
    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private byte[] buffer;

    public UDPClient(DatagramSocket datagramSocket, InetAddress inetAddress) {
        this.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    public void sendThenReceive(){

        Scanner scanner = new Scanner(System.in);
        while(true) {
            try {
                String messageToSend = scanner.nextLine();
                if(messageToSend.equals("exit")) {
                    LOGGER.log(Level.INFO,"Exiting client...");
                    break;
                }
                buffer = messageToSend.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 8002);
                datagramSocket.send(datagramPacket);

                // Receive response
                buffer = new byte[256]; // Reset buffer for receiving
                datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);

                String messageFromServer = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                // LOGGER.log(Level.INFO,"The server says you said: " + messageFromServer);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An error occurred during communication", e);
                break;
            }
        }
        scanner.close();
        datagramSocket.close();
    }
    public static void main(String[] args) throws SocketException, UnknownHostException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("localhost");
        UDPClient udpClient = new UDPClient(datagramSocket, inetAddress);
        LOGGER.log(Level.INFO,"Send datagram packets to a server. Type 'exit' to stop.");
        udpClient.sendThenReceive();
    }

}
