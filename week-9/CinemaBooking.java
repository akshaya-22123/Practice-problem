import java.util.Scanner;

// Base class handling shared components
abstract class Ticket {
    public static final double CONVENIENCE_FEE = 20.00;
    protected int count;

    public Ticket(int count) {
        this.count = count;
    }

    public abstract double getSeatPrice();

    public double calculateAmount() {
        return this.count * (this.getSeatPrice() + CONVENIENCE_FEE);
    }
}

// Subclasses defining unique prices
class RegularTicket extends Ticket {
    public RegularTicket(int count) { super(count); }
    
    @Override
    public double getSeatPrice() { return 150.00; }
}

class PremiumTicket extends Ticket {
    public PremiumTicket(int count) { super(count); }
    
    @Override
    public double getSeatPrice() { return 250.00; }
}

class ReclinerTicket extends Ticket {
    public ReclinerTicket(int count) { super(count); }
    
    @Override
    public double getSeatPrice() { return 400.00; }
}

// Main class matches your workspace build project name
public class CinemaBooking {
    public static void main(String[] args) {
        // Wrapped in try-with-resources to fix the resource leak warning
        try (Scanner scanner = new Scanner(System.in)) {
            
            if (!scanner.hasNextInt()) return;
            int numBookings = scanner.nextInt();
            
            double totalCollected = 0.00;

            for (int i = 0; i < numBookings; i++) {
                if (!scanner.hasNext()) break;
                String seatType = scanner.next().toUpperCase();
                
                if (!scanner.hasNextInt()) break;
                int count = scanner.nextInt();

                Ticket ticket;

                // Using standard arrow rule switches to resolve the switch-to-rule-switch warnings
                switch (seatType) {
                    case "REGULAR" -> ticket = new RegularTicket(count);
                    case "PREMIUM" -> ticket = new PremiumTicket(count);
                    case "RECLINER" -> ticket = new ReclinerTicket(count);
                    default -> {
                        continue; // Skips invalid inputs safely
                    }
                }

                // Removed potential null pointer vulnerability
                double amount = ticket.calculateAmount();
                totalCollected += amount;

                System.out.printf("%s: %.2f%n", seatType, amount);
            }

            System.out.printf("Total: %.2f%n", totalCollected);
        } 
    }
}