package utils;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String name;
    private final int BUID;

    private final List<Assignment> assignments = new ArrayList<>();

    public Student(String name, int BUID) {
        this.name = name;
        this.BUID = BUID;
    }

    public String getName() {
        return name;
    }

    public int getBUID() {
        return BUID;
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public String getLetterGrade() {
        double cumulative = 0.0;

        for (int i = 0; i < assignments.size(); i++) {
            Assignment a = assignments.get(i);
            cumulative += (a.getPercentage() * a.getWeight());
        }

        int finalGrade = (int) cumulative;

        if (finalGrade >= 97) {
            return "A+";
        } else if (finalGrade >= 93 && finalGrade <= 96) {
            return "A";
        } else if (finalGrade >= 90 && finalGrade <= 92) {
            return "A-";
        } else if (finalGrade >= 87 && finalGrade <= 89) {
            return "B+";
        } else if (finalGrade >= 83 && finalGrade <= 86) {
            return "B";
        } else if (finalGrade >= 80 && finalGrade <= 82) {
            return "B-";
        } else if (finalGrade >= 77 && finalGrade <= 79) {
            return "C+";
        } else if (finalGrade >= 73 && finalGrade <= 76) {
            return "C";
        } else if (finalGrade >= 70 && finalGrade <= 72) {
            return "C-";
        } else if (finalGrade >= 67 && finalGrade <= 69) {
            return "D+";
        } else if (finalGrade >= 65 && finalGrade <= 66) {
            return "D";
        } else {
            return "F";
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", BUID=" + BUID +
                '}';
    }
}
