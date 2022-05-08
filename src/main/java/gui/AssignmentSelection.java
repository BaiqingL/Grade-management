package gui;

import utils.Assignment;
import utils.GradedClass;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AssignmentSelection extends JPanel {
    private JPanel AssignmentPanel;
    private JPanel spacer;
    private JScrollPane AssignmentTile;
    private JPanel AssignmentAction;
    private JButton addAssignmentButton;
    private JButton logoutButton;
    private JButton editAssignmentButton;
    private JTable Assignments;
    private JButton showLetterGradeButton;
    private JLabel courseName;

    private DefaultTableModel model;


    private boolean isLoggedIn;

    private GradedClass course;

    public AssignmentSelection(GradedClass course) {
        //TODO: overload constructor to load data from csv

        this.isLoggedIn = true;
        this.course = course;

        courseName.setText(course.toString());

        Assignments.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // prevent the following function being called twice
                if (!e.getValueIsAdjusting() && Assignments.getSelectedRow() != -1 ) {
                    firePropertyChange("AssignmentSelected", null, Assignments.getSelectedRow());
                }
            }
        });
        addAssignmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(createAssignment());
            }
        });
        editAssignmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int removeAt = Assignments.getSelectedRow();
                if (removeAt >= 0) {
                    model.removeRow(removeAt);
                }
                Assignments = new JTable(model);
                Assignments.updateUI();
                AssignmentTile.revalidate();
                AssignmentTile.updateUI();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firePropertyChange("previousPage", null, null);
            }
        });

        this.setVisible(true);
        this.add(AssignmentPanel);
        System.out.println("Assignment Selection page created");
        showLetterGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firePropertyChange("LetterGradeSelected", null, null);
            }
        });
    }

    private String[] createAssignment() {
        String[] value = {"Assignment 3", "March 19, 2022", "April 19, 2022", "198"};
        return value;
    }

    private String[][] getValue() {
        String[][] values = new String[course.getAssignments().size()][4];
        List<Assignment> assignments = course.getAssignments();
        for (int i = 0; i < assignments.size(); i++) {
            Assignment a = assignments.get(i);
            values[i] = new String[] {a.getName(), a.getAssignedDate().toString(), a.getDueDate().toString(), String.valueOf(course.getAmountOfStudentsSubmittedAssignment(i))};
        }

        return values;
    }

    private String[] getHeader() {
        String[] header = {"Name", "Released Date", "Due Date", "Submissions"};
        return header;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        AssignmentTile = new JScrollPane();
        model = new DefaultTableModel(getValue(), getHeader());
        Assignments = new JTable(model);
    }
}
