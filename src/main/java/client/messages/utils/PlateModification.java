package client.messages.utils;

public class PlateModification implements Priceable{
    private int id;
    // Retrieve from db
    private String name;
    private float price;

    public PlateModification(int id) {
        this.id = id;
        //TODO Retrieve other info from db
    }

    @Override
    public float getPrice() {
        return 0;
    }

    public int getId() {
        return id;
    }
}
