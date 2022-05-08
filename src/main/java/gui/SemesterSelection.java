package gui;

import org.springframework.core.task.SimpleAsyncTaskExecutor;
import utils.Semester;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class SemesterSelection extends JFrame{
    private JComboBox<Semester> semesters;
    private JPanel panel1;
    private JLabel title;
    private JButton selectSemesterBtn;
    private JTextField semester;
    private JTextField year;
    private JButton addSemButton;
    private List<Semester> semestersList;

    private Semester selectedSemester = null;

    public SemesterSelection() {
        super("Semester Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();


        title.setText("Select semester");
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        selectSemesterBtn = new JButton();
        selectSemesterBtn.setText("Select");
        selectSemesterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firePropertyChange("semesterSelected",null,selectedSemester);
            }
        });

        addSemButton = new JButton();
        addSemButton.setText("Add");
        addSemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assert !semester.getText().equals("") && !year.getText().equals("");
                Semester newSem = new Semester(year.getText(), semester.getText(),new ArrayList<>());
                firePropertyChange("newSemesterAdded",null,newSem);
                firePropertyChange("semesterSelected",null,newSem);
            }
        });
    }

    public void setSemestersList(List<Semester> semestersList) {
        this.semestersList = semestersList;
        selectedSemester = semestersList.get(0);
        for (Semester sem : semestersList) {
            semesters.addItem(sem);
        }
        semesters.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedSemester = (Semester) e.getItem();
            }
        });

    }
}
