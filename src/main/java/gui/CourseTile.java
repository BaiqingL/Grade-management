package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseTile extends JPanel {
    private JButton courseTile;
    private JPanel tilePanel;

    private boolean isSelected;

    private String courseName;

    private int idx;
    private String courseSection;

    public CourseTile(String name, String sec, int idx) {
        super();
        this.courseName = name;
        this.courseSection = sec;
        this.idx = idx;
        this.isSelected = false;

        courseTile.setText(this.courseName + " " + this.courseSection);

        courseTile.setVisible(true);

        tilePanel.setSize(200, 150);

        tilePanel.setVisible(true);

//        tilePanel.setBackground(Color.BLACK);

        courseTile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("selected course " + courseName + " " + courseSection);
                setSelected();
            }
        });
    }

    private void setSelected() {

        this.firePropertyChange("isSelected", -1, idx);
        this.isSelected = !this.isSelected;

    }

    public JPanel getTilePanel() {
        return this.tilePanel;
    }

    public String toString() {
        return this.courseName + " " + this.courseSection;
    }

}
