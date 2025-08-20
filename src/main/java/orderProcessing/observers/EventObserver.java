package orderProcessing.observers;

import orderProcessing.events.Event;
import orderProcessing.model.Order;

public interface EventObserver {
    void onEventProcessed(Order order, Event event);
}