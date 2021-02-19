package com.example;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080));
        System.out.println("Listening to 8080");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.printf("Client connected %s:%d\n", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
            ClientHandler client = new ClientHandler( clientSocket);
            new Thread(client).start();
        }
    }

}


class ClientHandler extends Main implements Runnable {
    private Socket socket;


    public ClientHandler ( Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                System.out.printf("GOT: %s\n", command);
                sendMessage(command);
            }
        } catch (Exception e) {
            // do nothing
        }
    }

    public void sendMessage(String message){
        try{
            socket.getOutputStream().write((message + "\n").getBytes());
            socket.getOutputStream().flush();
        }catch (Exception e) {
            //do nothing
        }
    }


}