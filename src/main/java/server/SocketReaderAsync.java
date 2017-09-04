package server;

import client.messages.Message;
import utils.Utils;

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
            // Read message code
            byte messageCode = (byte) inputStream.read();
            // Read extra data
            byte[] extraData = null;
            // Read extra data length
            byte[] lengthByte = new byte[4];
            lengthByte[0] = (byte) inputStream.read();
            lengthByte[1] = (byte) inputStream.read();
            lengthByte[2] = (byte) inputStream.read();
            lengthByte[3] = (byte) inputStream.read();

            int length = Utils.byteArrayToInt(lengthByte);
            if(length != 0) {
                extraData = new byte[length];
                for(int i = 0; i < length; i++) {
                    extraData[i] = (byte) inputStream.read();
                }
            }


            // Read message content (json)
            JsonReader jsonReader = Json.createReader(inputStream);
            JsonObject jsonObject = jsonReader.readObject();

            // Return read message
            if(extraData == null)
                return Message.getMessage(messageCode, jsonObject);
            else
                return Message.getMessage(messageCode, jsonObject, extraData);
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
