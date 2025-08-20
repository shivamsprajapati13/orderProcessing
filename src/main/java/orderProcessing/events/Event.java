package orderProcessing.events;

import java.time.Instant;

public abstract class Event {
    protected final String eventId;
    protected final Instant timestamp;
    protected final String eventType;

    public Event(String eventId, Instant timestamp, String eventType) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.eventType = eventType;
    }

    public String getEventType() { return eventType; }
}