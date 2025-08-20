package orderProcessing;

import orderProcessing.events.*;
import orderProcessing.model.Order;
import orderProcessing.model.OrderStatus;
import orderProcessing.observers.AlertObserver;
import orderProcessing.observers.LoggerObserver;
import orderProcessing.observers.EventObserver;  // import the external interface

import java.util.*;

public class EventProcessor {
    private final Map<String, Order> orderStore = new HashMap<>();
    private final List<EventObserver> observers = new ArrayList<>();

    public void registerObserver(EventObserver observer) {
        observers.add(observer);
    }

    // Optionally, you can keep or remove these convenience methods:
    public void registerAlertObserver(AlertObserver obs) {
        observers.add(obs);  // no cast needed if AlertObserver implements EventObserver
    }

    public void registerLogObserver(LoggerObserver obs) {
        observers.add(obs);
    }

    private void notifyObservers(Order order, Event event) {
        for (EventObserver o : observers) {
            o.onEventProcessed(order, event);
        }
    }

    public void process(Event e) {
        if (e instanceof OrderCreatedEvent oce) {
            Order order = new Order(oce.orderId, oce.customerId, oce.items, oce.totalAmount);
            order.addEvent(e);
            orderStore.put(order.getOrderId(), order);
            notifyObservers(order, e);
        } else if (e instanceof PaymentReceivedEvent pe) {
            Order order = orderStore.get(pe.orderId);
            if (order != null) {
                if (pe.amountPaid >= order.getTotalAmount()) {
                    order.setStatus(OrderStatus.PAID);
                } else {
                    order.setStatus(OrderStatus.PARTIALLY_PAID);
                }
                order.addEvent(e);
                notifyObservers(order, e);
            }
        } else if (e instanceof ShippingScheduledEvent se) {
            Order order = orderStore.get(se.orderId);
            if (order != null) {
                order.setStatus(OrderStatus.SHIPPED);
                order.addEvent(e);
                notifyObservers(order, e);
            }
        } else if (e instanceof OrderCancelledEvent ce) {
            Order order = orderStore.get(ce.orderId);
            if (order != null) {
                order.setStatus(OrderStatus.CANCELLED);
                order.addEvent(e);
                notifyObservers(order, e);
            }
        } else {
            System.out.println("[WARN] Unknown event type: " + e.getEventType());
        }
    }
}
