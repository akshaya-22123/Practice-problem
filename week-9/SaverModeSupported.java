import java.util.Scanner;

// Interface to mark appliances that support Saver Mode
interface SaverModeSupported {}

// Base class representing a general Home Appliance
abstract class Appliance {
    private final String name;
    private final double powerRating; // in Watts

    protected Appliance(String name, double powerRating) {
        this.name = name;
        this.powerRating = powerRating;
    }

    public String getName() {
        return name;
    }

    // Standard calculation for units (kWh)
    public double calculateUnits(double hours) {
        return (powerRating * hours) / 1000.0;
    }
}

// Concrete Appliance Classes
class Fridge extends Appliance {
    public Fridge() { super("FRIDGE", 150); }
}

class AC extends Appliance implements SaverModeSupported {
    public AC() { super("AC", 1500); }
}

class TV extends Appliance {
    public TV() { super("TV", 100); }
}

class Washer extends Appliance implements SaverModeSupported {
    public Washer() { super("WASHER", 500); }
}

// REMOVED 'public' HERE SO IT COMPILES INSIDE SaverModeSupported.java
class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) return;
            int n = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            double totalCost = 0.0;

            for (int i = 0; i < n; i++) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                
                String[] tokens = line.split("\\s+");
                if (tokens.length < 2) continue;
                
                String applianceName = tokens[0];
                double hours;
                
                try {
                    hours = Double.parseDouble(tokens[1]);
                } catch (NumberFormatException e) {
                    continue;
                }
                
                boolean isSaverRequested = tokens.length > 2 && tokens[2].equalsIgnoreCase("SAVER");

                Appliance appliance = switch (applianceName.toUpperCase()) {
                    case "FRIDGE" -> new Fridge();
                    case "AC"     -> new AC();
                    case "TV"     -> new TV();
                    case "WASHER" -> new Washer();
                    default       -> null;
                };

                if (appliance == null) {
                    continue;
                }

                if (isSaverRequested && !(appliance instanceof SaverModeSupported)) {
                    System.out.printf("%s: saver mode not supported.%n", appliance.getName());
                } else {
                    double units = appliance.calculateUnits(hours);
                    
                    if (isSaverRequested) {
                        units *= 0.75;
                    }
                    
                    double cost = units * 8.0;
                    totalCost += cost;

                    System.out.printf("%s: Units=%.2f Cost=%.2f%n", appliance.getName(), units, cost);
                }
            }

            System.out.printf("Total Cost: %.2f%n", totalCost);
        }
    }
}