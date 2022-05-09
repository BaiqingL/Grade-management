package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import utils.GradedClass;
import utils.Student;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static javax.swing.JOptionPane.showMessageDialog;

public class Assignment extends JPanel {
    private final GradedClass course;
    private final int assignmentIdx;
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
    private JLabel minVal;
    private JLabel maxVal;
    private JLabel medianVal;
    private JLabel meanVal;
    private JLabel stdVal;
    private JLabel assignmentName;
    private JButton linearCurveButton;
    private JButton squareCurveButton;
    private JButton percentageCurveButton;
    private JButton deleteStudentButton;
    private JButton saveButton;
    private DefaultTableModel model;

    public Assignment(GradedClass course, int assignmentIdx) {
        this.course = course;
        this.assignmentIdx = assignmentIdx;

        $$$setupUI$$$();
        updateStats();
        assignmentName.setText(course.getAssignments().get(assignmentIdx).getName());

        this.add(assignmentContainer);

        back.addActionListener(e -> {
            firePropertyChange("previousPage", null, null);
        });
        saveButton.addActionListener(e -> {
            ArrayList<Integer> data = new ArrayList<>();
            for (int count = 0; count < model.getRowCount(); count++) {
                data.add(Integer.valueOf(grades.getValueAt(count, 2).toString()));
            }
            for (int i = 0; i < course.getStudents().size(); i++) {
                course.getStudents().get(i).getAssignments().get(assignmentIdx).setGrade(data.get(i));
            }
            updateStats();
            updateTable();
            showMessageDialog(null, "Saved!");
        });
        squareCurveButton.addActionListener(actionEvent -> {
            firePropertyChange("curveSquare", null, "square");
            course.applySquareCurve(assignmentIdx);
            updateStats();
            updateTable();
        });
        linearCurveButton.addActionListener(actionEvent -> {
            firePropertyChange("curveLinear", null, "linear");
            course.applyLinearCurve(assignmentIdx, promptInteger("Enter the amount to curve linearly"));
            updateStats();
            updateTable();
        });
        percentageCurveButton.addActionListener(actionEvent -> {
            firePropertyChange("curvePercentage", null, "percentage");
            course.applyPercentageCurve(assignmentIdx, promptInteger("Enter the amount to curve by percentage"));
            updateStats();
            updateTable();
        });
        deleteStudentButton.addActionListener(actionEvent -> {
            firePropertyChange("deleteStudent", null, null);
            int selected = grades.getSelectedRow();
            int BUID = course.getStudents().get(selected).getBUID();
            String name = course.getStudents().get(selected).getName();
            int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + name + " from this class?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                course.removeStudentByBUID(BUID);
            }
            updateTable();
        });
    }

    private void updateTable() {
        // Refresh the table
        model.setRowCount(0);
        for (Student s : course.getStudents()) {
            model.addRow(new Object[]{s.getName(), s.getBUID(), s.getAssignments().get(assignmentIdx).getGrade(), s.getAssignments().get(assignmentIdx).getSubmissionDate()});
        }
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

    private int promptInteger(String message) {
        return Integer.parseInt(JOptionPane.showInputDialog(message));
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
            values[i] = new String[]{s.getName(), String.valueOf(s.getBUID()), String.valueOf(a.getGrade()), a.getSubmissionDate().toString()};
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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        assignmentContainer = new JPanel();
        assignmentContainer.setLayout(new GridLayoutManager(5, 1, new Insets(0, 30, 0, 30), -1, -1));
        statistic = new JPanel();
        statistic.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        assignmentContainer.add(statistic, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        statMin = new JPanel();
        statMin.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        statistic.add(statMin, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        statMin.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        min = new JLabel();
        min.setText("Min");
        statMin.add(min, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        minVal.setText("Label");
        statMin.add(minVal, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        statSD = new JPanel();
        statSD.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        statistic.add(statSD, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        statSD.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        std = new JLabel();
        std.setText("Std Dev.");
        statSD.add(std, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stdVal.setText("Label");
        statSD.add(stdVal, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        statMedian = new JPanel();
        statMedian.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        statistic.add(statMedian, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        statMedian.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        median = new JLabel();
        median.setText("Median");
        statMedian.add(median, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        medianVal.setText("Label");
        statMedian.add(medianVal, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        statMax = new JPanel();
        statMax.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        statistic.add(statMax, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        statMax.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        max = new JLabel();
        max.setText("Max");
        statMax.add(max, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        maxVal.setText("Label");
        statMax.add(maxVal, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        statMean = new JPanel();
        statMean.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        statistic.add(statMean, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        statMean.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        mean = new JLabel();
        mean.setText("Mean");
        statMean.add(mean, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        meanVal.setText("Label");
        statMean.add(meanVal, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeActions = new JPanel();
        gradeActions.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        assignmentContainer.add(gradeActions, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        back = new JButton();
        back.setText("Back");
        gradeActions.add(back, new GridConstraints(0, 3, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        saveButton = new JButton();
        saveButton.setText("Save");
        gradeActions.add(saveButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        gradeActions.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        deleteStudentButton = new JButton();
        deleteStudentButton.setText("Delete Student");
        gradeActions.add(deleteStudentButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        assignmentContainer.add(gradeContainer, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        gradeContainer.setViewportView(grades);
        assignmentName = new JLabel();
        Font assignmentNameFont = this.$$$getFont$$$("Century Gothic", Font.BOLD, 26, assignmentName.getFont());
        if (assignmentNameFont != null) assignmentName.setFont(assignmentNameFont);
        assignmentName.setText("Label");
        assignmentContainer.add(assignmentName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        assignmentContainer.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        linearCurveButton = new JButton();
        linearCurveButton.setText("Linear Curve");
        panel1.add(linearCurveButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        squareCurveButton = new JButton();
        squareCurveButton.setText("Square Curve");
        panel1.add(squareCurveButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        percentageCurveButton = new JButton();
        percentageCurveButton.setText("Percentage Curve");
        panel1.add(percentageCurveButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return assignmentContainer;
    }

}
