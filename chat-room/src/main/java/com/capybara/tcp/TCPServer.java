package com.capybara.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
public class TCPServer {
    private static final Logger LOGGER = Logger.getLogger(TCPServer.class.getName());
    private ServerSocket serverSocket;

    public TCPServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                LOGGER.log(Level.INFO, "A new client has connected!");
                ClientHandler clientHandler= new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error in server loop: ", e);
        } finally {
            closeServerSocket();
        }
    }

    public void closeServerSocket() {
        try{
            if(serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error closing server socket: ", e);
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(9002);
            TCPServer tcpServer = new TCPServer(serverSocket);
            tcpServer.startServer();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to start server: ", e);
        }
    }
}
