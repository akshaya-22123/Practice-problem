import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Abstract Base Class to allow open-closed design
abstract class Plot {
    protected String owner;
    protected String shapeType;

    public Plot(String owner, String shapeType) {
        this.owner = owner;
        this.shapeType = shapeType;
    }

    public abstract double getArea();

    public void printReport() {
        System.out.printf("%s (%s): %.2f%n", owner, shapeType, getArea());
    }
}

// Circle subclass
class CirclePlot extends Plot {
    // Fixed: Added 'final' keyword to fields
    private final double radius;

    public CirclePlot(String owner, double radius) {
        super(owner, "CIRCLE");
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}

// Rectangle subclass
class RectanglePlot extends Plot {
    // Fixed: Added 'final' keyword to fields
    private final double length;
    private final double width;

    public RectanglePlot(String owner, double length, double width) {
        super(owner, "RECTANGLE");
        this.length = length;
        this.width = width;
    }

    @Override
    public double getArea() {
        return length * width;
    }
}

// Triangle subclass
class TrianglePlot extends Plot {
    // Fixed: Added 'final' keyword to fields
    private final double base;
    private final double height;

    public TrianglePlot(String owner, double base, double height) {
        super(owner, "TRIANGLE");
        this.base = base;
        this.height = height;
    }

    @Override
    public double getArea() {
        return 0.5 * base * height;
    }
}

// Main Class
public class GardenReport {
    public static void main(String[] args) {
        // Fixed: Wrapped Scanner in a try-with-resources block to auto-close it
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) return;
            int n = scanner.nextInt();
            
            List<Plot> plots = new ArrayList<>();
            
            for (int i = 0; i < n; i++) {
                String shape = scanner.next();
                String owner = scanner.next();
                
                if (shape.equalsIgnoreCase("CIRCLE")) {
                    double radius = scanner.nextDouble();
                    plots.add(new CirclePlot(owner, radius));
                } else if (shape.equalsIgnoreCase("RECTANGLE")) {
                    double length = scanner.nextDouble();
                    double width = scanner.nextDouble();
                    plots.add(new RectanglePlot(owner, length, width));
                } else if (shape.equalsIgnoreCase("TRIANGLE")) {
                    double base = scanner.nextDouble();
                    double height = scanner.nextDouble();
                    plots.add(new TrianglePlot(owner, base, height));
                }
            }
            
            double totalArea = 0;
            for (Plot plot : plots) {
                plot.printReport();
                totalArea += plot.getArea();
            }
            
            System.out.printf("Total Area: %.2f%n", totalArea);
        }
    }
}