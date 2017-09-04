package client.messages.utils;

public class Plate implements Priceable {
    private int id;
    private PlateModification[] plateModifications;
    private String memo;
    // Retrieve from db
    private String name;
    private float price;

    public Plate(int id, PlateModification[] plateModifications, String memo) {
        this.id = id;
        this.plateModifications = plateModifications;
        this.memo = memo;
        //TODO Retrieve other info from db
    }

    @Override
    public float getPrice() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getMemo() {
        return memo;
    }

    public PlateModification[] getPlateModifications() {
        return plateModifications;
    }
}
