package gui;

import utils.CSVWriter;
import utils.GradedClass;
import utils.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

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
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select output location");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // disable the "All files" option.
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String directory = String.valueOf(chooser.getSelectedFile());
            CSVWriter.writeFinalGrades(directory, course.getClassName(), getHeaders(), getValues());
        }
        else {
            System.out.println("No Selection ");
        }
    }

    private String[] getHeaders() {
        String[] headers = {"Name", "BUID", "Letter Grade"};
        return headers;
    }

    private String[][] getValues() {
        String[][] values = new String[course.getStudents().size()][3];
        List<Student> students = course.getStudents();
        for (int i = 0; i < course.getStudents().size(); i++) {
            Student s = students.get(i);
            values[i] = new String[] {s.getName(), String.valueOf(s.getBUID()), s.getLetterGrade()};
        }
        return values;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        letterGradesContainer = new JScrollPane();
        model = new DefaultTableModel(getValues(), getHeaders());
        letterGrades = new JTable(model);
    }
}
