package utils;

import java.util.List;

public class Semester {
    private final int year;
    private final int semester;
    private final List<Class> classes;

    public Semester(int year, int semester, List<Class> classes) {
        this.year = year;
        this.semester = semester;
        this.classes = classes;
    }

    public int getYear() {
        return year;
    }

    public int getSemester() {
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
        return "Semester " + semester + ", " + year;
    }
}
