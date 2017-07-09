package fr.paris.hashcode2016.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Axel on 13/02/2016.
 */
public class Warehouse extends AbstractElement {

    private Map<ProductType, Integer> stocks;
    private Map<ProductType, Integer> ordersProducts;
    private List<Order> orders;

    public Warehouse(int id, int row, int col, Grid grid) {
        super(id, row, col, grid);
        this.stocks = new HashMap<>();
        this.ordersProducts = new HashMap<>();
        this.orders = new ArrayList<>();
    }

    public boolean hasProduct(ProductType productType, int quantity) {
        Integer numItems = this.stocks.get(productType);
        return numItems != null && numItems >= quantity;
    }

    public boolean hasOrdersProducts() {
        for (Map.Entry<ProductType, Integer> entry : ordersProducts.entrySet()) {
            if (!hasProduct(entry.getKey(), entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    public boolean hasOrderProducts(Order order) {
        for (Map.Entry<ProductType, Integer> entry : order.getCommandProducts().entrySet()) {
            if (!hasProduct(entry.getKey(), entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    public void decrementProduct(ProductType productType, int quantity) {
        if (hasProduct(productType, quantity)) {
            int numItems = this.stocks.get(productType);
            this.stocks.replace(productType, numItems - quantity);
        }
    }

    public void incrementProduct(ProductType productType, int quantity) {
        Integer numItems = this.stocks.get(productType);
        if (numItems != null) {
            this.stocks.replace(productType, numItems + quantity);
        } else {
            this.stocks.put(productType, quantity);
        }
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setWarehouse(this);
        this.addOrdersProducts(order);
    }

    private void addOrdersProducts(Order order) {
        for (Map.Entry<ProductType, Integer> entry : order.getCommandProducts().entrySet()) {
            Integer value = ordersProducts.get(entry.getKey());
            if (value == null) {
                value = 0;
            }
            ordersProducts.put(entry.getKey(), value + entry.getValue());
        }
    }

    public float calculAverageOrdersDistance() {
        float average = 0;
        for (Order order : orders) {
            average += this.getDistance(order);
        }
        return orders.isEmpty() ? 0 : average / (float)orders.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(" : ").append(stocks.size()).append(" products");
        sb.append(" {");
        for (Map.Entry<ProductType, Integer> entry : stocks.entrySet()) {
            if (entry.getValue() > 0) {
                sb.append(entry.getKey().getId()).append("=").append(entry.getValue());
                sb.append(",");
            }
        }
        if (!stocks.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }

    public Map<ProductType, Integer> getStocks() {
        return stocks;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
