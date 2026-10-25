
package home.menu;

import java.util.Scanner;

public class LibraryMenu {

    private final Scanner scanner = new Scanner(System.in);

    public int displayMenu() {
        System.out.println("\n===== LisoftLibrary =====");
        System.out.println("1. Add Book");
        System.out.println("2. Search Book");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Display All Books");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number.");
            scanner.next();
            System.out.print("Choose an option: ");
        }

        return scanner.nextInt();
    }
}
