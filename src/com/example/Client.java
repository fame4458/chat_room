package com.example;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket clientSocket = new Socket();
        System.out.println("Listening");
        clientSocket.connect(new InetSocketAddress("127.0.0.1", 8080));
        System.out.printf("Connected from port %d\n", clientSocket.getLocalPort());

        Scanner userInput = new Scanner(System.in);

        Scanner scanner = new Scanner(clientSocket.getInputStream());

        for (int i = 0; i < 10; i++) {
            System.out.print("Enter Command: ");
            String command = userInput.nextLine();
            clientSocket.getOutputStream().write((command + "\n").getBytes());
            clientSocket.getOutputStream().flush();
            String text = scanner.nextLine();
            System.out.println("Message : "+text);
            Thread.sleep(1000);
        }
        clientSocket.close();
    }
}
