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

    public void applySquareCurve(int assignmentIndex) {
        for (Student student : students) {
            student.getAssignments().get(assignmentIndex).applySquareCurve();
        }
    }

    public void applyLinearCurve(int assignmentIndex, int amount) {
        for (Student student : students) {
            student.getAssignments().get(assignmentIndex).applyLinearCurve(amount);
        }
    }

    public void applyPercentageCurve(int assignmentIndex, double percentage) {
        for (Student student : students) {
            student.getAssignments().get(assignmentIndex).applyPercentageCurve(percentage);
        }
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }
}
