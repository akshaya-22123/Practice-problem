import java.util.Scanner;

// Abstract Base Class
abstract class LibraryItem {
    protected String title;
    protected int daysLate;

    public LibraryItem(String title, int daysLate) {
        this.title = title;
        this.daysLate = daysLate;
    }

    public abstract double calculateFine();
    
    public String getTitle() {
        return title;
    }
}

// Derived classes implementing specific business rules
class Book extends LibraryItem {
    public Book(String title, int daysLate) {
        super(title, daysLate);
    }

    @Override
    public double calculateFine() {
        return daysLate * 2.0;
    }
}

class DVD extends LibraryItem {
    public DVD(String title, int daysLate) {
        super(title, daysLate);
    }

    @Override
    public double calculateFine() {
        double fine = daysLate * 5.0;
        return Math.min(fine, 50.0);
    }
}

class Magazine extends LibraryItem {
    public Magazine(String title, int daysLate) {
        super(title, daysLate);
    }

    @Override
    public double calculateFine() {
        return daysLate * 1.0;
    }
}

// Main Driver Class - Save this file as LibraryFineCounter.java
public class LibraryFineCounter {
    public static void main(String[] args) {
        // Fixes Warning 1 & 2: try-with-resources automatically closes the scanner safely
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) return;

            int n = scanner.nextInt();
            double totalFines = 0.0;

            for (int i = 0; i < n; i++) {
                String type = scanner.next().toUpperCase();
                String title = scanner.next();
                int daysLate = scanner.nextInt();

                // Fixes Warning 3: Uses modern rule-based switch expressions (arrow syntax)
                LibraryItem item = switch (type) {
                    case "BOOK" -> new Book(title, daysLate);
                    case "DVD" -> new DVD(title, daysLate);
                    case "MAGAZINE" -> new Magazine(title, daysLate);
                    default -> null;
                };

                if (item != null) {
                    double fine = item.calculateFine();
                    totalFines += fine;
                    System.out.printf("%s: %.2f\n", item.getTitle(), fine);
                }
            }

            System.out.printf("Total Fines: %.2f\n", totalFines);
        } 
    }
}
