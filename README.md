# TCP and UDP Client-Server Communication

This project demonstrates both **UDP** and **TCP** client-server communication using Java. Each protocol is implemented separately to showcase their distinct use cases.

## Table of Contents
- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Features](#features)
- [Usage](#usage)
- [License](#license)



## Project Structure

```
├── README.md               # Documentation for the project
├── tcp/                    # Directory for TCP components
│   ├── ClientHandler.java   # Handles individual client connections on the TCPServer
│   ├── TCPClient.java       # The client-side code for TCP connection and communication
│   └── TCPServer.java       # The server-side code for handling multiple clients
├── udp/                    # Directory for UDP components
    ├── UDPClient.java       # The client-side code for sending and receiving UDP packets
    └── UDPServer.java       # The server-side code for receiving and responding to UDP packets
```

- **tcp/**: Contains all components for TCP communication.
- **udp/**: Contains all components for UDP communication.



## Requirements

- Java 8 or higher
- A terminal or command line for running the Java application
- Basic understanding of networking protocols (UDP and TCP)



## **Features**

### **UDP**

​	•	**Stateless Communication**: Each UDP message is independent, with no connection maintained between the client and server.

​	•	**Low Overhead**: Suitable for cases where data loss is acceptable and speed is important (e.g., real-time data).

### **TCP**

​	•	**Connection-Oriented**: TCP ensures reliable communication by establishing a connection between the client and server.

​	•	**Multithreading**: The TCP server supports multiple client connections at the same time using ClientHandler.java.



## **Usage**

### **UDP**

​	•	**Send and receive messages**: The client sends messages to the server and receives responses.

​	•	**Exit the client and server**: Type exit in the client to stop communication.

### **TCP**

​	•	**Multiclient chat**: Multiple TCP clients can connect to the server and exchange messages.



## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

