package orderProcessing.observers;

import orderProcessing.events.Event;
import orderProcessing.events.OrderCancelledEvent;
import orderProcessing.model.Order;
import orderProcessing.model.OrderStatus;

public class AlertObserver implements EventObserver {
    @Override
    public void onEventProcessed(Order order, Event event) {
        if (event instanceof OrderCancelledEvent || order.getStatus() == OrderStatus.SHIPPED) {
            System.out.println("[ALERT] Order " + order.getOrderId() +
                    " changed to " + order.getStatus());
        }
    }
}