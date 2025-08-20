# Event-Driven Order Processing System

This is a simplified Java backend system simulating an e-commerce platform's order processing using an event-driven architecture.

---

## Overview

The system processes several types of order-related events:

- **OrderCreatedEvent**: Creates a new order with status `PENDING`.
- **PaymentReceivedEvent**: Updates payment status (`PAID` or `PARTIALLY_PAID`) based on amount.
- **ShippingScheduledEvent**: Updates order status to `SHIPPED`.
- **OrderCancelledEvent**: Updates order status to `CANCELLED`.

Events are ingested from a JSON file containing one JSON event object per line.

---

### Project Strucute 
```
order-processing-system/
│
├── pom.xml                         # Maven dependency file
├── src/
│   └── main/
│       ├── java/
│       │   └── orderProcessing/
│       │       ├── OrderProcessingApp.java
│       │       ├── EventProcessor.java
|       │       ├── EventParser.java
│       │       ├── model/
│       │       │   ├── Order.java
│       │       │   ├── OrderStatus.java
│       │       │   └── Item.java
│       │       ├── events/
│       │       │   ├── Event.java
│       │       │   ├── OrderCreatedEvent.java
│       │       │   ├── PaymentReceivedEvent.java
│       │       │   ├── ShippingScheduledEvent.java
│       │       │   └── OrderCancelledEvent.java
│       │       └── observers/
│       │           ├── EventObserver.java
│       │           ├── LoggerObserver.java
│       │           └── AlertObserver.java
│       └── resources/
│           └── events.json            # Place test/event data here

```


## Key Components

### Domain Model
- **Order**: Represents a customer order with fields such as `orderId`, `customerId`, `items`, `totalAmount`, and `status`.
- **OrderStatus**: Enum representing possible order states (`PENDING`, `PAID`, `PARTIALLY_PAID`, `SHIPPED`, `CANCELLED`).

### Events
- Abstract `Event` class and concrete subclasses for each event type including event metadata.

### Event Processor
- Handles event application logic, updates order states, and maintains order data.
- Notifies registered observers on state changes.

### Observer Pattern
- Observers subscribe to order state changes/events:
    - `LoggerObserver`: Logs event processing and status changes.
    - `AlertObserver`: Sends alerts for critical status changes like shipping or cancellation.

### Event Ingestion
- Reads events line-by-line from a JSON file (`events.json`).
- Parses JSON strings into corresponding event objects.
- Supports graceful skipping of empty or malformed lines.

---

## How to Run

### 1. Import Project
Import the project into a Java IDE (IntelliJ IDEA, Eclipse, etc.) supporting Java 11+.

### 2. Add Dependency
The system uses Jackson for JSON parsing.  
If using Maven, add the following dependency in your `pom.xml`:

```xml
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>2.14.0</version>
</dependency>
```

### 3. Project Structure
#### Place the Java source files in src/main/java under appropriate packages:
- orderProcessing
- orderProcessing.events
- orderProcessing.observers
- Place events.json in src/main/resources/.

### 4. Prepare events.json
The file must contain one JSON event object per line.

Example:
```
{"eventId": "e1", "timestamp": "2025-07-29T10:00:00Z", "eventType": "OrderCreated", "orderId": "ORD001", "customerId": "CUST001", "items": [{"itemId": "P001", "qty": 2}], "totalAmount": 100.00}
```


### 5. Run Main Class

#### Execute orderProcessing.OrderProcessingApp which reads events, processes them, and outputs logs/alerts to the console.


### Behavior Notes
- The system processes events sequentially and updates order state accordingly.
- Observers log events and raise alerts for important changes.
- Malformed or unknown events are logged but do not stop processing.
- Currently supports only line-delimited JSON input format.

### Extensibility

- This foundational design can be extended to:
- Support batch JSON array input.
- Persist order data to databases.
- Add additional event types (e.g., returns, refunds).
- Implement asynchronous or distributed event handling.
- Integrate with messaging systems like Kafka or RabbitMQ.