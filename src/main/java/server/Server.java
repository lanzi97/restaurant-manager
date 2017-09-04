package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private static final int port = 500;

    /**
     * Start server listening.
     */
    public void startServer() {
        this.start();
    }

    @Override
    public void run() {
        // Open server
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening at port " + port);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Executor for threads of client connected
        ExecutorService executor = Executors.newCachedThreadPool();

        while (true) {
            try {
                // Wait for a client connection
                Socket client = serverSocket.accept();
                // Add new client to thread pool
                ClientManager clientManager = new ClientManager(client);
                executor.submit(clientManager);
            } catch (IOException e) {
                // When called stop server method
                if(e.getLocalizedMessage().equals("socket closed"))
                    break;
                else
                    e.printStackTrace();
            }
        }

        executor.shutdown();
    }

    /**
     * Stop server.
     */
    public void stopServer() {
        try {
            if(serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
