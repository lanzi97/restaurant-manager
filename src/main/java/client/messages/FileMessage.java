package client.messages;

import javax.json.Json;
import javax.json.JsonObject;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileMessage extends Message {
    private static final Charset charset = StandardCharsets.UTF_8;

    public static final int MENU_DB_FILE = 1;
    public static final int TABLE_STATUS_DB_FILE = 2;

    private static final String JSON_FILE_TYPE = "fileType";

    private int fileType;
    private byte[] data;

    public FileMessage(byte messageCode, JsonObject jsonObject, byte[] extraData) {
        super(messageCode);
        parseJson(jsonObject);
        this.data = extraData;
    }

    private void parseJson(JsonObject jsonObject) {
        fileType = jsonObject.getInt(JSON_FILE_TYPE);
    }

    //--------------------
    public FileMessage(byte messageCode, int fileType, byte[] data) {
        super(messageCode);
        this.fileType = fileType;
        this.data = data;
    }

    @Override
    public JsonObject createJsonObject() {
        return Json.createObjectBuilder()
                .add(JSON_FILE_TYPE, fileType)
                .build();
    }

    @Override
    public byte[] getExtraData() {
        return data;
    }
}
