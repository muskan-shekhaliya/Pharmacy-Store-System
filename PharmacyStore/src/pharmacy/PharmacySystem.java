package pharmacy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class PharmacySystem {
    private Inventory inventory = new Inventory();
    private final String SALES_FILE = "sales.csv";

    public void start() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Pharmacy Store Management ===");
            System.out.println("1. Add Medicine");
            System.out.println("2. Update Medicine");
            System.out.println("3. Remove Medicine");
            System.out.println("4. Display Medicines");
            System.out.println("5. Low Stock Alert");
            System.out.println("6. Expired Stock Alert");
            System.out.println("7. Generate Bill");
            System.out.println("8. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1: addMedicine(sc); break;
                case 2: updateMedicine(sc); break;
                case 3: removeMedicine(sc); break;
                case 4: inventory.displayMedicines(); break;
                case 5: 
                    System.out.print("Enter threshold: ");
                    int threshold = sc.nextInt();
                    inventory.lowStockAlert(threshold);
                    break;
                case 6: inventory.expiredStockAlert(); break;
                case 7: generateBill(sc); break;
                case 8: 
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void addMedicine(Scanner sc) {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Expiry Date (yyyy-MM-dd): ");
        String expiryStr = sc.nextLine();
        LocalDate expiry = LocalDate.parse(expiryStr);

        inventory.addMedicine(new Medicine(id, name, price, qty, expiry));
    }

    private void updateMedicine(Scanner sc) {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new Name: ");
        String name = sc.nextLine();
        System.out.print("Enter new Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter new Quantity: ");
        int qty = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new Expiry Date (yyyy-MM-dd): ");
        String expiryStr = sc.nextLine();
        LocalDate expiry = LocalDate.parse(expiryStr);

        inventory.updateMedicine(id, name, price, qty, expiry);
    }

    private void removeMedicine(Scanner sc) {
        System.out.print("Enter ID to remove: ");
        int id = sc.nextInt();
        inventory.removeMedicine(id);
    }

    private void generateBill(Scanner sc) {
        Bill bill = new Bill();
        while (true) {
            inventory.displayMedicines();
            System.out.print("Enter Medicine ID to add (0 to finish): ");
            int id = sc.nextInt();
            if (id == 0) break;
            Medicine med = inventory.getMedicineById(id);
            if (med == null) {
                System.out.println("Invalid medicine ID!");
                continue;
            }
            System.out.print("Enter quantity: ");
            int qty = sc.nextInt();
            if (qty > med.getQuantity()) {
                System.out.println("Insufficient stock!");
                continue;
            }
            bill.addItem(med, qty);
            med.setQuantity(med.getQuantity() - qty);
            inventory.saveToFile();
        }
        bill.printBill();
        saveSale(bill.getTotalWithTax());
    }

    private void saveSale(double total) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SALES_FILE, true))) {
            writer.println(total);
        } catch (IOException e) {
            System.out.println("Error saving sales record: " + e.getMessage());
        }
    }
}
