package realClient;

import client.messages.Message;
import client.messages.OrderMessage;
import server.SocketReaderAsync;
import utils.Utils;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    private OutputStream outputStream;

    @Override
    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1", 500);

            outputStream = socket.getOutputStream();

            // Start reader service
            new SocketReaderAsync(socket.getInputStream(), socketReaderCallback).start();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //...
    }

    /**
     * Send message through socket.
     * @param message Message to send.
     * @return True if message is sent, False otherwise.
     */
    private boolean sendMessage(Message message) {
        try {
            // Write message code
            outputStream.write(message.getMessageCode());

            // Write extra data lenght
            if(message.getExtraData() != null) {
                byte[] extraData = message.getExtraData();
                byte[] length = Utils.intToByteArray(extraData.length);
                outputStream.write(length);
                // Write extra data
                for(int i = 0; i < extraData.length; i++) {
                    outputStream.write(extraData[i]);
                }
            }
            else {
                byte[] byte0 = Utils.intToByteArray(0);
                outputStream.write(byte0);
            }

            // Write content of message (json)
            JsonWriter jsonWriter = Json.createWriter(outputStream);
            jsonWriter.writeObject(message.createJsonObject());
            // Finish this message
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendNewOrder() {
        String input = "{\"tableNumber\":10,\"numClients\":4,\"priceListId\":0,\"plates\":[{\"plateId\":1,\"modifications\":[1,2],\"memo\":\"\"},{\"plateId\":2,\"modifications\":[],\"memo\":\"\"},{\"plateId\":4,\"modifications\":[],\"memo\":\"\"},{\"plateId\":6,\"modifications\":[],\"memo\":\"\"}]}";
        JsonReader reader = Json.createReader(new StringReader(input));
        OrderMessage message = new OrderMessage((byte) 2, reader.readObject());
        sendMessage(message);
    }

    public void sendFile() {
        try {
            FileInputStream fileStream = new FileInputStream("D:\\Documenti\\menu - Prova.db");
            byte[] data = new byte[fileStream.available()];
            int i = 0;
            int read = fileStream.read();
            while (read != -1) {
                data[i] = (byte) read;
                i++;
                read = fileStream.read();
            }
            System.out.println("num byte : " + data.length + " = " + i);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SocketReaderAsync.SocketReaderCallback socketReaderCallback = new SocketReaderAsync.SocketReaderCallback() {
        @Override
        public void onMessageReceived(Message message) {
            System.out.println("Message received. Code = " + message.getMessageCode() + " // Content = " + message.createJsonObject());  //TODO
        }
    };
}
