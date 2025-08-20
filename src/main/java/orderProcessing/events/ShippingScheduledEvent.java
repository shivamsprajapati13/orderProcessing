package orderProcessing.events;

import java.time.Instant;

public class ShippingScheduledEvent extends Event {
    public String orderId;
    String shippingDate;

    public ShippingScheduledEvent(String eventId, Instant ts, String orderId, String shippingDate) {
        super(eventId, ts, "ShippingScheduled");
        this.orderId = orderId;
        this.shippingDate = shippingDate;
    }
}