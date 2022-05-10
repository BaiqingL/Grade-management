package gui;

import utils.CSVWriter;
import utils.ClassPathTuple;
import utils.GradedClass;
import utils.Semester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ContainerAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class MainFrame extends JFrame {

    private final State state;
    public JPanel panelContainer;
    private CardLayout cl;
    private UserLogin userLogin;
    private CourseSelection courseSelection;
    private SemesterSelection semesterSelection;
    private AssignmentSelection assignmentSelection;

    private Assignment assignment;

    private LetterGrades letterGrades;

    private GradedClass course;
    private int assignmentIdx;

    public MainFrame() {
        super("Grade Calculator");

        $$$setupUI$$$();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        this.state = new State();
        this.setContentPane(panelContainer);
        this.setBounds(300, 300, 800, 600);
        this.setVisible(true);


        cl = (CardLayout) panelContainer.getLayout();

        userLogin.addPropertyChangeListener(evt -> {
            boolean isAuthenticated = (boolean) evt.getNewValue();
            if (isAuthenticated) {
                if (state.getSelectedSemester() == null) {
                    if (!state.getSemesters().isEmpty()) {
                        semesterSelection.setSemestersList(state.getSemesters());
                    }
                    cl.show(panelContainer, "semesterSelectionPage");
                } else {
                    cl.show(panelContainer, "courseSelectPage");
                }
            }
        });

        semesterSelection.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("semesterSelected")) {
                    state.setSelectedSemester((Semester) evt.getNewValue());
                    courseSelection.setSemesterLabel(state.getSelectedSemester().toString());
                    try {
                        courseSelection.populateSemesterCourse(state.getSelectedSemester().getCourses(), state.getSelectedSemester().getCoursePaths());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cl.show(panelContainer, "courseSelectPage");

                } else if (evt.getPropertyName().equals("newSemesterAdded")) {
                    state.addSemester((Semester) evt.getNewValue());
                    try {
                        CSVWriter.writeSemesters(state);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    semesterSelection.setSemestersList(state.getSemesters());
                    courseSelection.setSemesterLabel(state.getSelectedSemester().toString());
                    try {
                        courseSelection.populateSemesterCourse(state.getSelectedSemester().getCourses(), state.getSelectedSemester().getCoursePaths());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cl.show(panelContainer, "courseSelectPage");

                }
            }
        });

        semesterSelection.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("semestersLoaded")) {
                    state.getSemesters().addAll((Collection<? extends Semester>) evt.getNewValue());
                }
            }
        });

        semesterSelection.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("semestersFilePath")) {
                    state.setSemestersFilePath((String) evt.getNewValue());
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
        panelContainer.addContainerListener(new ContainerAdapter() {
        });

        courseSelection.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("addedNewCourse")) {
                    ClassPathTuple newTuple = (ClassPathTuple) evt.getNewValue();
                    state.getSelectedSemester().addCourse(newTuple);
                    try {
                        CSVWriter.writeSemesters(state);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Course " + newTuple.getClassName() + " is added to semester " + state.getSelectedSemester().toString());
                }
            }
        });

        courseSelection.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("deleteCourse")) {
                    ClassPathTuple toDelete = (ClassPathTuple) evt.getNewValue();
                    state.getSelectedSemester().deleteCourse(toDelete);
                    try {
                        CSVWriter.writeSemesters(state);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Course " + toDelete.getClassName() + " deleted from semester " + state.getSelectedSemester().toString());
                }
            }
        });

        courseSelection.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("previousPage")) {
                    cl.show(panelContainer, "semesterSelectionPage");
                    panelContainer.remove(courseSelection);
                    panelContainer.revalidate();
                    panelContainer.updateUI();
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
                    assignmentSelection.setLayout(new GridLayout(0, 1));
                    assignmentSelection.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (evt.getPropertyName().equals("AssignmentSelected")) {
                                System.out.println("notified main for create assignment page");
                                assignmentIdx = (int) evt.getNewValue();
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

                            if (evt.getPropertyName().equals("modifiedCourse")) {
                                course = (GradedClass) evt.getNewValue();
                                utils.Assignment a = course.getStudents().get(0).getAssignments().get(6);
                                System.out.println("in modifed Course listener: " + a);
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
//        userLogin = new UserLogin();
//        courseSelection = new CourseSelection();
        panelContainer = new JPanel(new CardLayout());
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
        panelContainer.setLayout(new CardLayout(0, 0));
        userLogin = new UserLogin();
        panelContainer.add(userLogin.$$$getRootComponent$$$(), "loginPage");
        courseSelection = new CourseSelection();
        panelContainer.add(courseSelection.$$$getRootComponent$$$(), "courseSelectPage");
        semesterSelection = new SemesterSelection();
        panelContainer.add(semesterSelection.$$$getRootComponent$$$(), "semesterSelectionPage");
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelContainer;
    }

}
