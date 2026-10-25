package home.model;

public class Member {
    private final String id;
    private String name;

    public Member(String id, String name) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("Member ID is required.");
        this.id = id;
        setName(name);
    }

    public String getId() { return id; }
    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name is required.");
        this.name = name;
    }

    public String getMembershipType() { return "STANDARD"; }

    @Override
    public String toString() { return id + " - " + name + " (" + getMembershipType() + ")"; }
}
