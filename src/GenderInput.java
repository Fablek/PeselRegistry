import java.util.Scanner;

public class GenderInput {

    public static String getGender(Scanner scanner) {
        boolean retry;

        do {
            System.out.print("Podaj płeć: wciśnij 'k' jeśli jesteś kobietą albo 'm' jeśli jesteś mężczyzną: ");
            retry = false;

            String genderInput = scanner.next().toLowerCase();

            if (genderInput.equals("k") || genderInput.equals("m")) {
                return genderInput;
            } else {
                System.out.println("Podałeś niepoprawną formę płci.");

                System.out.print("Wciśnij 'p' jeśli chcesz podać ponownie płeć lub wciśnij inny klawisz jeśli chcesz zakończyć: ");
                String choice = scanner.next();

                if (choice.equalsIgnoreCase("p")) {
                    retry = true;
                }
            }

        } while (retry);

        return null;
    }
}