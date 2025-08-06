package pharmacy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Medicine {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private LocalDate expiryDate;

    public Medicine(int id, String name, double price, int quantity, LocalDate expiryDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public LocalDate getExpiryDate() { return expiryDate; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    @Override
    public String toString() {
        return id + " | " + name + " | Price: " + price + " | Qty: " + quantity +
                " | Expiry: " + expiryDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String toCSV() {
        return id + "," + name + "," + price + "," + quantity + "," + expiryDate;
    }
}
