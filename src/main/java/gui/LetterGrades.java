package gui;

import utils.CSVWriter;
import utils.GradedClass;
import utils.Student;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LetterGrades extends JPanel {
    private JButton backButton;
    private JButton exportButton;
    private JTable letterGrades;
    private JScrollPane letterGradesContainer;
    private JPanel letterGradeActions;
    private JPanel panelContainer;
    private JLabel courseName;

    private DefaultTableModel model;

    private GradedClass course;

    public LetterGrades(GradedClass course) {
        this.course = course;
        $$$setupUI$$$();
        courseName.setText(course.toString());
        this.add(panelContainer);
        backButton.addActionListener(e -> firePropertyChange("previousPage", null, null));
        exportButton.addActionListener(e -> {
            try {
                exportGrades(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void exportGrades(ActionEvent e) throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("Select output location");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // disable the "All files" option.
        chooser.setAcceptAllFileFilterUsed(false);
        // set chooser to show select instead of open a file
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        // remove files of type option
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory();
            }

            @Override
            public String getDescription() {
                return null;
            }
        });
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String directory = String.valueOf(chooser.getSelectedFile());
            CSVWriter.writeFinalGrades(directory, course.getClassName(), getHeaders(), getValues());
            // Show success message
            String fileOutName = directory + "/" + course.getClassName() + ".csv";
            JOptionPane.showMessageDialog(this,
                    "Grades exported to: \n" + fileOutName,
                    "Success!",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("No Selection ");
        }
    }

    private String[] getHeaders() {
        return new String[]{"Name", "BUID", "Letter Grade"};
    }

    private String[][] getValues() {
        String[][] values = new String[course.getStudents().size()][3];
        List<Student> students = course.getStudents();
        for (int i = 0; i < course.getStudents().size(); i++) {
            Student s = students.get(i);
            values[i] = new String[]{s.getName(), String.valueOf(s.getBUID()), s.getLetterGrade()};
        }
        return values;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        letterGradesContainer = new JScrollPane();
        model = new DefaultTableModel(getValues(), getHeaders());
        letterGrades = new JTable(model);
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
        panelContainer = new JPanel();
        panelContainer.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 30, 0, 30), -1, -1));
        letterGradeActions = new JPanel();
        letterGradeActions.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelContainer.add(letterGradeActions, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        exportButton = new JButton();
        exportButton.setText("Export");
        letterGradeActions.add(exportButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setText("Back");
        letterGradeActions.add(backButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        letterGradeActions.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        panelContainer.add(letterGradesContainer, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        letterGradesContainer.setViewportView(letterGrades);
        courseName = new JLabel();
        Font courseNameFont = this.$$$getFont$$$("Century Gothic", Font.BOLD, 26, courseName.getFont());
        if (courseNameFont != null) courseName.setFont(courseNameFont);
        courseName.setText("Label");
        panelContainer.add(courseName, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return panelContainer;
    }
}
