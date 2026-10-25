package home.storage;

import home.model.Book;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Map provides fast lookup by book ID; List preserves a convenient ordered view. */
public class InMemoryBookRepository {
    private final Map<String, Book> booksById = new LinkedHashMap<String, Book>();

    public boolean containsId(String id) {
        return booksById.containsKey(id);
    }

    public void save(Book book) {
        booksById.put(book.getId(), book);
    }

    public Optional<Book> findById(String id) {
        return Optional.ofNullable(booksById.get(id));
    }

    public List<Book> findAll() {
        return new ArrayList<Book>(booksById.values());
    }
}
