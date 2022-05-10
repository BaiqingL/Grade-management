package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import utils.Assignment;
import utils.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static javax.swing.JOptionPane.showMessageDialog;
import static org.codehaus.plexus.util.StringUtils.isNumeric;

public class AssignmentSelection extends JPanel {
    private final boolean isLoggedIn;
    private final GradedClass course;
    private JPanel AssignmentPanel;
    private JScrollPane AssignmentTile;
    private JPanel AssignmentAction;
    private JButton addAssignmentButton;
    private JButton logoutButton;
    private JButton deleteAssignmentButton;
    private JTable Assignments;
    private JButton showLetterGradeButton;
    private JLabel courseName;
    private JButton saveChanges;
    private JButton changeWeightsButton;
    private DefaultTableModel model;

    public AssignmentSelection(GradedClass course) {
        //TODO: overload constructor to load data from csv

        this.isLoggedIn = true;
        this.course = course;

        // IntelliJ generated code
        $$$setupUI$$$();

        // Set the text to be used for the course title
        courseName.setText("Current course: " + course.toString());

        Assignments.getSelectionModel().addListSelectionListener(e -> {
            // prevent the following function being called twice
            if (!e.getValueIsAdjusting() && Assignments.getSelectedRow() != -1) {
                firePropertyChange("AssignmentSelected", null, Assignments.getSelectedRow());
                Assignments.clearSelection();
            }
        });

        // Configure add assignment button action
        addAssignmentButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Select a CSV file");
            String filePath = "";
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                filePath = fileChooser.getSelectedFile().getAbsolutePath();
            }

            createAssignment(filePath);

            updateTable();

        });

        // Configure the edit assignment button action
        deleteAssignmentButton.addActionListener(e -> {
            if (Assignments.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No assignments to delete");
            } else {
                String selected = JOptionPane.showInputDialog(null, "Select a course to delete: \n" + getAssignmentsAndIndex(), "Delete Course", JOptionPane.QUESTION_MESSAGE);
                // Make sure the input is a number
                try {
                    while (selected != null && !isNumeric(selected) || Integer.parseInt(Objects.requireNonNull(selected)) > Assignments.getRowCount() || Integer.parseInt(Objects.requireNonNull(selected)) == 0) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                        selected = JOptionPane.showInputDialog(null, "Select a course to delete: \n" + getAssignmentsAndIndex(), "Delete Course", JOptionPane.QUESTION_MESSAGE);
                    }
                    course.removeAssignment(Integer.parseInt(selected) - 1);
                    updateTable();

                } catch (Exception ignored) {

                }
            }
        });

        // Log out and return to previous page
        logoutButton.addActionListener(e -> firePropertyChange("previousPage", null, null));

        this.setVisible(true);
        this.add(AssignmentPanel);

        // Go to the letter grade page
        showLetterGradeButton.addActionListener(e -> firePropertyChange("LetterGradeSelected", null, null));

        // Save changes, and write to original CSV
        saveChanges.addActionListener(e -> {
            firePropertyChange("SaveChanges", null, null);
            try {
                String filePath = CourseSelection.getCourseFilePath(course.getClassName());
                CSVWriter.writeGradedClass(course, filePath);
                showMessageDialog(null, "Class saved successfully");
            } catch (IOException ex) {
                showMessageDialog(null, ex.getMessage());
                throw new RuntimeException(ex);
            }
        });

        // Change weights and prompt user to enter new ones
        changeWeightsButton.addActionListener(e -> {
            firePropertyChange("ChangeWeights", null, null);
            for (Assignment assignment : course.getAssignments()) {
                String newWeight = JOptionPane.showInputDialog("Enter the new weight for " + assignment.getName() + "\nCurrent weight: " + assignment.getWeight());
                while (newWeight != null && !isNumeric(newWeight)) {
                    showMessageDialog(null, "Invalid input");
                    newWeight = JOptionPane.showInputDialog("Enter the new weight for " + assignment.getName() + "\nCurrent weight: " + assignment.getWeight());
                }
                if (newWeight != null) {
                    assignment.setWeight(Integer.parseInt(newWeight));
                } else {
                    break;
                }
            }
        });
    }

    private String getAssignmentsAndIndex() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < course.getAssignments().size(); i++) {
            sb.append(i + 1).append(". ").append(course.getAssignments().get(i).getName()).append("\n");
        }
        return sb.toString();
    }

    // method to create an assignment
    private void createAssignment(String filePath) {
        try {
            GradedClass temp = CSVReader.loadCSV(filePath);
            Assignment assignment = temp.getAssignments().get(0);
            course.addJustAssignment(assignment);
            for (Student s : temp.getStudents()) {
                Student target = course.getStudentByBUID(s.getBUID());
                target.addAssignment(s.getAssignments().get(0));
            }
        } catch (IOException ignored) {

        }
        firePropertyChange("modifiedCourse", null, course);
    }

    private void updateTable() {
        // Refresh the table
        model.setRowCount(0);

        for (int i = 0; i < course.getAssignments().size(); i++) {
            Assignment a = course.getAssignments().get(i);
            model.addRow(new String[]{a.getName(), String.valueOf(a.getAssignedDate()), String.valueOf(a.getDueDate()), String.valueOf(course.getAmountOfStudentsSubmittedAssignment(i)), String.valueOf(a.getSubmissionDate())});
        }
    }

    // Checks value to be shown in tables
    private String[][] getValue() {
        String[][] values = new String[course.getAssignments().size()][4];
        List<Assignment> assignments = course.getAssignments();
        for (int i = 0; i < assignments.size(); i++) {
            Assignment a = assignments.get(i);
            values[i] = new String[]{a.getName(), a.getAssignedDate().toString(), a.getDueDate().toString(), String.valueOf(course.getAmountOfStudentsSubmittedAssignment(i))};
        }

        return values;
    }

    private String[] getHeader() {
        return new String[]{"Name", "Released Date", "Due Date", "Submissions"};
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        AssignmentTile = new JScrollPane();
        model = new DefaultTableModel(getValue(), getHeader());
        Assignments = new JTable(model);
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
        AssignmentPanel = new JPanel();
        AssignmentPanel.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1));
        AssignmentAction = new JPanel();
        AssignmentAction.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 30), -1, -1));
        AssignmentPanel.add(AssignmentAction, new GridConstraints(3, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addAssignmentButton = new JButton();
        addAssignmentButton.setText("Add Assignment");
        AssignmentAction.add(addAssignmentButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteAssignmentButton = new JButton();
        deleteAssignmentButton.setText("Delete Assignment");
        AssignmentAction.add(deleteAssignmentButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logoutButton = new JButton();
        logoutButton.setText("Back");
        AssignmentAction.add(logoutButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showLetterGradeButton = new JButton();
        showLetterGradeButton.setText("Show Letter Grade");
        AssignmentAction.add(showLetterGradeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        saveChanges = new JButton();
        saveChanges.setText("Save Changes");
        AssignmentAction.add(saveChanges, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        changeWeightsButton = new JButton();
        changeWeightsButton.setText("Change Weights");
        AssignmentAction.add(changeWeightsButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        AssignmentPanel.add(AssignmentTile, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        AssignmentTile.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        Assignments.setFillsViewportHeight(false);
        AssignmentTile.setViewportView(Assignments);
        final Spacer spacer1 = new Spacer();
        AssignmentPanel.add(spacer1, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        courseName = new JLabel();
        Font courseNameFont = this.$$$getFont$$$("SF Pro Display", Font.BOLD, 26, courseName.getFont());
        if (courseNameFont != null) courseName.setFont(courseNameFont);
        courseName.setText("Label");
        AssignmentPanel.add(courseName, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        AssignmentPanel.add(spacer2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        AssignmentPanel.add(spacer3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        AssignmentPanel.add(spacer4, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
        return AssignmentPanel;
    }

}
