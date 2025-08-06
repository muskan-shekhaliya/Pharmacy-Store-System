package pharmacy;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Inventory {
    private List<Medicine> medicines = new ArrayList<>();
    private final String FILE_NAME = "medicines.csv";

    public Inventory() {
        loadFromFile();
    }

    public void addMedicine(Medicine med) {
        medicines.add(med);
        saveToFile();
    }

    public void updateMedicine(int id, String name, double price, int quantity, LocalDate expiry) {
        for (Medicine m : medicines) {
            if (m.getId() == id) {
                m.setName(name);
                m.setPrice(price);
                m.setQuantity(quantity);
                m.setExpiryDate(expiry);
                break;
            }
        }
        saveToFile();
    }

    public void removeMedicine(int id) {
        medicines.removeIf(m -> m.getId() == id);
        saveToFile();
    }

    public Medicine getMedicineById(int id) {
        for (Medicine m : medicines) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    public void displayMedicines() {
        if (medicines.isEmpty()) {
            System.out.println("No medicines available.");
            return;
        }
        System.out.println("\n--- Medicine List ---");
        for (Medicine m : medicines) {
            System.out.println(m);
        }
    }

    public void lowStockAlert(int threshold) {
        System.out.println("\n--- Low Stock Alert (Below " + threshold + ") ---");
        for (Medicine m : medicines) {
            if (m.getQuantity() < threshold) {
                System.out.println(m);
            }
        }
    }

    public void expiredStockAlert() {
        System.out.println("\n--- Expired Medicines ---");
        LocalDate today = LocalDate.now();
        for (Medicine m : medicines) {
            if (m.getExpiryDate().isBefore(today)) {
                System.out.println(m);
            }
        }
    }

    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Medicine m : medicines) {
                writer.println(m.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    double price = Double.parseDouble(data[2]);
                    int qty = Integer.parseInt(data[3]);
                    LocalDate expiry = LocalDate.parse(data[4]);
                    medicines.add(new Medicine(id, name, price, qty, expiry));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
