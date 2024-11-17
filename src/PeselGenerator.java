import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        loadExistingPesel();
        peselList.add(pesel);
        peselList.sort(new PeselComparator());
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

    private static void loadExistingPesel() {
        peselList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    peselList.add(line.trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found, starting with an empty list.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static class PeselComparator implements Comparator<String> {
        @Override
        public int compare(String pesel1, String pesel2) {
            // Extract date and gender
            int year1 = getYearFromPesel(pesel1);
            int year2 = getYearFromPesel(pesel2);
            int month1 = getMonthFromPesel(pesel1);
            int month2 = getMonthFromPesel(pesel2);
            int day1 = getDayFromPesel(pesel1);
            int day2 = getDayFromPesel(pesel2);
            boolean isFemale1 = isFemale(pesel1);
            boolean isFemale2 = isFemale(pesel2);

            // Compare years
            if (year1 != year2) return Integer.compare(year1, year2);

            // Compare months
            if (month1 != month2) return Integer.compare(month1, month2);

            // Compare days
            if (day1 != day2) return Integer.compare(day1, day2);

            // Compare gender (females first)
            return Boolean.compare(isFemale2, isFemale1);
        }

        private int getYearFromPesel(String pesel) {
            int year = Integer.parseInt(pesel.substring(0, 2));
            int month = Integer.parseInt(pesel.substring(2, 4));
            if (month > 80) year += 1800;
            else if (month > 60) year += 2200;
            else if (month > 40) year += 2100;
            else if (month > 20) year += 2000;
            else year += 1900;
            return year;
        }

        private int getMonthFromPesel(String pesel) {
            int month = Integer.parseInt(pesel.substring(2, 4));
            if (month > 80) return month - 80;
            else if (month > 60) return month - 60;
            else if (month > 40) return month - 40;
            else if (month > 20) return month - 20;
            else return month;
        }

        private int getDayFromPesel(String pesel) {
            return Integer.parseInt(pesel.substring(4, 6));
        }

        private boolean isFemale(String pesel) {
            int genderDigit = Character.getNumericValue(pesel.charAt(9));
            return genderDigit % 2 == 0;
        }
    }
}