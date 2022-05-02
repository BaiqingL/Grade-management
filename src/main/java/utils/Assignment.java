package utils;

public class Assignment {
    private String name;
    private int weight;
    private int maxGrade;
    private int grade;

    public Assignment(String name, int weight, int maxGrade, int grade) {
        this.name = name;
        this.weight = weight;
        this.maxGrade = maxGrade;
        this.grade = grade;
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
        int boost = (int) Math.pow(maxGrade - grade, 2);
        grade = Math.min(grade + boost, maxGrade);
    }

    public void applyLinearCurve(int boost) {
        grade = Math.min(grade + boost, maxGrade);
    }

    public void applyPercentageCurve(double percentage) {
        int boost = (int) (maxGrade * percentage);
        grade = Math.min(grade + boost, maxGrade);
    }

    public String toString() {
        return name + " (" + weight + "%)";
    }
}
