package gui;

import utils.GradedClass;
import utils.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Assignment extends JPanel {
    private JPanel assignmentContainer;
    private JPanel gradeContainer;
    private JPanel statistic;
    private JPanel statMin;
    private JLabel min;
    private JPanel statMax;
    private JPanel statMedian;
    private JPanel statMean;
    private JPanel statSD;
    private JLabel max;
    private JLabel median;
    private JLabel mean;
    private JLabel std;
    private JTable grades;
    private JPanel gradeActions;
    private JButton back;
    private JButton logout;
    private JLabel minVal;
    private JLabel maxVal;
    private JLabel medianVal;
    private JLabel meanVal;
    private JLabel stdVal;

    private DefaultTableModel model;

    private GradedClass course;
    private int assignmentIdx;

    public Assignment(GradedClass course, int assignmentIdx) {
        this.course = course;
        this.assignmentIdx = assignmentIdx;
        updateStats();
        initTable();

    }

    private void setStats(JLabel label, String val) {
        label.setText(val);
    }

    private void updateStats() {
        setStats(minVal, String.valueOf(course.getLowestGradeForAssignment(assignmentIdx)));
        setStats(maxVal, String.valueOf(course.getHighestGradeForAssignment(assignmentIdx)));
        setStats(medianVal, String.valueOf(course.getMedianGradeForAssignment(assignmentIdx)));
        setStats(meanVal, String.valueOf(course.getMeanGradeForAssignment(assignmentIdx)));
        setStats(stdVal, String.valueOf(course.getStandardDevForAssignment(assignmentIdx)));
    }

    private void initTable() {
        model = new DefaultTableModel(getValue(), getHeader());
        grades = new JTable(model);
    }

    private String[] getHeader() {
        String[] header = {"Name", "BUID", "Score", "Submission Date"};
        return header;
    }

    private String[][] getValue() {
        String[][] values = new String[course.getStudents().size()][4];
        List<Student> students = course.getStudents();

        for (int i = 0; i < students.size(); i++) {
            utils.Assignment a = students.get(i).getAssignments().get(assignmentIdx);
            Student s = students.get(i);
            values[i] = new String[] {s.getName(), String.valueOf(s.getBUID()), String.valueOf(a.getGrade()), a.getSubmissionDate().toString()};
        }

        return values;
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        gradeContainer = new JPanel(new ScrollPaneLayout());
    }
}
