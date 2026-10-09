import java.util.Scanner;

// Abstract base class for all electricity connections
abstract class ElectricityConnection {
    protected String type;
    protected int units;

    protected ElectricityConnection(String type, int units) {
        this.type = type;
        this.units = units;
    }

    // Abstract method to be overridden by subclasses
    public abstract double calculateBill();
}

// Subclass for Home connection
class HomeConnection extends ElectricityConnection {
    public HomeConnection(String type, int units) {
        super(type, units);
    }

    @Override
    public double calculateBill() {
        if (units <= 100) {
            return units * 5.0;
        } else {
            return (100 * 5.0) + ((units - 100) * 7.0);
        }
    }
}

// Subclass for Shop connection
class ShopConnection extends ElectricityConnection {
    public ShopConnection(String type, int units) {
        super(type, units);
    }

    @Override
    public double calculateBill() {
        return (units * 8.0) + 100.0;
    }
}

// Subclass for Factory connection
class FactoryConnection extends ElectricityConnection {
    public FactoryConnection(String type, int units) {
        super(type, units);
    }

    @Override
    public double calculateBill() {
        double bill = units * 6.0;
        return Math.max(bill, 1000.0);
    }
}

// Main class matching the file name
public class ElectricityBilling {
    public static void main(String[] args) {
        // Fix 1: Try-with-resources automatically closes the Scanner safely
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                return;
            }

            int n = scanner.nextInt();
            double totalBill = 0.0;

            for (int i = 0; i < n; i++) {
                String type = scanner.next();
                int units = scanner.nextInt();

                // Fix 2 & 3: Uses Java's enhanced rule switch expressions to safely return the object
                ElectricityConnection connection = switch (type) {
                    case "HOME" -> new HomeConnection(type, units);
                    case "SHOP" -> new ShopConnection(type, units);
                    case "FACTORY" -> new FactoryConnection(type, units);
                    default -> null;
                };

                // Fix 4: Prevents possible null pointer warnings if an unexpected input type is passed
                if (connection == null) {
                    continue;
                }

                double individualBill = connection.calculateBill();
                totalBill += individualBill;

                System.out.printf("%s: %.2f%n", type, individualBill);
            }

            System.out.printf("Total: %.2f%n", totalBill);
        }
    }
}
