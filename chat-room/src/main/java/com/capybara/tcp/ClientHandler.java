package com.capybara.tcp;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());
    public static List<ClientHandler> clientHandlers = new CopyOnWriteArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("Client " + clientUsername + " has joined!");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error initializing client handler: ", e);
            closeEverything();
        }
    }

    private void closeEverything() {
        System.out.println("closeEverything");
        removeClientHandler();
        try {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error closing resources: ", e);
        }
    }

    private void broadcastMessage(String msg) {
        if(msg == null || msg.isEmpty()) return;
        LOGGER.log(Level.INFO, "Broadcasting message: {0}", msg);
        for(ClientHandler clientHandler : clientHandlers) {
            try {
                if(!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(msg);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error sending message to client: ", e);
                closeEverything();
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("Client " + clientUsername + " has left.");
    }


    @Override
    public void run() {
        String msgFromClient;

        while (socket.isConnected()) {
            try {
                msgFromClient = bufferedReader.readLine();
                if(msgFromClient == null) break; // Client disconnected
                broadcastMessage(msgFromClient);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error reading message from client: ", e);
                closeEverything();
                break;
            }

        }
        closeEverything();
    }
}
