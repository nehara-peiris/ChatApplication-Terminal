package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("\nEnter '~' to exit\n");

        try (Socket socket = new Socket("localhost", 3000);
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {

            while (true) {
                // Send message to the server
                System.out.print("Client : ");
                String message = input.nextLine();

                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();

                // Check if the client wants to exit
                if (message.equals("~")) {
                    System.out.println("Client exiting...");
                    break;
                }

                // Receive message from the server
                try {
                    String serverMessage = dataInputStream.readUTF();
                    System.out.println("Server : " + serverMessage);

                    // Check if the server wants to exit
                    if (serverMessage.equals("~")) {
                        System.out.println("Server disconnected.");
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
