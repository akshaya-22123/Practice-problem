import java.util.Scanner;

// Interface to handle the "can it be insured?" design requirement cleanly
interface Insurable {
    default double calculateInsurance(double declaredValue) {
        return 0.02 * declaredValue;
    }
}

// Base abstract class for all parcel types
abstract class Parcel {
    protected double weight;
    protected double declaredValue;

    public Parcel(double weight, double declaredValue) {
        this.weight = weight;
        this.declaredValue = declaredValue;
    }

    // Every parcel must answer: "how much does shipping cost?"
    public abstract double getCharge();

    // Default insurance is 0.0 unless overridden or implementing Insurable
    public double getInsurance() {
        return 0.0;
    }

    public final double getTotal() {
        return getCharge() + getInsurance();
    }
}

// 1. Standard Parcel
class StandardParcel extends Parcel {
    public StandardParcel(double weight, double declaredValue) {
        super(weight, declaredValue);
    }

    @Override
    public double getCharge() {
        return 40.0 + (10.0 * weight);
    }
}

// 2. Express Parcel
class ExpressParcel extends Parcel implements Insurable {
    public ExpressParcel(double weight, double declaredValue) {
        super(weight, declaredValue);
    }

    @Override
    public double getCharge() {
        return 80.0 + (15.0 * weight);
    }

    @Override
    public double getInsurance() {
        return calculateInsurance(declaredValue);
    }
}

// 3. Fragile Parcel
class FragileParcel extends Parcel implements Insurable {
    public FragileParcel(double weight, double declaredValue) {
        super(weight, declaredValue);
    }

    @Override
    public double getCharge() {
        // Standard charge formula + 50 handling fee
        double standardCharge = 40.0 + (10.0 * weight);
        return standardCharge + 50.0;
    }

    @Override
    public double getInsurance() {
        return calculateInsurance(declaredValue);
    }
}

// Main Desk Execution Class
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            scanner.close();
            return;
        }

        int n = scanner.nextInt();
        double grandTotal = 0.0;

        for (int i = 0; i < n; i++) {
            if (!scanner.hasNext()) break;
            
            String type = scanner.next().toUpperCase();
            double weight = scanner.nextDouble();
            double declaredValue = scanner.nextDouble();

            Parcel parcel = null;

            // Factory logic to instantiate proper type
            switch (type) {
                case "STANDARD":
                    parcel = new StandardParcel(weight, declaredValue);
                    break;
                case "EXPRESS":
                    parcel = new ExpressParcel(weight, declaredValue);
                    break;
                case "FRAGILE":
                    parcel = new FragileParcel(weight, declaredValue);
                    break;
                default:
                    continue; // Skip invalid parcel types safely
            }

            double charge = parcel.getCharge();
            double insurance = parcel.getInsurance();
            double total = parcel.getTotal();
            grandTotal += total;

            System.out.printf("%s: Charge=%.2f Insurance=%.2f Total=%.2f\n", 
                    type, charge, insurance, total);
        }

        System.out.printf("Grand Total: %.2f\n", grandTotal);
        scanner.close();
    }
}
