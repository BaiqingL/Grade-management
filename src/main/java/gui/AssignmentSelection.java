package gui;

import utils.Assignment;
import utils.GradedClass;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AssignmentSelection extends JPanel {
    private JPanel AssignmentPanel;
    private JPanel spacer;
    private JPanel AssignmentTile;
    private JPanel AssignmentAction;
    private JButton addAssignmentButton;
    private JButton logoutButton;
    private JButton editAssignmentButton;
    private JTable Assignments;

    private DefaultTableModel model;


    private boolean isLoggedIn;

    private GradedClass course;

    public AssignmentSelection(GradedClass course) {
        //TODO: overload constructor to load data from csv

        this.isLoggedIn = true;
        this.course = course;
        initTable();

        Assignments.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(Assignments.getValueAt(Assignments.getSelectedRow(), 0).toString());
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
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loggout();
            }
        });
        this.setVisible(true);
        System.out.println("Assignment Selection page created");
    }

    private void initTable() {
        model = new DefaultTableModel(getValue(), getHeader());
        Assignments = new JTable(model);
    }

    private void loggout() {
        this.isLoggedIn = false;
        firePropertyChange("isLoggedIn", true, false);
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
            values[i] = new String[] {a.getName(), a.getAssignedDate().toString(), a.getDueDate().toString(), "num_submission"};
        }

        return values;
    }

    private String[] getHeader() {
        String[] header = {"Name", "Released Date", "Due Date", "Submissions"};
        return header;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        AssignmentTile = new JPanel(new ScrollPaneLayout());
    }
}
