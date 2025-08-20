package orderProcessing.events;

import orderProcessing.model.Item;

import java.time.Instant;
import java.util.List;

public class OrderCreatedEvent extends Event {
    public String orderId;
    public String customerId;
    public List<Item> items;
    public double totalAmount;

    public OrderCreatedEvent(String eventId, Instant ts, String orderId,
                             String customerId, List<Item> items, double totalAmount) {
        super(eventId, ts, "OrderCreated");
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
    }
}