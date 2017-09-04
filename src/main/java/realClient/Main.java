package realClient;

import java.util.Scanner;

public class Main {
    public static final int appVersion = 1;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();

        exit:
        while (true) {
            switch (readInput()) {
                case "send":
                    client.sendNewOrder();
                    break;
                case "sendfile":
                    client.sendFile();
                    break;
                case "close":
                    break exit;
                default:
                    System.out.println("Unknown command.");
            }
        }

        System.exit(0);
    }

    private static String readInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">");
        return scanner.next().toLowerCase();
    }
}
