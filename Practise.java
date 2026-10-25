import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Practise {

    public static void main(String[] args) {

        String filePath = "application.log";

        int infoCount = 0;
        int warningCount = 0;
        int errorCount = 0;
        int totalCount = 0;

        try (BufferedReader br =
                     new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = br.readLine()) != null) {

                String upperLine = line.toUpperCase();

                if (upperLine.contains("INFO")) {
                    infoCount++;
                    totalCount++;
                } else if (upperLine.contains("WARNING")) {
                    warningCount++;
                    totalCount++;
                } else if (upperLine.contains("ERROR")) {
                    errorCount++;
                    totalCount++;
                }
            }

            System.out.println("===== LOG STATISTICS =====");
            System.out.println("Total log entries: " + totalCount);

            System.out.println("INFO count: " + infoCount);
            System.out.println("WARNING count: " + warningCount);
            System.out.println("ERROR count: " + errorCount);

            if (totalCount > 0) {
                System.out.printf("INFO percentage: %.2f%%%n",
                        infoCount * 100.0 / totalCount);

                System.out.printf("WARNING percentage: %.2f%%%n",
                        warningCount * 100.0 / totalCount);

                System.out.printf("ERROR percentage: %.2f%%%n",
                        errorCount * 100.0 / totalCount);
            }

        } catch (IOException e) {
            System.out.println("Error reading log file: "
                    + e.getMessage());
        }
    }
}
