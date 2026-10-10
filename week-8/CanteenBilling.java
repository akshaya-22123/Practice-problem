import java.util.*;

// Base interface for bill calculation strategy
interface BillingStrategy {
    double calculateFinalAmount(double baseAmount);
}

// Concrete strategies for each customer type
class StudentBilling implements BillingStrategy {
    @Override
    public double calculateFinalAmount(double baseAmount) {
        return baseAmount * 0.90; // 10% discount
    }
}

class StaffBilling implements BillingStrategy {
    @Override
    public double calculateFinalAmount(double baseAmount) {
        return baseAmount * 0.95; // 5% discount
    }
}

class GuestBilling implements BillingStrategy {
    @Override
    public double calculateFinalAmount(double baseAmount) {
        return baseAmount + 10.0; // ₹10 service charge
    }
}

// Representing a single Bill item
class Bill {
    private final String customerType;
    private final double baseAmount;
    private final BillingStrategy strategy;

    public Bill(String customerType, double baseAmount, BillingStrategy strategy) {
        this.customerType = customerType;
        this.baseAmount = baseAmount;
        this.strategy = strategy;
    }

    public double getFinalAmount() {
        return strategy.calculateFinalAmount(baseAmount);
    }

    public void displayBill() {
        System.out.printf("%s: %.2f\n", customerType, getFinalAmount());
    }
}

// Main execution class matching the filename CanteenBilling.java
public class CanteenBilling {
    public static void main(String[] args) {
        
        // Strategy map initialization
        Map<String, BillingStrategy> strategyMap = new HashMap<>();
        strategyMap.put("STUDENT", new StudentBilling());
        strategyMap.put("STAFF", new StaffBilling());
        strategyMap.put("GUEST", new GuestBilling());

        // Try-with-resources block safely handles closing the scanner automatically
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                return;
            }
            int n = scanner.nextInt();
            
            List<Bill> bills = new ArrayList<>();
            double grandTotal = 0.0;

            for (int i = 0; i < n; i++) {
                String type = scanner.next();
                double amount = scanner.nextDouble();
                
                BillingStrategy strategy = strategyMap.get(type.toUpperCase());
                if (strategy != null) {
                    Bill bill = new Bill(type, amount, strategy);
                    bills.add(bill);
                }
            }

            // Process bills polymorphically
            for (Bill bill : bills) {
                bill.displayBill();
                grandTotal += bill.getFinalAmount();
            }

            System.out.printf("Total: %.2f\n", grandTotal);
        }
    }
}