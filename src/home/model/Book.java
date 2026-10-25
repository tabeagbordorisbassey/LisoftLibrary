package home.model;

public class Book extends LibraryItem {
    private String author;
    private int publicationYear;
    private boolean available = true;

    public Book(String id, String title, String author, int publicationYear) {
        super(id, title);
        setAuthor(author);
        setPublicationYear(publicationYear);
    }

    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }
    public boolean isAvailable() { return available; }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author must not be blank.");
        }
        this.author = author;
    }

    public void setPublicationYear(int year) {
        if (year < 0) throw new IllegalArgumentException("Publication year cannot be negative.");
        this.publicationYear = year;
    }

    public void markBorrowed() { available = false; }
    public void markReturned() { available = true; }

    @Override
    public String getDescription() {
        return "Book{id='" + getId() + "', title='" + getTitle()
                + "', author='" + author + "', year=" + publicationYear
                + ", available=" + available + "}";
    }
}
