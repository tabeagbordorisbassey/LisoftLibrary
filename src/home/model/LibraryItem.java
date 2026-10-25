package home.model;

/**
 * Abstract base class: shared state and behavior for items in the library.
 */
public abstract class LibraryItem {
    private final String id;
    private String title;

    protected LibraryItem(String id, String title) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Item ID must not be blank.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title must not be blank.");
        }
        this.id = id;
        this.title = title;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title must not be blank.");
        }
        this.title = title;
    }

    public abstract String getDescription();
}
