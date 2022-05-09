package utils;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class Assignment {
    private String name;
    private int weight;
    private int maxGrade;
    private int grade;

    private LocalDate assignedDate;
    private LocalDate dueDate;
    private LocalDate submissionDate;

    public Assignment(String name, int weight, int maxGrade, int grade, LocalDate assignedDate, LocalDate dueDate, LocalDate submissionDate) {
        this.name = name;
        this.weight = weight;
        this.maxGrade = maxGrade;
        this.grade = grade;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
        this.submissionDate = submissionDate;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public boolean isSubmittedLate() {
        return submissionDate.isAfter(dueDate);
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public boolean isAssignmentLate() {
        if (submissionDate == null) {
            return false;
        }
        return assignedDate.isAfter(dueDate);
    }

    public int getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getPercentage() {
        return (double) grade / maxGrade;
    }

    public String getGradeString() {
        return grade + "/" + maxGrade;
    }

    public void applySquareCurve() {
        int boost = (int) Math.sqrt(maxGrade - grade);
        grade = Math.min(grade + boost, maxGrade);
    }

    public void applyLinearCurve(int boost) {
        grade = Math.min(grade + boost, maxGrade);
    }

    public void applyPercentageCurve(double percentage) {
        int boost = (int) (grade * (percentage / 100.0));
        grade = Math.min(grade + boost, maxGrade);
    }

    public String toString() {
        return "Assignment Name: " + name + " (" + weight + "%)" + " Assigned: " + assignedDate + " Due: " + dueDate + " Submitted: " + submissionDate + " Grade: " + getGradeString();
    }
}
