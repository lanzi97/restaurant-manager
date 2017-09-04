package server;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(); //TODO REMOVE
        exit:
        while (true) {
            switch (readInput()) {
                case "start" :
                    System.out.println("Starting server.");
                    server.startServer();
                    break;
                case "close":
                    server.stopServer();
                    break exit;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }

    private static String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next().toLowerCase();
    }
}
