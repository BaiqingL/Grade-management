package gui;

import utils.Semester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ContainerAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainFrame extends JFrame {

    public JPanel panelContainer;
    private CardLayout cl;
    private UserLogin userLogin;
    private CourseSelection courseSelection;
    private SemesterSelection semesterSelection;
    private State state;

    public MainFrame() {
        super("Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        this.state = new State();

        cl = (CardLayout) panelContainer.getLayout();

        userLogin.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                boolean isAuthenticated = (boolean) evt.getNewValue();
                if (isAuthenticated) {
                    semesterSelection.setSemestersList(state.getSemesters());
                    cl.show(panelContainer, "semesterSelectionPage");
                }
            }
        });
        semesterSelection.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("semesterSelected")) {
                    state.setSelectedSemester((Semester) evt.getNewValue());
                } else if (evt.getPropertyName().equals("newSemesterAdded")) {
                    state.addSemester((Semester) evt.getNewValue());
                    state.setSelectedSemester((Semester) evt.getNewValue());
                }
                courseSelection.setSemesterLabel(state.getSelectedSemester().toString());
                cl.show(panelContainer, "courseSelectPage");
            }
        });

        courseSelection.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("isLoggedIn")) {
                    boolean isLoggedIn = (boolean) evt.getNewValue();
                    if (!isLoggedIn) {
                            userLogin.setLogout();
                        cl.show(panelContainer, "loginPage");
                    }
                }

                if (evt.getPropertyName().equals("GUIupdate")) {
                    pack();
                }
            }
        });
        panelContainer.addContainerListener(new ContainerAdapter() {
        });

        courseSelection.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("CourseSelected")) {
                    System.out.println("notified main");
                    System.out.println(panelContainer.getComponents());
                    panelContainer.add(((CourseTile)evt.getNewValue()).toString(), new AssignmentSelection());
                    cl.addLayoutComponent(((CourseTile)evt.getNewValue()).toString(), new AssignmentSelection());
                    System.out.println(((CourseTile)evt.getNewValue()).toString());
                    panelContainer.revalidate();
                    panelContainer.updateUI();
                    cl = (CardLayout) panelContainer.getLayout();
                    System.out.println(panelContainer.getComponents());
                    cl.show(panelContainer, ((CourseTile)evt.getNewValue()).toString());
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        panelContainer = new JPanel(new CardLayout());
    }
}
