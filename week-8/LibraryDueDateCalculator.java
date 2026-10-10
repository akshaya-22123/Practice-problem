import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

abstract class LibraryItem {
    protected String title;

    public LibraryItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract int getBorrowDuration();

    public String calculateDueDate(LocalDate currentDate) {
        LocalDate dueDate = currentDate.plusDays(getBorrowDuration());
        return dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

class Book extends LibraryItem {
    public Book(String title) { super(title); }
    @Override public int getBorrowDuration() { return 14; }
}

class Dvd extends LibraryItem {
    public Dvd(String title) { super(title); }
    @Override public int getBorrowDuration() { return 7; }
}

class Magazine extends LibraryItem {
    public Magazine(String title) { super(title); }
    @Override public int getBorrowDuration() { return 3; }
}

public class LibraryDueDateCalculator {
    public static void main(String[] args) {
        // Warning 1 Fix: Try-with-resources ensures the scanner automatically closes safely
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                return;
            }

            int n = scanner.nextInt();
            scanner.nextLine(); 

            LocalDate currentDate = LocalDate.of(2023, 10, 26);

            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextLine()) break;
                
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                int firstSpaceIdx = line.indexOf(' ');
                if (firstSpaceIdx == -1) continue;

                String type = line.substring(0, firstSpaceIdx);
                String title = line.substring(firstSpaceIdx + 1).trim();

                if (title.startsWith("\"") && title.endsWith("\"")) {
                    title = title.substring(1, title.length() - 1);
                }

                // Warning 2, 3, & 4 Fix: Modern arrow switch syntax prevents unused values and null pointer risks
                LibraryItem item = switch (type) {
                    case "BOOK" -> new Book(title);
                    case "DVD" -> new Dvd(title);
                    case "MAGAZINE" -> new Magazine(title);
                    default -> null;
                };

                // Skip processing if the input type didn't match any library items
                if (item == null) {
                    continue;
                }

                System.out.println(item.getTitle() + ": " + item.calculateDueDate(currentDate));
            }
        }
    }
}
