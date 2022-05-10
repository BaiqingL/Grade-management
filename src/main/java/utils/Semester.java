package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Semester {
    private final String year;
    private final String semester;
    private final List<String> coursePath;
    private List<GradedClass> courses;


    public Semester(String year, String semester) {
        this.year = year;
        this.semester = semester;
        this.coursePath = new ArrayList<>();
    }

    public List<GradedClass> getCourses() throws IOException {
        if (this.courses != null) {
            return this.courses;
        }
        this.courses = new ArrayList<>();
        for (String path : coursePath) {
            try {
                GradedClass course = CSVReader.loadCSV(path);
                this.courses.add(course);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Cannot read from" + path);
            }
        }
        return courses;
    }

    public void addCourse(ClassPathTuple tuple) {
        this.coursePath.add(tuple.getPath());
        this.courses.add(tuple.getCourse());
    }



    public String toString() {
        return semester + " " + year;
    }
}
