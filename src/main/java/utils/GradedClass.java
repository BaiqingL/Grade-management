package utils;

import java.util.ArrayList;
import java.util.List;

public class GradedClass {

    private final List<Student> students = new ArrayList<>();
    private final List<Assignment> assignments = new ArrayList<>();

    public GradedClass(List<Student> students, List<Assignment> assignments) {
        this.students.addAll(students);
        this.assignments.addAll(assignments);
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }
}
