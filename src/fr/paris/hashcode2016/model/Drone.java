package fr.paris.hashcode2016.model;

import java.util.HashMap;
import java.util.Map;

public class Drone extends AbstractElement {

    private int maxPayload;
    private int currentPayload;

    private Map<ProductType, Integer> productTypes;

    public Drone(int id, int row, int col, int payloadMax, Grid grid) {
        super(id, row, col, grid);
        this.maxPayload = payloadMax;
        this.currentPayload = 0;
        this.productTypes = new HashMap<>();
    }

    public boolean flyTo(Cell cell) {
        if (!isInGrid(cell)) {
            return false;
        }
        setCell(cell);
        return true;
    }

    public boolean flyTo(int direction) {
        return flyTo(this.getCell().getAdjacent(direction));
    }

    public boolean load(ProductType productType) {
        if (currentPayload + productType.getPayload() > maxPayload) {
            return false;
        }
        Integer numItems = productTypes.get(productType);
        if (numItems == null) {
            numItems = 0;
        }
        productTypes.put(productType, numItems + 1);
        currentPayload += productType.getPayload();
        return true;
    }

    public boolean unload(ProductType productType) {
        Integer numItems = productTypes.get(productType);
        if (numItems == null || numItems == 0) {
            return false;
        }
        productTypes.put(productType, numItems - 1);
        currentPayload -= productType.getPayload();
        return true;
    }

    public boolean deliver(ProductType productType) {
        return unload(productType);
    }

    public void waitCommand(int turns) {
        // TODO: 13/02/2016 WaitDrone
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(" : ").append(productTypes.size()).append(" products ");
        sb.append(productTypes);
        sb.append(" [").append(currentPayload).append("/").append(maxPayload).append("u]");
        return sb.toString();
    }

    public Map<ProductType, Integer> getProductTypes() {
        return productTypes;
    }
}
