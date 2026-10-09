import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Abstract base class to store common properties
abstract class Student {
    protected String name;
    // Keep the transport fee in one place as requested in the hint
    protected final double TRANSPORT_FEE = 12000.0;

    protected Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Abstract method to dynamically calculate fees based on type
    public abstract double calculateFee();
}

// Day Scholar Implementation
class DayScholar extends Student {
    public DayScholar(String name) {
        super(name);
    }

    @Override
    public double calculateFee() {
        double tuition = 40000.0;
        return tuition + TRANSPORT_FEE;
    }
}

// Hosteller Implementation
class Hosteller extends Student {
    public Hosteller(String name) {
        super(name);
    }

    @Override
    public double calculateFee() {
        double tuition = 40000.0;
        double hostelFee = 60000.0;
        return tuition + hostelFee; // Does not use the bus
    }
}

// Scholarship Student Implementation
class Scholar extends Student {
    public Scholar(String name) {
        super(name);
    }

    @Override
    public double calculateFee() {
        double tuition = 20000.0; // Half tuition
        return tuition + TRANSPORT_FEE;
    }
}

// Main Counter Class matching the filename
public class Solution {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        double totalCollected = 0.0;

        // Try-with-resources safely closes the scanner automatically
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                return;
            }
            
            int n = scanner.nextInt();

            // Process inputs
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNext()) break;
                String type = scanner.next();
                
                if (!scanner.hasNext()) break;
                String name = scanner.next();

                // Using modern rule switch blocks (Eliminates break warnings)
                switch (type) {
                    case "DAY_SCHOLAR" -> students.add(new DayScholar(name));
                    case "HOSTELLER"   -> students.add(new Hosteller(name));
                    case "SCHOLAR"     -> students.add(new Scholar(name));
                    default            -> { /* Handle unexpected types if necessary */ }
                }
            }
        } // Scanner auto-closes here

        // Print individual output and accumulate total
        for (Student student : students) {
            double fee = student.calculateFee();
            System.out.printf("%s: %.2f%n", student.getName(), fee);
            totalCollected += fee;
        }

        // Print final total
        System.out.printf("Total Collected: %.2f%n", totalCollected);
    }
}