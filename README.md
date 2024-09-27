# Chat-Room



# TCP Server-Client Chat Application



## Project Overview

This project is a **multi-threaded TCP-based chat application** that allows multiple clients to connect to a server and communicate with each other in real-time. The server manages multiple client connections, handles message broadcasts, and ensures that clients can exchange messages in a chat room environment. Each client can send and receive messages simultaneously using separate threads for sending and receiving, providing a seamless communication experience.



## Design and Architecture

### 1. **Server Design**
- The **server** listens for incoming client connections on a specific port using a `ServerSocket`.
- For each connected client, the server creates a **dedicated client handler** (`ClientHandler`), which runs on a separate thread, allowing the server to manage multiple clients simultaneously.
- Messages received from a client are **broadcasted** to all other connected clients in the chat room.
- The server supports:
  - Adding new clients to the chat room.
  - Broadcasting messages to all clients.
  - Handling client disconnections gracefully by removing them from the active client list and notifying other clients.

### 2. **Client Design**
- The **client** connects to the server using a TCP connection and communicates via sockets.
- Upon connection, the client enters their username, which will be displayed alongside their messages in the chat.
- The client uses two threads:
  - One thread listens for incoming messages from the server (sent by other clients).
  - Another thread reads user input and sends it to the server.
- The client gracefully handles server shutdowns or disconnections by closing resources and exiting.

### 3. **Multi-Threading**
- The server uses multi-threading to handle each client connection concurrently.
- The `ClientHandler` class runs on its own thread for each client, allowing the server to scale and handle multiple clients.
- The client also uses threads to ensure that sending and receiving messages are independent and can occur simultaneously.



### 4. **Message Broadcasting**
- Each message sent by a client is relayed to all other connected clients through the server.
- The broadcast system ensures that no client misses a message and provides a real-time chat experience.



## Technologies Used

- **Java (JDK 8+)**: The core programming language used to implement both the server and the client.
- **Socket Programming**: Java `Socket` and `ServerSocket` classes are used to establish TCP connections between the client and server.
- **Multi-threading**: Java threads (`Runnable`) are utilized for handling multiple clients on the server and for asynchronous message handling on the client.
- **Logging**: `java.util.logging` is used for logging important events, such as client connections, disconnections, and message transfers.
- **Concurrent Collections**: `CopyOnWriteArrayList` is used to maintain a thread-safe list of active client connections.



## Features

- **Multi-Client Support**: Multiple clients can join the chat simultaneously, with each client having its own thread.
- **Real-Time Messaging**: Messages are sent and received in real-time across all connected clients.
- **Graceful Disconnection**: Clients can leave the chat at any time, and other clients will be notified of the disconnection.
- **Thread Safety**: The server ensures thread-safe operations when handling multiple clients using concurrent collections.



## How to Run

### Prerequisites

- **Java Development Kit (JDK) 8 or higher** installed on your machine.
- A terminal or command prompt to run the server and client applications.

### 1. Running the Server

**Compile the server code**:

```bash
javac TCPServer.java ClientHandler.java
```

**Run the server**:

```bash
java TCPServer
```

​	•	The server will start listening for client connections on port 9001.

​	•	The server will log each client connection and disconnection.

### **2. Running the Client**

**Compile the client code**:

```bash
javac TCPClient.java
```

**Run the client**::

```bash
java TCPClient
```

**Enter your username**: Once the client connects to the server, you’ll be prompted to enter a username for the chat.

**Start chatting**: Type messages in the console, and they will be broadcast to all connected clients. You will also see messages from other clients.



## **Folder Structure**

```
.
├── ClientHandler.java   # Handles individual client connections on the server
├── TCPClient.java       # The client-side code for connecting and chatting with the server
├── TCPServer.java       # The main server code that handles incoming connections and broadcasts messages
└── README.md            # Project documentation
```

**Future Enhancements**

​	1.	**User Authentication**: Add user authentication with usernames and passwords.

​	2.	**Private Messaging**: Implement private messages between specific users.

​	3.	**Message History**: Store and retrieve chat history from a database.

​	4.	**UI-Based Client**: Build a graphical user interface (GUI) using JavaFX or Swing for a better user experience.

	5.	**Encryption**: Add encryption to secure messages sent over the network.



## **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.