import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Base Abstract Class
abstract class Employee {
    protected String name;
    protected double monthlySalary;

    public Employee(String name, double monthlySalary) {
        this.name = name;
        this.monthlySalary = monthlySalary;
    }

    public String getName() {
        return name;
    }

    // Abstract method overridden polymorphically by subclasses
    public abstract double calculateBonus();
}

// Full-Time Employee Subclass
class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name, double monthlySalary) {
        super(name, monthlySalary);
    }

    @Override
    public double calculateBonus() {
        return this.monthlySalary * 0.10; // 10% bonus
    }
}

// Part-Time Employee Subclass
class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name, double monthlySalary) {
        super(name, monthlySalary);
    }

    @Override
    public double calculateBonus() {
        return this.monthlySalary * 0.05; // 5% bonus
    }
}

// Intern Subclass
class InternEmployee extends Employee {
    public InternEmployee(String name, double monthlySalary) {
        super(name, monthlySalary);
    }

    @Override
    public double calculateBonus() {
        return 2000.00; // Fixed bonus of ₹2,000
    }
}

// Main Runner Class
public class FestivalBonusCalculator {
    public static void main(String[] args) {
        // Safe resource management via try-with-resources
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) return;
            int n = scanner.nextInt();
            
            List<Employee> employees = new ArrayList<>();
            
            // Populating list polymorphically using modern rule switches
            for (int i = 0; i < n; i++) {
                String type = scanner.next();
                String name = scanner.next();
                double salary = scanner.nextDouble();
                
                switch (type) {
                    case "FULLTIME" -> employees.add(new FullTimeEmployee(name, salary));
                    case "PARTTIME" -> employees.add(new PartTimeEmployee(name, salary));
                    case "INTERN"   -> employees.add(new InternEmployee(name, salary));
                }
            }
            
            double totalBonus = 0.0;
            
            // Processing loop without explicit type checking
            for (Employee emp : employees) {
                double bonus = emp.calculateBonus();
                System.out.printf("%s: %.2f\n", emp.getName(), bonus);
                totalBonus += bonus;
            }
            
            System.out.printf("Total Bonus: %.2f\n", totalBonus);
        } 
    }
}
