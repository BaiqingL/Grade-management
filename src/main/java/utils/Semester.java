package utils;

import java.util.List;

public class Semester {
    private final String year;
    private final String semester;
    private final List<GradedClass> classes;

    public Semester(String year, String semester, List<GradedClass> classes) {
        this.year = year;
        this.semester = semester;
        this.classes = classes;
    }

    public String getYear() {
        return year;
    }

    public String getSemester() {
        return semester;
    }

    public List<GradedClass> getClasses() {
        return classes;
    }

    public void addClass(GradedClass c) {
        classes.add(c);
    }

    public void removeClass(GradedClass c) {
        classes.remove(c);
    }

    public String toString() {
        return semester + " " + year;
    }
}
