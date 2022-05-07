package utils;

import java.util.List;

public class Semester {
    private final String year;
    private final String semester;
    private final List<Class> classes;

    public Semester(String year, String semester, List<Class> classes) {
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

    public List<Class> getClasses() {
        return classes;
    }

    public void addClass(Class c) {
        classes.add(c);
    }

    public void removeClass(Class c) {
        classes.remove(c);
    }

    public String toString() {
        return semester + " " + year;
    }
}
