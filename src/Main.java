import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Zadanie 1 - Pesel");
        Scanner scanner = new Scanner(System.in);

        boolean retry = true;

        do {
            retry = false;

            Integer year = DateInput.getYear(scanner);
            Integer month = DateInput.getMonth(scanner);
            Integer day = DateInput.getDayOfMonth(scanner, month, year);
            String gender = GenderInput.getGender(scanner);

            System.out.printf("Czy chcesz dokonać wpisu %d, %d, %d, %s? Klawisz t – tak, pozostałe – nie.%n", year, month, day, gender);
            String confirmEntry = scanner.next();
            if (!confirmEntry.equalsIgnoreCase("t")) break;

            String pesel = PeselGenerator.generatePesel(year, month, day, gender, 0);
            PeselGenerator.savePesel(pesel);

            System.out.println("Czy chcesz dokonać kolejnego wpisu? Klawisz t – tak, pozostałe – nie.");
            String anotherEntry = scanner.next();

            if (anotherEntry.equalsIgnoreCase("t")) {
                retry = true;
            };

        } while (retry);

        scanner.close();
    }
}