import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PeselGenerator {

    private static final String FILE_NAME = "pesel.txt";
    private static final List<String> peselList = new ArrayList<>();

    public static String generatePesel(int year, int month, int day, String gender, int orderNumber) {
        StringBuilder pesel = new StringBuilder();

        // Year
        pesel.append(String.format("%02d", year % 100));

        // Month - Adjust month based on year range
        if (year >= 2000 && year < 2100) {
            month += 20;
        } else if (year >= 2100 && year < 2200) {
            month += 40;
        } else if (year >= 2200 && year < 2300) {
            month += 60;
        }
        pesel.append(String.format("%02d", month));

        // Day
        pesel.append(String.format("%02d", day));

        // Order number
        pesel.append(String.format("%03d", orderNumber));

        // Gender
        int genderDigit = (gender.equals("k") ? 0 : 1) + orderNumber * 2 % 10;
        pesel.append(genderDigit);

        // Control digit
        pesel.append(calculateControlDigit(pesel.toString()));

        return pesel.toString();
    }

    private static int calculateControlDigit(String peselWithoutControl) {
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;

        for (int i = 0; i < weights.length; i++) {
            sum += (peselWithoutControl.charAt(i) - '0') * weights[i];
        }

        return (10 - (sum % 10)) % 10;
    }

    public static void savePesel(String pesel) {
        peselList.add(pesel);
        Collections.sort(peselList);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String peselEntry : peselList) {
                writer.write(peselEntry);
                writer.newLine();
            }
            System.out.println("PESEL saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving PESEL: " + e.getMessage());
        }
    }
}