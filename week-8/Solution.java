import java.time.LocalDate;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        // Safe resource handling using try-with-resources
        try (Scanner scanner = new Scanner(System.in)) {
            
            if (!scanner.hasNextInt()) {
                return;
            }
            int n = scanner.nextInt();
            
            // Loop through each subscriber line
            for (int i = 0; i < n; i++) {
                String planType = scanner.next();
                String name = scanner.next();
                String startDateStr = scanner.next();
                
                // Automatically handles standard YYYY-MM-DD strings
                LocalDate startDate = LocalDate.parse(startDateStr);
                
                // Modern Java switch expression
                LocalDate renewalDate = switch (planType.toUpperCase()) {
                    case "BASIC" -> startDate.plusDays(30);
                    case "STANDARD" -> startDate.plusDays(90);
                    case "PREMIUM" -> startDate.plusDays(365);
                    default -> null; 
                };
                
                // Print output in required format if plan is valid
                if (renewalDate != null) {
                    System.out.println(name + ": " + renewalDate);
                }
            }
        } 
    }
}