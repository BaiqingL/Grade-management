package utils;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String name;
    private final int BUID;

    private final List<Assignment> assignments = new ArrayList<>();

    private int[] bracket = new int[]{95, 90, 87, 83, 80, 77, 73, 70, 67, 63, 60, 57, 0};

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

    public int[] getBrackets() {
        return bracket;
    }

    public void setBracket(int[] bracket) {
        this.bracket = bracket;
    }

    public String getNumberGrade() {
        double cumulative = 0.0;

        for (Assignment a : assignments) {
            cumulative += (a.getPercentage() * a.getWeight());
        }

        int finalGrade = (int) cumulative;
        return Integer.toString(finalGrade);
    }

    public String getLetterGrade() {
        double cumulative = 0.0;

        for (Assignment a : assignments) {
            cumulative += (a.getPercentage() * a.getWeight());
        }

        int finalGrade = (int) cumulative;

        if (finalGrade >= bracket[0]) {
            return "A+";
        } else if (finalGrade >= bracket[1]) {
            return "A";
        } else if (finalGrade >= bracket[2]) {
            return "A-";
        } else if (finalGrade >= bracket[3]) {
            return "B+";
        } else if (finalGrade >= bracket[4]) {
            return "B";
        } else if (finalGrade >= bracket[5]) {
            return "B-";
        } else if (finalGrade >= bracket[6]) {
            return "C+";
        } else if (finalGrade >= bracket[7]) {
            return "C";
        } else if (finalGrade >= bracket[8]) {
            return "C-";
        } else if (finalGrade >= bracket[9]) {
            return "D+";
        } else if (finalGrade >= bracket[10]) {
            return "D";
        } else if (finalGrade >= bracket[11]) {
            return "D-";
        } else {
            return "F";
        }
    }

    @Override
    public String toString() {
        return "Student{" + "name='" + name + '\'' + ", BUID=" + BUID + '}';
    }
}
