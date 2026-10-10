import java.util.Scanner;

public class HostelElectricityBill {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        if (!scanner.hasNextInt()) {
            scanner.close();
            return;
        }
        int n = scanner.nextInt();
        
        double totalGrandTotal = 0.0;
        
        for (int i = 0; i < n; i++) {
            String roomType = scanner.next();
            int units = scanner.nextInt();
            double bill = 0.0;
            
            if (roomType.equalsIgnoreCase("SINGLE")) {
                bill = units * 8.0;
            } else if (roomType.equalsIgnoreCase("SHARED")) {
                int occupants = scanner.nextInt();
                bill = (units * 6.0) / occupants;
            } else if (roomType.equalsIgnoreCase("AC")) {
                bill = (units * 10.0) + 200.0;
            }
            
            totalGrandTotal += bill;
            System.out.printf("%s: %.2f\n", roomType, bill);
        }
        
        System.out.printf("Total: %.2f\n", totalGrandTotal);
        
        scanner.close();
    }
}
