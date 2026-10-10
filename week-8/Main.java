import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Uniform interface for all transport types
interface Transport {
    double calculateFare();
}

// Bus implementation with a $10 maximum cap
class Bus implements Transport {
    private final double distance;

    public Bus(double distance) {
        this.distance = distance;
    }

    @Override
    public double calculateFare() {
        double fare = 2.00 + (distance * 0.10);
        return Math.min(fare, 10.00); 
    }
}

// Train implementation
class Train implements Transport {
    private final double distance;

    public Train(double distance) {
        this.distance = distance;
    }

    @Override
    public double calculateFare() {
        return 3.00 + (distance * 0.15);
    }
}

// Metro implementation with peak hour factor multiplier
class Metro implements Transport {
    private final double distance;
    private final double peakHourFactor;

    public Metro(double distance, double peakHourFactor) {
        this.distance = distance;
        this.peakHourFactor = peakHourFactor;
    }

    @Override
    public double calculateFare() {
        double baseFare = 1.50 + (distance * 0.20);
        return baseFare * peakHourFactor;
    }
}

// Helper class to store output label alongside the object
class JourneyRecord {
    String typeLabel;
    Transport transport;

    public JourneyRecord(String typeLabel, Transport transport) {
        this.typeLabel = typeLabel;
        this.transport = transport;
    }
}

// Public class name matches the Main.java file name perfectly
public class Main {
    public static void main(String[] args) {
        List<JourneyRecord> journeys = new ArrayList<>();

        // Try-with-resources handles the scanner resource leak automatically
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) return;

            int n = scanner.nextInt();

            // Modern switch expressions satisfy the IDE hints perfectly
            for (int i = 0; i < n; i++) {
                String type = scanner.next().toUpperCase();
                double distance = scanner.nextDouble();

                switch (type) {
                    case "BUS" -> journeys.add(new JourneyRecord("BUS", new Bus(distance)));
                    case "TRAIN" -> journeys.add(new JourneyRecord("TRAIN", new Train(distance)));
                    case "METRO" -> {
                        double factor = scanner.nextDouble();
                        journeys.add(new JourneyRecord("METRO", new Metro(distance, factor)));
                    }
                }
            }
        } // Scanner safely auto-closes right here

        // Process all journeys uniformly via polymorphism
        double grandTotal = 0.0;
        for (JourneyRecord record : journeys) {
            double fare = record.transport.calculateFare();
            grandTotal += fare;
            System.out.printf("%s: %.2f%n", record.typeLabel, fare);
        }

        System.out.printf("%nTotal: %.2f%n", grandTotal);
    }
}
