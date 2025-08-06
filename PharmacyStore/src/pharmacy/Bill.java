package pharmacy;

import java.text.SimpleDateFormat;
import java.util.*;

public class Bill {
    private List<Medicine> purchasedMeds = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
    private double total = 0.0;
    private static final double TAX_RATE = 0.05; // 5% GST

    public void addItem(Medicine med, int quantity) {
        purchasedMeds.add(med);
        quantities.add(quantity);
        total += med.getPrice() * quantity;
    }

    public void printBill() {
        System.out.println("\n======= PHARMACY BILL =======");
        System.out.println("Date: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        System.out.println("-----------------------------");
        for (int i = 0; i < purchasedMeds.size(); i++) {
            Medicine m = purchasedMeds.get(i);
            int qty = quantities.get(i);
            System.out.println(m.getName() + " x " + qty + " = " + (m.getPrice() * qty));
        }
        double tax = total * TAX_RATE;
        System.out.println("-----------------------------");
        System.out.println("Subtotal: " + total);
        System.out.println("Tax (5%): " + tax);
        System.out.println("Total: " + (total + tax));
        System.out.println("=============================\n");
    }

    public double getTotalWithTax() {
        return total + (total * TAX_RATE);
    }
}
