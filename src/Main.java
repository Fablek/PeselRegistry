import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Zadanie 1 - Pesel");

        Scanner scanner = new Scanner(System.in);

        int year = DateInput.getYear(scanner);
        int month = DateInput.getMonth(scanner);
        int day = DateInput.getDayOfMonth(scanner, month, year);
        String gender = GenderInput.getGender(scanner);
    }
}
