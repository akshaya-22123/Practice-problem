import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Abstract Base Class for Vehicle
abstract class Vehicle {
    protected String vehicleType;
    protected int hours;

    public Vehicle(String vehicleType, int hours) {
        this.vehicleType = vehicleType;
        this.hours = hours;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public abstract double calculateCharge();
}

// Concrete subclass for Bike
class Bike extends Vehicle {
    public Bike(String vehicleType, int hours) {
        super(vehicleType, hours);
    }

    @Override
    public double calculateCharge() {
        return this.hours * 10.0;
    }
}

// Concrete subclass for Car
class Car extends Vehicle {
    public Car(String vehicleType, int hours) {
        super(vehicleType, hours);
    }

    @Override
    public double calculateCharge() {
        return 30.0 + (this.hours - 1) * 20.0;
    }
}

// Concrete subclass for Truck
class Truck extends Vehicle {
    public Truck(String vehicleType, int hours) {
        super(vehicleType, hours);
    }

    @Override
    public double calculateCharge() {
        double charge = this.hours * 50.0;
        return Math.max(charge, 100.0);
    }
}

// Main Public Class
public class ParkingCalculator {
    public static void main(String[] args) {
        List<Vehicle> vehiclesList = new ArrayList<>();

        // Fix 1: Using try-with-resources to manage the Scanner lifecycle automatically
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                return;
            }
            
            int n = scanner.nextInt();

            // Reading input entries
            for (int i = 0; i < n; i++) {
                if (scanner.hasNext()) {
                    String type = scanner.next().toUpperCase();
                    int hours = scanner.nextInt();

                    // Fix 2: Updated classic switch to modern Java Rule Switch syntax (->)
                    switch (type) {
                        case "BIKE" -> vehiclesList.add(new Bike(type, hours));
                        case "CAR" -> vehiclesList.add(new Car(type, hours));
                        case "TRUCK" -> vehiclesList.add(new Truck(type, hours));
                        default -> { }
                    }
                }
            }
        } // Scanner closes automatically here without extra warnings

        double totalCharge = 0.0;

        // Polymorphic charge computation
        for (Vehicle vehicle : vehiclesList) {
            double charge = vehicle.calculateCharge();
            totalCharge += charge;
            System.out.printf("%s: %.2f\n", vehicle.getVehicleType(), charge);
        }

        // Print Grand Total
        System.out.printf("Total: %.2f\n", totalCharge);
    }
}
