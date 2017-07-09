package fr.paris.hashcode2016.model;

import fr.paris.hashcode2016.Main;
import fr.paris.hashcode2016.algorithm.DeliveryAlgorithm;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Axel on 11/02/2016.
 */
public class Delivery {

    private Grid grid;
    private int maxTurn;
    private String output;
    private DeliveryAlgorithm algorithm;

    public Delivery() {
        this.grid = new Grid(this);
        this.maxTurn = 0;
        this.output = "";
        this.algorithm = DeliveryAlgorithm.getInstance();
    }

    public void load(Drone drone, Warehouse warehouse, ProductType productType, int quantity) {
        if (warehouse.hasProduct(productType, quantity)) {
            if (drone.load(productType)) {
                warehouse.decrementProduct(productType, quantity);
                appendOutput(drone, 'L', warehouse, productType, quantity);
            }
        }
    }

    public void unload(Drone drone, Warehouse warehouse, ProductType productType, int quantity) {
            if (drone.unload(productType)) {
                warehouse.incrementProduct(productType, quantity);
                appendOutput(drone, 'U', warehouse, productType, quantity);
            }
    }

    public void deliver(Drone drone, Order order, ProductType productType, int quantity) {
        if (drone.deliver(productType)) {
//            warehouse.incrementProduct(productType, quantity);
            // TODO: 14/02/2016 deliver order
            appendOutput(drone, 'D', order, productType, quantity);
        }
    }

    public void waitDrone(Drone drone, int turns) {
        // TODO: 13/02/2016 WaitDrone
        appendOutput(drone, 'W', turns);
    }

    private void appendOutput(Drone drone, Character command, Warehouse warehouse,
                                    ProductType productType, int quantity) {
        output += drone.getId() + " " + command + " " + warehouse.getId() + " " +
                productType.getId() + " " + quantity + "\n ";
    }

    private void appendOutput(Drone drone, Character command, Order order,
                              ProductType productType, int quantity) {
        output += drone.getId() + " " + command + " " + order.getId() + " " +
                productType.getId() + " " + quantity + "\n ";
    }

    private void appendOutput(Drone drone, Character command, int turns) {
        output += drone.getId() + " " + command + " " + turns + "\n ";
    }

    public void init() throws IOException {
        List<String> lines = FileUtils.readLines(Main.INPUT_FILE);
        this.grid.init(lines);
        this.algorithm.init(this);
    }

    public void start() {
        this.algorithm.start();
    }

    @Override
    public String toString() {
        return "GRID : " + grid + "\n" + "TURNS : " + algorithm.getCurrentTurn() + "/" + maxTurn + "\n" +
                "PRODUCTS : " + grid.getProductTypes() + "\n" +
                "DRONES : " + grid.getDrones() + "\n" +
                "WAREHOUSES : " + grid.getWarehouses() + "\n" +
                "ORDERS : " + grid.getOrders() + "\n";
    }

    public Grid getGrid() {
        return grid;
    }

    public void setMaxTurn(int maxTurn) {
        this.maxTurn = maxTurn;
    }

    public String getOutput() {
        return output;
    }
}
