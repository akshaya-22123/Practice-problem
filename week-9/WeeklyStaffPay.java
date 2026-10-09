import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Abstract Base Class for Staff Member
abstract class StaffMember {
    protected final String name; // Fixed: Made final

    public StaffMember(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Abstract method to be overridden by subclasses
    public abstract double calculatePay();
}

// Full-time Staff subclass
class FullTimeStaff extends StaffMember {
    private final double weeklySalary; // Fixed: Made final

    public FullTimeStaff(String name, double weeklySalary) {
        super(name);
        this.weeklySalary = weeklySalary;
    }

    @Override
    public double calculatePay() {
        return weeklySalary;
    }
}

// Hourly Staff subclass with overtime
class HourlyStaff extends StaffMember {
    private final double hours; // Fixed: Made final
    private final double rate;  // Fixed: Made final

    public HourlyStaff(String name, double hours, double rate) {
        super(name);
        this.hours = hours;
        this.rate = rate;
    }

    @Override
    public double calculatePay() {
        if (hours <= 40) {
            return hours * rate;
        } else {
            double regularPay = 40 * rate;
            double overtimeHours = hours - 40;
            double overtimePay = overtimeHours * (1.5 * rate);
            return regularPay + overtimePay;
        }
    }
}

// Intern subclass
class Intern extends StaffMember {
    private final double stipend; // Fixed: Made final

    public Intern(String name, double stipend) {
        super(name);
        this.stipend = stipend;
    }

    @Override
    public double calculatePay() {
        return stipend;
    }
}

// Main execution class matching the filename
public class WeeklyStaffPay {
    public static void main(String[] args) {
        List<StaffMember> staffList = new ArrayList<>();
        double totalPayroll = 0.0;

        // Fixed: Converted to try-with-resources (auto-closes scanner)
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                return;
            }
            
            int n = scanner.nextInt();

            for (int i = 0; i < n; i++) {
                if (!scanner.hasNext()) break;
                String staffType = scanner.next();
                String name = scanner.next();

                // Fixed: Modern arrow switch (Rule Switch) removes tedious breaks
                switch (staffType) {
                    case "FULLTIME" -> {
                        double weeklySalary = scanner.nextDouble();
                        staffList.add(new FullTimeStaff(name, weeklySalary));
                    }
                    case "HOURLY" -> {
                        double hours = scanner.nextDouble();
                        double rate = scanner.nextDouble();
                        staffList.add(new HourlyStaff(name, hours, rate));
                    }
                    case "INTERN" -> {
                        double stipend = scanner.nextDouble();
                        staffList.add(new Intern(name, stipend));
                    }
                    default -> {
                        // Skip unknown staff types
                    }
                }
            }
        } // Scanner explicitly closes here automatically

        // Print individual payroll
        for (StaffMember staff : staffList) {
            double pay = staff.calculatePay();
            totalPayroll += pay;
            System.out.printf("%s: %.2f%n", staff.getName(), pay);
        }

        // Print total payroll formatted to two decimal places
        System.out.printf("Total Payroll: %.2f%n", totalPayroll);
    }
}
