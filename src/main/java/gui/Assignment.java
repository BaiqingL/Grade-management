package gui;

import utils.GradedClass;
import utils.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Assignment extends JPanel {
    private JPanel assignmentContainer;
    private JScrollPane gradeContainer;
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
    private JLabel assignmentName;

    private DefaultTableModel model;

    private GradedClass course;
    private int assignmentIdx;

    public Assignment(GradedClass course, int assignmentIdx) {
        this.course = course;
        this.assignmentIdx = assignmentIdx;

        updateStats();
        assignmentName.setText(course.getAssignments().get(assignmentIdx).getName());

        this.add(assignmentContainer);

        back.addActionListener(e -> firePropertyChange("previousPage", null, null));
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

    private String[] getHeader() {
        return new String[]{"Name", "BUID", "Score", "Submission Date"};
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
        gradeContainer = new JScrollPane();
        model = new DefaultTableModel(getValue(), getHeader());
        grades = new JTable(model);

        minVal = new JLabel(String.valueOf(course.getLowestGradeForAssignment(assignmentIdx)));
        maxVal = new JLabel(String.valueOf(course.getHighestGradeForAssignment(assignmentIdx)));
        medianVal = new JLabel(String.valueOf(course.getMedianGradeForAssignment(assignmentIdx)));
        meanVal = new JLabel(String.valueOf(course.getMeanGradeForAssignment(assignmentIdx)));
        stdVal = new JLabel(String.valueOf(course.getStandardDevForAssignment(assignmentIdx)));
    }
}
