package gui;

import org.checkerframework.checker.units.qual.C;
import utils.GradedClass;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainFrame extends JFrame {

    private JPanel panelContainer;
    private CardLayout cl;
    private UserLogin userLogin;
    private CourseSelection courseSelection;

    public MainFrame() {
        super("Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

//        panelContainer.add(userLogin, "loginPage");
//        panelContainer.add(courseSelection, "courseSelectionPage");

        cl = (CardLayout) panelContainer.getLayout();

        userLogin.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                boolean isAuthenticated = (boolean) evt.getNewValue();
                if (isAuthenticated) {
                    cl.show(panelContainer, "courseSelectPage");
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

        courseSelection.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("CourseSelected")) {
                    System.out.println("notified main");
                    System.out.println(panelContainer.getComponentCount());
                    panelContainer.add(((GradedClass)evt.getNewValue()).toString(), new AssignmentSelection((GradedClass) evt.getNewValue()));
                    cl.addLayoutComponent(((GradedClass)evt.getNewValue()).toString(), new AssignmentSelection((GradedClass) evt.getNewValue()));
                    System.out.println(((GradedClass)evt.getNewValue()).toString());
                    panelContainer.revalidate();
                    panelContainer.updateUI();
                    cl = (CardLayout) panelContainer.getLayout();
                    System.out.println(panelContainer.getComponentCount());
                    cl.show(panelContainer, ((GradedClass)evt.getNewValue()).toString());
                    pack();
                }
            }
        });
    }


    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        System.out.println(frame.panelContainer.getComponentCount());
        frame.setContentPane(frame.panelContainer);
        frame.setBounds(300, 300, 800, 600);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
//        userLogin = new UserLogin();
//        courseSelection = new CourseSelection();
        panelContainer = new JPanel(new CardLayout());
    }
}
