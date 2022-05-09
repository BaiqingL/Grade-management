package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import utils.Assignment;
import utils.CSVWriter;
import utils.GradedClass;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
    private JButton editAssignmentButton;
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
            }
        });

        // Configure add assignment button action
        addAssignmentButton.addActionListener(e -> model.addRow(createAssignment()));

        // Configure the edit assignment button action
        editAssignmentButton.addActionListener(e -> {
            int removeAt = Assignments.getSelectedRow();
            // Confirm the removal
            int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this class?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                // Remove the assignment, and update the table
                course.removeAssignment(removeAt);
                // Update the table
                model.removeRow(removeAt);
                Assignments = new JTable(model);
                Assignments.updateUI();
                AssignmentTile.revalidate();
                AssignmentTile.updateUI();
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
                String filePath = CourseSelection.getFilePath();
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

    // Dummy method to create an assignment
    private String[] createAssignment() {
        return new String[]{"Assignment 3", "March 19, 2022", "April 19, 2022", "198"};
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
        editAssignmentButton = new JButton();
        editAssignmentButton.setText("Delete Assignment");
        AssignmentAction.add(editAssignmentButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
