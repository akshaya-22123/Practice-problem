import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Base class managing shared attributes and the universal minimum fare rule
abstract class Cab {
    protected String type;
    protected double ratePerKm;

    public Cab(String type, double ratePerKm) {
        this.type = type;
        this.ratePerKm = ratePerKm;
    }

    public String getType() {
        return type;
    }

    // Abstract declaration: lets subclasses choose if they support night service
    public abstract boolean supportsNightService();

    // Universal shared logic: Fare = distance * rate, but never less than 100
    public double calculateBaseFare(double distance) {
        double fare = distance * this.ratePerKm;
        return Math.max(100.0, fare);
    }
}

// Subclass for Mini Cab (No night service support)
class MiniCab extends Cab {
    public MiniCab() {
        super("MINI", 10.0);
    }

    @Override
    public boolean supportsNightService() {
        return false;
    }
}

// Subclass for Sedan Cab (Supports night service)
class SedanCab extends Cab {
    public SedanCab() {
        super("SEDAN", 14.0);
    }

    @Override
    public boolean supportsNightService() {
        return true;
    }
}

// Subclass for SUV Cab (Supports night service)
class SuvCab extends Cab {
    public SuvCab() {
        super("SUV", 18.0);
    }

    @Override
    public boolean supportsNightService() {
        return true;
    }
}

// Main Driver Class
public class CityCabFareMeter {
    public static void main(String[] args) {
        
        // Using try-with-resources to automatically manage and close the scanner resource
        try (Scanner scanner = new Scanner(System.in)) {
            
            // Register available cab types mapping to their OOP classes
            Map<String, Cab> cabRegistry = new HashMap<>();
            cabRegistry.put("MINI", new MiniCab());
            cabRegistry.put("SEDAN", new SedanCab());
            cabRegistry.put("SUV", new SuvCab());

            if (!scanner.hasNextInt()) {
                return;
            }

            int n = scanner.nextInt();
            double totalFare = 0.0;

            for (int i = 0; i < n; i++) {
                if (!scanner.hasNext()) break;
                String cabType = scanner.next().toUpperCase();
                double distance = scanner.nextDouble();
                String timeOfDay = scanner.next().toUpperCase();

                Cab cab = cabRegistry.get(cabType);
                if (cab == null) continue;

                // Business Rule: Validate if cab supports night service requests
                if (timeOfDay.equals("NIGHT") && !cab.supportsNightService()) {
                    System.out.println(cab.getType() + ": night service not available");
                    continue;
                }

                // Step 1: Calculate base fare applying the shared minimum-fare constraint
                double fare = cab.calculateBaseFare(distance);

                // Step 2: Apply 20% surcharge if requested at night
                if (timeOfDay.equals("NIGHT")) {
                    fare *= 1.20;
                }

                totalFare += fare;
                System.out.printf("%s: %.2f\n", cab.getType(), fare);
            }

            System.out.printf("Total: %.2f\n", totalFare);
        } 
        // Scanner is automatically closed here when the try block exits
    }
}