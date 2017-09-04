package client.messages;

import client.messages.utils.Plate;
import client.messages.utils.PlateModification;

import javax.json.*;
import java.io.StringReader;
import java.util.List;

public class OrderMessage extends Message {
    private static final String JSON_TABLE_NUMBER = "tableNumber";
    private static final String JSON_NUM_CLIENTS = "numClients";
    private static final String JSON_PRICE_LIST_ID = "priceListId";
    private static final String JSON_PLATES = "plates";
        private static final String JSON_PLATE_ID = "plateId";
        private static final String JSON_MODIFICATIONS = "modifications";
        private static final String JSON_MEMO = "memo";

    private int tableNumber;
    private int numClients;
    private int priceList;
    private Plate[] plates;

    //-------------------------------------------------------SERVER---------------------------------------------------
    /**
     * Constructor called when receive message.
     * @param jsonObject Message content as json object.
     */
    public OrderMessage(byte messageCode, JsonObject jsonObject) {
        super(messageCode);
        parseJson(jsonObject);
    }

    // Initialize attributes by parsing input json
    private void parseJson(JsonObject jsonObject) {
        // Retrieve data from json object and initialize attributes
        this.tableNumber = jsonObject.getInt(JSON_TABLE_NUMBER);
        this.numClients = jsonObject.getInt(JSON_NUM_CLIENTS);
        this.priceList = jsonObject.getInt(JSON_PRICE_LIST_ID);

        // Create plate array
        JsonArray platesJson = jsonObject.getJsonArray(JSON_PLATES);
        Plate[] plates = new Plate[platesJson.size()];
        for(int i = 0; i < platesJson.size(); i++) {
            JsonObject currentPlate = platesJson.getJsonObject(i);

            // Modifications array
            List<JsonNumber> modificationsJson = currentPlate.getJsonArray(JSON_MODIFICATIONS).getValuesAs(JsonNumber.class);
            PlateModification[] modifications = new PlateModification[modificationsJson.size()];
            for(int j = 0; j < modificationsJson.size(); j++) {
                modifications[j] = new PlateModification(modificationsJson.get(j).intValue());
            }

            int plateId = currentPlate.getInt(JSON_PLATE_ID);
            String memo = currentPlate.getString(JSON_MEMO);

            plates[i] = new Plate(plateId, modifications, memo);
        }

        this.plates = plates;
    }

    //-------------------------------------------------------CLIENT---------------------------------------------------

    /**
     * Constructor called before send message.
     * @param tableNumber Table number.
     * @param numClients Number of clients.
     * @param plates Ordered plates.
     */
    public OrderMessage(byte messageCode, int tableNumber, int numClients, int priceList, Plate[] plates) {
        super(messageCode);
        this.tableNumber = tableNumber;
        this.numClients = numClients;
        this.priceList = priceList;
        this.plates = plates;
    }


    @Override
    public JsonObject createJsonObject() {
        // Construct plates array
        JsonArrayBuilder platesJsonArray = Json.createArrayBuilder();
        for (Plate plate : plates) {

            // Construct modifications array
            JsonArrayBuilder modificationsJsonArray = Json.createArrayBuilder();
            if(plate.getPlateModifications() != null)
                for(PlateModification modification : plate.getPlateModifications())
                    modificationsJsonArray.add(modification.getId());

            // Add to plates array
            platesJsonArray.add(Json.createObjectBuilder()
                    .add(JSON_PLATE_ID, plate.getId())
                    .add(JSON_MODIFICATIONS, modificationsJsonArray)
                    .add(JSON_MEMO, plate.getMemo())
            );
        }

        return Json.createObjectBuilder()
                .add(JSON_TABLE_NUMBER, tableNumber)
                .add(JSON_NUM_CLIENTS, numClients)
                .add(JSON_PRICE_LIST_ID, priceList)
                .add(JSON_PLATES, platesJsonArray)
                .build();
    }

    @Override
    public byte[] getExtraData() {
        return null;
    }
}
