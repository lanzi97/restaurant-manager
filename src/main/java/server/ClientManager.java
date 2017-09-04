package server;

import client.messages.Message;

import javax.json.Json;
import javax.json.JsonWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientManager implements Runnable {
    private Socket socket;
    private OutputStream outputStream;

    public ClientManager(Socket client) {
        this.socket = client;
    }

    @Override
    public void run() {
        // Get stream from client socket
        InputStream inputStream;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            // Doesn't go further if unable to get streams
            return;
        }

        System.out.println("Client connected, address : " + socket.getInetAddress());

        // Start reader service
        new SocketReaderAsync(inputStream, socketReaderCallback).start();

        //...
    }

    /**
     * Send message through socket.
     * @param message Message to send.
     * @return True if message is sent, False otherwise.
     */
    private boolean sendMessage(Message message) {
        try {
            outputStream.write(message.getMessageCode());
            JsonWriter jsonWriter = Json.createWriter(outputStream);
            jsonWriter.writeObject(message.createJsonObject());
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private SocketReaderAsync.SocketReaderCallback socketReaderCallback = new SocketReaderAsync.SocketReaderCallback() {
        @Override
        public void onMessageReceived(Message message) {
            System.out.println("Message received. Code = " + message.getMessageCode() + " // Content = " + message.createJsonObject());  //TODO
        }
    };
}
