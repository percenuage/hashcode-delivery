package fr.paris.hashcode2016.algorithm;

import fr.paris.hashcode2016.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Axel on 13/02/2016.
 */
public class DeliveryAlgorithm {

    private Delivery delivery;
    private int currentTurn;

    private DeliveryAlgorithm() {}

    public static DeliveryAlgorithm getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void init(Delivery delivery) {
        this.delivery = delivery;
        this.currentTurn = 0;
    }

    public void start() {
        int count = 0;

       /* System.out.println(delivery);

        Drone drone = delivery.getGrid().getDrones().get(0);

        if (!allOrdersDelivered()) {
            Order order = getNearestOrder(drone);
            Warehouse  warehouse = getNearestWarehouse(drone);

            drone.flyTo(warehouse.getCell());
            count += drone.getDistance(warehouse);
            count += loadOrderProducts(drone, warehouse, order);

            drone.flyTo(order.getCell());
            count += drone.getDistance(order);
        }

        System.out.println(count);
        System.out.println(delivery);*/

        List<Order> missingOrders = linkOrdersToWarehouses();

        System.out.println(delivery.getGrid().getWarehouses().get(0).getOrders().get(0).getCommandProducts());
        System.out.println(delivery.getGrid().getWarehouses().get(0).getOrders().get(0));
        System.out.println(delivery.getGrid().getWarehouses().get(0));
        System.out.println("------------");
        for (Warehouse w : delivery.getGrid().getWarehouses()) {
            count+=w.getOrders().size();
            System.out.println(w.getId() + "::" + w.getOrders().size()+"::"+w.calculAverageOrdersDistance());
            System.out.println(w.hasOrdersProducts());
        }
        System.out.println("MISSING ORDERS : "+missingOrders.size());
        System.out.println(count);
    }

    private List<Order> linkOrdersToWarehouses() {
        List<Order> missingOrders = new ArrayList<>();
        Warehouse warehouse;
        for (Order order : delivery.getGrid().getOrders()) {
            if (order.getWarehouse() == null) {
                boolean canContinue;
                List<Warehouse> warehousesCopy = new ArrayList<>(delivery.getGrid().getWarehouses());
                do {
                    warehouse = (Warehouse) order.getNearestElement(warehousesCopy);
                    if (!warehouse.hasOrderProducts(order)) {
                        warehousesCopy.remove(warehouse);
                        canContinue = true;
                    } else {
                        canContinue = false;
                    }
                } while (canContinue && !warehousesCopy.isEmpty());

                if (warehousesCopy.isEmpty()) {
                    missingOrders.add(order);
                    //System.err.println("No warehouse for this order : "+order);
                } else {
                    warehouse.addOrder(order);
                }
            }
        }
        return missingOrders;
    }

    private int loadOrderProducts(Drone drone, Warehouse warehouse, Order order) {
        int count = 0;
        Map<ProductType, Integer> missingProducts = getMissingProducts(drone, order);
        for (Map.Entry<ProductType, Integer> entry : missingProducts.entrySet()) {
            delivery.load(drone, warehouse, entry.getKey(), entry.getValue());
            count++;
        }
        return count;
    }

    private int deliverOrderProducts(Drone drone, Order order) {
        int count = 0;
        for (Map.Entry<ProductType, Integer> entry : drone.getProductTypes().entrySet()) {
            delivery.deliver(drone, order, entry.getKey(), entry.getValue());
            count++;
        }
        return count;
    }

    private boolean allOrdersDelivered() {
        for (Order o : delivery.getGrid().getOrders()) {
            if (!o.isDelivered()) {
                return false;
            }
        }
        return true;
    }

    private Order getNearestOrder(Drone drone) {
        // TODO: 14/02/2016 if is not delivered
        return (Order)drone.getNearestElement(delivery.getGrid().getOrders());
    }

    private Warehouse getNearestWarehouse(Drone drone) {
        // TODO: 14/02/2016 Warehouse enough product
        return (Warehouse)drone.getNearestElement(delivery.getGrid().getWarehouses());
    }

    private Map<ProductType, Integer> getMissingProducts(Drone drone, Order order) {
        Map<ProductType, Integer> missingProducts = new HashMap<>();
        for (Map.Entry<ProductType, Integer> entry : order.getCommandProducts().entrySet()) {
            int numMissingItem = entry.getValue();
            ProductType product = entry.getKey();

            if (order.getCurrentProducts().containsKey(product)) {
                numMissingItem -= order.getCurrentProducts().get(product);
            }
            if (drone.getProductTypes().containsKey(product)) {
                numMissingItem -= drone.getProductTypes().get(product);
            }
            if (numMissingItem > 0) {
                missingProducts.put(product, numMissingItem);
            }
        }
        return missingProducts;
    }

    /* NESTED STATIC CLASS SINGLETON HOLDER */
    private static class SingletonHolder {
        private final static DeliveryAlgorithm INSTANCE = new DeliveryAlgorithm();
    }

    public int getCurrentTurn() {
        return currentTurn;
    }
}
