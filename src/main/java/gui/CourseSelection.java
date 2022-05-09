package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import utils.ClassPathTuple;
import utils.CSVReader;
import utils.GradedClass;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static org.codehaus.plexus.util.StringUtils.isNumeric;

public class CourseSelection extends JPanel {
    private JPanel coursePanel;
    private JButton addCourseButton;
    private JButton deleteCourseButton;
    private JButton logoutButton;
    private JPanel courseActions;
    private JPanel courseTiles;
    private JPanel spacer;
    private JLabel semester;

    private final List<CourseTile> tiles;
    private boolean isLoggedIn;

    private List<GradedClass> courses;

    private int count = 0;

    private static final List<ClassPathTuple> CLASS_PATH_TUPLE = new ArrayList<>();

    public CourseSelection() {

        this.isLoggedIn = true;
        tiles = new ArrayList<>();
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
            count++;
            System.out.println(tiles.size() + " courses added");

            courseTiles.removeAll();

            for (CourseTile ct : tiles) {
                courseTiles.add(ct.getTilePanel());
            }

            courseTiles.revalidate();
            courseTiles.updateUI();
            firePropertyChange("GUIupdate", 0, 0);
        });
        deleteCourseButton.addActionListener(e -> {
            System.out.println(tiles.size() + " courses left");

            if (tiles.size() > 0) {
                String selected = JOptionPane.showInputDialog(null, "Select a course to delete: \n" + getCoursesAndIndex(), "Delete Course", JOptionPane.QUESTION_MESSAGE);
                // Make sure the input is a number
                try {
                    while (selected != null && !isNumeric(selected) || Integer.parseInt(Objects.requireNonNull(selected)) > tiles.size()) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                        selected = JOptionPane.showInputDialog(null, "Select a course to delete: \n" + getCoursesAndIndex(), "Delete Course", JOptionPane.QUESTION_MESSAGE);
                    }
                    deleteCourse(Integer.parseInt(selected) - 1);
                } catch (Exception ignored) {

                }
            } else {
                JOptionPane.showMessageDialog(null, "No courses to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }

            courseTiles.revalidate();
            courseTiles.updateUI();
            firePropertyChange("GUIupdate", 0, 0);

        });
        logoutButton.addActionListener(e -> loggout());

    }

    private String getCoursesAndIndex() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < courses.size(); i++) {
            sb.append(i + 1).append(": ").append(courses.get(i).getClassName()).append("\n");
        }
        return sb.toString();
    }

    public static String getCourseFilePath(String assignmentName) {
        for (ClassPathTuple tuple : CLASS_PATH_TUPLE) {
            if (tuple.getClassName().equals(assignmentName)) {
                return tuple.getPath();
            }
        }
        return null;
    }

    private void addCourse(String filePath) {
        try {
            GradedClass course = CSVReader.loadCSV(filePath);
            courses.add(course);
            CLASS_PATH_TUPLE.add(new ClassPathTuple(course.getClassName(), filePath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Cannot read from" + filePath);
        }
        CourseTile ct = new CourseTile(courses.get(courses.size() - 1).toString(), tiles.size());
        ct.addPropertyChangeListener(evt -> {
            System.out.println("notified course selection page");
            if (evt.getPropertyName().equals("isSelected")) {
                firePropertyChange("CourseSelected", null, courses.get((int) evt.getNewValue()));
            }
        });

        tiles.add(ct);
    }

    private void deleteCourse(int idx) {
        CourseTile ct = tiles.remove(idx);
        courseTiles.remove(ct.getTilePanel());
        courses.remove(idx);
    }

    private void loggout() {
        this.isLoggedIn = false;
        firePropertyChange("isLoggedIn", true, false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        courseTiles = new JPanel(new GridLayout(0, 4));
        courseTiles.setBorder(new EmptyBorder(0, 30, 0, 0));
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
        coursePanel.add(courseTiles, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
        courseActions.setLayout(new GridLayoutManager(1, 3, new Insets(0, 30, 0, 30), -1, -1));
        panel1.add(courseActions, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addCourseButton = new JButton();
        addCourseButton.setText("Add Course");
        courseActions.add(addCourseButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteCourseButton = new JButton();
        deleteCourseButton.setText("Delete Course");
        courseActions.add(deleteCourseButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logoutButton = new JButton();
        logoutButton.setText("Logout");
        courseActions.add(logoutButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
