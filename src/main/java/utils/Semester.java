package utils;

import java.util.List;

public class Semester {
    private final int year;
    private final int semester;
    private final List<GradedClass> classes;

    public Semester(int year, int semester, List<GradedClass> classes) {
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
        return "Semester " + semester + ", " + year;
    }
}
