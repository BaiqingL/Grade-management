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

    public float getMeanGradeForAssignment(int assignmentIndex) {
        float sum = 0;
        for (Student student : students) {
            sum += student.getAssignments().get(assignmentIndex).getGrade();
        }
        return sum / (float) students.size();
    }

    public int getMedianGradeForAssignment(int assignmentIndex) {
        int[] grades = new int[students.size()];
        int i = 0;
        for (Student student : students) {
            grades[i++] = student.getAssignments().get(assignmentIndex).getGrade();
        }
        return median(grades);
    }

    private int median(int[] grades) {
        int[] copy = grades.clone();
        java.util.Arrays.sort(copy);
        return copy[copy.length / 2];
    }

    public int getLowestGradeForAssignment(int assignmentIndex) {
        int[] grades = new int[students.size()];
        int i = 0;
        for (Student student : students) {
            grades[i++] = student.getAssignments().get(assignmentIndex).getGrade();
        }
        return lowest(grades);
    }

    private int lowest(int[] grades) {
        int[] copy = grades.clone();
        java.util.Arrays.sort(copy);
        return copy[0];
    }

    public int getHighestGradeForAssignment(int assignmentIndex) {
        int[] grades = new int[students.size()];
        int i = 0;
        for (Student student : students) {
            grades[i++] = student.getAssignments().get(assignmentIndex).getGrade();
        }
        return highest(grades);
    }

    private int highest(int[] grades) {
        int[] copy = grades.clone();
        java.util.Arrays.sort(copy);
        return copy[copy.length - 1];
    }

    public double getStandardDev(int assignmentIndex) {
        double sum = 0;
        for (Student student : students) {
            sum += Math.pow(student.getAssignments().get(assignmentIndex).getGrade() - getMeanGradeForAssignment(assignmentIndex), 2);
        }
        return Math.sqrt(sum / (double) students.size());
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }
}
