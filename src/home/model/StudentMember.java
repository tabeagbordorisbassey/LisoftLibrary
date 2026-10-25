package home.model;

public class StudentMember extends Member {
    private String course;

    public StudentMember(String id, String name, String course) {
        super(id, name);
        setCourse(course);
    }

    public String getCourse() { return course; }

    public void setCourse(String course) {
        if (course == null || course.trim().isEmpty()) throw new IllegalArgumentException("Course is required.");
        this.course = course;
    }

    @Override
    public String getMembershipType() { return "STUDENT"; }
}
