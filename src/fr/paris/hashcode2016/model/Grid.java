package fr.paris.hashcode2016.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Axel on 13/02/2016.
 */
public class Grid {

    private int rows;
    private int cols;

    private Delivery delivery;

    private List<Drone> drones;
    private List<Warehouse> warehouses;
    private List<ProductType> productTypes;
    private List<Order> orders;

    public Grid(Delivery delivery) {
        this.delivery = delivery;
        this.rows = 0;
        this.cols = 0;
        this.drones = new ArrayList<>();
        this.warehouses = new ArrayList<>();
        this.productTypes = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void init(List<String> lines) {
        int index = 0;

        initMap(lines.get(index++));
        initProduct(lines.get(index++) + " " + lines.get(index++));

        int numWarehouse = Integer.parseInt(lines.get(index++));
        for (int i = 0; i < numWarehouse; i++) {
            initWarehouse(i, lines.get(index++) + " " + lines.get(index++));
        }
        initDrone();

        int numOrder = Integer.parseInt(lines.get(index++));
        for (int i = 0; i < numOrder; i++) {

            initOrder(i, lines.get(index++) + " " + lines.get(index++) + " " + lines.get(index++));
        }
    }

    private void initMap(String line) {
        String s[] = line.split(" ");

        this.rows = Integer.parseInt(s[0]);
        this.cols = Integer.parseInt(s[1]);
        this.delivery.setMaxTurn(Integer.parseInt(s[3]));

        int numDrone = Integer.parseInt(s[2]);
        int payloadDrone = Integer.parseInt(s[4]);

        for (int i = 0; i < numDrone; i++) {
            Drone d = new Drone(i, 0, 0, payloadDrone, this);
            this.drones.add(d);
        }
    }

    private void initDrone() {
        if (!warehouses.isEmpty()) {
            Warehouse w = warehouses.get(0);
            for (Drone d : drones) {
                d.setCell(w.getCell());
            }
        }
    }

    private void initProduct(String line) {
        String s[] = line.split(" ");

        int numProduct = Integer.parseInt(s[0]);

        for (int i = 0; i < numProduct; i++) {
            int payload = Integer.parseInt(s[i+1]);
            ProductType p = new ProductType(i,payload);
            this.productTypes.add(p);
        }
    }

    private void initWarehouse(int id, String line) {
        String s[] = line.split(" ");

        int row = Integer.parseInt(s[0]);
        int col = Integer.parseInt(s[1]);

        Warehouse w = new Warehouse(id, row, col, this);

        for (int i = 2; i < s.length; i++) {
            ProductType p = this.productTypes.get(i - 2);
            int numItems = Integer.parseInt(s[i]);
            w.getStocks().put(p, numItems);
        }

        this.warehouses.add(w);
    }

    private void initOrder(int id, String line) {
        String s[] = line.split(" ");

        int row = Integer.parseInt(s[0]);
        int col = Integer.parseInt(s[1]);

        Order o = new Order(id, row, col, this);

        int numItem = Integer.parseInt(s[2]);
        for (int i = 0; i < numItem; i++) {
            int j = Integer.parseInt(s[i + 3]);
            ProductType p = this.productTypes.get(j);
            Integer value = o.getCommandProducts().get(p);
            if (value == null) {
                value = 0;
            }
            o.getCommandProducts().put(p, value + 1);
        }

        this.orders.add(o);
    }

    public String getGridString() {
        StringBuilder sb = new StringBuilder();
        AbstractElement[] elements = new AbstractElement[rows * cols];
        for (Drone d : drones) {
            elements[d.getIndex()] = d;
        }
        for (Warehouse w : warehouses) {
            elements[w.getIndex()] = w;
        }
        for (Order o : orders) {
            elements[o.getIndex()] = o;
        }
        for (int i = 0; i < rows * cols; i++) {
            if (elements[i] != null) {
                sb.append("|").append(elements[i].getElementString());
            } else {
                sb.append("|..");
            }
            if ((i+1) % cols == 0) {
                sb.append("|\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return rows + " rows ; " + cols + " column";
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public List<Drone> getDrones() {
        return drones;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
