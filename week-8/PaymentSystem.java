import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// 1. Strategy Interface
interface PaymentMethod {
    double calculateTotal(double amount);
}

// 2. Concrete Strategy for CARD
class CardPayment implements PaymentMethod {
    @Override
    public double calculateTotal(double amount) {
        return amount * 1.02; // 2% processing fee
    }
}

// 3. Concrete Strategy for WALLET
class WalletPayment implements PaymentMethod {
    @Override
    public double calculateTotal(double amount) {
        return amount * 1.01; // 1% processing fee
    }
}

// 4. Concrete Strategy for BANKTRANSFER
class BankTransferPayment implements PaymentMethod {
    @Override
    public double calculateTotal(double amount) {
        return amount; // No processing fee
    }
}

// 5. Factory to resolve payment strategies uniformly
class PaymentFactory {
    private static final Map<String, PaymentMethod> strategies = new HashMap<>();

    static {
        strategies.put("CARD", new CardPayment());
        strategies.put("WALLET", new WalletPayment());
        strategies.put("BANKTRANSFER", new BankTransferPayment());
    }

    public static PaymentMethod getStrategy(String type) {
        return strategies.getOrDefault(type.toUpperCase(), new BankTransferPayment());
    }
}

// 6. Main execution class
public class PaymentSystem {
    public static void main(String[] args) {
        // Declaring scanner inside the try block eliminates resource leak warnings automatically
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) return;
            int n = scanner.nextInt();
            
            double grandTotal = 0.0;
            
            for (int i = 0; i < n; i++) {
                String paymentType = scanner.next();
                double amount = scanner.nextDouble();
                
                // Uniformly handle transactions via polymorphism
                PaymentMethod method = PaymentFactory.getStrategy(paymentType);
                double adjustedAmount = method.calculateTotal(amount);
                
                grandTotal += adjustedAmount;
                
                // Display adjusted amount per transaction
                System.out.printf("%s: %.2f\n", paymentType.toUpperCase(), adjustedAmount);
            }
            
            // Display final grand total
            System.out.printf("Total: %.2f\n", grandTotal);
        } // The scanner closes automatically here
    }
}
