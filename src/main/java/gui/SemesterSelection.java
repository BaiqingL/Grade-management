package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import utils.Semester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class SemesterSelection extends JFrame {
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
        $$$setupUI$$$();
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
                firePropertyChange("semesterSelected", null, selectedSemester);
            }
        });

        addSemButton = new JButton();
        addSemButton.setText("Add");
        addSemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assert !semester.getText().equals("") && !year.getText().equals("");
                Semester newSem = new Semester(year.getText(), semester.getText(), new ArrayList<>());
                firePropertyChange("newSemesterAdded", null, newSem);
                firePropertyChange("semesterSelected", null, newSem);
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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        semesters = new JComboBox();
        panel2.add(semesters, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        title = new JLabel();
        title.setHorizontalAlignment(0);
        title.setHorizontalTextPosition(0);
        title.setText("");
        panel2.add(title, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        selectSemesterBtn.setText("Select");
        panel2.add(selectSemesterBtn, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 30), null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("New Semester");
        panel2.add(label1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        semester = new JTextField();
        semester.setToolTipText("Semester");
        panel2.add(semester, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        year = new JTextField();
        year.setToolTipText("Year");
        panel2.add(year, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addSemButton.setText("Add");
        panel2.add(addSemButton, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
