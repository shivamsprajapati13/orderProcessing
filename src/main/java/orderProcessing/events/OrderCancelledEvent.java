package orderProcessing.events;


import java.time.Instant;

public class OrderCancelledEvent extends Event {
    public String orderId;
    String reason;

    public OrderCancelledEvent(String eventId, Instant ts, String orderId, String reason) {
        super(eventId, ts, "OrderCancelled");
        this.orderId = orderId;
        this.reason = reason;
    }
}