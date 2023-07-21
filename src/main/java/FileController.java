import java.io.*;

public class FileController {
    private static final String FILE_NOT_FOUND_MSG = "Couldn't find the file %s%n";
    private static final String FILE_NOT_READABLE_MSG = "There was a problem with file %s reading%n";

    public static void calculateFromFile(String fileNameToRead, String fileNameToSave) {
        String operations = readFile(fileNameToRead);
        String[] results = calculateResults(operations.split("\n"));
        showResults(results);
        saveResults(results, fileNameToSave);
    }
    
    private static String readFile(String fileName) {
        try (var br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder builder = new StringBuilder();
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                builder.append(nextLine).append("\n");
            }
            return builder.toString();
        } catch (FileNotFoundException ex) {
            System.err.printf(FILE_NOT_FOUND_MSG, fileName);
            return "";
        } catch (IOException ex) {
            System.err.printf(FILE_NOT_READABLE_MSG, fileName);
            return "";
        }
    }

    private static String[] calculateResults(String[] operations) {
        String[] operationsWithResults = new String[operations.length];
        double a;
        String operator;
        double b;
        for (int i = 0; i < operations.length; i++) {
            String[] operation = operations[i].split(" ");
            a = Double.parseDouble(operation[0]);
            operator = operation[1];
            b = Double.parseDouble(operation[2]);
            double result = calculate(a, b, operator);
            operationsWithResults[i] = String.format("%s = %f%n", operations[i], result);
        }
        return operationsWithResults;
    }

    private static double calculate(double a, double b, String operator) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new IllegalArgumentException("Unproper operator");
        };
    }

    private static void showResults(String[] results) {
        for (String result : results) {
            System.out.print(result);
        }
    }

    private static void saveResults(String[] results, String fileName) {
        try (var bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String result : results) {
                bw.write(result);
            }
        } catch (FileNotFoundException ex) {
            System.err.printf(FILE_NOT_FOUND_MSG, fileName);
        } catch (IOException ex) {
            System.err.printf(FILE_NOT_READABLE_MSG, fileName);
        }
    }
}
