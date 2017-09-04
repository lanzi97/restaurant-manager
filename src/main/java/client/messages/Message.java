package client.messages;

import javax.json.JsonObject;

public abstract class Message {

    private byte messageCode;
    // Every subclass should have a constructor that initialize attributes to create the json string to send

    /**
     * Constructor called by subclass.
     * @param messageCode Message code.
     */
    Message(byte messageCode) {
        this.messageCode = messageCode;
    }

    /**
     * Create json object to send by attribute values.
     * @return Json object created.
     */
    public abstract JsonObject createJsonObject();

    /**
     * Getter message code.
     * @return Message code.
     */
    public byte getMessageCode() {
        return messageCode;
    }

    /**
     * Getter extra message raw data.
     * @return Extra data.
     */
    public abstract byte[] getExtraData();

    /**
     * Getter message from its message code and content (jsonObject).
     * @param messageCode Message code.
     * @param jsonObject Content of message.
     * @return New instance of a message subclass.
     */
    public static Message getMessage(byte messageCode, JsonObject jsonObject) {
        switch (messageCode) {
            case MessageCode.PLACE_ORDER:
                return new OrderMessage(messageCode, jsonObject);
            case MessageCode.EDIT_ORDER:
                return new OrderMessage(messageCode, jsonObject);
            //... INSERT HERE (NEW MESSAGE CODES)...
            default:
                    return null;
        }
    }

    public static Message getMessage(byte messageCode, JsonObject jsonObject, byte[] extraData) {
        switch (messageCode) {
            case MessageCode.FILE_MESSAGE:
                return new FileMessage(messageCode, jsonObject, extraData);
            //... INSERT HERE (NEW MESSAGE CODES)...
            default:
                return null;
        }
    }
}
