package gui;

import utils.Semester;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class SemesterSelection extends JFrame{
    private JComboBox<Semester> semesters;
    private JPanel panel1;
    private JLabel title;
    private JButton selectSemesterBtn;

    private Semester selectedSemester = null;

    public SemesterSelection() {
        super("Semester Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        State state = new State();

        title.setText("Select semester");


        List<Semester> semesterList = state.getSemesters();
        for (Semester sem : semesterList) {
            semesters.addItem(sem);
        }
        semesters.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedSemester = (Semester) e.getItem();
            }
        });




    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        selectSemesterBtn = new JButton();
        selectSemesterBtn.setText("Select");
        selectSemesterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State.selectedSemester = selectedSemester;
                firePropertyChange("semesterSelected",false,true);
            }
        });

    }
}
