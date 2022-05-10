package utils;

public class ClassPathTuple {
    private final GradedClass course;
    private final String path;

    public ClassPathTuple(GradedClass course, String path) {
        this.course = course;
        this.path = path;
    }

    public GradedClass getCourse() {return this.course;}

    public String getClassName() {
        return course.getClassName();
    }

    public String getPath() {
        return path;
    }
}
