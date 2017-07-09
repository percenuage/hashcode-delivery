package fr.paris.hashcode2016.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Axel on 13/02/2016.
 */
public class Order extends AbstractElement {

    private Map<ProductType, Integer> commandProducts;
    private Map<ProductType, Integer> currentProducts;
    private Warehouse warehouse;

    public Order(int id, int row, int col, Grid grid) {
        super(id, row, col, grid);
        this.commandProducts = new HashMap<>();
        this.currentProducts = new HashMap<>();
    }

    public boolean isDelivered() {
        for (Map.Entry<ProductType, Integer> entry : commandProducts.entrySet()) {
            Integer numItem = currentProducts.get(entry.getKey());
            if (numItem == null || numItem < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public void deliver(ProductType productType, int quantity) {
        Integer value = currentProducts.get(productType);
        if (value == null) {
            value = 0;
        }
        currentProducts.put(productType, quantity + value);
    }

    @Override
    public String toString() {
        return super.toString() + " : " + currentProducts.size() + "/" +
                commandProducts.size() + " items delivered";
    }

    public Map<ProductType, Integer> getCommandProducts() {
        return commandProducts;
    }

    public Map<ProductType, Integer> getCurrentProducts() {
        return currentProducts;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
