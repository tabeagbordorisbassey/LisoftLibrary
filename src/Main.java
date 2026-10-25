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

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        InMemoryBookRepository repository = new InMemoryBookRepository();
        BookService bookService = new BookService(repository);
        MemberService memberService = new MemberService();
        BorrowService borrowService = new BorrowService(bookService);

        try {
            bookService.addBook(new Book("B001", "Effective Java", "Joshua Bloch", 2018));
            bookService.addBook(new Book("B002", "Clean Code", "Robert C. Martin", 2008));
            bookService.addBook(new EBook("B003", "Modern Java", "C. Reader", 2024, 2.5));

            Member member = new StudentMember("M001", "Doris", "Computer Science");
            memberService.register(member);

            System.out.println("=== All books ===");
            bookService.getAllBooks().forEach(book -> System.out.println(book.getDescription()));

            System.out.println("\n=== Books published after 2010 (filter + map + collect) ===");
            List<String> recentTitles = bookService.getBooksPublishedAfter(2010);
            recentTitles.forEach(System.out::println);

            System.out.println("\n=== Optional search ===");
            Optional<Book> found = bookService.findBook("B001");
            found.ifPresent(book -> System.out.println("Found: " + book.getTitle()));
            System.out.println("Missing book present? " + bookService.findBook("UNKNOWN").isPresent());

            System.out.println("\n=== Borrowing ===");
            borrowService.borrowBook("B001", member);
            System.out.println("B001 available? " + bookService.findBook("B001")
                    .map(Book::isAvailable).orElse(false));

            System.out.println("\n=== Overloaded search ===");
            bookService.searchBooks("Java").forEach(book -> System.out.println(book.getTitle()));
            bookService.searchBooks("Robert C. Martin", 2000)
                    .forEach(book -> System.out.println("Author/year match: " + book.getTitle()));

        } catch (DuplicateBookException | BookNotFoundException | LoanException e) {
            System.err.println("Library operation failed: " + e.getMessage());
        }
    }
}
