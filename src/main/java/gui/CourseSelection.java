package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CourseSelection extends JPanel {
    private JPanel coursePanel;
    private JButton addCourseButton;
    private JButton editCourseButton;
    private JButton logoutButton;
    private JPanel courseActions;
    private JPanel courseTiles;
    private JPanel spacer;
    private JLabel semester;

    private final List<CourseTile> tiles;
    private boolean isLoggedIn;

    private int count = 0;

    public CourseSelection() {

        this.isLoggedIn = true;
        tiles = new ArrayList<CourseTile>();
        System.out.println(State.selectedSemester);
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                addCourse("CS611", "A" + count);
                count ++;
                System.out.println(tiles.size() + " courses added");

                courseTiles.removeAll();

                for (CourseTile ct : tiles) {
                    courseTiles.add(ct.getTilePanel());
//                    courseTiles.add(new JLabel("This is a Course"));
                }

                courseTiles.revalidate();
                courseTiles.updateUI();
                firePropertyChange("GUIupdate", 0, 0);
            }
        });
        editCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(tiles.size() + " courses left");

                if (tiles.size() > 0) {
                    deleteCourse(0);
                }

                courseTiles.revalidate();
                courseTiles.updateUI();
                firePropertyChange("GUIupdate", 0, 0);

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loggout();
            }
        });

    }

    private void addCourse(String name, String sec) {
        CourseTile ct = new CourseTile(name, sec, tiles.size());
        ct.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("notified course selection page");
                if (evt.getPropertyName().equals("isSelected")) {
                    firePropertyChange("CourseSelected", null, tiles.get((int) evt.getNewValue()));
                }
            }
        });
        tiles.add(ct);
    }

    private void deleteCourse(int idx) {
        CourseTile ct = tiles.remove(idx);
        courseTiles.remove(ct.getTilePanel());
    }

    private void loggout() {
        this.isLoggedIn = false;
        firePropertyChange("isLoggedIn", true, false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        courseTiles = new JPanel(new GridLayout(0,4));
        courseTiles.setBorder(new EmptyBorder(0,30,0,0));
    }

    public void setSemesterLabel(String semesterLabel) {
        semester.setText(semesterLabel);
    }
}
