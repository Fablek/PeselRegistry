import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static Integer getYear(Scanner scanner) {
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
                scanner.next(); // Clear invalid input
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

    public static void main(String[] args) {
        System.out.println("Zadanie 1 - Pesel");

        Scanner scanner = new Scanner(System.in);

        getYear(scanner);
    }

}