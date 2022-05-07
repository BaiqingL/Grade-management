package utils;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String name;
    private final int BUID;

    private List<Assignment> assignments = new ArrayList<>();

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

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", BUID=" + BUID +
                '}';
    }
}
