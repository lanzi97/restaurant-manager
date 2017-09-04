import client.messages.MessageCode;
import client.messages.OrderMessage;
import client.messages.utils.Plate;
import client.messages.utils.PlateModification;
import server.Server;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        Plate[] plates = new Plate[4];
        PlateModification[] plateModifications = new PlateModification[2];
        plateModifications[0] = new PlateModification(1);
        plateModifications[1]  = new PlateModification(2);
        plates[0] = new Plate(1, plateModifications, "");
        plates[1] = new Plate(2, null, "");
        plates[2] = new Plate(4, null, "");
        plates[3] = new Plate(6, null, "");

        OrderMessage message = new OrderMessage(MessageCode.PLACE_ORDER, 10, 4, 0, plates);

        System.out.println("Code : " + message.getMessageCode() + " // Content : " + message.createJsonObject());*/

        /*String input = "{\"tableNumber\":10,\"numClients\":4,\"priceListId\":0,\"plates\":[{\"plateId\":1,\"modifications\":[123,101],\"memo\":\"\"},{\"plateId\":2,\"modifications\":[],\"memo\":\"\"},{\"plateId\":4,\"modifications\":[],\"memo\":\"\"},{\"plateId\":3,\"modifications\":[],\"memo\":\"\"}]}";
        OrderMessage message = new OrderMessage((byte) 2, input);


        System.out.println("Code : " + message.getMessageCode() + " // Content : " + message.getMessageContent());
        System.out.print("----------------");*/

        int v1 = -1;
        System.out.println(v1);

        byte[] bytes = intToByteArray(v1);

        int v2 = byteArrayToInt(bytes);
        System.out.println(v2);
    }

    private static byte[] intToByteArray (int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
    private static int byteArrayToInt (byte[] bytes) {
        return ((0xFF & bytes[0]) << 24) | ((0xFF & bytes[1]) << 16) |
                ((0xFF & bytes[2]) << 8) | (0xFF & bytes[3]);
    }
}
