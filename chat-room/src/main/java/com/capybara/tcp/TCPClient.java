package com.capybara.tcp;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class TCPClient {
    private static final Logger LOGGER = Logger.getLogger(TCPClient.class.getName());
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    public TCPClient(Socket socket, String clientUsername) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = clientUsername;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error initializing client: ", e);
            closeEverything();
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(clientUsername);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            try (Scanner scanner = new Scanner(System.in)) {

                while (socket.isConnected()) {
                    String msgToSend = scanner.nextLine();
                    bufferedWriter.write(clientUsername + ": " + msgToSend);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error sending message: ", e);
            closeEverything();
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while(socket.isConnected()) {
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        if(msgFromGroupChat != null) System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "Error receiving message: ", e);
                        closeEverything();
                        break;
                    }

                }
            }
        }).start();
    }

    private void closeEverything() {
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

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter your username for the group chat: ");
            String username = scanner.nextLine();
            Socket socket = new Socket("localhost", 9002);
            TCPClient tcpClient = new TCPClient(socket, username);
            tcpClient.listenForMessage();
            tcpClient.sendMessage();
        } catch(IOException e) {
            LOGGER.log(Level.SEVERE, "Error connecting to server: ", e);
        }
    }
}
