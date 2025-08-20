package orderProcessing;

import orderProcessing.events.Event;

import orderProcessing.observers.AlertObserver;
import orderProcessing.observers.LoggerObserver;

import java.io.*;


public class OrderProcessingApp {
    public static void main(String[] args) throws Exception {
        EventProcessor processor = new EventProcessor();
        processor.registerLogObserver(new LoggerObserver());
        processor.registerAlertObserver(new AlertObserver());


        try (InputStream is = OrderProcessingApp.class.getResourceAsStream("/events.json");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            if (is == null) {
                throw new FileNotFoundException("Resource 'events.json' not found in classpath");
            }
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    Event e = EventParser.parse(line);
                    processor.process(e);
                } catch (Exception ex) {
                    System.err.println("Failed to parse/process event: " + ex.getMessage());
                }
            }
        }
    }
}
