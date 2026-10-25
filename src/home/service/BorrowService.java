package home.service;

import home.exception.BookNotFoundException;
import home.exception.LoanException;
import home.model.Book;
import home.model.Member;

public class BorrowService {
    private final BookService bookService;

    public BorrowService(BookService bookService) {
        if (bookService == null) throw new IllegalArgumentException("BookService is required.");
        this.bookService = bookService;
    }

    public void borrowBook(String bookId, Member member)
            throws BookNotFoundException, LoanException {
        if (member == null) throw new LoanException("A member is required to borrow a book.");
        Book book = bookService.requireBook(bookId);
        if (!book.isAvailable()) {
            throw new LoanException("Book is already borrowed: " + book.getTitle());
        }
        book.markBorrowed();
    }

    public void returnBook(String bookId) throws BookNotFoundException {
        bookService.requireBook(bookId).markReturned();
    }
}
