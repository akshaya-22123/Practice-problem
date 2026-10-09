
import java.util.Scanner;

// 1. Make this class public so it matches the file name "TravelBooking.java"
public abstract class TravelBooking {
    protected static final double BOOKING_FEE = 50.0;
    protected double distanceKm;

    public TravelBooking(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public abstract double calculateBaseFare();

    public double calculateTotalFare() {
        return calculateBaseFare() + BOOKING_FEE;
    }
}

class BusBooking extends TravelBooking {
    public BusBooking(double distanceKm) {
        super(distanceKm);
    }

    @Override
    public double calculateBaseFare() {
        return 2.0 * distanceKm;
    }
}

class TrainBooking extends TravelBooking {
    public TrainBooking(double distanceKm) {
        super(distanceKm);
    }

    @Override
    public double calculateBaseFare() {
        return 1.5 * distanceKm;
    }
}

class FlightBooking extends TravelBooking {
    public FlightBooking(double distanceKm) {
        super(distanceKm);
    }

    @Override
    public double calculateBaseFare() {
        return 2500.0 + (4.0 * distanceKm);
    }
}

// 2. Remove the "public" keyword from Main
class Main {
    public static void main(String[] args) {
        // To fix the "try-with-resources" warning, we wrap the Scanner setup
        try (Scanner sc = new Scanner(System.in)) {
            if (!sc.hasNextInt()) return;
            
            int n = sc.nextInt();

            for (int i = 0; i < n; i++) {
                String mode = sc.next();
                double distance = sc.nextDouble();
                TravelBooking booking = null;

                switch (mode.toUpperCase()) {
                    case "BUS" -> booking = new BusBooking(distance);
                    case "TRAIN" -> booking = new TrainBooking(distance);
                    case "FLIGHT" -> booking = new FlightBooking(distance);
                }

                if (booking != null) {
                    System.out.printf("%s: %.2f\n", mode.toUpperCase(), booking.calculateTotalFare());
                }
            }
        } // The scanner automatically closes safely here
    }
}