import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static Integer getYear(Scanner scanner) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int minYear = currentYear - 120;

        System.out.print("Podaj swój czterocyfrowy rok urodzenia: ");

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
            scanner.next(); // Oczyszczenie błędnego wejścia
            throw new IllegalArgumentException("Błąd: Wprowadź liczbę całkowitą.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Zadanie 1 - Pesel");

        Scanner scanner = new Scanner(System.in);

        getYear(scanner);
    }

}