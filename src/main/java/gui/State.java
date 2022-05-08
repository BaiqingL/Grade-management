package gui;

import utils.Semester;

import java.util.ArrayList;
import java.util.List;

public class State {
    private final List<Semester> semesters = new ArrayList<>();
    private Semester selectedSemester;

    public State() {
        semesters.add(new Semester("2021", "Spring", new ArrayList<>()));
        semesters.add(new Semester("2021", "Fall", new ArrayList<>()));
        semesters.add(new Semester("2022", "Spring", new ArrayList<>()));
        selectedSemester = null;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void addSemester(Semester semester) {
        this.semesters.add(semester);
    }

    public Semester getSelectedSemester() {
        return this.selectedSemester;
    }

    public void setSelectedSemester(Semester semester) {
        this.selectedSemester = semester;
    }
}
