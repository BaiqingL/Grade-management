package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import utils.ClassPathTuple;
import utils.CSVReader;
import utils.ClassPathTuple;
import utils.GradedClass;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static org.apache.maven.shared.utils.StringUtils.isNumeric;

public class CourseSelection extends JPanel {
    private static List<ClassPathTuple> CLASS_PATH_TUPLE = new ArrayList<>();
    private JPanel coursePanel;
    private JButton addCourseButton;
    private JButton deleteCourseButton;
    private JButton logoutButton;
    private JPanel courseActions;
    private JPanel spacer;
    private JLabel semester;
    private JScrollPane courseContainer;
    private JTable courseTable;
    private JButton backButton;

    private DefaultTableModel model;
    private boolean isLoggedIn;
    private List<GradedClass> courses;
    private final int count = 0;

    public CourseSelection() {

        this.isLoggedIn = true;
        courses = new ArrayList<>();
        $$$setupUI$$$();
        addCourseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Select a CSV file");
            String filePath = "";
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                filePath = fileChooser.getSelectedFile().getAbsolutePath();
            }
            addCourse(filePath);
            firePropertyChange("GUIupdate", 0, 0);
        });
        deleteCourseButton.addActionListener(e -> {
            if (courseTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No courses to delete");
            } else {
                String selected = JOptionPane.showInputDialog(null, "Select a course to delete: \n" + getCoursesAndIndex(), "Delete Course", JOptionPane.QUESTION_MESSAGE);
                // Make sure the input is a number
                try {
                    while (selected != null && !isNumeric(selected) || Integer.parseInt(Objects.requireNonNull(selected)) > courseTable.getRowCount() || Integer.parseInt(Objects.requireNonNull(selected)) == 0) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                        selected = JOptionPane.showInputDialog(null, "Select a course to delete: \n" + getCoursesAndIndex(), "Delete Course", JOptionPane.QUESTION_MESSAGE);
                    }
                    deleteCourse(Integer.parseInt(selected) - 1);
                } catch (Exception ignored) {

                }
            }
        });

        backButton.addActionListener(e -> firePropertyChange("previousPage", null, null));

        logoutButton.addActionListener(e -> loggout());

        courseTable.getSelectionModel().addListSelectionListener(e -> {
            // prevent the following function being called twice
            if (!e.getValueIsAdjusting() && courseTable.getSelectedRow() != -1) {
                firePropertyChange("CourseSelected", null, courses.get(courseTable.getSelectedRow()));
                courseTable.clearSelection();
            }
        });

    }

    public static String getCourseFilePath(String assignmentName) {
        for (ClassPathTuple tuple : CLASS_PATH_TUPLE) {
            if (tuple.getClassName().equals(assignmentName)) {
                return tuple.getPath();
            }
        }
        return null;
    }

    private String getCoursesAndIndex() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < courses.size(); i++) {
            sb.append(i + 1).append(": ").append(courses.get(i).getClassName()).append("\n");
        }
        return sb.toString();
    }

    private void addCourse(String filePath) {
        try {
            GradedClass course = CSVReader.loadCSV(filePath);
            courses.add(course);
            model.addRow(new String[]{course.getClassName(), String.valueOf(course.getAssignments().size()), String.valueOf(course.getStudents().size())});
            ClassPathTuple newTuple = new ClassPathTuple(course, filePath);
            CLASS_PATH_TUPLE.add(newTuple);
            firePropertyChange("addedNewCourse", "", newTuple);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Cannot read from" + filePath);
        }

    }

    public void populateSemesterCourse(List<GradedClass> coursesToPopulate, List<String> paths) {
        courses = new ArrayList<>();
        model.setRowCount(0);
        CLASS_PATH_TUPLE = new ArrayList<>();

        assert coursesToPopulate.size() == paths.size();
        for (int i = 0; i < coursesToPopulate.size(); i++) {
            GradedClass course = coursesToPopulate.get(i);
            courses.add(course);
            model.addRow(new String[]{course.getClassName(), String.valueOf(course.getAssignments().size()), String.valueOf(course.getStudents().size())});
            ClassPathTuple newTuple = new ClassPathTuple(course, paths.get(i));
            CLASS_PATH_TUPLE.add(newTuple);
        }
    }

    private void deleteCourse(int idx) {

        model.removeRow(idx);
        ClassPathTuple toDelete = CLASS_PATH_TUPLE.get(idx);
        courses.remove(idx);
        firePropertyChange("deleteCourse", null, toDelete);
    }

    private void loggout() {
        this.isLoggedIn = false;
        firePropertyChange("isLoggedIn", true, false);
    }

    private String[] getHeaders() {
        return new String[]{"Name", "Assignment Number", "Students Enrolled"};
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        courseContainer = new JScrollPane();
        model = new DefaultTableModel();
        model.setColumnIdentifiers(getHeaders());
        courseTable = new JTable(model);

    }

    public void setSemesterLabel(String semesterLabel) {
        semester.setText("Current semester: " + semesterLabel);
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
        coursePanel = new JPanel();
        coursePanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        spacer = new JPanel();
        spacer.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        coursePanel.add(spacer, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        semester = new JLabel();
        Font semesterFont = this.$$$getFont$$$("SF Pro Display", Font.BOLD, 36, semester.getFont());
        if (semesterFont != null) semester.setFont(semesterFont);
        semester.setText("");
        spacer.add(semester, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        coursePanel.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        courseActions = new JPanel();
        courseActions.setLayout(new GridLayoutManager(1, 4, new Insets(0, 30, 0, 30), -1, -1));
        panel1.add(courseActions, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addCourseButton = new JButton();
        addCourseButton.setText("Add Course");
        courseActions.add(addCourseButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteCourseButton = new JButton();
        deleteCourseButton.setText("Delete Course");
        courseActions.add(deleteCourseButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logoutButton = new JButton();
        logoutButton.setText("Logout");
        courseActions.add(logoutButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setText("Back");
        courseActions.add(backButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        coursePanel.add(courseContainer, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        courseContainer.setViewportView(courseTable);
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
        return coursePanel;
    }

}
