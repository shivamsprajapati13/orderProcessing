package orderProcessing.events;


import java.time.Instant;

public class PaymentReceivedEvent extends Event {
    public String orderId;
    public double amountPaid;

    public PaymentReceivedEvent(String eventId, Instant ts, String orderId, double amountPaid) {
        super(eventId, ts, "PaymentReceived");
        this.orderId = orderId;
        this.amountPaid = amountPaid;
    }
}