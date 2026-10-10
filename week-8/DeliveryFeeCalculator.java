import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Abstract Base Class representing a general Delivery Request
abstract class DeliveryRequest {
    protected String deliveryType;
    protected double weight;
    protected double distance;

    public DeliveryRequest(String deliveryType, double weight, double distance) {
        this.deliveryType = deliveryType;
        this.weight = weight;
        this.distance = distance;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    // Abstract method to be overridden by specific delivery types
    public abstract double calculateFee();
}

// Subclass for Standard Delivery
class StandardDelivery extends DeliveryRequest {
    public StandardDelivery(String deliveryType, double weight, double distance) {
        super(deliveryType, weight, distance);
    }

    @Override
    public double calculateFee() {
        return 5.00 + (weight * 0.50) + (distance * 0.10);
    }
}

// Subclass for Express Delivery
class ExpressDelivery extends DeliveryRequest {
    public ExpressDelivery(String deliveryType, double weight, double distance) {
        super(deliveryType, weight, distance);
    }

    @Override
    public double calculateFee() {
        return 15.00 + (weight * 1.00) + (distance * 0.20);
    }
}

// Subclass for International Delivery
class InternationalDelivery extends DeliveryRequest {
    // Fixed: Marked as 'final' to satisfy the immutability recommendation
    private final double customsFee;

    public InternationalDelivery(String deliveryType, double weight, double distance, double customsFee) {
        super(deliveryType, weight, distance);
        this.customsFee = customsFee;
    }

    @Override
    public double calculateFee() {
        return 25.00 + (weight * 2.00) + (distance * 0.50) + customsFee;
    }
}

// Main Driving Class matching the requested file name
public class DeliveryFeeCalculator {
    public static void main(String[] args) {
        
        // Fixed: Implemented try-with-resources to safely handle standard input streams automatically
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                return;
            }
            
            int n = scanner.nextInt();
            List<DeliveryRequest> deliveries = new ArrayList<>();

            // Parse each delivery request from input
            for (int i = 0; i < n; i++) {
                String type = scanner.next();
                double weight = scanner.nextDouble();
                double distance = scanner.nextDouble();

                if (type.equalsIgnoreCase("STANDARD")) {
                    deliveries.add(new StandardDelivery(type, weight, distance));
                } else if (type.equalsIgnoreCase("EXPRESS")) {
                    deliveries.add(new ExpressDelivery(type, weight, distance));
                } else if (type.equalsIgnoreCase("INTERNATIONAL")) {
                    double customsFee = scanner.nextDouble();
                    deliveries.add(new InternationalDelivery(type, weight, distance, customsFee));
                }
            }

            double grandTotal = 0.0;

            // Process requests uniformly using Polymorphism
            for (DeliveryRequest delivery : deliveries) {
                double fee = delivery.calculateFee();
                grandTotal += fee;
                System.out.printf("%s: %.2f%n", delivery.getDeliveryType(), fee);
            }

            // Print grand total output
            System.out.printf("Total: %.2f%n", grandTotal);
        }
    }
}