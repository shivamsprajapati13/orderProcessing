package orderProcessing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import orderProcessing.events.*;
import orderProcessing.model.Item;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class EventParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Event parse(String jsonLine) throws Exception {
        JsonNode node = mapper.readTree(jsonLine);
        String type = node.get("eventType").asText();
        String eventId = node.get("eventId").asText();
        Instant ts = Instant.parse(node.get("timestamp").asText());

        return switch (type) {
            case "OrderCreated" -> {
                String orderId = node.get("orderId").asText();
                String customerId = node.get("customerId").asText();
                double total = node.get("totalAmount").asDouble();
                List<Item> items = new ArrayList<>();
                for (JsonNode itemNode : node.get("items")) {
                    items.add(new Item(itemNode.get("itemId").asText(),
                            itemNode.get("qty").asInt()));
                }
                yield new OrderCreatedEvent(eventId, ts, orderId, customerId, items, total);
            }
            case "PaymentReceived" ->
                    new PaymentReceivedEvent(eventId, ts,
                            node.get("orderId").asText(),
                            node.get("amountPaid").asDouble());
            case "ShippingScheduled" ->
                    new ShippingScheduledEvent(eventId, ts,
                            node.get("orderId").asText(),
                            node.get("shippingDate").asText());
            case "OrderCancelled" ->
                    new OrderCancelledEvent(eventId, ts,
                            node.get("orderId").asText(),
                            node.get("reason").asText());
            default -> throw new IllegalArgumentException("Unknown eventType: " + type);
        };
    }
}
