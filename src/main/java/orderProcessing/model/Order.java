package orderProcessing.model;

import orderProcessing.events.Event;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String orderId;
    private final String customerId;
    private final List<Item> items;
    private final double totalAmount;
    private OrderStatus status;
    private final List<Event> eventHistory = new ArrayList<>();

    public Order(String orderId, String customerId, List<Item> items, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
    }

    public String getOrderId() { return orderId; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public void addEvent(Event e) { eventHistory.add(e); }
    public double getTotalAmount() { return totalAmount; }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                '}';
    }
}