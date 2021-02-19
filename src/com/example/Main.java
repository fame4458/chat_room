package com.example;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        ArrayList<Socket> clients = new ArrayList<>();
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080));
        System.out.println("Listening to 8080");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.printf("Client connected %s:%d\n", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
            ClientHandler client = new ClientHandler( clientSocket, clients);

            new Thread(client).start();
            clients.add(clientSocket);
        }
    }

}


class ClientHandler extends Main implements Runnable {
    private Socket socket;
    private ArrayList<Socket> clients;

    public ClientHandler ( Socket socket, ArrayList<Socket> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Scanner scanner = new Scanner(socket.getInputStream());
                String command = scanner.nextLine();
                System.out.printf("GOT: %s\n", command);
                for (Socket client: clients){
                    client.getOutputStream().write((command + "\n").getBytes());
                    client.getOutputStream().flush();
                }
            }
        } catch (Exception e) {
            // do nothing
        }
    }
}