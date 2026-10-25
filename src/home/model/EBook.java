package home.model;

/** Inheritance example: an e-book is a kind of book. */
public class EBook extends Book {
    private double fileSizeMb;

    public EBook(String id, String title, String author, int year, double fileSizeMb) {
        super(id, title, author, year);
        setFileSizeMb(fileSizeMb);
    }

    public double getFileSizeMb() { return fileSizeMb; }

    public void setFileSizeMb(double fileSizeMb) {
        if (fileSizeMb <= 0) throw new IllegalArgumentException("File size must be positive.");
        this.fileSizeMb = fileSizeMb;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", format=EBOOK, fileSizeMb=" + fileSizeMb;
    }
}
