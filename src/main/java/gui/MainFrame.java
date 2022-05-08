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

    private AssignmentSelection assignmentSelection;

    private Assignment assignment;

    private LetterGrades letterGrades;

    private GradedClass course;
    private int assignmentIdx;

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
                    course = (GradedClass) evt.getNewValue();
                    assignmentSelection = new AssignmentSelection(course);
                    assignmentSelection.setVisible(true);
                    assignmentSelection.setLayout(new GridLayout(0,1));
                    assignmentSelection.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (evt.getPropertyName().equals("AssignmentSelected")) {
                                System.out.println("notified main for create assignment page");
                                assignmentIdx = (int)evt.getNewValue();
                                System.out.println(assignmentIdx);
                                assignment = new Assignment(course, assignmentIdx);
                                assignment.setVisible(true);
                                assignment.setLayout(new GridLayout(0, 1));
                                assignment.addPropertyChangeListener(new PropertyChangeListener() {
                                    @Override
                                    public void propertyChange(PropertyChangeEvent evt) {
                                        if (evt.getPropertyName().equals("previousPage")) {
                                            cl.show(panelContainer, "assignmentSelectionPage");
                                            panelContainer.remove(assignment);
                                            panelContainer.revalidate();
                                            panelContainer.updateUI();
                                        }
                                    }
                                });
                                System.out.println(assignment.getComponentCount());
                                panelContainer.add("assignment", assignment);
                                panelContainer.revalidate();
                                panelContainer.updateUI();
                                cl = (CardLayout) panelContainer.getLayout();
                                cl.show(panelContainer, "assignment");
                            }
                        }
                    });

                    assignmentSelection.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (evt.getPropertyName().equals("previousPage")) {
                                cl.show(panelContainer, "courseSelectPage");
                                panelContainer.remove(assignmentSelection);
                                panelContainer.revalidate();
                                panelContainer.updateUI();
                            }
                        }
                    });

                    assignmentSelection.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (evt.getPropertyName().equals("LetterGradeSelected")) {
                                letterGrades = new LetterGrades(course);
                                letterGrades.setVisible(true);
                                letterGrades.setLayout(new GridLayout(0, 1));
                                letterGrades.addPropertyChangeListener(new PropertyChangeListener() {
                                    @Override
                                    public void propertyChange(PropertyChangeEvent evt) {
                                        if (evt.getPropertyName().equals("previousPage")) {
                                            cl.show(panelContainer, "assignmentSelectionPage");
                                            panelContainer.remove(letterGrades);
                                            panelContainer.revalidate();
                                            panelContainer.updateUI();
                                        }
                                    }
                                });
                                panelContainer.add("letterGradePage", letterGrades);
                                panelContainer.revalidate();
                                panelContainer.updateUI();
                                cl = (CardLayout) panelContainer.getLayout();
                                cl.show(panelContainer, "letterGradePage");
                            }
                        }
                    });

                    panelContainer.add("assignmentSelectionPage", assignmentSelection);
                    panelContainer.revalidate();
                    panelContainer.updateUI();
                    cl = (CardLayout) panelContainer.getLayout();
                    cl.show(panelContainer, "assignmentSelectionPage");
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
