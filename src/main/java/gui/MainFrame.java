package gui;

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

    public MainFrame() {
        super("Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        cl = (CardLayout) panelContainer.getLayout();

        userLogin.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                boolean isAuthenticated = (boolean) evt.getNewValue();
                if (isAuthenticated) {
                    cl.show(panelContainer, "semesterSelectionPage");
                }
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
        semesterSelection.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                boolean selected = (boolean) evt.getNewValue();
                if (selected) {
                    courseSelection.setSemesterLabel(State.selectedSemester.toString());
                    cl.show(panelContainer, "courseSelectPage");
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


    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setContentPane(frame.panelContainer);
        frame.setBounds(300, 300, 800, 600);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        panelContainer = new JPanel(new CardLayout());
    }
}
