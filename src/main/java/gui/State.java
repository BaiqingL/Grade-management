package gui;

import utils.Semester;

import java.util.ArrayList;
import java.util.List;

public class State {
    private final List<Semester> semesters = new ArrayList<>();
    private Semester selectedSemester;
    private String semestersFilePath;

    public State() {
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

    public String getSemestersFilePath() {
        return semestersFilePath;
    }

    public void setSemestersFilePath(String semestersFilePath) {
        this.semestersFilePath = semestersFilePath;
    }
}
