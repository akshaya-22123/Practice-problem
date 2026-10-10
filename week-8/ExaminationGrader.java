import java.util.*;

// Abstract Base Class representing a generic question
abstract class Question {
    protected String text;
    protected String correctAnswer;
    protected double points;

    public Question(String text, String correctAnswer, double points) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }

    public abstract double grade(String studentAnswer);
}

// Multiple Choice Question subclass
class MCQQuestion extends Question {
    public MCQQuestion(String text, String correctAnswer, double points) {
        super(text, correctAnswer, points);
    }

    @Override
    public double grade(String studentAnswer) {
        return studentAnswer.equals(correctAnswer) ? points : 0.0;
    }
}

// True/False Question subclass
class TFQuestion extends Question {
    public TFQuestion(String text, String correctAnswer, double points) {
        super(text, correctAnswer, points);
    }

    @Override
    public double grade(String studentAnswer) {
        return studentAnswer.equals(correctAnswer) ? points : 0.0;
    }
}

// Essay Question subclass with partial grading logic
class EssayQuestion extends Question {
    public EssayQuestion(String text, String correctAnswer, double points) {
        super(text, correctAnswer, points);
    }

    @Override
    public double grade(String studentAnswer) {
        String[] keywords = correctAnswer.split(",");
        String studentAnswerLower = studentAnswer.toLowerCase();
        int matchCount = 0;

        for (String kw : keywords) {
            if (studentAnswerLower.contains(kw.trim().toLowerCase())) {
                matchCount++;
            }
        }

        if (matchCount >= 2) {
            return points * 0.75;
        } else if (matchCount == 1) {
            return points * 0.50;
        }
        return 0.0;
    }
}

// Main class execution
public class ExaminationGrader {
    public static void main(String[] args) {
        // Solved resource leak & try-with-resources warnings
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextLine()) return;

            int numQuestions = Integer.parseInt(scanner.nextLine().trim());
            double totalScore = 0.0;

            for (int i = 0; i < numQuestions; i++) {
                if (!scanner.hasNextLine()) break;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                List<String> tokens = parseLine(line);
                if (tokens.size() < 5) continue;

                String type = tokens.get(0);
                String text = tokens.get(1);
                String correctAnswer = tokens.get(2);
                String studentAnswer = tokens.get(3);
                double points = Double.parseDouble(tokens.get(4));

                // Solved modern rule switch warning
                Question question = switch (type) {
                    case "MCQ" -> new MCQQuestion(text, correctAnswer, points);
                    case "TF" -> new TFQuestion(text, correctAnswer, points);
                    case "ESSAY", "CLASSROOM" -> new EssayQuestion(text, correctAnswer, points);
                    default -> null;
                };

                if (question != null) {
                    double score = question.grade(studentAnswer);
                    totalScore += score;
                    System.out.printf(Locale.US, "%s: %.2f\n", type, score);
                }
            }

            System.out.printf(Locale.US, "Total Score: %.2f\n", totalScore);
        }
    }

    // Helper method to split spaces while preserving text inside quotes
    private static List<String> parseLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ' ' && !inQuotes) {
                if (sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb.setLength(0);
                }
            } else {
                sb.append(c);
            }
        }
        if (sb.length() > 0) {
            tokens.add(sb.toString());
        }
        return tokens;
    }
}
