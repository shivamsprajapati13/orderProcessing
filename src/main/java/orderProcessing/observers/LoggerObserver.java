package orderProcessing.observers;

import orderProcessing.events.Event;
import orderProcessing.model.Order;



public class LoggerObserver implements EventObserver {
    @Override
    public void onEventProcessed(Order order, Event event) {
        System.out.println("[LOG] Event " + event.getEventType() +
                " applied to Order " + order.getOrderId() +
                " => Status: " + order.getStatus());
    }
}

