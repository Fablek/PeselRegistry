import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class DateInput {

    public static Integer getYear(Scanner scanner) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int minYear = currentYear - 120;
        boolean retry;

        do {
            System.out.print("Podaj czterocyfrowy rok urodzenia, np. 1993: ");
            retry = false;

            try {
                Integer dateOfBirth = scanner.nextInt();

                if (minYear > dateOfBirth) {
                    throw new IllegalArgumentException("Błąd: Rok urodzenia jest za mały. Minimalny rok urodzenia to: " + minYear + ".");
                }

                if (dateOfBirth > currentYear) {
                    throw new IllegalArgumentException("Błąd: Rok urodzenia jest za duży. Nie możesz się urodzić w przyszłości.");
                }

                return dateOfBirth;

            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("Błąd: Wprowadź liczbę całkowitą.");

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            System.out.print("Podałeś złą formę roku, wciśnij 'p' jeśli chcesz podać ponownie rok lub wciśnij inny klawisz jeśli chcesz zakończyć: ");
            String choice = scanner.next();

            if (choice.equalsIgnoreCase("p")) {
                retry = true;
            }

        } while (retry);

        System.out.println("Zakończono wprowadzanie roku.");
        return null;
    }

    public static Integer getMonth(Scanner scanner) {
        boolean retry;

        do {
            System.out.print("Podaj miesiąc urodzenia, np. czerwiec albo 6: ");
            retry = false;

            String monthInput = scanner.next().toLowerCase(Locale.ROOT);

            try {
                Integer month = parseMonth(monthInput);

                if (month == null || month < 1 || month > 12) {
                    throw new IllegalArgumentException("Błąd: Podano nieprawidłowy miesiąc.");
                }

                return month;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            System.out.print("Podałeś złą formę miesiąca, wciśnij 'p' jeśli chcesz podać ponownie miesiąc lub wciśnij inny klawisz jeśli chcesz zakończyć: ");
            String choice = scanner.next();

            if (choice.equalsIgnoreCase("p")) {
                retry = true;
            }

        } while(retry);

        return null;
    }

    private static Integer parseMonth(String monthInput) {
        try {
            return Integer.parseInt(monthInput);
        } catch (NumberFormatException e) {
            switch (monthInput) {
                case "styczeń": return 1;
                case "luty": return 2;
                case "marzec": return 3;
                case "kwiecień": return 4;
                case "maj": return 5;
                case "czerwiec": return 6;
                case "lipiec": return 7;
                case "sierpień": return 8;
                case "wrzesień": return 9;
                case "październik": return 10;
                case "listopad": return 11;
                case "grudzień": return 12;
                default: return null;
            }
        }
    }

    public static Integer getDayOfMonth(Scanner scanner, int month, int year) {
        boolean retry;
        int maxDays = getDaysInMonth(month, year);

        do {
            System.out.print("Podaj numer dnia miesiąca urodzenia, np. 23: ");
            retry = false;

            try {
                int day = scanner.nextInt();

                if (day < 1 || day > maxDays) {
                    throw new IllegalArgumentException("Błąd: Podano nieprawidłowy numer dnia. " +
                            "Miesiąc " + month + " ma maksymalnie " + maxDays + " dni.");
                }

                return day;

            } catch (InputMismatchException e) {
                scanner.next(); // Clear invalid input
                System.out.println("Błąd: Wprowadź liczbę całkowitą.");

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            System.out.print("Podałeś zły numer, wciśnij 'p' jeśli chcesz podać ponownie numer dnia lub wciśnij inny klawisz jeśli chcesz zakończyć: ");
            String choice = scanner.next();

            if (choice.equalsIgnoreCase("p")) {
                retry = true;
            }

        } while (retry);

        System.out.println("Zakończono wprowadzanie dnia miesiąca.");
        return null;
    }

    private static Integer getDaysInMonth(int month, int year) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12: return 31;
            case 4: case 6: case 9: case 11: return 30;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                throw new IllegalArgumentException("Nieprawidłowy miesiąc.");
        }
    }
}