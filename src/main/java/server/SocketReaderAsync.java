package server;

import client.messages.Message;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;

public class SocketReaderAsync extends Thread{
    private InputStream inputStream;
    private SocketReaderCallback callback;

    /**
     * Create instance of SocketReader.
     * @param inputStream Socket stream on witch read.
     * @param callback SocketReader callbacks.
     */
    public SocketReaderAsync(InputStream inputStream, SocketReaderCallback callback) {
        this.inputStream = inputStream;
        this.callback = callback;
    }

    /**
     * Read a message blocking caller thread.
     * @return Message read or null if an error occurred.
     */
    public Message readMessageSync() {
        try {
            byte messageCode = (byte) inputStream.read();
            JsonReader jsonReader = Json.createReader(inputStream);
            JsonObject jsonObject = jsonReader.readObject();


            return Message.getMessage(messageCode, jsonObject);
        } catch (Exception e) {
            if(!e.getLocalizedMessage().equals("Connection reset"))
                e.printStackTrace();
            return null;
        }
    }

    @Override
    public void run() {
        while (true) {
            Message message = readMessageSync();
            if (message == null)
                break;

            callback.onMessageReceived(message);
        }
        //TODO close stream
    }


    /**
     * Callback interface for SocketReader class.
     */
    public interface SocketReaderCallback {
        /**
         * Called when a message is read from socket
         * @param message Message read.
         */
        void onMessageReceived(Message message);
    }
}
