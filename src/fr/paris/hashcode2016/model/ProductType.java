package fr.paris.hashcode2016.model;

/**
 * Created by Axel on 11/02/2016.
 */
public class ProductType {

    private int id;
    private int payload;

    public ProductType(int id, int payload) {
        this.id = id;
        this.payload = payload;
    }

    @Override
    public String toString() {
        return id + " (" + payload + "u)";
    }

    public int getId() {
        return id;
    }

    public int getPayload() {
        return payload;
    }
}
