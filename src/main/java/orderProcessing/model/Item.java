package orderProcessing.model;

public class Item {
    private final String itemId;
    private final int qty;

    public Item(String itemId, int qty) {
        this.itemId = itemId;
        this.qty = qty;
    }
}