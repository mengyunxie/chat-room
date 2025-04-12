package com.capybara.udp;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.net.*;


public class UDPClientTest {
    private static Thread serverThread;

    @BeforeAll
    public static void startUDPServer() {
        serverThread = new Thread(() -> {
            try {
                DatagramSocket serverSocket = new DatagramSocket(8002);
                UDPServer udpServer = new UDPServer(serverSocket);
                udpServer.receiveThenSend();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {}
    }

    @Test
    public void testUDPClientSendAndReceive() {
        assertDoesNotThrow(() -> {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");

            // Send "hello" to the server
            byte[] buffer = "hello".getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 8002);
            socket.send(packet);

            // Receive echo
            byte[] receiveBuffer = new byte[256];
            DatagramPacket response = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(response);

            String reply = new String(response.getData(), 0, response.getLength());
            assertEquals("hello", reply.trim()); // Expect same response from echo server

            // Gracefully exit the server
            byte[] exitBuffer = "exit".getBytes();
            DatagramPacket exitPacket = new DatagramPacket(exitBuffer, exitBuffer.length, address, 8002);
            socket.send(exitPacket);

            socket.close();
        });
    }

    @AfterAll
    public static void tearDown() {
        try {
            serverThread.join(1000);
        } catch (InterruptedException ignored) {}
    }
}
