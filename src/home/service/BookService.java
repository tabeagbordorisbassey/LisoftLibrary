package home.service;

import home.exception.BookNotFoundException;
import home.exception.DuplicateBookException;
import home.model.Book;
import home.storage.InMemoryBookRepository;
import home.common.Searchable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService implements Searchable<Book> {
    private final InMemoryBookRepository repository;

    public BookService(InMemoryBookRepository repository) {
        if (repository == null) throw new IllegalArgumentException("Repository is required.");
        this.repository = repository;
    }

    public void addBook(Book book) throws DuplicateBookException {
        if (book == null) throw new IllegalArgumentException("Book is required.");
        if (repository.containsId(book.getId())) {
            throw new DuplicateBookException("A book with ID " + book.getId() + " already exists.");
        }
        repository.save(book);
    }

    public Optional<Book> findBook(String id) {
        if (id == null || id.trim().isEmpty()) return Optional.empty();
        return repository.findById(id);
    }

    public Book requireBook(String id) throws BookNotFoundException {
        return findBook(id).orElseThrow(
                () -> new BookNotFoundException("No book found with ID: " + id));
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @Override
    public List<Book> search(String query) {
        final String normalized = query == null ? "" : query.toLowerCase();
        return repository.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(normalized)
                        || book.getAuthor().toLowerCase().contains(normalized))
                .collect(Collectors.toList());
    }

    /** Overload 1: search by title or author. */
    public List<Book> searchBooks(String query) {
        return search(query);
    }

    /** Overload 2: search by author and minimum publication year. */
    public List<Book> searchBooks(String author, int minimumYear) {
        return repository.findAll().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .filter(book -> book.getPublicationYear() >= minimumYear)
                .collect(Collectors.toList());
    }

    public List<String> getBooksPublishedAfter(int year) {
        return repository.findAll().stream()
                .filter(book -> book.getPublicationYear() > year)
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }
}
