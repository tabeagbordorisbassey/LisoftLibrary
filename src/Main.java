import home.exception.BookNotFoundException;
import home.exception.DuplicateBookException;
import home.exception.LoanException;
import home.model.Book;
import home.model.EBook;
import home.model.Member;
import home.model.StudentMember;
import home.storage.InMemoryBookRepository;
import home.service.BookService;
import home.service.BorrowService;
import home.service.MemberService;
import home.menu.LibraryMenu;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        // 1. Create the repository
        InMemoryBookRepository repository =
                new InMemoryBookRepository();

        // 2. Create the services
        BookService bookService =
                new BookService(repository);

        MemberService memberService =
                new MemberService();

        BorrowService borrowService =
                new BorrowService(bookService);

        boolean running = true;

        while (running) {
            displayMenu();

            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {

                    case 1:
                        addBook(bookService);
                        break;

                    case 2:
                        displayBooks(bookService);
                        break;

                    case 3:
                        searchBook(bookService);
                        break;

                    case 4:
                        registerMember(memberService);
                        break;

                    case 5:
                        borrowBook(
                                bookService,
                                memberService,
                                borrowService
                        );
                        break;

                    case 6:
                        returnBook(bookService, borrowService);
                        break;

                    case 7:
                        running = false;
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (DuplicateBookException
                    | BookNotFoundException
                    | LoanException e) {

                System.out.println(
                        "Operation failed: " + e.getMessage()
                );

            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Invalid input: " + e.getMessage()
                );
            }

            System.out.println();
        }

        input.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== LisoftLibrary =====");
        System.out.println("1. Add Book");
        System.out.println("2. Display All Books");
        System.out.println("3. Search Book");
        System.out.println("4. Register Member");
        System.out.println("5. Borrow Book");
        System.out.println("6. Return Book");
        System.out.println("7. Exit");
    }

    private static void addBook(BookService bookService)
            throws DuplicateBookException {

        System.out.println("--- Add Book ---");

        String id = readText("Book ID: ");
        String title = readText("Book title: ");
        String author = readText("Author: ");
        int year = readInt("Publication year: ");

        Book book = new Book(id, title, author, year);

        bookService.addBook(book);

        System.out.println("Book added successfully!");
    }

    private static void displayBooks(BookService bookService) {

        List<Book> books = bookService.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        books.forEach(book ->
                System.out.println(book.getDescription()));
    }

    private static void searchBook(BookService bookService) {

        String keyword = readText("Enter title or author: ");

        List<Book> results =
                bookService.searchBooks(keyword);

        if (results.isEmpty()) {
            System.out.println("No matching books found.");
        } else {
            results.forEach(book ->
                    System.out.println(book.getDescription()));
        }
    }

    private static void registerMember(
            MemberService memberService) {

        System.out.println("--- Register Member ---");

        String id = readText("Member ID: ");
        String name = readText("Member name: ");

        System.out.println("1. Standard member");
        System.out.println("2. Student member");

        int type = readInt("Choose member type: ");

        Member member;

        if (type == 1) {
            member = new Member(id, name);

        } else if (type == 2) {
            String course = readText("Student course: ");

            member = new StudentMember(id, name, course);

        } else {
            System.out.println("Invalid member type.");
            return;
        }

        memberService.register(member);

        System.out.println("Member registered: " + member);
    }

    private static void borrowBook(
            BookService bookService,
            MemberService memberService,
            BorrowService borrowService)
            throws BookNotFoundException, LoanException {

        String bookId = readText("Enter book ID: ");
        String memberId = readText("Enter member ID: ");

        Optional<Member> member =
                memberService.findMember(memberId);

        if (!member.isPresent()) {
            System.out.println("Member not found. Register first.");
            return;
        }

        borrowService.borrowBook(bookId, member.get());

        System.out.println(
                "Book borrowed by " + member.get().getName()
        );

        System.out.println(
                "Book available: "
                + bookService.requireBook(bookId).isAvailable()
        );
    }

    private static void returnBook(
            BookService bookService,
            BorrowService borrowService)
            throws BookNotFoundException {

        String bookId = readText("Enter book ID to return: ");

        Book book = bookService.requireBook(bookId);

        if (book.isAvailable()) {
            System.out.println("This book is already available.");
            return;
        }

        borrowService.returnBook(bookId);

        System.out.println("Book returned successfully!");
    }

    private static String readText(String prompt) {
        System.out.print(prompt);

        String value = input.nextLine().trim();

        if (value.isEmpty()) {
            throw new IllegalArgumentException(
                    "This field cannot be empty."
            );
        }

        return value;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            String value = input.nextLine().trim();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }
}



// public class Main {
//     public static void main(String[] args) {
        // InMemoryBookRepository repository = new InMemoryBookRepository();
        // BookService bookService = new BookService(repository);
        // MemberService memberService = new MemberService();
        // BorrowService borrowService = new BorrowService(bookService);

//         try {
//             bookService.addBook(new Book("B001", "Java Programming", "James Gosling", 2023));
//             bookService.addBook(new Book("B002", "Clean Code", "Robert C. Martin", 2008));
//             bookService.addBook(new EBook("B003", "Modern Java", "C. Reader", 2024, 2.5));
//             bookService.addBook(new Book("B004", "Effective Java", "Joshua Bloch", 2018));

//             Member member = new StudentMember("M001", "Doris", "Computer Science");
//             memberService.register(member);

//             System.out.println("=== All books ===");
//             bookService.getAllBooks().forEach(book -> System.out.println(book.getDescription()));

//             System.out.println("\n=== Books published after 2010 (filter + map + collect) ===");
//             List<String> recentTitles = bookService.getBooksPublishedAfter(2010);
//             recentTitles.forEach(System.out::println);

//             System.out.println("\n=== Optional search ===");
//             Optional<Book> found = bookService.findBook("B001");
//             found.ifPresent(book -> System.out.println("Found: " + book.getTitle()));
//             System.out.println("Missing book present? " + bookService.findBook("UNKNOWN").isPresent());

//             System.out.println("\n=== Borrowing ===");
//             borrowService.borrowBook("B001", member);
//             System.out.println("B001 available? " + bookService.findBook("B001")
//                     .map(Book::isAvailable).orElse(false));

//             System.out.println("\n=== Overloaded search ===");
//             bookService.searchBooks("Java").forEach(book -> System.out.println(book.getTitle()));
//             bookService.searchBooks("Robert C. Martin", 2000)
//                     .forEach(book -> System.out.println("Author/year match: " + book.getTitle()));

//         } catch (DuplicateBookException | BookNotFoundException | LoanException e) {
//             System.err.println("Library operation failed: " + e.getMessage());
//         }
//     }
//  }
