package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseTile {
    private JButton courseTile;
    private JPanel tilePanel;

    private boolean isSelected;

    private final String courseName;
    private final String courseSection;

    public CourseTile(String name, String sec) {
        super();
        this.courseName = name;
        this.courseSection = sec;
        this.isSelected = false;

        courseTile.setText(this.courseName + " " + this.courseSection);

        courseTile.setVisible(true);

        tilePanel.setSize(200, 150);

        tilePanel.setVisible(true);

//        tilePanel.setBackground(Color.BLACK);

        courseTile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("selected course" + courseName + " " + courseSection);
                setSelected();
            }
        });
    }

    private void setSelected() {

        tilePanel.firePropertyChange("isSelected", this.isSelected, !this.isSelected);
        this.isSelected = !this.isSelected;

    }

    public JPanel getTilePanel() {
        return this.tilePanel;
    }

}
