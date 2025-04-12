package com.capybara.tcp;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.net.*;
import java.io.*;

public class TCPClientTest {
    private static Thread serverThread;

    @BeforeAll
    public static void startServer() {
        serverThread = new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(9002);
                TCPServer server = new TCPServer(serverSocket);
                server.startServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();


        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {}
    }

    @Test
    public void testTCPClientConnectionAndMessageSend() {
        assertDoesNotThrow(() -> {
            Socket clientSocket = new Socket("localhost", 9002);

            // Simulate a client sending and receiving messages
            ByteArrayOutputStream clientOutput = new ByteArrayOutputStream();
            ByteArrayInputStream clientInput = new ByteArrayInputStream("testUser\nhello\nexit\n".getBytes());

            // Wrap streams and inject them
            TCPClient client = new TCPClient(clientSocket, "testUser");

            // Simulate listener in background
            client.listenForMessage();

            // Manually send a message without Scanner
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            writer.write("testUser\n");
            writer.flush();
            writer.write("testUser: hello\n");
            writer.flush();

            // Clean up
            clientSocket.close();
        });
    }

    @AfterAll
    public static void stopServer() throws InterruptedException {
        serverThread.interrupt(); // Best effort, since the server loop is blocking on accept()
        serverThread.join(1000);
    }
}
