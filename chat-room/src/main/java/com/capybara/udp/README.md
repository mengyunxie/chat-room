# UDP Client-Server Communication

This is a simple Java implementation of a UDP client-server communication model. The client sends a message to the server, and the server echoes the message back to the client. Logging is implemented using `java.util.logging.Logger` for better error handling and visibility.



## Table of Contents

- [Requirements](#requirements)
- [Project Structure](#project-structure)
- [How to Run](#how-to-run)
- [Features](#features)
- [Usage](#usage)
- [License](#license)



## Requirements

- Java 8 or higher
- Internet connection (for communication)
- A terminal or command line for running the Java application



## Project Structure

```
.
├── UDPClient.java # Implements a UDP client that sends messages to the server and listens for responses.
├── UDPServer.java # Implements a UDP server that receives messages from clients and responds with the same message.
└── README.md      # Documentation
```



## How to Run

### Step 1: Compile the Server and Client
You need to compile both `UDPServer` and `UDPClient` using the following commands:

```bash
# Compile the UDP Server
javac UDPServer.java

# Compile the UDP Client
javac UDPClient.java
```

### Step 2: Run the Server
Start the server on port 8002 by running:
```bash
java UDPServer
```
This will start the server, which will listen for incoming messages.

### Step 3: Run the Client
In a separate terminal window, start the client:
```bash
java UDPClient
```
This will prompt you to enter a message. After you send the message, the server will echo it back, and the client will print it to the console.



## Features**

- Client-Server Communication: The client sends messages to the server using UDP, and the server responds back.
- Logging: Logging is implemented using java.util.logging.Logger to track events and errors.
- Exit Handling: Type exit in the client to gracefully close both client and server.



## **Usage**

1. Send and receive messages: When the client sends a message, the server echoes it back.
2. Exit the client and server: Type exit in the client to terminate the connection.



## **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.