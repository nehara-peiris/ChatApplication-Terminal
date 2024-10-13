package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("\nEnter '~' to exit\n");

        try (ServerSocket serverSocket = new ServerSocket(3000);
             Socket socket = serverSocket.accept();
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {

            while (true) {
                // Send message to the client
                System.out.print("Server : ");
                String message = input.nextLine();

                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();

                // Check if the server wants to exit
                if (message.equals("~")) {
                    System.out.println("Server exiting...");
                    break;
                }

                // Receive message from the client
                try {
                    String clientMessage = dataInputStream.readUTF();
                    System.out.println("Client : " + clientMessage);

                    // Check if the client wants to exit
                    if (clientMessage.equals("~")) {
                        System.out.println("Client disconnected.");
                        break;
                    }

                } catch (IOException e) {
                    System.out.println("Error receiving message.");
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
